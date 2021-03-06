package pers.walyex.forum.service;

import pers.walyex.forum.dto.BasePageQueryDTO;
import pers.walyex.order.model.PayOrder;

import java.util.List;

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

    /**
     * 添加订单
     *
     * @param payOrder order model
     * @return 插入的条数
     */
    int insertOrder(PayOrder payOrder);

    /**
     * 获取订单分页数据
     *
     * @param basePageQueryDTO 分页传输dto
     * @return List<PayOrder>
     */
    List<PayOrder> listPageOrder(BasePageQueryDTO basePageQueryDTO);
}
