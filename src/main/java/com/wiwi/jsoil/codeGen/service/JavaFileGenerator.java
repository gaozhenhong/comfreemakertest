package com.wiwi.jsoil.codeGen.service;

import com.wiwi.jsoil.codeGen.Column;
import com.wiwi.jsoil.codeGen.Function;
import com.wiwi.jsoil.codeGen.controller.CodeGenController;
import com.wiwi.jsoil.codeGen.util.Table2ClassTools;
import com.wiwi.jsoil.util.FileUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaFileGenerator
{
  private static final Logger log = LoggerFactory.getLogger(CodeGenController.class);
  static StringBuffer commonImportContent = new StringBuffer("import com.wiwi.jsoil.db.PageUtil;\n");
  static StringBuffer queryModelImportContent = new StringBuffer(commonImportContent);
  static StringBuffer daoImportContent = new StringBuffer(commonImportContent).append
    ("import java.util.List;\n").append
    ("import com.wiwi.jsoil.db.DaoBase;\n").append
    ("import com.wiwi.jsoil.db.DbAdapter;\n").append
    ("import com.wiwi.jsoil.exception.RenderException;\n").append
    ("import com.wiwi.jsoil.exception.DaoException;\n");
  static StringBuffer serviceImportContent = new StringBuffer(commonImportContent).append
    ("import java.util.List;\n").append
    ("import com.wiwi.jsoil.exception.RenderException;\n").append
    ("import com.wiwi.jsoil.exception.DaoException;\n");
  static StringBuffer controllerImportContent = new StringBuffer(commonImportContent);

  public static void genModel(Function function, String srcPath)
  {
    try
    {
      String packageName = function.getPackageName() + ".model";
      String javaSrc = Table2ClassTools.getFilePath(function.getClassName(), srcPath, packageName, "");

      String packageStr = "package " + packageName + ";\n\n";

      String importStr = "";
      String propertyStr = "";
      String methodStr = "";
      List columnTypeList = new ArrayList();
      for (Iterator localIterator = function.getColumnList().iterator(); localIterator.hasNext(); ) { Column column = (Column)localIterator.next();
        if ((!(column.getType().startsWith("java.lang"))) && 
          (!(columnTypeList.contains(column.getType()))))
        {
          columnTypeList.add(column.getType());
          importStr = importStr + "import " + column.getType() + ";\n";
        }

        if ("java.util.Date".equals(column.getType()))
        {
          if (!(columnTypeList.contains("org.springframework.format.annotation.DateTimeFormat"))) {
            importStr = importStr + "import org.springframework.format.annotation.DateTimeFormat;\n";
            columnTypeList.add("org.springframework.format.annotation.DateTimeFormat");
          }
          propertyStr = propertyStr + "    @DateTimeFormat(pattern=\"yyyy-MM-dd HH:mm:ss\")\n";
        }
        propertyStr = propertyStr + "    private " + column.getType().substring(column.getType().lastIndexOf(".") + 1) + " " + column.getId() + ";\n\n";

        methodStr = methodStr + "    public " + column.getType().substring(column.getType().lastIndexOf(".") + 1) + " get" + Character.toUpperCase(column.getId().charAt(0)) + column.getId().substring(1) + "() {\n";
        methodStr = methodStr + "        return this." + column.getId() + ";\n";
        methodStr = methodStr + "    }\n\n";

        methodStr = methodStr + "    public void set" + Character.toUpperCase(column.getId().charAt(0)) + column.getId().substring(1) + " (" + column.getType().substring(column.getType().lastIndexOf(".") + 1) + " " + column.getId() + ") {\n";
        methodStr = methodStr + "        this." + column.getId() + "=" + column.getId() + ";\n";
        methodStr = methodStr + "    }\n\n";
      }
      String bodyStr = "\npublic class " + function.getClassName() + "{\n\n" + propertyStr + methodStr;
      bodyStr = bodyStr + "}";

      String content = packageStr + importStr + bodyStr;

      FileUtil.writeFile(javaSrc, new StringBuffer(content));
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }

  public static void genQueryModel(Function function, String srcPath)
  {
    try {
      String packageName = function.getPackageName() + ".model";

      String javaSrc = Table2ClassTools.getFilePath(function.getClassName(), srcPath, packageName, "Q");

      String packageStr = "package " + packageName + ";\n\n";

      StringBuffer whereStr = new StringBuffer("    public String toWhereString() {\n\n").append
        ("        parameterList.clear();\n\n").append
        ("        String sqlStr = super.getOtherCondition();\n\n");

      StringBuffer importStr = new StringBuffer(queryModelImportContent);
      String propertyStr = "";
      String methodStr = "";
      List columnTypeList = new ArrayList();
      for (Iterator localIterator = function.getColumnList().iterator(); localIterator.hasNext(); ) { Column column = (Column)localIterator.next();
        if ((!(column.getType().startsWith("java.lang"))) && 
          (!(columnTypeList.contains(column.getType()))))
        {
          columnTypeList.add(column.getType());
          importStr.append("import " + column.getType() + ";\n");
        }

        if ("java.util.Date".equals(column.getType()))
        {
          if (!(columnTypeList.contains("org.springframework.format.annotation.DateTimeFormat")))
            importStr.append("import org.springframework.format.annotation.DateTimeFormat;\n");

          propertyStr = propertyStr + "    @DateTimeFormat(pattern=\"yyyy-MM-dd HH:mm:ss\")\n";
        }
        propertyStr = propertyStr + "    private " + column.getType().substring(column.getType().lastIndexOf(".") + 1) + " " + column.getId() + ";\n\n";

        if (column.getType().substring(column.getType().lastIndexOf(".") + 1).equalsIgnoreCase("String")) {
          whereStr.append("        if (" + column.getId() + " != null && !" + column.getId() + ".equalsIgnoreCase(\"\")){\n");
          whereStr.append("            sqlStr += \" AND " + column.getId() + " like ?\";\n");
          whereStr.append("            parameterList.add('%' + " + column.getId() + " + '%'); \n");

          whereStr.append("        }\n\n");
        } else if ((column.getType().substring(column.getType().lastIndexOf(".") + 1).equalsIgnoreCase("Integer")) || (column.getType().substring(column.getType().lastIndexOf(".") + 1).equalsIgnoreCase("Long"))) {
          whereStr.append("        if (" + column.getId() + " != null && " + column.getId() + " != 0 ){\n");
          whereStr.append("            sqlStr += \" AND " + column.getId() + " =?\";\n");
          whereStr.append("            parameterList.add(" + column.getId() + "); \n");
          whereStr.append("        }\n\n");
        } else {
          whereStr.append("        if (" + column.getId() + " != null){\n");
          whereStr.append("            sqlStr += \" AND " + column.getId() + " =?\";\n");
          whereStr.append("            parameterList.add(" + column.getId() + "); \n");
          whereStr.append("        }\n\n");
        }

        methodStr = methodStr + "    public " + column.getType().substring(column.getType().lastIndexOf(".") + 1) + " get" + Character.toUpperCase(column.getId().charAt(0)) + column.getId().substring(1) + "() {\n";
        methodStr = methodStr + "        return this." + column.getId() + ";\n";
        methodStr = methodStr + "    }\n\n";

        methodStr = methodStr + "    public void set" + Character.toUpperCase(column.getId().charAt(0)) + column.getId().substring(1) + " (" + column.getType().substring(column.getType().lastIndexOf(".") + 1) + " " + column.getId() + ") {\n";
        methodStr = methodStr + "        this." + column.getId() + "=" + column.getId() + ";\n";
        methodStr = methodStr + "    }\n\n";
      }

      whereStr.append("        return sqlStr;\n");
      whereStr.append("    }\n\n");

      String bodyStr = "\npublic class " + function.getClassName() + "Q extends PageUtil {\n\n" + propertyStr + whereStr.toString() + methodStr;
      bodyStr = bodyStr + "}";

      String content = packageStr + importStr.toString() + bodyStr;

      FileUtil.writeFile(javaSrc, new StringBuffer(content));
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }

  public static void genDao(Function function, String srcPath) {
    try {
      String basePackageName = (org.thymeleaf.util.StringUtils.isEmpty(function.getPackageName())) ? "" : function.getPackageName();
      String packageName = basePackageName + ".dao";
      String javaSrc = Table2ClassTools.getFilePath(function.getClassName(), srcPath, packageName, "Dao");

      String packageStr = "package " + packageName + ";\n\n";

      StringBuffer importStr = new StringBuffer(daoImportContent);

      importStr.append("import " + basePackageName + ".model." + function.getClassName() + ";\n");
      String idType = Table2ClassTools.getIdType(function);
      StringBuffer bodyStr = new StringBuffer();
      bodyStr.append("\npublic class " + function.getClassName() + "Dao extends DaoBase {\n\n");
      bodyStr.append("    private String sql = null;\n\n");
      bodyStr.append("    public void insert(" + function.getClassName() + " instance) throws DaoException, RenderException {\n");
      bodyStr.append("        DbAdapter.insert2SingleTable(instance, \"" + function.getTableName() + "\");\n");
      bodyStr.append("    }\n\n");
      bodyStr.append("    public void update(" + function.getClassName() + " instance) throws DaoException, RenderException {\n");
      bodyStr.append("        DbAdapter.update2SingleTable(instance, \"" + function.getTableName() + "\");\n");
      bodyStr.append("    }\n\n");
      bodyStr.append("    public void delete(String ids) throws DaoException {\n");

      bodyStr.append("        if (ids.startsWith(\",\")) {\n");
      bodyStr.append("        \t\t\tids = ids.substring(1);\n");
      bodyStr.append("        }\n");

      bodyStr.append("        if(ids.indexOf(\",\") != -1){\n");
      bodyStr.append("        \t\tids = ids.replaceAll(\",\", \"','\");\n");
      bodyStr.append("        }\n");
      bodyStr.append("        if(!ids.startsWith(\"'\")){\n");
      bodyStr.append("        \t\tids = \"'\" + ids;\n");
      bodyStr.append("        }\n");
      bodyStr.append("        if(!ids.endsWith(\"'\")){\n");
      bodyStr.append("        \t\tids = ids + \"'\";\n");
      bodyStr.append("        }\n");

      bodyStr.append("        sql = \"DELETE FROM " + function.getTableName() + " WHERE id in (\" + ids + \") \";\n");
      bodyStr.append("        DbAdapter.executeUpdate(sql);\n");
      bodyStr.append("    }\n\n");
      bodyStr.append("    public " + function.getClassName() + " get(" + idType + " id) throws DaoException,RenderException {\n");
      bodyStr.append("        sql = \"select * FROM " + function.getTableName() + " WHERE id ='\" + id +\"'\";\n");
      bodyStr.append("        return (" + function.getClassName() + ")DbAdapter.get(sql," + function.getClassName() + ".class);\n");
      bodyStr.append("    }\n\n");
      bodyStr.append("    @SuppressWarnings(\"unchecked\")\n");
      bodyStr.append("    public List<" + function.getClassName() + "> getList(PageUtil pageUtil) throws DaoException,RenderException {\n");
      bodyStr.append("        sql = \"select * FROM " + function.getTableName() + "\";\n");
      bodyStr.append("        return DbAdapter.getList(sql,pageUtil, " + function.getClassName() + ".class);\n");
      bodyStr.append("    }\n\n");
      bodyStr.append("}");

      String content = packageStr + importStr.toString() + bodyStr.toString();

      FileUtil.writeFile(javaSrc, new StringBuffer(content));
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }

  public static void genService(Function function, String srcPath) {
    try {
      String basePackageName = (org.thymeleaf.util.StringUtils.isEmpty(function.getPackageName())) ? "" : function.getPackageName();
      String packageName = basePackageName + ".service";
      String javaSrc = Table2ClassTools.getFilePath(function.getClassName(), srcPath, packageName, "Service");

      String packageStr = "package " + packageName + ";\n\n";

      StringBuffer importStr = new StringBuffer(serviceImportContent);
      importStr.append("import " + basePackageName + ".model." + function.getClassName() + ";\n");
      importStr.append("import " + basePackageName + ".dao." + function.getClassName() + "Dao;\n");
      String idType = Table2ClassTools.getIdType(function);
      StringBuffer bodyStr = new StringBuffer();
      bodyStr.append("\npublic class " + function.getClassName() + "Service {\n\n");
      bodyStr.append("    public void insert(" + function.getClassName() + " instance) throws DaoException, RenderException {\n");
      bodyStr.append("        " + function.getClassName() + "Dao dao = new " + function.getClassName() + "Dao();\n");
      bodyStr.append("        dao.insert(instance);\n");
      bodyStr.append("    }\n\n");
      bodyStr.append("    public void update(" + function.getClassName() + " instance) throws DaoException, RenderException {\n");
      bodyStr.append("        " + function.getClassName() + "Dao dao = new " + function.getClassName() + "Dao();\n");
      bodyStr.append("        dao.update(instance);\n");
      bodyStr.append("    }\n\n");
      bodyStr.append("    public void delete(" + idType + " id) throws DaoException {\n");
      bodyStr.append("        batchDelete(id+\"\");\n");
      bodyStr.append("    }\n\n");
      bodyStr.append("    public void batchDelete(String ids) throws DaoException {\n");
      bodyStr.append("        " + function.getClassName() + "Dao dao = new " + function.getClassName() + "Dao();\n");
      bodyStr.append("        dao.delete(ids);\n");
      bodyStr.append("    }\n\n");
      bodyStr.append("    public " + function.getClassName() + " get(" + idType + " id) throws DaoException,RenderException {\n");
      bodyStr.append("        " + function.getClassName() + "Dao dao = new " + function.getClassName() + "Dao();\n");
      bodyStr.append("        return dao.get(id);\n");
      bodyStr.append("    }\n\n");
      bodyStr.append("    public List<" + function.getClassName() + "> getList(PageUtil pageUtil) throws DaoException,RenderException {\n");
      bodyStr.append("        " + function.getClassName() + "Dao dao = new " + function.getClassName() + "Dao();\n");
      bodyStr.append("        return dao.getList(pageUtil);\n");
      bodyStr.append("    }\n");
      bodyStr.append("}");

      String content = packageStr + importStr.toString() + bodyStr.toString();

      FileUtil.writeFile(javaSrc, new StringBuffer(content));
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }
}