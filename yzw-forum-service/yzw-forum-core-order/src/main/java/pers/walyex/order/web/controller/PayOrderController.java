package pers.walyex.order.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pers.walyex.common.core.dto.ResponseDataDTO;
import pers.walyex.common.core.enums.ResultEnum;
import pers.walyex.common.core.util.ResultUtil;
import pers.walyex.common.util.FastJsonUtil;
import pers.walyex.core.controller.AbstractBaseController;
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
public class PayOrderController extends AbstractBaseController {

    @Resource
    private PayOrderService payOrderService;

    @Resource
    private AccountFeignClient accountFeignClient;

    @GetMapping("/check")
    public Object check() {
        log.info("check===");
        return ResultUtil.getResult(ResultEnum.SUCCESS);
    }

    @GetMapping("/check2")
    public Object check2(@RequestParam Map<String, String> requestMap) {
        log.info("check2=== requestMap={}", FastJsonUtil.toJson(requestMap));
        return ResultUtil.getSuccessResult(requestMap);
    }

    @GetMapping("/redirect/baiDuTest")
    public Object redirect() {

        return ResultUtil.getSuccessResult("https://www.baidu.com");
    }

    @GetMapping("/getOrderInfo")
    public Object getOrderInfo(Integer orderId, Integer userId, HttpServletRequest request) {
        log.info("getOrderInfo===,orderId={},userId={}", orderId, userId);
        log.info("头部信息:userName={}", request.getHeader("userName"));
        if (orderId == null) {
            orderId = 1;
        }

        PayOrder payOrder = this.payOrderService.getByOrderId(orderId);
        ResponseDataDTO<AccountResq> responseDataDTO = this.accountFeignClient.getAccountInfo();
        Map<String, Object> map = new HashMap<>(8);
        map.put("orderInfo", payOrder);
        map.put("accountInfo", responseDataDTO.getData());
        return ResultUtil.getSuccessResult(map);
    }

    @PostMapping("/postJson")
    public Object postJson(@RequestBody PayOrderQueryReq payOrderQueryReq) {
        log.info("postJson ==");
        return ResultUtil.getSuccessResult(payOrderQueryReq);
    }

    @PostMapping("/postJson2")
    public Object postJson2(@RequestBody Map<String, String> requestMap) {
        log.info("postJson2 == requestMap={}", FastJsonUtil.toJson(requestMap));
        return ResultUtil.getSuccessResult(requestMap);
    }

    @PostMapping("/postFormData")
    public Object postFormData(PayOrderQueryReq payOrderQueryReq) {
        log.info("postFormData ==");
        return ResultUtil.getSuccessResult(payOrderQueryReq);
    }
}
