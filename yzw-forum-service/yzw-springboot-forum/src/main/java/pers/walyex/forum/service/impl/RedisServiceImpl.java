package pers.walyex.forum.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import pers.walyex.common.redis.util.RedisUtil;
import pers.walyex.common.util.DateUtil;
import pers.walyex.forum.constant.ForumConstant;
import pers.walyex.forum.service.RedisService;

import javax.annotation.Resource;
import java.util.Date;

/**
 * redis实现类
 *
 * @author Waldron Ye
 * @date 2019/12/21 12:46
 */
@Service
@Slf4j
public class RedisServiceImpl implements RedisService {

    @Resource
    private RedisUtil redisUtil;


    @Override
    public String getOrderNo() {

        String key = ForumConstant.ORDER_NO_INCR_KEY;

        long orderNoIncr = this.redisUtil.incr(key);

        String str = "0000" + orderNoIncr;
        str = str.substring(str.length() - 5);
        return "30100" + DateUtil.format(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + RandomStringUtils.randomNumeric(5) + str;
    }
}
