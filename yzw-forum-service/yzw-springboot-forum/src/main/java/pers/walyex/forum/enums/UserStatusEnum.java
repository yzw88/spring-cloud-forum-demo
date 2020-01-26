package pers.walyex.forum.enums;

/**
 * 用户状态枚举
 *
 * @author Waldron Ye
 * @date 2020/1/26 10:33
 */
public enum UserStatusEnum {

    /**
     * 禁用
     */
    DISABLE(0, "禁用"),

    /**
     * 启用
     */
    ENABLE(1, "启用");

    /**
     * 编码
     */
    private int code;

    /**
     * 内容
     */
    private String content;

    UserStatusEnum(int code, String content) {
        this.code = code;
        this.content = content;
    }

    public int getCode() {
        return code;
    }

    public String getContent() {
        return content;
    }

    public static String getContentByCode(int code) {
        for (UserStatusEnum userStatusEnum : UserStatusEnum.values()) {
            if (userStatusEnum.getCode() == code) {
                return userStatusEnum.getContent();
            }
        }

        return null;
    }
}
