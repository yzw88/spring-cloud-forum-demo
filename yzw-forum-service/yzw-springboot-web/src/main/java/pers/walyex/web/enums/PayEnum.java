package pers.walyex.web.enums;

/**
 * 支付枚举
 *
 * @author Waldron Ye
 * @date 2020/4/25 11:22
 */
public enum PayEnum {

    WX_H5("wxH5", "微信H5支付"),
    WX_APP("wxApp", "微信H5支付");

    private String payType;

    private String payMsg;


    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayMsg() {
        return payMsg;
    }

    public void setPayMsg(String payMsg) {
        this.payMsg = payMsg;
    }

    PayEnum(String payType, String payMsg) {
        this.payType = payType;
        this.payMsg = payMsg;
    }
}
