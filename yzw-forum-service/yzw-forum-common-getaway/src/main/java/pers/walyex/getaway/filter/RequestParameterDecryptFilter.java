package pers.walyex.getaway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import pers.walyex.common.core.constant.GetawayConstant;
import pers.walyex.common.core.dto.ResponseDataDTO;
import pers.walyex.common.core.util.ResultUtil;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Objects;

/**
 * 参数解密过滤器
 *
 * @author Waldron Ye
 * @date 2019/12/13 22:23
 */
@Component
@Slf4j
public class RequestParameterDecryptFilter extends AbstractBaseRequestFilter {

    @Override
    protected ResponseDataDTO<Mono<Void>> filterHandle(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("RequestParameterDecryptFilter handle start==");
        ServerHttpRequest serverHttpRequest = exchange.getRequest();

        String requestParameter = exchange.getAttribute(GetawayConstant.REQUEST_PARAMETER);
        log.info("从Attribute获取的参数requestParameter={}", requestParameter);

        //get请求处理
        if (Objects.equals(serverHttpRequest.getMethod(), HttpMethod.GET)) {
            log.info("get处理暂时不做解密");
            return ResultUtil.getSuccessResult(chain.filter(exchange));
        }

        //post请求处理

        //解密处理
        requestParameter = "{\n" +
                "\t\"orderId\":1,\n" +
                "\t\"productName\":\"解密后产品\"\n" +
                "}";

        //下面的将请求体再次封装写回到request里，传到下一级，否则，由于请求体已被消费，后续的服务将取不到值
        URI uri = serverHttpRequest.getURI();
        URI newUri = UriComponentsBuilder.fromUri(uri).build(true).toUri();
        ServerHttpRequest request = exchange.getRequest().mutate().uri(newUri).build();
        DataBuffer bodyDataBuffer = super.getDataBufferByStr(requestParameter);
        Flux<DataBuffer> bodyFlux = Flux.just(bodyDataBuffer);

        // 定义新的消息头
        HttpHeaders headers = new HttpHeaders();
        headers.putAll(exchange.getRequest().getHeaders());


        // 由于修改了传递参数，需要重新设置CONTENT_LENGTH，长度是字节长度，不是字符串长度
        int length = requestParameter
                .getBytes().length;
        headers.remove(HttpHeaders.CONTENT_LENGTH);
        headers.setContentLength(length);
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        // 由于post的body只能订阅一次，由于上面代码中已经订阅过一次body。所以要再次封装请求到request才行，不然会报错请求已经订阅过
        request = new ServerHttpRequestDecorator(request) {
            @Override
            public HttpHeaders getHeaders() {
                long contentLength = headers.getContentLength();
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.putAll(super.getHeaders());
                httpHeaders.setContentLength(contentLength);
                return httpHeaders;
            }

            @Override
            public Flux<DataBuffer> getBody() {
                return bodyFlux;
            }
        };

        //封装request，传给下一级
        Mono<Void> mono = chain.filter(exchange.mutate().request(request).build());
        return ResultUtil.getSuccessResult(mono);
    }

    @Override
    protected int getFilterOrder() {
        return -4;
    }
}
