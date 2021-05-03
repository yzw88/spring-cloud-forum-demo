package pers.walyex.mybatis.mapper;

import pers.walyex.mybatis.entity.SysUser;

/**
 * 用户dao
 *
 * @author Waldron Ye
 * @date 2019/11/9 10:44
 */
public interface SysUserMapper {

    /**
     * 通过用户主键删除
     *
     * @param userId 用户id
     * @return 删除记录数
     */
    int deleteByPrimaryKey(Long userId);

    /**
     * 添加用户
     *
     * @param record 用户model
     * @return 插入记录数
     */
    int insertSelective(SysUser record);

    /**
     * 查询用户model
     *
     * @param userId 用户id
     * @return 用户model
     */
    SysUser selectByPrimaryKey(Long userId);

    /**
     * 更新用户信息
     *
     * @param record 用户model
     * @return 更新记录数
     */
    int updateByPrimaryKeySelective(SysUser record);

    /**
     * 查询用户model
     *
     * @param username 用户名
     * @return 用户model
     */
    SysUser selectByUserName(String username);

}
