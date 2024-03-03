package edu.jnu.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Tools {
	/**
	 * 获取对象的文件类型.
	 * 根据数据对象的名字来判断文件的类型.
	 * @param fileName  文件名
	 * @return  该数据对象的文件类型
	 */
	public static String getContentType(String fileName){
		String fileExtension = fileName.substring(fileName.lastIndexOf("."));
		if(".bmp".equalsIgnoreCase(fileExtension)) return "image/bmp";
		if(".gif".equalsIgnoreCase(fileExtension)) return "image/gif";
		if(".jpeg".equalsIgnoreCase(fileExtension) || "jpg".equalsIgnoreCase(fileExtension)  || "png".equalsIgnoreCase(fileExtension) ) return "image/jpeg";
		if(".html".equalsIgnoreCase(fileExtension)) return "text/html";
		if(".txt".equalsIgnoreCase(fileExtension)) return "text/plain";
		if(".vsd".equalsIgnoreCase(fileExtension)) return "application/vnd.visio";
		if(".ppt".equalsIgnoreCase(fileExtension) || ".pptx".equalsIgnoreCase(fileExtension)) return "application/vnd.ms-powerpoint";
		if(".doc".equalsIgnoreCase(fileExtension) || ".docx".equalsIgnoreCase(fileExtension)) return "application/msword";
		if(".xml".equalsIgnoreCase(fileExtension)) return "text/xml";
		return "text/html";
	}

	/**
	 * 从文件名中回去文件前缀.
	 * 以"-"分割文件名originalFilename，例如data-text.txt则返回data
	 * @param originalFilename	文件名
	 * @return	去前缀的文件名
	 */
	public static String getPreNameInOriginalFile(String originalFilename) {
		return originalFilename.split("-")[0];
	}

	/**
	 * 判断两个输入流的内容是否相同
	 * @param input1	输入流1
	 * @param input2	输入流2
	 * @return	返回布尔值:1、如果输入流1和输入流2的内容相同则返回true，2、否则返回false
	 */
	public static boolean isSameContent(InputStream input1, InputStream input2) throws IOException {
		if (!(input1 instanceof BufferedInputStream)) {
			input1 = new BufferedInputStream(input1);
		}
		if (!(input2 instanceof BufferedInputStream)) {
			input2 = new BufferedInputStream(input2);
		}

		int ch = input1.read();
		while (-1 != ch) {
			int ch2 = input2.read();
			if (ch != ch2)
			{
				return false;
			}
			ch = input1.read();
		}

		int ch2 = input2.read();
		return (ch2 == -1);
	}
}
