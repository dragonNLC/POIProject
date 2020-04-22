package com.lb.poi;

import java.io.File;
import java.io.IOException;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

import icepdf.e;

public class OpenOfficeWordToPdf {
    public static void WordToPDF(String startFile, String overFile) throws Exception {
        // 源文件目录
        File inputFile = new File(startFile);
        if (!inputFile.exists()) {
            System.out.println("源文件不存在！");
            return;
        }

        // 输出文件目录
        File outputFile = new File(overFile);
        if (!outputFile.getParentFile().exists()) {
            outputFile.getParentFile().exists();
        }

        // 调用openoffice服务线程
        /** 我把openOffice下载到了 C:/Program Files (x86)/下  ,下面的写法自己修改编辑就可以**/
        //String command = "C:/Program Files (x86)/OpenOffice 4/program/soffice.exe -headless -accept=\"socket,host=127.0.0.1,port=8100;urp;\"";
        String command = "C:/Program Files (x86)/OpenOffice 4/program/soffice.exe -headless -accept=\"socket,host=127.0.0.1,port=8100;urp;\"";
        Process p = Runtime.getRuntime().exec(command);

        // 连接openoffice服务
        OpenOfficeConnection connection = new SocketOpenOfficeConnection("127.0.0.1", 8100);
        connection.connect();

        // 转换
        DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
        converter.convert(inputFile, outputFile);

        // 关闭连接
        connection.disconnect();

        // 关闭进程
        p.destroy();
    }

    public static void main(String[] args) {
        String rootPath = "F:\\公司文件夹\\横栏人大\\AppleTimes\\横栏人大\\展示机\\中山市横栏镇人民代表大会\\2-中山市横栏镇人民代表大会办公室\\3-坚持党的领导";
        ///String rootPath = "F:\\公司文件夹\\横栏人大\\AppleTimes\\中山市横栏镇人民代表大会\\1-中山市横栏镇人民代表大会主席团\\3-2019年闭会期间主席团第一、二、三次会议\\2-横栏镇第十六届人民代表大会2019年（闭会期间）第二次主席团会议\\2019年闭会期间主席团第二次会议材料";
        cover(rootPath);
        System.out.println("完成！");
    }

    private static void cover(String path) {
        File file = new File(path);
        File[] childFile = file.listFiles();
        if (childFile != null) {
            for (int i = 0; i < childFile.length; i++) {
                File f = childFile[i];
                if (f.isFile() && (f.getName().endsWith(".docx") || f.getName().endsWith(".doc") || f.getName().endsWith(".ppt") || f.getName().endsWith(".pptx")
                        || f.getName().endsWith(".xls") || f.getName().endsWith(".xlsx") || f.getName().endsWith(".rtf"))) {
                    String parentPath = f.getParentFile().getAbsolutePath();
                    String pdfFile = "";
                    if (f.getName().endsWith(".doc")) {
                        pdfFile = parentPath + File.separator + f.getName().replace(".doc", ".pdf");
                    } else if (f.getName().endsWith(".docx")) {
                        pdfFile = parentPath + File.separator + f.getName().replace(".docx", ".pdf");
                    } else if (f.getName().endsWith(".ppt")) {
                        pdfFile = parentPath + File.separator + f.getName().replace(".ppt", ".pdf");
                    } else if (f.getName().endsWith(".pptx")) {
                        pdfFile = parentPath + File.separator + f.getName().replace(".pptx", ".pdf");
                    } else if (f.getName().endsWith(".xls")) {
                        pdfFile = parentPath + File.separator + f.getName().replace(".xls", ".pdf");
                    } else if (f.getName().endsWith(".xlsx")) {
                        pdfFile = parentPath + File.separator + f.getName().replace(".xlsx", ".pdf");
                    } else if (f.getName().endsWith(".rtf")) {
                        pdfFile = parentPath + File.separator + f.getName().replace(".rtf", ".pdf");
                    }
                    File toPdfFile = new File(pdfFile);
                    if (!toPdfFile.exists()) {
                        System.out.println(f.getAbsolutePath() + "开始转换！");
                        try {
                            WordToPDF(f.getAbsolutePath(), pdfFile);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("pdf文件已存在，跳过当前文件！");
                    }
                } else if (f.isDirectory()) {
                    cover(f.getAbsolutePath());
                }
            }
        }
    }

}
