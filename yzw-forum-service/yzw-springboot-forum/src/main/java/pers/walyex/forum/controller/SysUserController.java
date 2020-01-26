package pers.walyex.forum.controller;

import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.walyex.common.core.enums.ResultEnum;
import pers.walyex.common.core.util.ResultUtil;
import pers.walyex.common.util.FastJsonUtil;
import pers.walyex.forum.dto.user.req.UserQueryReq;
import pers.walyex.forum.dto.user.req.UserRegisterReq;
import pers.walyex.forum.dto.user.req.UserStatusUpdateReq;
import pers.walyex.forum.dto.user.req.UserUpdateReq;
import pers.walyex.forum.enums.UserStatusEnum;
import pers.walyex.forum.service.SysUserService;
import pers.walyex.forum.util.RegexpUtil;
import pers.walyex.order.model.SysUser;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

/**
 * 用户控制器
 *
 * @author Waldron Ye
 * @date 2020/1/26 9:47
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class SysUserController {

    @Resource
    private SysUserService sysUserService;

    /**
     * 用户注册
     *
     * @param userRegisterReq 用户注册请求对象
     * @return object
     */
    @PostMapping("/userRegister")
    public Object userRegister(@Validated @RequestBody UserRegisterReq userRegisterReq) {
        log.info("用户注册参数:{}", FastJsonUtil.toJson(userRegisterReq));
        if (!StringUtils.isBlank(userRegisterReq.getEmail()) &&
                !RegexpUtil.isValid(RegexpUtil.REGEX_EMAIL_STR, userRegisterReq.getEmail())) {
            return ResultUtil.getResult(ResultEnum.PARAMETER_ERROR, "邮箱格式错误");
        }

        if (!Objects.equals(userRegisterReq.getPassword(), userRegisterReq.getPasswordRepeat())) {
            return ResultUtil.getResult(ResultEnum.PARAMETER_ERROR, "两次密码输入不一致");
        }

        SysUser sysUser = this.sysUserService.selectByUsername(userRegisterReq.getUsername());
        //用户名已存在，不能注册
        if (sysUser != null) {
            return ResultUtil.getResult(ResultEnum.PARAMETER_ERROR, "用户名已存在");
        }

        //手机号已存在，不能注册，暂不考虑更换手机号码
        SysUser sysUser1 = this.sysUserService.selectByMobile(userRegisterReq.getMobile());
        if (sysUser1 != null) {
            return ResultUtil.getResult(ResultEnum.PARAMETER_ERROR, "手机号码已存在");
        }

        SysUser addUser = new SysUser();
        addUser.setCreateTime(new Date());
        addUser.setEmail(userRegisterReq.getEmail());
        addUser.setMobile(userRegisterReq.getMobile());
        addUser.setUsername(userRegisterReq.getUsername());
        //盐值随机生成
        String saltStr = RandomStringUtils.randomAlphanumeric(10);
        addUser.setSalt(saltStr);
        addUser.setStatus(UserStatusEnum.ENABLE.getCode());
        addUser.setPassword(MD5Encoder.encode((userRegisterReq.getPassword() + saltStr).getBytes()));
        this.sysUserService.addUser(addUser);

        return ResultUtil.getSuccessResult();
    }

    /**
     * 更新用户信息
     *
     * @param userUpdateReq 用户更新请求对象
     * @return object
     */
    @PutMapping("/updateUser")
    public Object updateUser(@Validated @RequestBody UserUpdateReq userUpdateReq) {
        log.info("更新用户信息参数:{}", FastJsonUtil.toJson(userUpdateReq));
        SysUser sysUser = this.sysUserService.getByUserId(userUpdateReq.getUserId());
        if (sysUser == null) {
            return ResultUtil.getResult(ResultEnum.PARAMETER_ERROR, "用户id非法");
        }
        //目前只更新邮箱，如果邮箱为空，则无需更新
        if (StringUtils.isBlank(userUpdateReq.getEmail())) {
            return ResultUtil.getSuccessResult();
        }
        if (!RegexpUtil.isValid(RegexpUtil.REGEX_EMAIL_STR, userUpdateReq.getEmail())) {
            return ResultUtil.getResult(ResultEnum.PARAMETER_ERROR, "邮箱格式错误");
        }

        //或者直接使用sysUser
        SysUser updateUser = new SysUser();
        updateUser.setUserId(userUpdateReq.getUserId());
        updateUser.setEmail(userUpdateReq.getEmail());
        this.sysUserService.updateUser(updateUser);

        return ResultUtil.getSuccessResult();
    }

    /**
     * 更新用户状态(启用和禁用)
     *
     * @param userStatusUpdateReq 用户状态更新请求对象
     * @return object
     */
    @PutMapping("/updateUserStatus")
    public Object updateUserStatus(@Validated @RequestBody UserStatusUpdateReq userStatusUpdateReq) {
        log.info("更新用户状态参数:{}", FastJsonUtil.toJson(userStatusUpdateReq));
        if (StringUtils.isBlank(UserStatusEnum.getContentByCode(userStatusUpdateReq.getStatus()))) {
            return ResultUtil.getResult(ResultEnum.PARAMETER_ERROR, "用户状态值非法");
        }
        SysUser sysUser = this.sysUserService.getByUserId(userStatusUpdateReq.getUserId());
        if (sysUser == null) {
            return ResultUtil.getResult(ResultEnum.PARAMETER_ERROR, "用户id非法");
        }
        SysUser updateUser = new SysUser();
        updateUser.setUserId(userStatusUpdateReq.getUserId());
        updateUser.setStatus(userStatusUpdateReq.getStatus());
        this.sysUserService.updateUser(updateUser);

        return ResultUtil.getSuccessResult();
    }

    /**
     * 获取用户分页列表
     *
     * @param userQueryReq 用户查询请求对象
     * @return object
     */
    @PostMapping("/listUserPage")
    public Object listUserPage(@Validated @RequestBody UserQueryReq userQueryReq) {
        log.info("获取用户分页列表参数:{}", FastJsonUtil.toJson(userQueryReq));
        PageInfo<SysUser> pageInfo = this.sysUserService.listUserPage(userQueryReq);
        return ResultUtil.getSuccessResult(pageInfo);
    }


}
