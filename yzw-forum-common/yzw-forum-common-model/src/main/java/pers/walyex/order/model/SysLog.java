package pers.walyex.order.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 日志model
 *
 * @author Waldron Ye
 * @date 2020/1/26 13:55
 */
@Data
public class SysLog implements Serializable {
    private static final long serialVersionUID = 2003669071019829422L;
    /**
     * 日志id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户操作
     */
    private String operation;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 请求参数
     */
    private String params;

    /**
     * 执行时长(毫秒)
     */
    private Long time;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 创建时间
     */
    private Date createDate;
}