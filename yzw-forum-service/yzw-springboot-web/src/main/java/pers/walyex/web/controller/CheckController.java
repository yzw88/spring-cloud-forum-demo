package pers.walyex.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.walyex.common.core.util.ResultUtil;
import pers.walyex.web.handle.PayNotifyHandle;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * check控制器
 *
 * @author Waldron Ye
 * @date 2019/12/28 9:40
 */
@RestController
@Slf4j
public class CheckController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(5);


    @GetMapping("/check")
    public Object check() {
        log.info("check====");

        return ResultUtil.getSuccessResult(200);
    }

    @GetMapping("/redisCheck")
    public Object redisCheck() {
        log.info("redisCheck====");
        String key = "phone";
        String value = "213423423";

        ValueOperations<String, String> ops = this.stringRedisTemplate.opsForValue();
        //1分钟过期
        ops.set(key, value, 1, TimeUnit.MINUTES);

        String value2 = ops.get(key);
        log.info("从redis中获取value={}", value2);

        return ResultUtil.getSuccessResult(200);
    }

    @GetMapping("/threadTest")
    public Object threadTest() {
        log.info("threadTest  test start===");
        for (int i = 0; i < 5; i++) {
            PayNotifyHandle payNotifyHandle = new PayNotifyHandle();
            EXECUTOR_SERVICE.execute(payNotifyHandle);
        }
        log.info("threadTest  test end===");
        return ResultUtil.getSuccessResult(200);
    }
}
