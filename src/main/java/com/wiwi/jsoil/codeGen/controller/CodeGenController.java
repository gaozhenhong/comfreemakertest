package com.wiwi.jsoil.codeGen.controller;

import com.wiwi.jsoil.base.BaseController;
import com.wiwi.jsoil.codeGen.Column;
import com.wiwi.jsoil.codeGen.Function;
import com.wiwi.jsoil.codeGen.service.CodeGenFromDbService;
import com.wiwi.jsoil.codeGen.util.Table2ClassTools;
import com.wiwi.jsoil.sys.service.ModuleService;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping({"/sys/codeGen/"})
public class CodeGenController extends BaseController
{
  private static final Logger logger = LoggerFactory.getLogger(CodeGenController.class);
  ModuleService moduleService;

  public CodeGenController()
  {
    this.moduleService = new ModuleService(); }

  @RequestMapping({"chooseTable.do"})
  public String chooseTable(Model model, HttpSession session, HttpServletRequest request) {
    logger.debug("codeGen==");
    model.addAttribute("tables", CodeGenFromDbService.getAllTables());
    return "sys/codeGen/codeGenEnter";
  }

  @RequestMapping({"functionDetails.do"})
  public String functionInput(@RequestParam("chooseTable") String tableName, Model model) {
    Function function = new Function();
    function.setTableName(tableName);
    function.setClassName(Table2ClassTools.getClassName(tableName));
    function.setColumnList(CodeGenFromDbService.getTableColumns(tableName));
    model.addAttribute("function", function);
    model.addAttribute("srcPath", getSession().getServletContext().getRealPath(""));
    return "sys/codeGen/functionDetails";
  }

  @RequestMapping({"genCode.do"})
  public String genCodeAction(@ModelAttribute("function") Function function, @RequestParam("javaSrcPath") String javaSrcPath, @RequestParam("viewPageSrcPath") String viewPageSrcPath, Model model)
  {
    HttpServletRequest request = getRequest();
    List list = getInputColumnList(request);
    function.setColumnList(list);
    String realProjectPath = request.getSession().getServletContext().getRealPath("/");
    CodeGenFromDbService.genFile(function, realProjectPath, javaSrcPath, viewPageSrcPath);
    return "sys/codeGen/genLog";
  }

  private List<Column> getInputColumnList(HttpServletRequest request)
  {
    List list = new ArrayList();
    String[] columnIds = request.getParameterValues("columnId");
    String[] columnTypes = request.getParameterValues("columnType");
    String[] columnNames = request.getParameterValues("columnName");
    String[] isQueryColumns = request.getParameterValues("isQueryColumn");
    String[] isTableHeads = request.getParameterValues("isTableHead");
    String[] isNotNulls = request.getParameterValues("isNotNull");
    String[] isInputs = request.getParameterValues("isInput");

    for (int i = 0; i < columnIds.length; ++i)
    {
      Column column = new Column();
      column.setId(columnIds[i]);
      column.setType(columnTypes[i]);
      column.setName(columnNames[i]);
      column.setNotNull(isHave(isNotNulls, columnIds[i]));
      column.setQueryColumn(isHave(isQueryColumns, columnIds[i]));
      column.setTableHead(isHave(isTableHeads, columnIds[i]));
      column.setInput(isHave(isInputs, columnIds[i]));
      list.add(column);
    }
    return list;
  }

  private boolean isHave(String[] isQueryColumns, String name)
  {
    if ((isQueryColumns == null) || (isQueryColumns.length < 1))
      return false;

    String[] arrayOfString = isQueryColumns; int j = isQueryColumns.length; for (int i = 0; i < j; ++i) { String id = arrayOfString[i];
      if (id.equalsIgnoreCase(name))
        return true;
    }

    return false;
  }
}