package com.wiwi.jsoil.db;

import com.wiwi.jsoil.exception.ReadCustomSQLException;
import com.wiwi.jsoil.util.AppConstants;
import java.util.Iterator;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class CustomSqlTools
{
  private static final Logger logger = LoggerFactory.getLogger(DbAdapter.class);
  private static final String PROJECT_SQL_FILE = "/custom-sql.xml";
  private static final String SYS_SQL_FILE = "/sys-custom-sql.xml";
  private static Document sysDocument = null;
  private static Document projectDocument = null;

  public static String getSysCustomSql(String moduleName, String sqlName)
    throws ReadCustomSQLException
  {
    if (sysDocument == null)
      sysDocument = getCustomSqlFile("/sys-custom-sql.xml".replace("-sql", "-" + AppConstants.DB_TYPE + "-sql"));

    return getSqlFromDocument(sysDocument, moduleName, sqlName);
  }

  public static String getProjectCustomSql(String moduleName, String sqlName) throws ReadCustomSQLException {
    if (projectDocument == null)
      projectDocument = getCustomSqlFile("/custom-sql.xml".replace("-sql", "-" + AppConstants.DB_TYPE + "-sql"));

    return getSqlFromDocument(projectDocument, moduleName, sqlName);
  }

  private static String getSqlFromDocument(Document document, String moduleName, String sqlName)
    throws ReadCustomSQLException
  {
    Iterator localIterator2;
    Element rootElement = document.getRootElement();
	String sql = null;
	try {
	List<Element> list =  rootElement.elements("module");
	for(Element element : list){
		if(element.attributeValue("name").equals(moduleName)){
			@SuppressWarnings("unchecked")
			List<Element> list2 =  element.elements("sql");
			for(Element element2 : list2){
				if(element2.attributeValue("name").equals(sqlName)){
					sql = element2.getTextTrim();
				}
			}
		}
	   }
    

  }catch(Exception e){
	  e.printStackTrace();
	  logger.error("自定义SQL读取失败：【module={}，sqlId={}】",moduleName,sqlName);
  }
	return sql;
  }

  private static Document getCustomSqlFile(String fileName)
    throws ReadCustomSQLException
  {
    SAXReader reader = new SAXReader();
    Resource resource = new ClassPathResource(fileName);
    try {
      return reader.read(resource.getFile());
    } catch (Exception e) {
      String message = "自定义SQL读取失败：未能够读取到SQL配置文件，【DB_TYPE＝" + AppConstants.DB_TYPE + "】,【fileName=" + fileName + "】";
      logger.error(message);
      throw new ReadCustomSQLException(message, e);
    }
  }
}