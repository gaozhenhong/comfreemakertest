package com.wiwi.jsoil.codeGen.service;

import com.wiwi.jsoil.codeGen.Column;
import com.wiwi.jsoil.codeGen.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ListViewFileGenerator
  extends PageFileGenerator
{
  private static final Logger logger = LoggerFactory.getLogger(ListViewFileGenerator.class);
  
  public ListViewFileGenerator(Function function, String projectRealPath, String viewPageSrcPath)
  {
    this.viewPageSrcPath = viewPageSrcPath;
    this.projectRealPath = projectRealPath;
    this.function = function;
    this.type = "List";
  }
  
  protected StringBuffer processTemplateContent(StringBuffer templateContent)
  {
    String content = templateContent.toString();
    
    content = content.replaceAll("#function_title#", this.function.getName());
    content = content.replaceAll("#basePath#", this.function.getRequestBasePath());
    
    String[] splitContent = content.split("#query_group#");
    if (splitContent.length > 1) {
      content = splitContent[0] + getQueryGroup(this.function) + splitContent[1];
    }
    splitContent = content.split("#table_head#");
    if (splitContent.length > 1) {
      content = splitContent[0] + getTableHead(this.function) + splitContent[1];
    }
    splitContent = content.split("#table_content#");
    if (splitContent.length > 1) {
      content = splitContent[0] + getTableContent(this.function) + splitContent[1];
    }
    return new StringBuffer(content);
  }
  
  private static String getQueryGroup(Function function)
  {
    StringBuffer content = new StringBuffer();
    for (Column column : function.getColumnList()) {
      if ((!column.getId().equals("id")) && 
        (column.isQueryColumn()))
      {
        content.append("\t\t\t\t\t\t\t\t\t\t\t<div class='input-group'>\n");
        content.append("\t\t\t\t\t\t\t\t\t\t\t\t<span class='input-group-addon'> " + column.getName() + " </span>\n");
        content.append("\t\t\t\t\t\t\t\t\t\t\t\t<input type='text' th:field='${query." + column.getId() + "}' placeholder='" + column.getName() + "' />\n");
        content.append("\t\t\t\t\t\t\t\t\t\t\t</div>\n");
      }
    }
    if (content.length() > 0)
    {
      content.append("\t\t\t\t\t\t\t\t\t\t\t<div class='input-group query-group'>\n");
      content.append("\t\t\t\t\t\t\t\t\t\t\t\t<button class='btn btn-sm' type='submit'>\n");
      content.append("\t\t\t\t\t\t\t\t\t\t\t\t\t<i class='icon-search nav-search-icon'></i>\n");
      content.append("\t\t\t\t\t\t\t\t\t\t\t\t\t查询\n");
      content.append("\t\t\t\t\t\t\t\t\t\t\t\t</button>\n");
      content.append("\t\t\t\t\t\t\t\t\t\t\t</div>\n");
    }
    logger.info("列表查询内容：{}", content.toString());
    return content.toString();
  }
  
  private static String getTableHead(Function function)
  {
    StringBuffer content = new StringBuffer();
    for (Column column : function.getColumnList()) {
      if (column.isTableHead()) {
        content.append("\t\t\t\t\t\t\t\t\t<th id='" + column.getId() + "' class=\"table-order-column\">" + column.getName() + "</th>\n");
      }
    }
    return content.toString();
  }
  
  private static String getTableContent(Function function)
  {
    StringBuffer content = new StringBuffer();
    for (Column column : function.getColumnList()) {
      if (column.isTableHead()) {
        if (column.getType().equalsIgnoreCase("Date.class")) {
          content.append("\t\t\t\t\t\t\t\t\t<td th:text=\"${#dates.format(obj." + column.getId() + ", 'yyyy-MM-dd HH:mm')}\">" + column.getName() + "</td>\n");
        } else {
          content.append("\t\t\t\t\t\t\t\t\t<td th:text=\"${obj." + column.getId() + "}\">" + column.getName() + "</td>\n");
        }
      }
    }
    return content.toString();
  }
}
