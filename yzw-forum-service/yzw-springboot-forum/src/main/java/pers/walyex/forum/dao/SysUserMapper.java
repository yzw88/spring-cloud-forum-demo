package pers.walyex.forum.dao;

import org.apache.ibatis.annotations.Param;
import pers.walyex.forum.dto.user.req.UserQueryReq;
import pers.walyex.order.model.SysUser;

import java.util.List;

/**
 * 用户dao接口
 *
 * @author Waldron Ye
 * @date 2020/1/26 9:40
 */
public interface SysUserMapper {

    /**
     * 新增用户
     *
     * @param record 用户model
     * @return 新增的记录数
     */
    int insertSelective(SysUser record);

    /**
     * 查询用户信息
     *
     * @param userId 用户id
     * @return 用户model
     */
    SysUser selectByPrimaryKey(Long userId);

    /**
     * 更新用户信息
     *
     * @param record 用户model
     * @return 更新的记录数
     */
    int updateByPrimaryKeySelective(SysUser record);

    /**
     * 查询用户信息
     *
     * @param username 用户名称
     * @return 用户model
     */
    SysUser selectByUsername(@Param("username") String username);

    /**
     * 查询用户信息
     *
     * @param mobile 手机号码
     * @return 用户model
     */
    SysUser selectByMobile(@Param("mobile") String mobile);

    /**
     * 获取用户列表
     *
     * @param userQueryReq 用户查询请求对象
     * @return List<SysUser>
     */
    List<SysUser> listUser(UserQueryReq userQueryReq);
}


