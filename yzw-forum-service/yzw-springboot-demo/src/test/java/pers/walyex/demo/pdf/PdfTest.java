package pers.walyex.demo.pdf;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.junit.jupiter.api.Test;
import pers.walyex.common.util.DateUtil;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class PdfTest {

    @Test
    public void pdfTest() {
        String path = "D:\\我的下载\\Java_manual.pdf";
        String basePath = "D:\\我的下载\\pdf\\" + DateUtil.format(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + "_i.png";
        long startTime = System.currentTimeMillis();
        PDDocument doc = null;
        try {
            doc = PDDocument.load(new File(path));
            List<?> allPages = doc.getDocumentCatalog().getAllPages();
            for (int i = 0; i < allPages.size(); i++) {
                PDPage page = (PDPage) allPages.get(i);
                ImageIO.write(page.convertToImage(), "PNG", new File(basePath.replace("i", String.valueOf(i))));
            }

            log.info("使用时间:userTime={}", System.currentTimeMillis() - startTime);
        } catch (Exception e) {
            log.error("转换图片异常");
        } finally {
            if (doc != null) {
                try {
                    doc.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void pdfTest2() {
        String path = "D:\\我的下载\\Java_manual.pdf";
        String basePath = "D:\\我的下载\\pdf2\\" + DateUtil.format(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + "_i.png";
        long startTime = System.currentTimeMillis();
        PDDocument doc = null;
        try {
            doc = PDDocument.load(new File(path));
            List<?> allPages = doc.getDocumentCatalog().getAllPages();
            int size = allPages.size();
            log.info("转换图片的数量:{}", size);
            //每页15行
            int pageSize = 15;
            //总页数 = (总记录数 + 每页数据大小 -1) / 每页数据大小;
            int totalPage = (size + pageSize - 1) / pageSize;
            log.info("总页数为:totalPage={}", totalPage);

            //使用多线程
            ExecutorService service = Executors.newFixedThreadPool(totalPage);
            final CountDownLatch latch = new CountDownLatch(totalPage);
            for (int i = 0; i < totalPage; i++) {
                int start = i * pageSize;
                int end;
                if (i == totalPage - 1) {
                    end = size;
                } else {
                    end = (i + 1) * pageSize;
                }
                log.info("获取分页:start={},end={}", start, end);
                System.out.println("获取分页:start={},end={}" + start + "," + end);
                PdfHandle pdfHandle = new PdfHandle(allPages.subList(start, end), basePath, latch, start);
                service.execute(pdfHandle);
            }

            log.info("主线程" + Thread.currentThread().getName() + "等待子线程执行完成...");
            //阻塞当前线程，直到计数器的值为0
            latch.await();
            log.info("使用时间:userTime={}", System.currentTimeMillis() - startTime);
        } catch (Exception e) {
            log.error("转换图片异常");
        } finally {
            if (doc != null) {
                try {
                    doc.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
