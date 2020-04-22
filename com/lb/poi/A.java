package com.lb.poi;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;

import javax.imageio.ImageIO;


public class A {

	public static void main(String[] args) {
		//System.out.print("s = " + Math.pow((255 * 255 * 255 - (108 * 108 * 108)), (1 / 3d)));
		/*System.out.println("MAX_VALUE = " + Integer.MAX_VALUE);
		System.out.println("MAX_VALUE2 = " + new BigDecimal(Math.pow(2, 31)).toString());
		System.out.println("MAX_VALUE2 = " + new BigDecimal(Math.pow(2, 24)).toString());
		//System.out.print("s = " + Math.pow((255 * 255 * 255 - (108 * 108 * 108)), (1 / 3d)));
		// TODO Auto-generated method stub
		//System.out.print("s = " +  (((0 + 1) << 24) | (0xff000000 & 0x00ffffff)));
		try {
			BufferedImage image = ImageIO.read(new File("E:\\公司文件备份\\工作\\新资料\\火炬开发区\\资料3.2\\整理后\\火炬开发区人大工委会\\前言\\导出图片\\2.png"));
			for (int j1 = image.getMinY(); j1 < image.getHeight(); j1++) {
				for (int j2 = image.getMinX(); j2 < image.getWidth(); j2++) {
					int rgb = image.getRGB(j2, j1);
					int A = (rgb & 0xff000000) >> 24;
					int R = (rgb & 0xff0000) >> 16;
					int G = (rgb & 0xff00) >> 8;
					int B = (rgb & 0xff); 
					printNum(rgb);
					System.out.println("rgb = " + rgb + " A = " + A + " R = " + R + "--- G = " + G + "--- B = " + B);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		File file = new File("E:\\ce shi" + File.separator + "a.txt");
		if (!file.getParentFile().exists()) {
			file.mkdirs();
		}
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			fileOutputStream.write("a".getBytes());
			fileOutputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void printNum(int n){
	    String num = Integer.toBinaryString(n);
        System.out.println(num);
	    if(num.length() == 32){
	        System.out.println(num);
	    }else{
	        StringBuilder sb = new StringBuilder("");
	        for(int i =0;i < 32 - num.length(); i ++){
	        sb.append("0");
	        }
	        System.out.println(sb.toString() + num);
	    }
	}

}
