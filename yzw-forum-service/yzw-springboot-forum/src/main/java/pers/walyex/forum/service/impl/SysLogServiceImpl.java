package pers.walyex.forum.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.walyex.forum.dao.SysLogMapper;
import pers.walyex.forum.service.SysLogService;
import pers.walyex.order.model.SysLog;

import javax.annotation.Resource;

/**
 * 日志服务接口实现类
 *
 * @author Waldron Ye
 * @date 2020/1/26 14:03
 */
@Service
public class SysLogServiceImpl implements SysLogService {

    @Resource
    private SysLogMapper sysLogMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addSysLog(SysLog sysLog) {
        return this.sysLogMapper.insertSelective(sysLog);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteBefore48HourLog() {
        return this.sysLogMapper.deleteBefore48HourLog();
    }
}
