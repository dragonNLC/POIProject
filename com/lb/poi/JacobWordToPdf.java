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
			String wordFile = "E:\\企石\\企石镇政务服务中心办事指南（镇级）.docx";
			String pdfFile = "E:\\企石\\企石镇政务服务中心办事指南（镇级）.pdf";
			dis = Dispatch.call(dox, "Open", wordFile).toDispatch();
			File coverFile = new File(pdfFile);
			if (coverFile.exists()) {
				coverFile.delete();
			}
			Dispatch.call(dis, "SaveAs", coverFile, 17);
			System.out.println("生成结束！");
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
