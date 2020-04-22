package com.lb.poi;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.w3c.dom.Document;

public class PoiWordToHtml {
	
	public static void main(String[] args) {
		try {
            wordToHtml("E:\\ConverToHtml\\���㶫ʡ����Ȩ�汣��������.doc", 
            		"E:\\ConverToHtml\\���㶫ʡ����Ȩ�汣��������.html");
            wordToHtml("E:\\ConverToHtml\\�ⶫ�������������Ƚ��������쵼С��.doc", 
            		"E:\\ConverToHtml\\�ⶫ�������������Ƚ��������쵼С��.html");
            wordToHtml("E:\\ConverToHtml\\�ⶫ��������Э����С����֯�ܹ�.doc", 
            		"E:\\ConverToHtml\\�ⶫ��������Э����С����֯�ܹ�.html");
            wordToHtml("E:\\ConverToHtml\\�ⶫ��������Э�Ṥ���ƶ�.doc", 
            		"E:\\ConverToHtml\\�ⶫ��������Э�Ṥ���ƶ�.html");
            wordToHtml("E:\\ConverToHtml\\�ⶫ��������Э��ܹ�.doc", 
            		"E:\\ConverToHtml\\�ⶫ��������Э��ܹ�.html");
            wordToHtml("E:\\ConverToHtml\\�»��ⶫ�������������㣨2017.12.30��.doc", 
            		"E:\\ConverToHtml\\�»��ⶫ�������������㣨2017.12.30��.html");
        } catch (IOException ex) {
            //Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        	ex.printStackTrace();
        } catch (ParserConfigurationException ex) {
            //Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        	ex.printStackTrace();
        } catch (TransformerException ex) {
            //Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        	ex.printStackTrace();
        }
		/*try {
			FileInputStream fis = new FileInputStream(new File("E:\\ConverToHtml\\���㶫ʡ����Ȩ�汣��������.doc"));
			byte[] buffer = new byte[fis.available()];
			int length = 0;
			StringBuffer stringBuffer = new StringBuffer();
			while((length = fis.read(buffer)) != -1) {
				stringBuffer.append(new String(buffer, "GBK"));
			}
			System.out.println(stringBuffer.toString());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	 /**
     * Word תΪ��HTML
     *
     * @param fileName
     * @param outputFile
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws TransformerException
     */
    public static void wordToHtml(String fileName, String outputFile) throws
            IOException, ParserConfigurationException, TransformerException {
        HWPFDocument wordDoc = new HWPFDocument(new FileInputStream(fileName));

        WordToHtmlConverter wthc = new WordToHtmlConverter(
                DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());

        wthc.setPicturesManager(new PicturesManager() {

            @Override
            public String savePicture(byte[] bytes, PictureType pt, String string, float f, float f1) {
                return string;
            }

        });

        wthc.processDocument(wordDoc);

        List<Picture> pics = wordDoc.getPicturesTable().getAllPictures();
        if (null != pics && pics.size() > 0) {
            for (Picture pic : pics) {
                pic.writeImageContent(new FileOutputStream(pic.suggestFullFileName()));
            }
        }

        Document htmlDocument = wthc.getDocument();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DOMSource domSource = new DOMSource(htmlDocument);
        StreamResult streamResult = new StreamResult(out);

        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer serializer = tf.newTransformer();
        serializer.setOutputProperty(OutputKeys.ENCODING, "GB2312");
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");
        serializer.setOutputProperty(OutputKeys.METHOD, "html");
        serializer.transform(domSource, streamResult);

        out.close();

        String htmlStr = new String(out.toByteArray());
        writeFile(htmlStr, outputFile);
    }

    public static void writeFile(String content, String path) {
        FileOutputStream fos = null;
        BufferedWriter bw = null;

        File file = new File(path);

       System.out.println("path" + file.getAbsolutePath());
       System.out.println("content" + content);

        try {
            fos = new FileOutputStream(file);

            bw = new BufferedWriter(new OutputStreamWriter(fos, "GB2312"));
            bw.write(content);
        } catch (FileNotFoundException ex) {
            //Logger.getLogger(PoiUtil.class.getName()).log(Level.SEVERE, null, ex);
        	ex.printStackTrace();
        } catch (UnsupportedEncodingException ex) {
            //Logger.getLogger(PoiUtil.class.getName()).log(Level.SEVERE, null, ex);
        	ex.printStackTrace();
        } catch (IOException ex) {
            //Logger.getLogger(PoiUtil.class.getName()).log(Level.SEVERE, null, ex);
        	ex.printStackTrace();
        } finally {
            try {
                if (null != bw) {
                    bw.close();
                }
                if (null != fos) {
                    fos.close();
                }
            } catch (IOException ex) {
                //Logger.getLogger(PoiUtil.class.getName()).log(Level.SEVERE, null, ex);
            	ex.printStackTrace();
            }

        }
    }
	
}
