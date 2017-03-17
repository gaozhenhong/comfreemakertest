package com.wiwi.jsoil.codeGen.service;

import com.wiwi.jsoil.codeGen.Function;
import com.wiwi.jsoil.codeGen.controller.CodeGenController;
import com.wiwi.jsoil.codeGen.util.Table2ClassTools;
import com.wiwi.jsoil.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControllerJavaFileGenerator
{
  private static final Logger log = LoggerFactory.getLogger(CodeGenController.class);
  static StringBuffer controllerImportContent = new StringBuffer().append
    ("import org.slf4j.Logger;\n").append
    ("import org.slf4j.LoggerFactory;\n").append
    ("import org.springframework.ui.Model;\n").append
    ("import org.springframework.stereotype.Controller;\n").append
    ("import org.springframework.web.bind.annotation.ModelAttribute;\n").append
    ("import org.springframework.web.bind.annotation.RequestMapping;\n").append
    ("import org.springframework.web.bind.annotation.RequestParam;\n\n").append
    ("import com.wiwi.jsoil.base.BaseController;\n");

  public static void genController(Function function, String srcPath)
  {
    try
    {
      String basePackageName = (org.thymeleaf.util.StringUtils.isEmpty(function.getPackageName())) ? "" : function.getPackageName();
      String packageName = basePackageName + ".controller";
      String packageStr = "package " + packageName + ";\n\n";

      StringBuffer importStr = new StringBuffer(controllerImportContent);

      importStr.append("import " + basePackageName + ".model." + function.getClassName() + ";\n");
      importStr.append("import " + basePackageName + ".model." + function.getClassName() + "Q;\n");
      importStr.append("import " + basePackageName + ".service." + function.getClassName() + "Service;\n");
      StringBuffer classDefine = getClassDefine(function.getClassName(), function.getRequestBasePath());
      StringBuffer propertyDefine = getPropertyDefine(function.getClassName());
      StringBuffer methodDefine = getMethodDefine(function);
      StringBuffer classEndDefine = new StringBuffer("\n}");
      StringBuffer content = new StringBuffer();
      content.append(packageStr);
      content.append(importStr);
      content.append(classDefine);
      content.append(propertyDefine);
      content.append(methodDefine);
      content.append(classEndDefine);
      String javaSrc = Table2ClassTools.getFilePath(function.getClassName(), srcPath, packageName, "Controller");
      FileUtil.writeFile(javaSrc, new StringBuffer(content));
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }

  private static StringBuffer getClassDefine(String className, String requestBasePath)
  {
    StringBuffer classDefine = new StringBuffer();
    classDefine.append("\n@Controller\n");
    classDefine.append("@RequestMapping(value = \"" + requestBasePath + "\")\n");
    classDefine.append("public class " + className + "Controller extends BaseController{\n\n");
    return classDefine;
  }

  private static StringBuffer getPropertyDefine(String className)
  {
    StringBuffer propertyDefine = new StringBuffer();
    propertyDefine.append("\n    private static final Logger logger = LoggerFactory.getLogger(" + className + "Controller.class);\n");
    propertyDefine.append("\n    " + className + "Service service = new " + className + "Service();\n\n");
    return propertyDefine;
  }

  private static StringBuffer getMethodDefine(Function function)
  {
    StringBuffer methodDefine = new StringBuffer();
    String className = function.getClassName();
    String requestBasePath = function.getRequestBasePath();
    String idType = Table2ClassTools.getIdType(function);
    methodDefine.append(getListMethodDefine(className, requestBasePath));
    methodDefine.append(getAddViewMethodDefine(className, requestBasePath));
    methodDefine.append(getAddActionMethodDefine(className));
    methodDefine.append(getEditViewMethodDefine(className, requestBasePath, idType));
    methodDefine.append(getEditActionMethodDefine(className));
    methodDefine.append(getViewMethodDefine(className, requestBasePath, idType));
    methodDefine.append(getDeleteActionMethodDefine(className, idType));
    methodDefine.append(getBatchDeleteActionMethodDefine(className));
    return methodDefine;
  }

  private static StringBuffer getListMethodDefine(String className, String requestBasePath)
  {
    StringBuffer content = new StringBuffer();
    content.append("\n\n    @RequestMapping(value = \"list.do\")");
    content.append("\n    public String list(@ModelAttribute(value=\"query\") " + className + "Q query,Model model) throws Exception {\n");
    content.append("\n        model.addAttribute(\"list\", service.getList(query));\n");
    content.append("\n        model.addAttribute(\"pager\", query);\n");
    content.append("\n        processOperationMessage(model);\n");
    content.append("\n        return \"" + requestBasePath + Table2ClassTools.getVarNameByClassName(className) + "List\";\n");
    content.append("\n    }\n");
    return content;
  }

  private static StringBuffer getAddViewMethodDefine(String className, String requestBasePath)
  {
    StringBuffer content = new StringBuffer();
    content.append("\n\n    @RequestMapping(value = \"add.do\")");
    content.append("\n    public String addView(Model model)  {\n");
    content.append("\n        model.addAttribute(\"instance\", new " + className + "() );\n");
    content.append("\n        return \"" + requestBasePath + Table2ClassTools.getVarNameByClassName(className) + "Add\";\n");
    content.append("\n    }\n");
    return content;
  }

  private static StringBuffer getAddActionMethodDefine(String className)
  {
    StringBuffer content = new StringBuffer();
    content.append("\n\n    @RequestMapping(value = \"addAction.do\")");
    content.append("\n    public String addAction(@ModelAttribute(value=\"instance\") " + className + " instance,Model model) throws Exception {\n");
    content.append("\n        service.insert(instance);\n");
    content.append("\n        setOperationMessage(\"添加成功！\");\n");
    content.append("\n        return \"redirect:list.do\";\n");
    content.append("\n    }\n");
    return content;
  }

  private static StringBuffer getEditViewMethodDefine(String className, String requestBasePath, String idType)
  {
    StringBuffer content = new StringBuffer();
    content.append("\n\n    @RequestMapping(value = \"edit.do\")");
    content.append("\n    public String editView(@RequestParam " + idType + " id,Model model)  throws Exception {\n");
    content.append("\n        model.addAttribute(\"instance\", service.get(id) );\n");
    content.append("\n        return \"" + requestBasePath + Table2ClassTools.getVarNameByClassName(className) + "Edit\";\n");
    content.append("\n    }\n");
    return content;
  }

  private static StringBuffer getEditActionMethodDefine(String className)
  {
    StringBuffer content = new StringBuffer();
    content.append("\n\n    @RequestMapping(value = \"editAction.do\")");
    content.append("\n    public String editAction(@ModelAttribute(value=\"instance\") " + className + " instance,Model model) throws Exception {\n");
    content.append("\n        service.update(instance);\n");
    content.append("\n        setOperationMessage(\"修改成功！\");\n");
    content.append("\n        return \"redirect:list.do\";\n");
    content.append("\n    }\n");
    return content;
  }

  private static StringBuffer getViewMethodDefine(String className, String requestBasePath, String idType)
  {
    StringBuffer content = new StringBuffer();
    content.append("\n\n    @RequestMapping(value = \"view.do\")");
    content.append("\n    public String view(@RequestParam " + idType + " id," + 
      " @RequestParam(required = false,value=\"isModal\") Boolean isModal, Model model) throws Exception  {\n");
    content.append("\n        model.addAttribute(\"instance\", service.get(id) );\n");
    content.append("\n        model.addAttribute(\"isModal\", isModal == null?true:isModal );\n");
    content.append("\n        return \"" + requestBasePath + Table2ClassTools.getVarNameByClassName(className) + "View\";\n");
    content.append("\n    }\n");
    return content;
  }

  private static StringBuffer getDeleteActionMethodDefine(String className, String idType)
  {
    StringBuffer content = new StringBuffer();
    content.append("\n\n    @RequestMapping(value = \"deleteAction.do\")");
    content.append("\n    public String deleteAction(@RequestParam(required=true) " + idType + " id,Model model) throws Exception {\n");
    content.append("\n        service.delete(id);\n");
    content.append("\n        setOperationMessage(\"删除成功！\");\n");
    content.append("\n        return \"redirect:list.do\";\n");
    content.append("\n    }\n");
    return content;
  }

  private static StringBuffer getBatchDeleteActionMethodDefine(String className)
  {
    StringBuffer content = new StringBuffer();
    content.append("\n\n    @RequestMapping(value = \"batchDeleteAction.do\")");
    content.append("\n    public String batchDeleteAction(@RequestParam(value=\"ids\",required=true) String ids,Model model) throws Exception {\n");
    content.append("\n        service.batchDelete(ids);\n");
    content.append("\n        setOperationMessage(\"批量删除成功！\");\n");
    content.append("\n        return \"redirect:list.do\";\n");
    content.append("\n    }\n");
    return content;
  }
}