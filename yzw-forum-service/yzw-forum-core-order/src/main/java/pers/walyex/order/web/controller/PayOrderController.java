package pers.walyex.order.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pers.walyex.common.core.dto.ResponseDataDTO;
import pers.walyex.common.core.enums.ResultEnum;
import pers.walyex.common.core.util.ResultUtil;
import pers.walyex.order.dto.AccountResq;
import pers.walyex.order.feign.AccountFeignClient;
import pers.walyex.order.model.PayOrder;
import pers.walyex.order.service.PayOrderService;
import pers.walyex.order.web.input.PayOrderQueryReq;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 支付订单控制器
 *
 * @author Waldron Ye
 * @date 2019/12/7 14:23
 */
@RestController
@Slf4j
public class PayOrderController {

    @Resource
    private PayOrderService payOrderService;

    @Resource
    private AccountFeignClient accountFeignClient;

    @GetMapping("/check")
    public Object check() {
        log.info("check===");
        return ResultUtil.getResult(ResultEnum.SUCCESS);
    }

    @GetMapping("/getOrderInfo")
    public Object getOrderInfo(Integer orderId, Integer userId, HttpServletRequest request) {
        log.info("getOrderInfo===,orderId={},userId={}", orderId, userId);
        log.info("头部信息:userName={}",request.getHeader("userName"));
        if (orderId == null) {
            orderId = 1;
        }

        PayOrder payOrder = this.payOrderService.getByOrderId(orderId);
        ResponseDataDTO<AccountResq> resqResponseDataDTO = this.accountFeignClient.getAccountInfo();
        Map<String, Object> map = new HashMap<>(8);
        map.put("orderInfo", payOrder);
        map.put("accountInfo", resqResponseDataDTO.getData());
        return ResultUtil.getSuccessResult(map);
    }

    @PostMapping("/postJson")
    public Object postJson(@RequestBody PayOrderQueryReq payOrderQueryReq) {
        log.info("postJson ==");
        return ResultUtil.getSuccessResult(payOrderQueryReq);
    }

    @PostMapping("/postFormData")
    public Object postFormData(PayOrderQueryReq payOrderQueryReq) {
        log.info("postFormData ==");
        return ResultUtil.getSuccessResult(payOrderQueryReq);
    }
}
