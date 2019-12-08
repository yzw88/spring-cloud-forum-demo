package pers.walyex.common.core.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 响应传输对象
 *
 * @author Waldron Ye
 * @date 2019/4/12 22:40
 */
@Data
public class ResponseDataDTO<T> implements Serializable {
    private static final long serialVersionUID = 7199667761005759015L;

    /**
     * 状态码
     */
    private Integer code;
    /**
     * 消息
     */
    private String message;
    /**
     * 数据
     */
    private T data;
}
