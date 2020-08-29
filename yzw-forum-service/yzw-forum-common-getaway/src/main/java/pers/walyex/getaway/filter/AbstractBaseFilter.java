package pers.walyex.getaway.filter;

import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.MDC;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.server.ServerWebExchange;
import pers.walyex.common.core.constant.CommonConstant;
import pers.walyex.common.util.FastJsonUtil;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * 过滤器基类
 *
 * @author Waldron Ye
 * @date 2020/8/28 21:00
 */
@Slf4j
public abstract class AbstractBaseFilter implements GlobalFilter, Ordered {

    /**
     * 最大order(越大优先级越低)
     */
    protected static final int MAX_ORDER = 10;

    @Override
    public final Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (isTrack()) {
            String traceId = RandomStringUtils.randomAlphanumeric(5);
            MDC.put(CommonConstant.THREAD_ID, traceId);
            log.info("生成追踪id:{}", traceId);
            //把traceId传入attributes中
            exchange.getAttributes().put(CommonConstant.TRACE_ID, traceId);
        } else {
            String traceId = (String) exchange.getAttributes().get(CommonConstant.TRACE_ID);
            MDC.put(CommonConstant.THREAD_ID, traceId);
            log.info("获取追踪id:{}", traceId);
        }

        try {
            return this.filterHandle(exchange, chain);
        } finally {
            //移除追踪id
            MDC.remove(CommonConstant.THREAD_ID);
        }
    }

    /**
     * 过滤器处理
     *
     * @param exchange serverWebExchange
     * @param chain    gatewayFilterChain
     * @return mono
     */
    protected abstract Mono<Void> filterHandle(ServerWebExchange exchange, GatewayFilterChain chain);

    /**
     * 是否生成追踪id(最早运行的过滤器要重写该方法返回true，为了生成追踪id)
     *
     * @return true-生成追踪id false-则不生成
     */
    protected boolean isTrack() {

        return false;
    }

    /**
     * 字符串转换 DataBuffer
     *
     * @param str 待转换的字符串
     * @return dataBuffer
     */
    protected DataBuffer getDataBufferByStr(String str) {
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);
        DataBuffer buffer = nettyDataBufferFactory.allocateBuffer(bytes.length);
        buffer.write(bytes);
        return buffer;
    }

    /**
     * 获取响应mono对象
     *
     * @param exchange     serverWebExchange
     * @param responseData 响应的内容
     * @return mono
     */
    protected Mono<Void> getResponseMono(ServerWebExchange exchange, Object responseData) {
        exchange.getResponse().getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return exchange.getResponse().writeWith(Mono.just(this.getDataBufferByStr(FastJsonUtil.toJson(responseData))));
    }
}
