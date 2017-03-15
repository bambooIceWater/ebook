package com.sunshine.ebook.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import org.apache.hadoop.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
	private static FileReader in;
	@SuppressWarnings("unused")
	private static String lineRead;

	@SuppressWarnings("resource")
	public static String readForPage(String filePath, int pageNo, int pageSize, int lineCount) {
		StringBuffer readContent = new StringBuffer();
		try {
			File sourceFile = new File(filePath);
			FileReader in = new FileReader(sourceFile);
			LineNumberReader reader = new LineNumberReader(in);
			String temp = "";
			if (pageNo <= 0 || pageNo > lineCount) {
				logger.error("不在文件的行数范围(1至总行数)之内。");
				return extracted(readContent);
			}
			int startRow = (pageNo - 1) * pageSize + 1;
			int endRow = pageNo * pageSize;
			int lines = 0;
			System.out.println("startRow:" + startRow);
			System.out.println("endRow:" + endRow);
			while (temp != null) {
				lines++;
				temp = reader.readLine();
				if (lines >= startRow && lines <= endRow) {
					// System.out.println("line:" + lines + ":" + s);
					readContent.append(temp + "\n");
				}
			}
			reader.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return extracted(readContent);
	}

	private static String extracted(StringBuffer readContent) {
		return readContent.toString();
	}

	public static int getTotalLines(String filePath) {
		int count = 0;
		try {
			File sourceFile = new File(filePath);
			in = new FileReader(sourceFile);
			LineNumberReader reader = new LineNumberReader(in);
			lineRead = "";
			while ((lineRead = reader.readLine()) != null) {
			}
			count = reader.getLineNumber();
			reader = null;
			lineRead = null;
			in = null;
		} catch (Exception e) {
			logger.error("获取文件总行数出错，文件路径：" + filePath + "\n" + e);
			e.printStackTrace();
		}
		return count;
	}

	public static void convertEncoding(String targetEncode, String path) {
		try {
			File srcFile = new File(path);
			if (!srcFile.exists()) {
				return;
			}
			BytesEncodingDetect encode = new BytesEncodingDetect();
			int index = encode.detectEncoding(srcFile);
			String charset = BytesEncodingDetect.javaname[index];
			// 编码相同，无需转码
			if (charset.equalsIgnoreCase(targetEncode)) {
				return;
			}

			InputStream in = new FileInputStream(path);

			BufferedReader br = new BufferedReader(new InputStreamReader(in, charset));

			StringBuffer sb = new StringBuffer();
			String s1;
			while ((s1 = br.readLine()) != null) {
				String s = URLEncoder.encode(s1, targetEncode);
				sb.append(s + "\r\n");// 一行+回车
			}

			br.close();
			srcFile.delete();// 删除原来文件
			// 重新以新编码写入文件并返回值
			File newfile = new File(path);// 重新建原来的文件
			newfile.createNewFile();
			OutputStream out = new FileOutputStream(newfile);
			OutputStreamWriter writer = new OutputStreamWriter(out, targetEncode);
			BufferedWriter bw = new BufferedWriter(writer);
			bw.write(URLDecoder.decode(sb.toString(), targetEncode));
			URLDecoder.decode(sb.toString(), targetEncode);
			bw.flush();// 刷到文件中
			bw.close();
		} catch (Exception e) {
			logger.error("文件编码转换出错" + e);
			e.printStackTrace();
		}
	}
	
	public static void saveFileToPath(String path, InputStream inputStream, boolean convertFlag) {
		try {
			FileOutputStream out = new FileOutputStream(new File(path));
	        IOUtils.copyBytes(inputStream, out, 1024, true);
	        if (convertFlag) {
	        	//保存成功后，转换文件编码格式
		        convertEncoding("UTF8", path);
	        }
		} catch (Exception e) {
			logger.error("文件保存出错，文件路径：" + path + "\n" + e);
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
//		File sourceFile = new File("/Users/limingguang/Downloads/余罪11.txt");
//		BytesEncodingDetect encode = new BytesEncodingDetect();
//		int index = encode.detectEncoding(sourceFile);
//		String charset = BytesEncodingDetect.javaname[index];
//		System.out.println(charset);
		FileUtil.convertEncoding("UTF8", "/Users/limingguang/Downloads/余罪.txt");
	}

}
