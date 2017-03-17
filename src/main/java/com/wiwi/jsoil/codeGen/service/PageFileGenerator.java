package com.wiwi.jsoil.codeGen.service;

import com.wiwi.jsoil.codeGen.Function;
import com.wiwi.jsoil.codeGen.controller.CodeGenController;
import com.wiwi.jsoil.codeGen.util.Table2ClassTools;
import com.wiwi.jsoil.util.FileUtil;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class PageFileGenerator
{
  private static final Logger logger = LoggerFactory.getLogger(CodeGenController.class);
  private static String templateFilePath = "WEB-INF/thymeleaf/sys/codeGen/template/";
  protected String viewPageSrcPath;
  protected String type;
  protected String projectRealPath;
  protected Function function;

  public PageFileGenerator()
  {
    this.viewPageSrcPath = "";
    this.type = "";
    this.projectRealPath = "";
    this.function = null;
  }

  public void genPageView() throws FileNotFoundException, IOException {
    String filePath = this.viewPageSrcPath + this.function.getRequestBasePath();
    logger.debug("{}页面的地址==={}", this.type, filePath);

    String fileName = filePath + "/" + Table2ClassTools.getVarNameByClassName(this.function.getClassName()) + this.type + 
      ".html";
    File fileDir = new File(filePath);
    if ((fileDir == null) || (!(fileDir.isDirectory())))
      fileDir.mkdirs();

    FileUtil.writeFile(fileName, processTemplateContent(getTemplateContent(this.type)));
  }

  protected StringBuffer getTemplateContent(String type) {
    String templateFileName = this.projectRealPath + templateFilePath + String.valueOf(type.charAt(0)).toUpperCase() + 
      type.substring(1) + ".html";
    return FileUtil.getFileContent(templateFileName);
  }

  protected abstract StringBuffer processTemplateContent(StringBuffer paramStringBuffer);
}