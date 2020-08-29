package pers.walyex.getaway.filter.post;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import pers.walyex.common.core.constant.CommonConstant;
import pers.walyex.common.core.enums.GetawayEnum;
import pers.walyex.common.util.FastJsonUtil;
import pers.walyex.getaway.filter.AbstractBaseFilter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * 说明
 *
 * @author Waldron Ye
 * @date 2020/8/28 23:24
 */
@Component
@Slf4j
public class ResponseParameterHandleFilter extends AbstractBaseFilter {


    @Override
    public Mono<Void> filterHandle(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpResponse originalResponse = exchange.getResponse();
        DataBufferFactory bufferFactory = originalResponse.bufferFactory();

        ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {

            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                if (body instanceof Flux) {
                    Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
                    return super.writeWith(fluxBody.map(dataBuffer -> {
                        byte[] content = new byte[dataBuffer.readableByteCount()];
                        dataBuffer.read(content);
                        //释放掉内存
                        DataBufferUtils.release(dataBuffer);
                        String responseBody = new String(content, StandardCharsets.UTF_8);
                        String traceId = (String) exchange.getAttributes().get(CommonConstant.TRACE_ID);
                        log.info("traceId:{} 响应的结果为:responseBody={}", traceId, responseBody);

                        String path = exchange.getRequest().getURI().getRawPath();
                        //从定向处理
                        if (path.contains(GetawayEnum.REDIRECT.getCode())) {
                            Map<String, String> responseMap = FastJsonUtil.jsonStrToMap(responseBody);
                            originalResponse.setStatusCode(HttpStatus.SEE_OTHER);
                            originalResponse.getHeaders().add(HttpHeaders.CONTENT_TYPE, "text/plain;charset=UTF-8");
                            originalResponse.getHeaders().set("Location", responseMap.get("data"));
                        }

                        byte[] uppedContent = new String(content, StandardCharsets.UTF_8).getBytes();
                        return bufferFactory.wrap(uppedContent);
                    }));
                }
                return super.writeWith(body);
            }

        };

        return chain.filter(exchange.mutate().response(decoratedResponse).build());
    }

    @Override
    public int getOrder() {
        //order要小于1才可以查看响应报文
        return MAX_ORDER - 12;
    }

    @Override
    protected boolean isTrack() {
        return true;
    }
}
