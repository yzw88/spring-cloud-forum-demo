package pers.walyex.order.feign.hystrix;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pers.walyex.common.core.dto.ResponseDataDTO;
import pers.walyex.order.dto.AccountResq;
import pers.walyex.order.feign.AccountFeignClient;

@Component
@Slf4j
public class AccountFeignHystrix implements AccountFeignClient {

    @Override
    public ResponseDataDTO<AccountResq> getAccountInfo() {
        log.info("获取account信息异常，开始熔断");
        return new ResponseDataDTO<>();
    }
}
