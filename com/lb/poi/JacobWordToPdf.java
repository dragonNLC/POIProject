package com.lb.poi;

import java.io.File;

import javax.print.Doc;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class JacobWordToPdf {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//System.out.println(System.getProperty("java.library.path"));
		wordToPdf();
	}
	
	public static void wordToPdf() {
		ActiveXComponent atx = null;
		Dispatch dis = null;
		try {
			atx = new ActiveXComponent("Word.Application");
			atx.setProperty("Visible", new Variant(false));
			Dispatch dox = atx.getProperty("Documents").toDispatch();
			String wordFile = "E:\\��ʯ\\��ʯ������������İ���ָ�ϣ��򼶣�.docx";
			String pdfFile = "E:\\��ʯ\\��ʯ������������İ���ָ�ϣ��򼶣�.pdf";
			dis = Dispatch.call(dox, "Open", wordFile).toDispatch();
			File coverFile = new File(pdfFile);
			if (coverFile.exists()) {
				coverFile.delete();
			}
			Dispatch.call(dis, "SaveAs", coverFile, 17);
			System.out.println("���ɽ�����");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (dis != null) {
				Dispatch.call(dis, "Close", false);
			}
			if (atx != null) {
				atx.invoke("Quit", new Variant[]{});
			}
		}
		ComThread.Release();
	}

}
