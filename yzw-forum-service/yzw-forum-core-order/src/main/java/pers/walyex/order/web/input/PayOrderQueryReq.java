package pers.walyex.order.web.input;

import lombok.Data;

import java.io.Serializable;

/**
 * 订单查询对象
 *
 * @author Waldron Ye
 * @date 2019/12/7 22:32
 */
@Data
public class PayOrderQueryReq implements Serializable {
    private static final long serialVersionUID = 6389013601982617506L;

    /**
     * 订单id
     */
    private Integer orderId;

    /**
     * 产品名称
     */
    private String productName;
}
