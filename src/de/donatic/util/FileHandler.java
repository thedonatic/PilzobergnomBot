package de.donatic.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileHandler {

	public static String getFileContent(String path) {
		BufferedReader br = null;
		String fileContent = "";
        try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
			int value;
			while((value = br.read()) != -1) {
		         
	            fileContent = fileContent + (char)value;
	            
	         }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return fileContent;
	}
	
	public static void writeFileContent(String path, String content) {
		FileWriter fw = null;
        try {
        	fw = new FileWriter(path,false);
			fw.write(content);
			fw.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
