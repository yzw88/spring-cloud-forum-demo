package pers.walyex.forum.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.walyex.forum.dao.PayOrderMapper;
import pers.walyex.forum.dto.BasePageQueryDTO;
import pers.walyex.forum.service.PayOrderService;
import pers.walyex.forum.service.RedisService;
import pers.walyex.order.model.PayOrder;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class PayOrderServiceImpl implements PayOrderService {

    @Resource
    private PayOrderMapper payOrderMapper;
    @Resource
    private RedisService redisService;

    @Override
    public PayOrder getByOrderId(Integer orderId) {
        return this.payOrderMapper.selectByPrimaryKey(orderId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertOrder(PayOrder payOrder) {

        payOrder.setOrderNo(this.redisService.getOrderNo());
        payOrder.setThirdOutTradeNo(this.redisService.getOrderNo());

        return this.payOrderMapper.insertSelective(payOrder);
    }

    @Override
    public List<PayOrder> listPageOrder(BasePageQueryDTO basePageQueryDTO) {
        return this.payOrderMapper.listPageOrder(basePageQueryDTO);
    }
}
