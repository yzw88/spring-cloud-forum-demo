package pers.walyex.mybatis.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * sys_user对应的实体
 *
 * @author Waldron Ye
 * @date 2019/11/9 10:42
 */
@Data
public class SysUser implements Serializable {
    private static final long serialVersionUID = -2028544137719692804L;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名
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
     * 状态  0：禁用   1：正常
     */
    private String status;

    /**
     * 部门id
     */
    private Long deptId;

    /**
     * 创建时间
     */
    private Date createTime;


}
