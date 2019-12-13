package pers.walyex.getaway.filter;

import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.server.ServerWebExchange;
import pers.walyex.common.core.dto.ResponseDataDTO;
import pers.walyex.common.core.enums.ResultEnum;
import pers.walyex.common.core.util.ResultUtil;
import pers.walyex.common.util.FastJsonUtil;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * 网关过滤器基类
 *
 * @author Waldron Ye
 * @date 2019/12/13 20:19
 */
@Slf4j
public abstract class AbstractBaseGetawayFilter implements GlobalFilter, Ordered {


    @Override
    public final Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        try {
            ResponseDataDTO<Mono<Void>> responseDataDTO = this.filterHandle(exchange, chain);

            if (!Objects.equals(responseDataDTO.getCode(), ResultEnum.SUCCESS.getCode())) {
                String responseJson = FastJsonUtil.toJson(responseDataDTO);
                log.info("过滤器校验不通过:msg={}", responseJson);

                return this.getResponseMono(exchange, responseJson);
            }

            //执行下一个过滤器
            return responseDataDTO.getData();
        } catch (Exception e) {
            log.error("GetawayFilter filterHandle error", e);
            String responseJson = FastJsonUtil.toJson(ResultUtil.getResult(ResultEnum.ERROR_SYS));
            return this.getResponseMono(exchange, responseJson);
        }
    }

    @Override
    public final int getOrder() {
        return this.getFilterOrder();
    }

    /**
     * 获取响应mono对象
     *
     * @param exchange     serverWebExchange
     * @param responseJson 响应的内容
     * @return mono
     */
    private Mono<Void> getResponseMono(ServerWebExchange exchange, String responseJson) {
        exchange.getResponse().getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return exchange.getResponse().writeWith(Mono.just(this.getDataBufferByStr(responseJson)));
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
     * 过滤器业务处理
     *
     * @param exchange serverWebExchange
     * @param chain    gatewayFilterChain
     * @return responseDataDTO
     */
    protected abstract ResponseDataDTO<Mono<Void>> filterHandle(ServerWebExchange exchange, GatewayFilterChain chain);


    /**
     * 过滤器顺序
     *
     * @return 过滤器执行顺序
     */
    protected abstract int getFilterOrder();

}
