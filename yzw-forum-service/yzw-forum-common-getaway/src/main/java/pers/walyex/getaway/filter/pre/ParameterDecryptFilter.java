package pers.walyex.getaway.filter.pre;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import pers.walyex.common.core.constant.GetawayConstant;
import pers.walyex.common.core.enums.ResultEnum;
import pers.walyex.common.core.util.ResultUtil;
import pers.walyex.common.util.FastJsonUtil;
import pers.walyex.getaway.filter.AbstractBaseFilter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * 参数解密过滤器
 *
 * @author Waldron Ye
 * @date 2020/8/28 21:03
 */
@Component
@Slf4j
public class ParameterDecryptFilter extends AbstractBaseFilter {

    @Override
    public Mono<Void> filterHandle(ServerWebExchange exchange, GatewayFilterChain chain) {

        log.info("请求的url:{}", exchange.getRequest().getURI().getPath());
        HttpMethod method = exchange.getRequest().getMethod();

        //OPTIONS请求放过
        if (Objects.equals(method, HttpMethod.OPTIONS)) {
            return chain.filter(exchange);
        }
        //暂只允许get和post请求
        if (!Objects.equals(method, HttpMethod.GET) && !Objects.equals(method, HttpMethod.POST)) {
            log.info("不支持当前请求方法");
            return super.getResponseMono(exchange, ResultUtil.getResult(ResultEnum.NO_SUPPORT_REQUEST_METHOD));
        }
        //post请求处理
        if (Objects.equals(method, HttpMethod.POST)) {
            return this.postRequestHandle(exchange, chain);
        }
        //get请求处理
        return this.getRequestHandle(exchange, chain);
    }

    /**
     * get请求处理
     *
     * @param exchange serverWebExchange
     * @param chain    gatewayFilterChain
     * @return mono
     */
    private Mono<Void> getRequestHandle(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("get请求处理");
        URI uri = exchange.getRequest().getURI();
        MultiValueMap<String, String> multiValueMap = exchange.getRequest().getQueryParams();

        String requestParameter = FastJsonUtil.toJson(multiValueMap.toSingleValueMap());
        log.info("get请求参数为:requestParameter={}", requestParameter);

        Map<String, String> map = new HashMap<>(16);
        if (!multiValueMap.isEmpty()) {
            multiValueMap.toSingleValueMap().forEach(map::put);
        }
        //添加参数或者加解密处理
        map.put("name", "walyex");
        map.put("address", "guangdong");

        StringJoiner joiner = new StringJoiner("&");
        map.forEach((k, v) -> joiner.add(k + "=" + v));
        log.info("添加参数:joiner={}", joiner.toString());

        URI newUri = UriComponentsBuilder.fromUri(uri)
                .replaceQuery(joiner.toString())
                .build(true)
                .toUri();

        ServerHttpRequest request = exchange.getRequest().mutate().uri(newUri).build();
        return chain.filter(exchange.mutate().request(request).build());
    }

    /**
     * post请求处理
     *
     * @param exchange serverWebExchange
     * @param chain    gatewayFilterChain
     * @return mono
     */
    private Mono<Void> postRequestHandle(ServerWebExchange exchange, GatewayFilterChain chain) {
        String contentType = exchange.getRequest().getHeaders().getFirst(HttpHeaders.CONTENT_TYPE);
        log.info("post请求contentType:{}", contentType);
        if (StringUtils.isBlank(contentType) || !contentType.contains(MediaType.APPLICATION_JSON_VALUE)) {
            return super.getResponseMono(exchange, ResultUtil.getResult(ResultEnum.NO_SUPPORT_REQUEST_MEDIA));
        }

        long contentLength = exchange.getRequest().getHeaders().getContentLength();
        log.info("post请求contentLength:{}", contentLength);
        if (contentLength == 0) {
            return super.getResponseMono(exchange, ResultUtil.getResult(ResultEnum.PARAMETER_ERROR, "请求参数格式有误"));
        }
        return DataBufferUtils.join(exchange.getRequest().getBody()).flatMap(dataBuffer -> {

            byte[] bytes = new byte[dataBuffer.readableByteCount()];
            dataBuffer.read(bytes);
            //释放掉内存
            DataBufferUtils.release(dataBuffer);
            String bodyString = new String(bytes, StandardCharsets.UTF_8);
            log.info("请求数据为:bodyString={}", bodyString);
            if (GetawayConstant.BANK_JSON.equals(bodyString)) {
                return super.getResponseMono(exchange, ResultUtil.getResult(ResultEnum.PARAMETER_ERROR, "请求参数格式有误"));
            }

            //解密处理、参数处理
            String bodyStringNew = "{\n" +
                    "\t\"orderId\":1,\n" +
                    "\t\"productName\":\"解密后产品\"\n" +
                    "}";

            //将请求体再次封装写回到request
            URI newUri = UriComponentsBuilder.fromUri(exchange.getRequest().getURI()).build(true).toUri();
            ServerHttpRequest request = exchange.getRequest().mutate().uri(newUri).build();
            DataBuffer bodyDataBuffer = super.getDataBufferByStr(bodyStringNew);
            Flux<DataBuffer> bodyFlux = Flux.just(bodyDataBuffer);

            // 定义新的消息头
            HttpHeaders headers = new HttpHeaders();
            headers.putAll(exchange.getRequest().getHeaders());

            //由于修改了传递参数，需要重新设置CONTENT_LENGTH
            int length = bodyStringNew.getBytes().length;
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
            return chain.filter(exchange.mutate().request(request).build());
        });
    }

    @Override
    public int getOrder() {
        return MAX_ORDER - 5;
    }
}
