package pers.walyex.forum.dto.user.req;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 用户状态更新请求对象
 *
 * @author Waldron Ye
 * @date 2020/1/26 9:52
 */
@Data
public class UserStatusUpdateReq implements Serializable {


    private static final long serialVersionUID = 3357687639520087060L;
    /**
     * 用户id
     */
    @NotNull(message = "用户id不能为空")
    @Min(value = 1, message = "用户id非法")
    private Long userId;

    /**
     * 用户状态
     */
    @NotNull(message = "用户状态不能为空")
    private Integer status;

}
