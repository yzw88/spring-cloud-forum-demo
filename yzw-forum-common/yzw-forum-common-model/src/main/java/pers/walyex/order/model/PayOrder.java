package pers.walyex.order.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PayOrder implements Serializable {
    private static final long serialVersionUID = 1683192773695698163L;
    private Integer orderId;

    private String productName;

    private String orderNo;

    private Long amount;

    private String payType;

    private String payState;

    private String tradeType;

    private Date orderStartTime;

    private Date orderEndTime;

    private String thirdOutTradeNo;

    private String failReason;

    private String remark;

    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;


}