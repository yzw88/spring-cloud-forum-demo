package pers.walyex.forum.dto.user.req;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 用户更新请求对象
 *
 * @author Waldron Ye
 * @date 2020/1/26 9:52
 */
@Data
public class UserUpdateReq implements Serializable {

    private static final long serialVersionUID = -6424511588648304942L;

    /**
     * 用户id
     */
    @NotNull(message = "用户id不能为空")
    @Min(value = 1, message = "用户id非法")
    private Long userId;

    /**
     * 邮箱
     */
    private String email;

}
