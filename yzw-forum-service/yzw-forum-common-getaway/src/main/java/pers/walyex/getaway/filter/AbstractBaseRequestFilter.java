package pers.walyex.getaway.filter;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.web.server.ServerWebExchange;
import pers.walyex.getaway.output.ParameterCachedBodyOutputMessage;
import reactor.core.publisher.Flux;

/**
 * “pre”过滤器基类
 * 可以做参数校验、权限校验、流量监控、日志输出、协议转换等等
 *
 * @author Waldron Ye
 * @date 2019/12/13 20:20
 */
public abstract class AbstractBaseRequestFilter extends AbstractBaseGetawayFilter{

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
