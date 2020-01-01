package pers.walyex.account.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.walyex.common.core.enums.ResultEnum;
import pers.walyex.common.core.util.ResultUtil;
import pers.walyex.common.redis.util.RedisUtil;
import pers.walyex.order.dto.AccountResq;

import javax.annotation.Resource;

/**
 * account 控制器
 *
 * @author Waldron Ye
 * @date 2019/12/8 13:21
 */
@RestController
@Slf4j
public class AccountController {

    @Resource
    private RedisUtil redisUtil;

    @GetMapping("/check")
    public Object check() {
        log.info("check ==");
        return ResultUtil.getResult(ResultEnum.SUCCESS);
    }

    @GetMapping("/checkRedis")
    public Object checkRedis() {
        log.info("checkRedis ==");
        try {
            String key = "name";
            String value = "张三";
            redisUtil.setString(key, value);
            String value2 = redisUtil.getString(key);
            log.info("从redis取出:value2={}", value2);
        } catch (Exception e) {
            return ResultUtil.getResult(ResultEnum.ERROR_SYS, "连接redis异常");
        }
        return ResultUtil.getResult(ResultEnum.SUCCESS);
    }

    @GetMapping("/getAccountInfo")
    public Object getAccountInfo() {
        log.info("getAccountInfo==");
        AccountResq accountResq = new AccountResq();
        accountResq.setAccountId(2);
        accountResq.setEmail("1303QQ.com");
        accountResq.setUsername("张三");

        try {
            Thread.sleep(4000L);
            log.info("模拟睡眠");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ResultUtil.getSuccessResult(accountResq);
    }
}
