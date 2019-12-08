package pers.walyex.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import pers.walyex.common.core.dto.ResponseDataDTO;
import pers.walyex.order.dto.AccountResq;
import pers.walyex.order.feign.hystrix.AccountFeignHystrix;

/**
 * account feign 客户端
 *
 * @author Waldron Ye
 * @date 2019/12/8 13:53
 */
@FeignClient(value = "yzw-forum-core-account", fallback = AccountFeignHystrix.class)
public interface AccountFeignClient {

    /**
     * 获取account信息
     *
     * @return responseDataDTO
     */
    @GetMapping(value = "/getAccountInfo")
    ResponseDataDTO<AccountResq> getAccountInfo();
}
