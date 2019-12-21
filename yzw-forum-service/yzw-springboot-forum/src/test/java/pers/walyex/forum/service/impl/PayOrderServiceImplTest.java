package pers.walyex.forum.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import pers.walyex.common.util.DateUtil;
import pers.walyex.forum.YzwSpringbootForumApplicationTests;
import pers.walyex.forum.service.PayOrderService;
import pers.walyex.order.model.PayOrder;

import javax.annotation.Resource;
import java.util.Date;

@Slf4j
public class PayOrderServiceImplTest extends YzwSpringbootForumApplicationTests {

    @Resource
    private PayOrderService payOrderService;

    @Test
    public void insertOrder() {

        PayOrder payOrder = new PayOrder();
        payOrder.setProductName("护肤品商品");
        payOrder.setAmount(Long.valueOf(RandomStringUtils.randomNumeric(5)));
        payOrder.setPayState("3");
        payOrder.setTradeType("JSAPI");
        payOrder.setPayType("108");
        payOrder.setRemark("啦啦");

        Date date = new Date();
        payOrder.setCreateBy("SYS");
        payOrder.setCreateTime(date);
        payOrder.setUpdateBy("SYS");
        payOrder.setUpdateTime(date);

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            payOrder.setThirdOutTradeNo(DateUtil.format(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + RandomStringUtils.randomNumeric(5));
            this.payOrderService.insertOrder(payOrder);
        }
        log.info("插入完成:userTime={}", System.currentTimeMillis() - startTime);
    }
}