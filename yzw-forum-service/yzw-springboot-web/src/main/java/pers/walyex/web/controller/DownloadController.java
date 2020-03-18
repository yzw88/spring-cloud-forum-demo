package pers.walyex.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class DownloadController {

    @GetMapping("/downloadTest")
    public void downloadTest(HttpServletResponse response) throws IOException {
        log.info("downloadTest===");

        String path = "D:\\local\\工作任务安排-Waldron Ye-2-10~3-2.xlsx";

        InputStream fis = null;
        OutputStream toClient = null;

        try {
            // path是指欲下载的文件的路径。
            File file = new File(path);
            // 取得文件名。
            String filename = file.getName();

            // 以流的形式下载文件。
            fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);

            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes(StandardCharsets.UTF_8), "ISO8859-1"));
            response.addHeader("Content-Length", "" + file.length());
            toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();

        } catch (Exception e) {
            log.info("下载文件异常");
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            if (toClient != null) {
                toClient.close();
            }
        }

    }

    @GetMapping("/downloadLocal")
    public void downloadLocal(HttpServletResponse response) throws FileNotFoundException, UnsupportedEncodingException {
        String path = "D:\\local\\工作任务安排-Waldron Ye-2-10~3-2.xlsx";

        // path是指欲下载的文件的路径。
        File file = new File(path);
        // 取得文件名。
        String filename = file.getName();
        // 读到流中
        InputStream inStream = new FileInputStream(file);
        filename = new String(filename.getBytes(StandardCharsets.UTF_8), "ISO8859-1");
        // 设置输出的格式
        response.reset();
        response.setContentType("bin");
        response.addHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
        // 循环取出流中的数据
        byte[] b = new byte[100];
        int len;
        try {
            while ((len = inStream.read(b)) > 0)
                response.getOutputStream().write(b, 0, len);
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
