package com.lb.poi;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.spire.pdf.PdfDocument;

public class SpirePdf2Pic {
	
	public static void main(String[] args) throws IOException {
		PdfDocument doc = new PdfDocument();
		doc.loadFromFile("E:\\房屋继承登记.docx.pdf");
		BufferedImage image;
		for (int i = 0; i < doc.getPages().getCount(); i++) {
		image = doc.saveAsImage(i);
		File file = new File("E:\\" + String.format("ToImage-img-%d.png", i));
		ImageIO.write(image, "PNG", file);
		}
		doc.close();
	}

}
