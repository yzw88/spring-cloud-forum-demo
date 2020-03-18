package pers.walyex.forum.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pers.walyex.forum.service.SysLogService;

import javax.annotation.Resource;

/**
 * 日志定时任务
 *
 * @author Waldron Ye
 * @date 2020/1/26 14:15
 */
@Component
@Slf4j
public class SysLogJob {

    @Resource
    private SysLogService sysLogService;

    //    @Scheduled(cron = "0 0 12 * * ?")
//    @Scheduled(cron = "0/5 * * * * ?")
    private void logJob() {
        log.info("定时任务执行======");
        int count = this.sysLogService.deleteBefore48HourLog();
        log.info("删除的记录数为:count={}", count);

    }
}
