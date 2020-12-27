package pers.walyex.demo.pdf;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDPage;

import javax.imageio.ImageIO;
import java.io.File;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class PdfHandle implements Runnable {

    private List<?> allPages;

    private String basePath;

    private CountDownLatch latch;

    private int start;

    @Override
    public void run() {
        log.info("子线程" + Thread.currentThread().getName() + "开始执行");
        try {
            for (int i = 0; i < allPages.size(); i++) {
                PDPage page = (PDPage) allPages.get(i);
                ImageIO.write(page.convertToImage(), "PNG", new File(basePath.replace("i", String.valueOf(i + start))));
            }
        } catch (Exception e) {
            log.error("异常", e);
        } finally {
            //当前线程调用此方法，则计数减一
            latch.countDown();
            log.info("子线程" + Thread.currentThread().getName() + "执行完成");

        }
    }

    public PdfHandle(List<?> allPages, String basePath, CountDownLatch latch, int start) {
        this.allPages = allPages;
        this.basePath = basePath;
        this.latch = latch;
        this.start = start;
    }

}
