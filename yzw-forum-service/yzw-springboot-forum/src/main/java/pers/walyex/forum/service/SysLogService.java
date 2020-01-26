package pers.walyex.forum.service;

import pers.walyex.order.model.SysLog;

/**
 * 日志服务接口
 *
 * @author Waldron Ye
 * @date 2020/1/26 14:02
 */
public interface SysLogService {

    /**
     * 添加日志
     *
     * @param sysLog 日志model
     * @return 新增记录数
     */
    int addSysLog(SysLog sysLog);

    /**
     * 删除48小时之前的日志
     *
     * @return 删除记录数
     */
    int deleteBefore48HourLog();
}
