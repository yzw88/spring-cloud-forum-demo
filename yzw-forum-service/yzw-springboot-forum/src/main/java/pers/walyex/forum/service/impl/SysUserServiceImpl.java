package pers.walyex.forum.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.walyex.forum.dao.SysUserMapper;
import pers.walyex.forum.dto.user.req.UserQueryReq;
import pers.walyex.forum.service.SysUserService;
import pers.walyex.order.model.SysUser;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户接口实现类
 *
 * @author Waldron Ye
 * @date 2020/1/26 9:45
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addUser(SysUser record) {
        return this.sysUserMapper.insertSelective(record);
    }

    @Override
    public SysUser getByUserId(Long userId) {
        return this.sysUserMapper.selectByPrimaryKey(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateUser(SysUser record) {
        return this.sysUserMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public SysUser selectByUsername(String username) {
        return this.sysUserMapper.selectByUsername(username);
    }

    @Override
    public SysUser selectByMobile(String mobile) {
        return null;
    }

    @Override
    public PageInfo<SysUser> listUserPage(UserQueryReq userQueryReq) {
        //设置分页信息
        PageHelper.startPage(userQueryReq.getPageNum(), userQueryReq.getPageSize());
        List<SysUser> list = this.sysUserMapper.listUser(userQueryReq);
        return new PageInfo<>(list);
    }

    @Override
    public List<SysUser> listUser(UserQueryReq userQueryReq) {
        return this.sysUserMapper.listUser(userQueryReq);
    }
}
