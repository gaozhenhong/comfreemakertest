package com.wiwi.jsoil.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileUtil {

	/**
	 * @param filePath
	 * @param content
	 */
	public static void writeFile(String filePath , StringBuffer content){
		FileOutputStream os = null;
		try {
			File file = new File(filePath);
			file.createNewFile();
			os = new FileOutputStream(file);
			os.write(content.toString().getBytes("UTF-8"));
			os.flush();
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(os != null){
				try {
					os.flush();
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static StringBuffer getFileContent(String filePath ){
		StringBuffer content = new StringBuffer();
		File file = new File(filePath);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader( new InputStreamReader(new FileInputStream(file),"UTF-8"));
            
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                content.append(tempString+"\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return content;
	}

}
