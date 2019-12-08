package pers.walyex.order.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pers.walyex.order.dao.PayOrderMapper;
import pers.walyex.order.model.PayOrder;
import pers.walyex.order.service.PayOrderService;

import javax.annotation.Resource;

@Service
@Slf4j
public class PayOrderServiceImpl implements PayOrderService {

    @Resource
    private PayOrderMapper payOrderMapper;

    @Override
    public PayOrder getByOrderId(Integer orderId) {
        return this.payOrderMapper.selectByPrimaryKey(orderId);
    }
}
