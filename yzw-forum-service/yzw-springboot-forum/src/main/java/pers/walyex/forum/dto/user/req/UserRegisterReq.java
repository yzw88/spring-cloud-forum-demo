package pers.walyex.forum.dto.user.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 用户注册请求对象
 *
 * @author Waldron Ye
 * @date 2020/1/26 9:52
 */
@Data
public class UserRegisterReq implements Serializable {
    private static final long serialVersionUID = -8933470301581660669L;

    /**
     * 用户名称
     */
    @NotBlank(message = "用户名不能为空")
    @Size(min = 1, max = 20, message = "用户名长度非法")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Size(min = 1, max = 20, message = "密码长度非法")
    private String password;

    /**
     * 密码
     */
    @NotBlank(message = "重复密码不能为空")
    @Size(min = 1, max = 20, message = "重复密码长度非法")
    private String passwordRepeat;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    @NotBlank(message = "手机号码不能为空")
    @Pattern(regexp = "^1[3456789]\\d{9}$", message = "手机号码格式错误")
    private String mobile;

}
