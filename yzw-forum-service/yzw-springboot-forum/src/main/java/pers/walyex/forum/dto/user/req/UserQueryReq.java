package pers.walyex.forum.dto.user.req;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pers.walyex.forum.dto.BasePageQueryDTO;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 用户查询请求对象
 *
 * @author Waldron Ye
 * @date 2020/1/26 9:52
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserQueryReq extends BasePageQueryDTO implements Serializable {
    private static final long serialVersionUID = -8933470301581660669L;

    /**
     * 用户名称
     */
    @Size(max = 20, message = "用户名长度非法")
    private String username;

    /**
     * 邮箱
     */
    @Size(max = 30, message = "邮箱长度非法")
    private String email;

    /**
     * 手机号码
     */
    @Size(max = 20, message = "手机号码长度非法")
    private String mobile;

}
