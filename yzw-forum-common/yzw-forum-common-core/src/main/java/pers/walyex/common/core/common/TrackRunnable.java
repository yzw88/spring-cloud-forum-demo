package pers.walyex.common.core.common;

import org.slf4j.MDC;
import pers.walyex.common.core.constant.CommonConstant;

public abstract class TrackRunnable implements Runnable {

    private String trackId = MDC.get(CommonConstant.THREAD_ID);

    @Override
    public void run() {

        try {
            MDC.put(CommonConstant.THREAD_ID, trackId);
            trackRun();
        } finally {
            MDC.remove(CommonConstant.THREAD_ID);
        }
    }

    protected abstract void trackRun();
}
