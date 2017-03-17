package com.wiwi.jsoil.codeGen.util;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wiwi.jsoil.codeGen.Column;
import com.wiwi.jsoil.codeGen.Function;

public class Table2ClassTools {
	static Log log = LogFactory.getLog(Table2ClassTools.class);
	public static String getClassName(String tableName){
		String className = "";
		if(tableName != null){
			if(tableName.indexOf("_")!= -1){
				className = tableName.substring(tableName.indexOf("_")+1);
			}
		}
		if(className.length()>0){
			className = String.valueOf(className.charAt(0)).toUpperCase() + className.substring(1);
		}
		return className;
	}
	
	
	/**
	 * 把首字母大写的类名转化为首字母小写的变更名
	 * @param className
	 * @return
	 */
	public static String getVarNameByClassName(String className){
		if(className == null || className.trim().length()<1)return "";
		return String.valueOf(className.charAt(0)).toLowerCase() + className.substring(1);
	}
	
	
	
	public static String getIdType(Function function){
		for(Column column : function.getColumnList()){
			if(column.getId().equals("id")){
				switch( column.getType()){
				case "java.lang.Integer":
						return "int";
				case "java.lang.Long":
					return "long";
				default : 
					return "String";
				}
			}
		}
		return "String";
	}
	
	/**
	 * @param className
	 * @param srcPath
	 * @param packageName
	 * @param type
	 * @return
	 */
	public static String getFilePath(String className, String srcPath, String packageName,String type) {
		String filePath = srcPath + "/" + packageName.replace(".", "/");

		File fileDir = new File(filePath);
		if(fileDir==null||!fileDir.isDirectory()){
			fileDir.mkdirs();
		}

		String javaSrc =  filePath+"/" + className + type + ".java";
		log.info(type + " 类 javaSrc:"+javaSrc);
		return javaSrc;
	}
}
