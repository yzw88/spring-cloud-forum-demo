package pers.walyex.demo.poi;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hslf.model.Slide;
import org.apache.poi.hslf.model.TextRun;
import org.apache.poi.hslf.usermodel.RichTextRun;
import org.apache.poi.hslf.usermodel.SlideShow;
import org.apache.poi.xslf.usermodel.*;
import org.junit.jupiter.api.Test;
import pers.walyex.common.util.FastJsonUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class PPTConvertImageTest {

    @Test
    public void test() {
        String pptPath = "D:\\logs\\ppt\\test.pptx";
        String imagePath = "D:\\logs\\image";
        List<String> imageFileNames = new ArrayList<>();
        boolean flag = doPPT2007toImage(new File(pptPath), new File(imagePath), imageFileNames);
        log.info("flag={},imageFileNames={}", flag, FastJsonUtil.toJson(imageFileNames));
    }


    /**
     * ppt2007文档的转换 后缀为.pptx
     *
     * @param pptFile PPT文件
     * @param imgFile 图片将要保存的路径目录（不是文件）
     * @param list    存放文件名的 list
     * @return
     */
    public static boolean doPPT2007toImage(File pptFile, File imgFile, List<String> list) {


        FileInputStream is = null;


        try {

            is = new FileInputStream(pptFile);

            XMLSlideShow xmlSlideShow = new XMLSlideShow(is);

            is.close();

            // 获取大小
            Dimension pgsize = xmlSlideShow.getPageSize();

            // 获取幻灯片
            XSLFSlide[] slides = xmlSlideShow.getSlides();

            for (int i = 0; i < slides.length; i++) {


                // 解决乱码问题
                XSLFShape[] shapes = slides[i].getShapes();
                for (XSLFShape shape : shapes) {

                    if (shape instanceof XSLFTextShape) {
                        XSLFTextShape sh = (XSLFTextShape) shape;
                        List<XSLFTextParagraph> textParagraphs = sh.getTextParagraphs();

                        for (XSLFTextParagraph xslfTextParagraph : textParagraphs) {
                            List<XSLFTextRun> textRuns = xslfTextParagraph.getTextRuns();
                            for (XSLFTextRun xslfTextRun : textRuns) {
                                xslfTextRun.setFontFamily("宋体");
                            }
                        }
                    }
                }


                //根据幻灯片大小生成图片
                BufferedImage img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.TYPE_INT_RGB);
                Graphics2D graphics = img.createGraphics();

                graphics.setPaint(Color.white);
                graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));

                // 最核心的代码
                slides[i].draw(graphics);

                //图片将要存放的路径
                String absolutePath = imgFile.getAbsolutePath() + "/" + (i + 1) + ".jpeg";
                File jpegFile = new File(absolutePath);
                // 图片路径存放
                list.add((i + 1) + ".jpeg");

                //如果图片存在，则不再生成
                if (jpegFile.exists()) {
                    continue;
                }
                // 这里设置图片的存放路径和图片的格式(jpeg,png,bmp等等),注意生成文件路径
                FileOutputStream out = new FileOutputStream(jpegFile);

                // 写入到图片中去
                ImageIO.write(img, "jpeg", out);

                out.close();

            }


            log.error("PPT转换成图片 成功！");

            return true;

        } catch (Exception e) {
            log.error("PPT转换成图片 发生异常！", e);
        }


        return false;

    }


    /**
     * ppt2003 文档的转换 后缀名为.ppt
     *
     * @param pptFile ppt文件
     * @param imgFile 图片将要保存的目录（不是文件）
     * @return
     */
    public static boolean doPPT2003toImage(File pptFile, File imgFile, List<String> list) {


        try {

            FileInputStream is = new FileInputStream(pptFile);
            SlideShow ppt = new SlideShow(is);

            //及时关闭掉 输入流
            is.close();

            Dimension pgsize = ppt.getPageSize();
            Slide[] slide = ppt.getSlides();

            for (int i = 0; i < slide.length; i++) {

                log.info("第" + i + "页。");

                TextRun[] truns = slide[i].getTextRuns();

                for (int k = 0; k < truns.length; k++) {

                    RichTextRun[] rtruns = truns[k].getRichTextRuns();

                    for (int l = 0; l < rtruns.length; l++) {

                        // 原有的字体索引 和 字体名字
                        int index = rtruns[l].getFontIndex();
                        String name = rtruns[l].getFontName();

                        log.info("原有的字体索引 和 字体名字: " + index + " - " + name);

                        // 重新设置 字体索引 和 字体名称 是为了防止生成的图片乱码问题
                        rtruns[l].setFontIndex(1);
                        rtruns[l].setFontName("宋体");
                    }

                }

                //根据幻灯片大小生成图片
                BufferedImage img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.TYPE_INT_RGB);
                Graphics2D graphics = img.createGraphics();

                graphics.setPaint(Color.white);
                graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));
                slide[i].draw(graphics);

                // 图片的保存位置
                String absolutePath = imgFile.getAbsolutePath() + "/" + (i + 1) + ".jpeg";
                File jpegFile = new File(absolutePath);
                // 图片路径存放
                list.add((i + 1) + ".jpeg");

                // 如果图片存在，则不再生成
                if (jpegFile.exists()) {
                    continue;
                }

                // 这里设置图片的存放路径和图片的格式(jpeg,png,bmp等等),注意生成文件路径
                FileOutputStream out = new FileOutputStream(jpegFile);

                ImageIO.write(img, "jpeg", out);

                out.close();

            }

            log.error("PPT转换成图片 成功！");

            return true;

        } catch (Exception e) {
            log.error("PPT转换成图片 发生异常！", e);
        }

        return false;

    }

    @Test
    public void test2() {

        String pdfFile = "D:\\桌面资料\\我的考试资料\\a.pdf";
        String newFile = "D:\\桌面资料\\我的考试资料\\b.pdf";
        partitionPdfFile(pdfFile, newFile, 3,3);
    }

    public static void partitionPdfFile(String pdfFile,
                                        String newFile, int from, int end) {
        Document document = null;
        PdfCopy copy = null;
        try {
            PdfReader reader = new PdfReader(pdfFile);
            int n = reader.getNumberOfPages();
            if (end == 0) {
                end = n;
            }

            document = new Document(reader.getPageSize(1));
            copy = new PdfCopy(document, new FileOutputStream(newFile));
            document.open();
            for (int j = from; j <= end; j++) {
                document.newPage();
                PdfImportedPage page = copy.getImportedPage(reader, j);
                copy.addPage(page);
            }
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
