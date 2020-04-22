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

public class Pdf2Pic {
	
	public static void pdf2Pic(String pdfPath, String path) {
		Document document = new Document();
		document.setFile(pdfPath);
		// 缩放比例
		float scale = 5.0f;
		// 旋转角度
		float rotation = 0f;
		File folderFile = new File(path);
		/*if (!folderFile.exists() || folderFile.isDirectory()) {
			folderFile.mkdirs();
			//System.out.println("是文件夹！");
		} else {
			//System.out.println("不是文件夹！" + path + folderFile.isDirectory());
		}*/
		if (!folderFile.getParentFile().exists()) {
			folderFile.getParentFile().mkdirs();
		}
		BufferedImage longImage = null;

		int translateMinX = -1;
		int translateMaxX = 0;
		
		for (int i = 0; i < document.getNumberOfPages(); i++) {
		
			BufferedImage image = (BufferedImage) document.getPageImage(i, GraphicsRenderingHints.SCREEN,
					org.icepdf.core.pobjects.Page.BOUNDARY_CROPBOX, rotation, scale);

			int translateMinY = 0;//最小透明Y值
			int translateMaxY = image.getHeight();//最大透明Y值
			
			
			//将图片的白色背景转换为透明
			ImageIcon imageIcon = new ImageIcon(image);
			BufferedImage cache = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
			Graphics2D graphics2d = cache.createGraphics();
			graphics2d.drawImage(imageIcon.getImage(), 0, 0, imageIcon.getImageObserver());
			int alpha = 0;
			for (int j1 = image.getMinY(); j1 < cache.getHeight(); j1++) {
				
				int translateTempMinX = -1;
				int translateTempMaxX = 0;
				
				for (int j2 = image.getMinX(); j2 < cache.getWidth(); j2++) {
					int rgb = cache.getRGB(j2, j1);
					int R = (rgb & 0xff0000) >> 16;
					int G = (rgb & 0xff00) >> 8;
					int B = (rgb & 0xff);
					if ((((255 - R) < 30) && ((255 - G) < 30) && ((255 - B) < 30)) 
							|| j1 < 5 || j2 == cache.getWidth() - 1) {
						rgb = ((alpha + 1) << 24) | (rgb & 0x00ffffff);
					} else {
						//如果开始出现不是白色像素的时候，则表示此时是最小透明Y值
						if (translateMinY <= 0) {
							translateMinY = j1;
						}
						//最大透明Y值的话需要判断最后出现不是白色像素点时候的Y值
						translateMaxY = j1;
						
						if (translateTempMinX < 0) {
							translateTempMinX = j2;
						}
						translateTempMaxX = j2;
					}
					//如果是黑色的，转换成白色
					/*if (((R > 0) && (G > 0) && (B > 0)) 
							|| j1 < 5 || j2 == cache.getWidth() - 1) {
						rgb = 0xffffffff;
					}*/
					cache.setRGB(j2, j1, rgb);
				}

		        
				if (translateMinX == -1) {
					if (translateTempMinX != -1) {
						//System.out.println("translateMinX1 = " + translateMinX);
						//System.out.println("translateTempMinX = " + translateTempMinX);
						translateMinX = translateTempMinX;
					}
				} else {
					if (translateTempMinX < translateMinX && translateTempMinX != -1) {
						translateMinX = translateTempMinX;
					}
				}
		        //System.out.println("translateMinX2 = " + translateMinX);
		        
				if (translateMaxX == 0) {
					translateMaxX = translateTempMaxX;
				} else {
					if (translateTempMaxX > translateMaxX) {
						translateMaxX = translateTempMaxX;
					}
				}
			}
			graphics2d.drawImage(cache, 0, 0, imageIcon.getImageObserver());
			//白色背景转透明结束
			

			//裁剪Y
			if (translateMinY >= 20) {
				translateMinY -= 20;
			} else {
				translateMinY = 0;
			}

			if (cache.getHeight() - translateMaxY >= 20) {
				if (i == document.getNumberOfPages() - 1) {
					if (cache.getHeight() - translateMaxY >= 300) {
						translateMaxY += 300;
					} else {
						translateMaxY = cache.getHeight();
					}
				} else {
					translateMaxY += 20;
				}
			} else {
				translateMaxY = cache.getHeight();
			}
			
	        ImageFilter cropFilter = new CropImageFilter(0, translateMinY, cache.getWidth(), translateMaxY - translateMinY);  
	        Image img = Toolkit.getDefaultToolkit().createImage(  
	                new FilteredImageSource(cache.getSource(), cropFilter));  
	        BufferedImage tag = new BufferedImage(cache.getWidth(), translateMaxY - translateMinY,  
	                BufferedImage.TYPE_INT_ARGB); 
	        Graphics g = tag.getGraphics();  
	        g.drawImage(img, 0, 0, null); // 绘制小图  
	        g.dispose();   
	        //裁剪结束
			
	        //生成小图片
			/*RenderedImage rendImage = tag;
			try {
				String imgName = i + ".png";
				//System.out.println(imgName);
				File file = new File(path + imgName);
				ImageIO.write(rendImage, "png", file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			tag.flush();*/
			//生成小图片结束
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (i > 0) {
				longImage = mergeImage(longImage, tag);
			} else {
				longImage = tag;
			}
		}

		//裁剪X
		if (translateMinX >= 20) {
			translateMinX -= 20;
		} else {
			translateMinX = 0;
		}

		if (longImage.getWidth() - translateMaxX >= 20) {
			translateMaxX += 20;
		} else {
			translateMaxX = longImage.getWidth();
		}
		
        ImageFilter cropFilter = new CropImageFilter(translateMinX, 0, translateMaxX - translateMinX, longImage.getHeight());  
        Image img = Toolkit.getDefaultToolkit().createImage(  
                new FilteredImageSource(longImage.getSource(), cropFilter));  
        BufferedImage dest = new BufferedImage(translateMaxX - translateMinX, longImage.getHeight(),  
                BufferedImage.TYPE_INT_ARGB); 
        Graphics g = dest.getGraphics();  
        g.drawImage(img, 0, 0, null); // 绘制小图  
        g.dispose();   
        //裁剪结束
        
		//保存合并的图片
		RenderedImage rendLongImage = dest;
		try {
			String imgName = ".png";
			//File file = new File(path + imgName);
			File file = new File(path);
			ImageIO.write(rendLongImage, "png", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		document.dispose();
	}
	
	//匹配本地文件夹内的文件
	private static void cover(String path) {
		File file = new File(path);
        File[] childFile = file.listFiles();
        for(int i = 0; i < childFile.length; i++) {
        	File f = childFile[i];
        	if (f.isFile() && f.getName().endsWith(".pdf")) {
        		System.out.println("正在处理：" + f.getAbsolutePath());
        		String parentPath = f.getParentFile().getAbsolutePath();
        		String pdfFile = parentPath + File.separator + "导出图片" + File.separator + f.getName().replace(".pdf", ".png");
        		File toPdfFile = new File(pdfFile);
        		if (!toPdfFile.exists()) {
        			pdf2Pic(f.getAbsolutePath(), pdfFile);
				} else {
					System.out.println(f.getAbsolutePath() + "文件已存在，跳过！");
				}
			} else if (f.isDirectory()) {
				cover(f.getAbsolutePath());
			}
        }
	}
	
	//图片合并
	public static BufferedImage mergeImage(BufferedImage first, BufferedImage second) {
		int w1 = first.getWidth();
		int h1 = first.getHeight();
		int w2 = second.getWidth();
		int h2 = second.getHeight();
		System.out.println("w1 = " + w1 + " --- h1 = " + h1 + " --- w2 = " + w2 + " --- h2 = " + h2);
		
		int[] imageArrayFirst = new int[w1 * h1];
		imageArrayFirst = first.getRGB(0,  0, w1, h1, imageArrayFirst, 0, w1);
		
		int[] imageArraySecond = new int[w2 * h2];
		imageArraySecond = second.getRGB(0, 0, w2, h2, imageArraySecond, 0, w2);
		
		BufferedImage destImage = new BufferedImage(w1 > w2 ? w1 : w2, h1 + h2, BufferedImage.TYPE_4BYTE_ABGR);
		destImage.setRGB(0,  0,  w1, h1, imageArrayFirst, 0, w1);
		destImage.setRGB(0,  h1, w2, h2, imageArraySecond, 0, w2);
		return destImage;
	}
	


	public static void main(String[] args) {
		String filePath = "F:\\公司文件夹\\企石\\企石文档（已分类）";
		cover(filePath);
		System.out.println("完成！");
	}
	
}
