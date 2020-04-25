package pers.walyex.web.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import pers.walyex.web.annotation.PayTradeTypeAnnotation;
import pers.walyex.web.service.PayService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 服务加载
 *
 * @author Waldron Ye
 * @date 2019/7/19 20:37
 */
@Component
@Slf4j
public class ServiceCacheLoad implements CommandLineRunner {

    @Resource
    private ApplicationContext applicationContext;

    /**
     * 支付服务map
     */
    private final static Map<String, PayService> PAY_SERVICE_MAP = new HashMap<>(16);

    @Override
    public void run(String... args) {
        log.info("开始加载服务");

        this.initPayService();

        log.info("加载服务结束");
    }


    /**
     * 初始化支付服务
     */
    private void initPayService() {
        Map<String, Object> payStrategyMap = applicationContext.getBeansWithAnnotation(PayTradeTypeAnnotation.class);
        payStrategyMap.forEach((k, v) -> {
            Class<PayService> orderStrategyClass = (Class<PayService>) v.getClass();
            PayTradeTypeAnnotation payTradeTypeAnnotation = orderStrategyClass.getAnnotation(PayTradeTypeAnnotation.class);
            for (int i = 0; i < payTradeTypeAnnotation.payEnums().length; i++) {
                PAY_SERVICE_MAP.put(payTradeTypeAnnotation.payEnums()[i].getPayType(), (PayService) v);
            }

        });

    }

    /**
     * 获取支付服务
     *
     * @param payType 支付类型
     * @return 支付服务
     */
    public static PayService getPayServiceByPayType(String payType) {
        return PAY_SERVICE_MAP.get(payType);
    }

}
