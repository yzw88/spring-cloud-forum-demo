package pers.walyex.order.service;

import pers.walyex.order.model.PayOrder;

/**
 * 订单服务接口
 *
 * @author Waldron Ye
 * @date 2019/12/7 14:19
 */
public interface PayOrderService {

    /**
     * 获取订单model
     *
     * @param orderId 订单id
     * @return payOrder model
     */
    PayOrder getByOrderId(Integer orderId);
}
