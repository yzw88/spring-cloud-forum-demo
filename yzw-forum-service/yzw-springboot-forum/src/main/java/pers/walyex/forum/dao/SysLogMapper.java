package pers.walyex.forum.dao;

import pers.walyex.order.model.SysLog;

/**
 * 日志dao
 *
 * @author Waldron Ye
 * @date 2020/1/26 13:58
 */
public interface SysLogMapper {

    /**
     * 添加日志
     *
     * @param sysLog 日志model
     * @return 新增记录数
     */
    int insertSelective(SysLog sysLog);

    /**
     * 删除48小时之前的日志
     *
     * @return 删除记录数
     */
    int deleteBefore48HourLog();
}
