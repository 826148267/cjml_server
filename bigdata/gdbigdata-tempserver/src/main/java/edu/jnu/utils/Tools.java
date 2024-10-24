package edu.jnu.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.SecureRandom;

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
}
