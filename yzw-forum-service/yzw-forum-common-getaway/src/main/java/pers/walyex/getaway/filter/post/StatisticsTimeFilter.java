package pers.walyex.getaway.filter.post;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import pers.walyex.getaway.filter.AbstractBaseFilter;
import reactor.core.publisher.Mono;

/**
 * 统计时间过滤器
 *
 * @author Waldron Ye
 * @date 2020/8/28 22:41
 */
@Component
@Slf4j
public class StatisticsTimeFilter extends AbstractBaseFilter {

    @Override
    public Mono<Void> filterHandle(ServerWebExchange exchange, GatewayFilterChain chain) {

        long startTime = System.currentTimeMillis();
        log.info("开始时间:startTime={}", startTime);

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            long userTime = System.currentTimeMillis() - startTime;
            log.info("url:{}使用时间:{}毫秒", exchange.getRequest().getURI().getRawPath(), userTime);
        }));

    }

    @Override
    public int getOrder() {
        return MAX_ORDER - 9;
    }
}
