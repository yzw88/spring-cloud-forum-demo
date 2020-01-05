package pers.walyex.web.handle;

import lombok.extern.slf4j.Slf4j;
import pers.walyex.common.core.common.TrackRunnable;

@Slf4j
public class PayNotifyHandle extends TrackRunnable {
    @Override
    public void trackRun() {
        log.info("线程号测试===");
    }
}
