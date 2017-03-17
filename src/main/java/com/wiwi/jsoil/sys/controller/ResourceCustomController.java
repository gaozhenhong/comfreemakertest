package com.wiwi.jsoil.sys.controller;

import com.wiwi.jsoil.base.BaseController;
import com.wiwi.jsoil.sys.model.Resource;
import com.wiwi.jsoil.sys.model.ResourceQ;
import com.wiwi.jsoil.sys.service.ResourceService;
import com.wiwi.jsoil.util.RequestUtil;
import com.wiwi.jsoil.util.ResourceUtil;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping({"/sys/resource/{className}"})
public class ResourceCustomController extends BaseController
{
  private static final Logger logger = LoggerFactory.getLogger(ResourceCustomController.class);
  ResourceService service;

  public ResourceCustomController()
  {
    this.service = new ResourceService();
  }

  @RequestMapping({"list.do"})
  public String list(@ModelAttribute("query") ResourceQ query, @PathVariable String className, @RequestParam(required=false, value="primaryKey") String primaryKey, @RequestParam(required=false, value="title") String title, Model model)
    throws Exception
  {
    query.setClassName(className);
    if (primaryKey != null) {
      query.setPrimaryKey(primaryKey);
      model.addAttribute("primaryKey", primaryKey);
    }

    if ((title == null) || (title.trim().isEmpty()))
      title = "相册管理";

    model.addAttribute("title", title);

    model.addAttribute("list", this.service.getList(query));

    model.addAttribute("className", className);
    model.addAttribute("pager", query);
    model.addAttribute("ResourceUtil", ResourceUtil.class);

    processOperationMessage(model);

    return "/sys/resource/custom/resourceList";
  }

  @RequestMapping({"add.do"})
  public String addView(@PathVariable String className, @RequestParam(required=false, value="primaryKey") String primaryKey, @RequestParam(required=false, value="title") String title, Model model)
  {
    if ((title == null) || (title.trim().isEmpty()))
      title = "相册管理";

    model.addAttribute("instance", new Resource());
    String folder = getFolder(className);
    model.addAttribute("folder", folder);
    model.addAttribute("primaryKey", primaryKey);
    model.addAttribute("title", title);
    return "/sys/resource/custom/resourceAdd";
  }

  @RequestMapping({"addAction.do"})
  public String addAction(@ModelAttribute("instance") Resource instance, @PathVariable String className, HttpServletRequest request, Model model)
    throws Exception
  {
    Long thumbId = Long.valueOf(RequestUtil.getLongParameter(request, "thumbId"));
    instance.setId(thumbId);
    instance.setClassName(className);
    String primaryKey = RequestUtil.getParameter(request, "primaryKey");
    String title = RequestUtil.getParameter(request, "title");
    if ((title == null) || (title.trim().isEmpty()))
      title = "相册管理";

    instance.setPrimaryKey(primaryKey);
    this.service.update(instance);

    setOperationMessage("添加成功！");

    return "redirect:list.do?primaryKey=" + primaryKey + "&title=" + URLEncoder.encode(title, "UTF-8");
  }

  @RequestMapping({"edit.do"})
  public String editView(@RequestParam long id, @PathVariable String className, @RequestParam(required=false, value="primaryKey") String primaryKey, @RequestParam(required=false, value="title") String title, Model model)
    throws Exception
  {
    model.addAttribute("instance", this.service.get(id));
    String folder = getFolder(className);
    model.addAttribute("folder", folder);
    model.addAttribute("primaryKey", primaryKey);
    if ((title == null) || (title.trim().isEmpty()))
      title = "相册管理";

    model.addAttribute("title", title);

    return "/sys/resource/custom/resourceEdit";
  }

  private String getFolder(String className)
  {
    String folder = className.substring(className.lastIndexOf(".") + 1);
    folder = folder.substring(0, 1).toLowerCase() + folder.substring(1);
    return folder;
  }

  @RequestMapping({"editAction.do"})
  public String editAction(@ModelAttribute("instance") Resource instance, @RequestParam(required=false, value="primaryKey") String primaryKey, @RequestParam(required=false, value="title") String title, Model model)
    throws Exception
  {
    this.service.update(instance);

    setOperationMessage("修改成功！");
    if ((title == null) || (title.trim().isEmpty()))
      title = "相册管理";

    return "redirect:list.do?primaryKey=" + primaryKey + "&title=" + URLEncoder.encode(title, "UTF-8");
  }

  @RequestMapping({"view.do"})
  public String view(@RequestParam long id, Model model)
    throws Exception
  {
    model.addAttribute("instance", this.service.get(id));

    return "/sys/resource/resourceView";
  }

  @RequestMapping({"deleteAction.do"})
  public String deleteAction(@RequestParam(required=true) long id, @RequestParam(required=false, value="primaryKey") String primaryKey, @RequestParam(required=false, value="title") String title, Model model)
    throws Exception
  {
    this.service.delete(id);

    setOperationMessage("删除成功！");
    if ((title == null) || (title.trim().isEmpty()))
      title = "相册管理";

    return "redirect:list.do?primaryKey=" + primaryKey + "&title=" + URLEncoder.encode(title, "UTF-8");
  }

  @RequestMapping({"batchDeleteAction.do"})
  public String batchDeleteAction(@RequestParam(value="ids", required=true) String ids, @RequestParam(required=false, value="primaryKey") String primaryKey, @RequestParam(required=false, value="title") String title, Model model)
    throws Exception
  {
    this.service.batchDelete(ids);
    setOperationMessage("批量删除成功！");
    if ((title == null) || (title.trim().isEmpty()))
      title = "相册管理";

    return "redirect:list.do?primaryKey=" + primaryKey + "&title=" + URLEncoder.encode(title, "UTF-8");
  }
}