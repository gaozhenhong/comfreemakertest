package com.wiwi.jsoil.codeGen.service;

import com.wiwi.jsoil.codeGen.Column;
import com.wiwi.jsoil.codeGen.Function;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EditViewFileGenerator extends PageFileGenerator
{
  private static final Logger logger = LoggerFactory.getLogger(EditViewFileGenerator.class);

  public EditViewFileGenerator(Function function, String projectRealPath, String viewPageSrcPath)
  {
    this.viewPageSrcPath = viewPageSrcPath;
    this.projectRealPath = projectRealPath;
    this.function = function;
    this.type = "Edit";
  }

  protected StringBuffer processTemplateContent(StringBuffer templateContent)
  {
    String content = templateContent.toString();

    content = content.replaceAll("#function_title#", this.function.getName());
    content = content.replaceAll("#basePath#", this.function.getRequestBasePath());

    String[] splitContent = content.split("#field_input_group#");
    if (splitContent.length > 1) {
      content = splitContent[0] + getFieldInputGroup(this.function) + splitContent[1];
    }

    splitContent = content.split("#input_validator#");
    if (splitContent.length > 1) {
      content = splitContent[0] + getInputValidator(this.function) + splitContent[1];
    }

    return new StringBuffer(content);
  }

  private static String getFieldInputGroup(Function function)
  {
		StringBuffer content = new StringBuffer();
		
		for(Column column : function.getColumnList()){
			if(column.getId().equals("id"))continue;
			String notNullTip = "";
			if(column.isNotNull()){
				notNullTip = " * ";
			}
			content.append("							<div class='form-group'>\n");
			content.append("								<label class='col-sm-3 control-label no-padding-right'> "+ column.getName() + notNullTip + " </label>\n");
			content.append("								<div class='col-sm-9'>\n");
			content.append("									<input type='text' th:field='${instance." + column.getId() +"}' placeholder='"+ column.getName() +"' class='col-xs-10 col-sm-5' />\n");
			content.append("								</div>\n");
			content.append("							</div>\n");
		}
		return content.toString();
  }

  private static String getInputValidator(Function function)
  {
		StringBuffer content = new StringBuffer();
		
		for(Column column : function.getColumnList()){
			if(column.getId().equals("id"))continue;
			if(column.isNotNull()){
				content.append("					" + column.getId() + ": \"required\",\n");
			}
		}
		String contentStr = content.toString();
		if(contentStr.endsWith(",")){
			contentStr = contentStr.substring(0,contentStr.length()-1);
		}
		logger.debug("添加页面的脚本验证：{}",contentStr);
		return contentStr;
  }
}