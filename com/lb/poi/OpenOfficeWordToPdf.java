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
        // Դ�ļ�Ŀ¼
        File inputFile = new File(startFile);
        if (!inputFile.exists()) {
            System.out.println("Դ�ļ������ڣ�");
            return;
        }

        // ����ļ�Ŀ¼
        File outputFile = new File(overFile);
        if (!outputFile.getParentFile().exists()) {
            outputFile.getParentFile().exists();
        }

        // ����openoffice�����߳�
        /** �Ұ�openOffice���ص��� C:/Program Files (x86)/��  ,�����д���Լ��޸ı༭�Ϳ���**/
        //String command = "C:/Program Files (x86)/OpenOffice 4/program/soffice.exe -headless -accept=\"socket,host=127.0.0.1,port=8100;urp;\"";
        String command = "C:/Program Files (x86)/OpenOffice 4/program/soffice.exe -headless -accept=\"socket,host=127.0.0.1,port=8100;urp;\"";
        Process p = Runtime.getRuntime().exec(command);

        // ����openoffice����
        OpenOfficeConnection connection = new SocketOpenOfficeConnection("127.0.0.1", 8100);
        connection.connect();

        // ת��
        DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
        converter.convert(inputFile, outputFile);

        // �ر�����
        connection.disconnect();

        // �رս���
        p.destroy();
    }

    public static void main(String[] args) {
        String rootPath = "F:\\��˾�ļ���\\�����˴�\\AppleTimes\\�����˴�\\չʾ��\\��ɽ�к��������������\\2-��ɽ�к��������������칫��\\3-��ֵ����쵼";
        ///String rootPath = "F:\\��˾�ļ���\\�����˴�\\AppleTimes\\��ɽ�к��������������\\1-��ɽ�к����������������ϯ��\\3-2019��ջ��ڼ���ϯ�ŵ�һ���������λ���\\2-�������ʮ�������������2019�꣨�ջ��ڼ䣩�ڶ�����ϯ�Ż���\\2019��ջ��ڼ���ϯ�ŵڶ��λ������";
        cover(rootPath);
        System.out.println("��ɣ�");
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
                        System.out.println(f.getAbsolutePath() + "��ʼת����");
                        try {
                            WordToPDF(f.getAbsolutePath(), pdfFile);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("pdf�ļ��Ѵ��ڣ�������ǰ�ļ���");
                    }
                } else if (f.isDirectory()) {
                    cover(f.getAbsolutePath());
                }
            }
        }
    }

}
