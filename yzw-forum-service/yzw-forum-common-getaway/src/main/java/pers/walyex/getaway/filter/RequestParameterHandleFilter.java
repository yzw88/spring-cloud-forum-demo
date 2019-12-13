package pers.walyex.getaway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import pers.walyex.common.core.constant.GetawayConstant;
import pers.walyex.common.core.dto.ResponseDataDTO;
import pers.walyex.common.core.enums.ResultEnum;
import pers.walyex.common.core.util.ResultUtil;
import pers.walyex.common.util.FastJsonUtil;
import pers.walyex.getaway.output.ParameterCachedBodyOutputMessage;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 参数处理过滤器
 *
 * @author Waldron Ye
 * @date 2019/12/13 21:13
 */
@Component
@Slf4j
public class RequestParameterHandleFilter extends AbstractBaseRequestFilter {

    @Override
    protected ResponseDataDTO<Mono<Void>> filterHandle(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("RequestParameterHandleFilter handle start==");
        ServerHttpRequest serverHttpRequest = exchange.getRequest();

        //目前仅支持get和post提交请求
        if (!Objects.equals(serverHttpRequest.getMethod(), HttpMethod.POST)
                && !Objects.equals(serverHttpRequest.getMethod(), HttpMethod.GET)) {
            log.info("请求类型不支持:httpMethod={}", serverHttpRequest.getMethod());
            return ResultUtil.getResult(ResultEnum.NO_SUPPORT_REQUEST_METHOD);
        }

        MediaType mediaType = exchange.getRequest().getHeaders().getContentType();
        //post请求只允许提交JSON
        if (Objects.equals(serverHttpRequest.getMethod(), HttpMethod.POST) &&
                !MediaType.APPLICATION_JSON.isCompatibleWith(mediaType)) {
            log.info("媒体类型不支持:mediaType={}", mediaType);
            return ResultUtil.getResult(ResultEnum.NO_SUPPORT_REQUEST_MEDIA);
        }

        //post请求处理
        if (Objects.equals(serverHttpRequest.getMethod(), HttpMethod.POST)) {
            log.info("post请求处理");
            ServerRequest serverRequest = ServerRequest.create(exchange, HandlerStrategies.withDefaults().messageReaders());
            Mono modifiedBody = serverRequest.bodyToMono(String.class).flatMap(requestParameter -> {
                log.info("post请求参数为:requestParameter={}", requestParameter);
                //先把请求参数放到Attributes这里
                exchange.getAttributes().put(GetawayConstant.REQUEST_PARAMETER, requestParameter);
                //这里无需传参数
                return Mono.just("{}");
            });

            BodyInserter bodyInserter = BodyInserters.fromPublisher(modifiedBody, String.class);
            HttpHeaders headers = new HttpHeaders();
            headers.putAll(exchange.getRequest().getHeaders());

            ParameterCachedBodyOutputMessage outputMessage = new ParameterCachedBodyOutputMessage(exchange, headers);
            Mono<Void> mono = bodyInserter.insert(outputMessage, new BodyInserterContext()).then(Mono.defer(() -> {
                ServerHttpRequest decorator = this.decorate(exchange, headers, outputMessage);
                return chain.filter(exchange.mutate().request(decorator).build()).then();
            }));

            return ResultUtil.getSuccessResult(mono);

        }

        //get请求处理
        log.info("get请求处理");

        URI uri = serverHttpRequest.getURI();
        MultiValueMap<String, String> multiValueMap = serverHttpRequest.getQueryParams();
        Map<String, String> map = new HashMap<>(16);
        if (!multiValueMap.isEmpty()) {
            multiValueMap.toSingleValueMap().forEach(map::put);
        }
        String requestParameter = FastJsonUtil.toJson(map);
        log.info("get请求参数为:requestParameter={}", requestParameter);
        //先把请求参数放到Attributes这里
        exchange.getAttributes().put(GetawayConstant.REQUEST_PARAMETER, requestParameter);
        URI newUri = UriComponentsBuilder.fromUri(uri)
                .build(true)
                .toUri();

        ServerHttpRequest request = exchange.getRequest().mutate().uri(newUri).build();
        Mono<Void> mono = chain.filter(exchange.mutate().request(request).build());

        return ResultUtil.getSuccessResult(mono);
    }

    @Override
    protected int getFilterOrder() {
        return -5;
    }
}
