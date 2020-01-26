package pers.walyex.forum.service;

import com.github.pagehelper.PageInfo;
import pers.walyex.forum.dto.user.req.UserQueryReq;
import pers.walyex.order.model.SysUser;

import java.util.List;

/**
 * 用户服务接口
 *
 * @author Waldron Ye
 * @date 2020/1/26 9:44
 */
public interface SysUserService {

    /**
     * 新增用户
     *
     * @param record 用户model
     * @return 新增的记录数
     */
    int addUser(SysUser record);

    /**
     * 查询用户信息
     *
     * @param userId 用户id
     * @return 用户model
     */
    SysUser getByUserId(Long userId);

    /**
     * 更新用户信息
     *
     * @param record 用户model
     * @return 更新的记录数
     */
    int updateUser(SysUser record);

    /**
     * 查询用户信息
     *
     * @param username 用户名称
     * @return 用户model
     */
    SysUser selectByUsername(String username);

    /**
     * 查询用户信息
     *
     * @param mobile 手机号码
     * @return 用户model
     */
    SysUser selectByMobile(String mobile);

    /**
     * 获取用户分页信息
     *
     * @param userQueryReq 用户查询请求对象
     * @return PageInfo<SysUser>
     */
    PageInfo<SysUser> listUserPage(UserQueryReq userQueryReq);

    /**
     * 获取用户列表
     *
     * @param userQueryReq 用户查询请求对象
     * @return List<SysUser>
     */
    List<SysUser> listUser(UserQueryReq userQueryReq);
}
