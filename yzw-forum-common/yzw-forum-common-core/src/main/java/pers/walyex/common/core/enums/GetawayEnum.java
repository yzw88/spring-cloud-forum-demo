package pers.walyex.common.core.enums;

/**
 * 网关枚举
 *
 * @author Waldron Ye
 * @date 2020/8/29 11:25
 */
public enum GetawayEnum {

    REDIRECT("/redirect/");

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    GetawayEnum(String code) {
        this.code = code;
    }
}
