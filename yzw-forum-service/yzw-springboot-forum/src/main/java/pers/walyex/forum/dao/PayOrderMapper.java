package pers.walyex.forum.dao;

import pers.walyex.forum.dto.BasePageQueryDTO;
import pers.walyex.order.model.PayOrder;

import java.util.List;

public interface PayOrderMapper {

    int deleteByPrimaryKey(Integer orderId);

    int insertSelective(PayOrder record);

    PayOrder selectByPrimaryKey(Integer orderId);

    int updateByPrimaryKeySelective(PayOrder record);


    /**
     * 获取订单分页数据
     *
     * @param basePageQueryDTO 分页传输dto
     * @return List<PayOrder>
     */
    List<PayOrder> listPageOrder(BasePageQueryDTO basePageQueryDTO);
}
