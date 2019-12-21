package pers.walyex.forum.dao;

import pers.walyex.order.model.PayOrder;

public interface PayOrderMapper {

    int deleteByPrimaryKey(Integer orderId);

    int insertSelective(PayOrder record);

    PayOrder selectByPrimaryKey(Integer orderId);

    int updateByPrimaryKeySelective(PayOrder record);
}
