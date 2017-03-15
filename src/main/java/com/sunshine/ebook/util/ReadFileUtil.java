package com.sunshine.ebook.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

public class ReadFileUtil {

    public static String readForPage(File sourceFile, int pageNo, int pageSize) throws IOException {
        StringBuffer readContent = new StringBuffer();
        FileReader in = new FileReader(sourceFile);
        LineNumberReader reader = new LineNumberReader(in);
        String temp = "";
        if (pageNo <= 0 || pageNo > getTotalLines(sourceFile)) {
            System.out.println("不在文件的行数范围(1至总行数)之内。");
            System.exit(0);
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
                //System.out.println("line:" + lines + ":" + s);
                readContent.append(temp + "\n");
            }
        }
        reader.close();
        in.close();
        return readContent.toString();
    }

    public static int getTotalLines(File sourceFile) throws IOException {
        FileReader in = new FileReader(sourceFile);
        LineNumberReader reader = new LineNumberReader(in);
        String lineRead = "";
        while ((lineRead = reader.readLine()) != null) {
        }
        int count = reader.getLineNumber();
        return count;
    }

    public static void main(String[] args) {
        // 指定读取的行号
        int lineNumber = 2;
        // 读取文件
        File sourceFile = new File("C:\\Users\\LMG\\Desktop\\透视高手.txt");

        try {
            System.out.println(readForPage(sourceFile, 1, 20));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
