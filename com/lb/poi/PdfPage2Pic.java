package com.lb.poi;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.icepdf.core.pobjects.Document;
import org.icepdf.core.util.GraphicsRenderingHints;

import icepdf.j;

public class PdfPage2Pic {

    public static void pdf2Pic(String pdfPath, String path) {
        Document document = new Document();
        document.setFile(pdfPath);
        // 缩放比例
        float scale = 5.0f;
        // 旋转角度
        float rotation = 0f;
        File folderFile = new File(path);

        if (!folderFile.exists()) {
            folderFile.mkdirs();
        }

        for (int i = 0; i < document.getNumberOfPages(); i++) {

            try {
                BufferedImage image = (BufferedImage) document.getPageImage(i, GraphicsRenderingHints.SCREEN,
                        org.icepdf.core.pobjects.Page.BOUNDARY_CROPBOX, rotation, scale);

                // 保存合并的图片
				String imgName = i + ".jpg";
                File file = new File(path, imgName);
                if (image != null) {
                    ImageIO.write(image, "jpg", file);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        document.dispose();
    }

    // 匹配本地文件夹内的文件
    private static void cover(String path) {
        File file = new File(path);
        File[] childFile = file.listFiles();
        for (int i = 0; i < childFile.length; i++) {
            File f = childFile[i];
            if (f.isFile() && f.getName().endsWith(".pdf")) {
                System.out.println("正在处理：" + f.getAbsolutePath());
                String parentPath = f.getParentFile().getAbsolutePath();
                String pdfFile = parentPath + File.separator + "导出图片" + File.separator + f.getName().replace(".pdf", "").trim();
                File toPdfFile = new File(pdfFile);
                if (!toPdfFile.exists() || (toPdfFile.isDirectory() && toPdfFile.listFiles().length <= 0)) {
                    pdf2Pic(f.getAbsolutePath(), pdfFile);
                } else {
                    System.out.println(f.getAbsolutePath() + "文件已存在，跳过！");
                }
            } else if (f.isDirectory()) {
                cover(f.getAbsolutePath());
            }
        }
    }

    // 图片合并
    public static BufferedImage mergeImage(BufferedImage first, BufferedImage second) {
        int w1 = first.getWidth();
        int h1 = first.getHeight();
        int w2 = second.getWidth();
        int h2 = second.getHeight();
        System.out.println("w1 = " + w1 + " --- h1 = " + h1 + " --- w2 = " + w2 + " --- h2 = " + h2);

        int[] imageArrayFirst = new int[w1 * h1];
        imageArrayFirst = first.getRGB(0, 0, w1, h1, imageArrayFirst, 0, w1);

        int[] imageArraySecond = new int[w2 * h2];
        imageArraySecond = second.getRGB(0, 0, w2, h2, imageArraySecond, 0, w2);

        BufferedImage destImage = new BufferedImage(Math.max(w1, w2), h1 + h2, BufferedImage.TYPE_4BYTE_ABGR);
        destImage.setRGB(0, 0, w1, h1, imageArrayFirst, 0, w1);
        destImage.setRGB(0, h1, w2, h2, imageArraySecond, 0, w2);
        return destImage;
    }

    public static void main(String[] args) {
        String filePath = "C:\\Users\\Administrator\\Desktop\\资料";
        //String filePath = "E:\\公司文件备份\\工作\\新资料\\火炬开发区\\资料3.2\\整理后\\火炬开发区人大工委会\\代表建议及办理\\2018年建议及回复";
        cover(filePath);
        System.out.println("完成！");
    }

}
