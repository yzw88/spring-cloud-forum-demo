package pers.walyex.order.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户model
 *
 * @author Waldron Ye
 * @date 2020/1/26 9:32
 */
@Data
public class SysUser implements Serializable {

    private static final long serialVersionUID = 263472938227103483L;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 盐值
     */
    private String salt;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 状态  0：禁用   1：启用
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;
}