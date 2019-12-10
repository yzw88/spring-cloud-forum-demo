package pers.walyex.getaway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Objects;

/**
 * 参数处理过滤器(可做加解密、鉴权操作)
 *
 * @author Waldron Ye
 * @date 2019/12/7 22:30
 */
@Component
@Slf4j
public class ParameterHandleFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("ParameterHandleFilter start===");

        ServerHttpRequest serverHttpRequest = exchange.getRequest();
        //post请求处理
        if (Objects.equals(serverHttpRequest.getMethod(), HttpMethod.POST)) {
            log.info("POST请求start");

            //获取参数test
            ServerRequest serverRequest = ServerRequest.create(exchange, HandlerStrategies.withDefaults().messageReaders());
            MediaType mediaType = exchange.getRequest().getHeaders().getContentType();
            //重点
            Mono modifiedBody = serverRequest.bodyToMono(String.class).flatMap(body -> {
                //提交json数据处理
                if (MediaType.APPLICATION_JSON.isCompatibleWith(mediaType)) {
                    String paramStr = body;
                    log.info("参数为:paramStr={}", paramStr);
                    String newBody;
                    try {
                        //参数解密处理
                        newBody = "{\n" +
                                "\t\"orderId\":1,\n" +
                                "\t\"productName\":\"小五子商品\"\n" +
                                "}";
                        newBody = body;
                    } catch (Exception e) {
                        return this.processError(e.getMessage());
                    }
                    return Mono.just(newBody);
                }

                //提交表单处理
                if (MediaType.APPLICATION_FORM_URLENCODED.isCompatibleWith(mediaType)) {
                    log.info("表单数据处理:body={}", body);
                    return Mono.just(body);
                }
                return Mono.empty();
            });
            BodyInserter bodyInserter = BodyInserters.fromPublisher(modifiedBody, String.class);
            HttpHeaders headers = new HttpHeaders();
            headers.putAll(exchange.getRequest().getHeaders());
            //修改了body要重新写content length
//            headers.remove("Content-Length");

            ParameterCachedBodyOutputMessage outputMessage = new ParameterCachedBodyOutputMessage(exchange, headers);
            return bodyInserter.insert(outputMessage, new BodyInserterContext()).then(Mono.defer(() -> {
                ServerHttpRequest decorator = this.decorate(exchange, headers, outputMessage);
                return returnMono(chain, exchange.mutate().request(decorator).build());
            }));
        }

        //get请求处理
        if (Objects.equals(serverHttpRequest.getMethod(), HttpMethod.GET)) {
            log.info("GET请求start");
            URI uri = serverHttpRequest.getURI();
            StringBuilder query = new StringBuilder();
            String originalQuery = uri.getRawQuery();
            if (org.springframework.util.StringUtils.hasText(originalQuery)) {
                query.append(originalQuery);
                if (originalQuery.charAt(originalQuery.length() - 1) != '&') {
                    query.append('&');
                }
            }
            log.info("get请求参数为:query={}", query.toString());
            //如需添加参数，调用query.append()即可
            query.append("userId=1");
            URI newUri = UriComponentsBuilder.fromUri(uri)
                    .replaceQuery(query.toString())
                    .build(true)
                    .toUri();

            ServerHttpRequest request = exchange.getRequest().mutate().uri(newUri).build();
            return chain.filter(exchange.mutate().request(request).build());
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }

    private Mono<Void> returnMono(GatewayFilterChain chain, ServerWebExchange exchange) {
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            Long startTime = exchange.getAttribute("startTime");
            if (startTime != null) {
                long executeTime = (System.currentTimeMillis() - startTime);
                log.info("耗时：{}ms", executeTime);
                log.info("状态码：{}", Objects.requireNonNull(exchange.getResponse().getStatusCode()).value());
            }
        }));
    }

    private Mono processError(String message) {
        return Mono.error(new Exception(message));
    }

    ServerHttpRequestDecorator decorate(ServerWebExchange exchange, HttpHeaders headers, ParameterCachedBodyOutputMessage outputMessage) {
        return new ServerHttpRequestDecorator(exchange.getRequest()) {
            @Override
            public HttpHeaders getHeaders() {
                long contentLength = headers.getContentLength();
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.putAll(super.getHeaders());
                if (contentLength > 0L) {
                    httpHeaders.setContentLength(contentLength);
                } else {
                    httpHeaders.set("Transfer-Encoding", "chunked");
                }
                return httpHeaders;
            }

            @Override
            public Flux<DataBuffer> getBody() {
                return outputMessage.getBody();
            }
        };
    }
}
