package com.wiwi.jsoil.codeGen.service;

import com.wiwi.jsoil.codeGen.Column;
import com.wiwi.jsoil.codeGen.Function;
import java.util.Iterator;
import java.util.List;

public class DetailsViewFileGenerator extends PageFileGenerator
{
  public DetailsViewFileGenerator(Function function, String projectRealPath, String viewPageSrcPath)
  {
    this.viewPageSrcPath = viewPageSrcPath;
    this.projectRealPath = projectRealPath;
    this.function = function;
    this.type = "View";
  }

  protected StringBuffer processTemplateContent(StringBuffer templateContent)
  {
    String content = templateContent.toString();

    content = content.replaceAll("#function_title#", this.function.getName());
    content = content.replaceAll("#basePath#", this.function.getRequestBasePath());

    String[] splitContent = content.split("#field_view_group#");
    if (splitContent.length > 1) {
      content = splitContent[0] + getFieldInputGroup(this.function) + splitContent[1];
    }

    return new StringBuffer(content);
  }

  private static String getFieldInputGroup(Function function)
  {
    StringBuffer content = new StringBuffer();

    for (Iterator localIterator = function.getColumnList().iterator(); localIterator.hasNext(); ) { Column column = (Column)localIterator.next();
      content.append("\t\t\t\t\t\t\t<div class='form-group'>\n");
      content.append("\t\t\t\t\t\t\t\t<label class='col-sm-3 control-label no-padding-right'> " + column.getName() + " </label>\n");
      content.append("\t\t\t\t\t\t\t\t<div class='col-sm-9'>\n");
      content.append("\t\t\t\t\t\t\t\t\t<input type='text' th:field='${instance." + column.getId() + "}' placeholder='" + column.getName() + "' class='col-xs-10 col-sm-5' />\n");
      content.append("\t\t\t\t\t\t\t\t</div>\n");
      content.append("\t\t\t\t\t\t\t</div>\n");
    }
    return content.toString();
  }
}