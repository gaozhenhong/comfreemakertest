package com.wiwi.jsoil.codeGen.service;

import com.wiwi.jsoil.codeGen.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ViewFileGenerator
{
  private static final Logger log = LoggerFactory.getLogger(ViewFileGenerator.class);

  public static void genJspFile(Function function, String projectRealPath, String srcPath)
  {
    ListViewFileGenerator listViewFileGenerator;
    try
    {
      listViewFileGenerator = new ListViewFileGenerator(function, projectRealPath, srcPath);
      listViewFileGenerator.genPageView();

      AddViewFileGenerator addViewFileGenerator = new AddViewFileGenerator(function, projectRealPath, srcPath);
      addViewFileGenerator.genPageView();

      EditViewFileGenerator editViewFileGenerator = new EditViewFileGenerator(function, projectRealPath, srcPath);
      editViewFileGenerator.genPageView();

      DetailsViewFileGenerator detailsViewFileGenerator = new DetailsViewFileGenerator(function, projectRealPath, srcPath);
      detailsViewFileGenerator.genPageView();
    }
    catch (Exception e) {
      log.error("生成jsp文件错误！", e);
    }
  }
}