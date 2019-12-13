package pers.walyex.getaway.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.MDC;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import pers.walyex.common.core.constant.CommonConstant;
import pers.walyex.common.core.dto.ResponseDataDTO;
import pers.walyex.common.core.util.ResultUtil;
import reactor.core.publisher.Mono;

/**
 * 追踪id处理
 *
 * @author Waldron Ye
 * @date 2019/12/9 20:45
 */
@Component
@Slf4j
public class RequestTraceIdHandleFilter extends AbstractBaseRequestFilter {


    @Override
    protected ResponseDataDTO<Mono<Void>> filterHandle(ServerWebExchange exchange, GatewayFilterChain chain) {
        String traceId = RandomStringUtils.randomAlphanumeric(5);
        MDC.put(CommonConstant.THREAD_ID, traceId);
        log.info("TraceIdHandleFilter create traceId={}", traceId);

        //把traceId传入header中
        ServerHttpRequest request = exchange.getRequest().mutate().header(CommonConstant.TRACE_ID, traceId).build();
        //将现在的request 变成 change对象
        ServerWebExchange build = exchange.mutate().request(request).build();

        return ResultUtil.getSuccessResult(chain.filter(build));
    }

    @Override
    protected int getFilterOrder() {
        return -10;
    }
}
