package com.wiwi.jsoil.sys.controller;

import com.wiwi.jsoil.base.BaseController;
import com.wiwi.jsoil.sys.model.Category;
import com.wiwi.jsoil.sys.model.CategoryQ;
import com.wiwi.jsoil.sys.model.Knowledge;
import com.wiwi.jsoil.sys.model.KnowledgeQ;
import com.wiwi.jsoil.sys.model.Organization;
import com.wiwi.jsoil.sys.model.Resource;
import com.wiwi.jsoil.sys.model.User;
import com.wiwi.jsoil.sys.service.CategoryService;
import com.wiwi.jsoil.sys.service.KnowledgeService;
import com.wiwi.jsoil.sys.service.ResourceService;
import com.wiwi.jsoil.util.RequestUtil;
import com.wiwi.jsoil.util.ResourceUploadUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping({"/sys/knowledge/"})
public class KnowledgeController
  extends BaseController
{
  private static final String TOP_CODE = "km";
  private static final Logger logger = LoggerFactory.getLogger(KnowledgeController.class);
  KnowledgeService service = new KnowledgeService();
  
  @RequestMapping({"treeList.do"})
  public String treeList(@ModelAttribute("query") KnowledgeQ query, Model model)
    throws Exception
  {
    CategoryQ cateQuery = new CategoryQ();
    cateQuery.setRecordPerPage(-1);
    cateQuery.setTopCode("km");
    
    CategoryService cService = new CategoryService();
    List<Category> allList = cService.getList(cateQuery);
    List<Category> topList = new ArrayList();
    Map<Long, List<Category>> cateMap = new HashMap();
    List<Category> subList = null;
    for (Category cat : allList) {
      if (cat.getParentCategory().getCode().equals("km"))
      {
        topList.add(cat);
      }
      else
      {
        subList = (List)cateMap.get(cat.getParentCategory().getId());
        if (subList == null) {
          cateMap.put(cat.getParentCategory().getId(), new ArrayList());
        }
        ((List)cateMap.get(cat.getParentCategory().getId())).add(cat);
      }
    }
    model.addAttribute("kmCategoryList", topList);
    model.addAttribute("cateMap", cateMap);
    processOperationMessage(model);
    
    return "/sys/knowledge/knowledgeTreeList";
  }
  
  @RequestMapping({"list.do"})
  public String list(@ModelAttribute("query") KnowledgeQ query, @RequestParam(required=false) Long chooseCategoryId, Model model)
    throws Exception
  {
    if ((chooseCategoryId != null) && (chooseCategoryId.longValue() != 0L))
    {
      query.setCategoryId(chooseCategoryId);
      model.addAttribute("chooseCategory", new CategoryService().get(query.getCategoryId().longValue()));
    }
    if ((query.getCategoryId() != null) && (query.getCategoryId().longValue() != 0L)) {
      model.addAttribute("chooseCategory", new CategoryService().get(query.getCategoryId().longValue()));
    }
    model.addAttribute("list", this.service.getList(query));
    
    model.addAttribute("pager", query);
    
    processOperationMessage(model);
    
    return "/sys/knowledge/knowledgeList";
  }
  
  @RequestMapping({"queryTreeList.do"})
  public String queryTreeList(@ModelAttribute("query") KnowledgeQ query, Model model)
    throws Exception
  {
    CategoryQ cateQuery = new CategoryQ();
    cateQuery.setRecordPerPage(-1);
    cateQuery.setTopCode("km");
    
    CategoryService cService = new CategoryService();
    List<Category> allList = cService.getList(cateQuery);
    List<Category> topList = new ArrayList();
    Map<Long, List<Category>> cateMap = new HashMap();
    List<Category> subList = null;
    for (Category cat : allList) {
      if (cat.getParentCategory().getCode().equals("km"))
      {
        topList.add(cat);
      }
      else
      {
        subList = (List)cateMap.get(cat.getParentCategory().getId());
        if (subList == null) {
          cateMap.put(cat.getParentCategory().getId(), new ArrayList());
        }
        ((List)cateMap.get(cat.getParentCategory().getId())).add(cat);
      }
    }
    model.addAttribute("kmCategoryList", topList);
    model.addAttribute("cateMap", cateMap);
    processOperationMessage(model);
    
    return "/sys/knowledge/knowledgeQueryTreeList";
  }
  
  @RequestMapping({"queryList.do"})
  public String queryList(@ModelAttribute("query") KnowledgeQ query, @RequestParam(required=false) Long chooseCategoryId, Model model)
    throws Exception
  {
    if ((chooseCategoryId != null) && (chooseCategoryId.longValue() != 0L))
    {
      query.setCategoryId(chooseCategoryId);
      model.addAttribute("chooseCategory", new CategoryService().get(query.getCategoryId().longValue()));
    }
    if ((query.getCategoryId() != null) && (query.getCategoryId().longValue() != 0L)) {
      model.addAttribute("chooseCategory", new CategoryService().get(query.getCategoryId().longValue()));
    }
    query.setStatus(Integer.valueOf(1));
    model.addAttribute("list", this.service.getList(query));
    
    model.addAttribute("pager", query);
    
    processOperationMessage(model);
    
    return "/sys/knowledge/knowledgeQueryList";
  }
  
  @RequestMapping({"add.do"})
  public String addView(@RequestParam(required=false) Long chooseCategoryId, Model model)
    throws Exception
  {
    CategoryQ cateQuery = new CategoryQ();
    cateQuery.setRecordPerPage(-1);
    cateQuery.setTopCode("km");
    model.addAttribute("kmCategoryList", new CategoryService().getList(cateQuery));
    Knowledge instance = new Knowledge();
    if ((chooseCategoryId != null) && (chooseCategoryId.longValue() != 0L)) {
      instance.setCategory(new Category(chooseCategoryId));
    }
    model.addAttribute("instance", instance);
    model.addAttribute("chooseCategoryId", chooseCategoryId);
    
    return "/sys/knowledge/knowledgeAdd";
  }
  
  @RequestMapping(value={"addAction.do"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String addAction(@ModelAttribute("instance") Knowledge instance, @RequestParam(required=false) Long chooseCategoryId, HttpServletRequest request, Model model)
    throws Exception
  {
    User user = getUser();
    instance.setOrgId(user.getOrg().getId());
    instance.setCreateTime(new Date());
    instance.setCreateUserId(user.getId());
    
    Long thumbId = Long.valueOf(RequestUtil.getLongParameter(request, "thumbId"));
    instance.setLogoId(thumbId);
    
    Resource resourceInstance = ResourceUploadUtil.uploadFile(request, user, "knowledge");
    if (resourceInstance != null)
    {
      instance.setFileId(resourceInstance.getId());
      instance.setFileType(resourceInstance.getFileType());
    }
    JSONArray jsonArray = new JSONArray();
    String[] parameterNames = request.getParameterValues("parameterName");
    String[] parameterValues = request.getParameterValues("parameterValue");
    JSONObject object = null;
    if (parameterNames != null) {
      for (int i = 0; i < parameterNames.length; i++)
      {
        object = new JSONObject();
        object.put("name", parameterNames[i]);
        object.put("value", parameterValues[i]);
        jsonArray.put(object);
      }
    }
    instance.setParameters(jsonArray.toString());
    this.service.insert(instance);
    
    setOperationMessage("添加成功！");
    
    return "redirect:list.do?chooseCategoryId=" + (chooseCategoryId == null ? 0L : chooseCategoryId.longValue());
  }
  
  @RequestMapping({"edit.do"})
  public String editView(@RequestParam long id, @RequestParam(required=false) Long chooseCategoryId, Model model)
    throws Exception
  {
    Knowledge instance = this.service.get(id);
    model.addAttribute("parameters", new JSONArray(instance.getParameters()));
    model.addAttribute("instance", instance);
    model.addAttribute("chooseCategoryId", chooseCategoryId);
    model.addAttribute("knowledgeFile", new ResourceService().get(instance.getFileId().longValue()));
    
    CategoryQ cateQuery = new CategoryQ();
    cateQuery.setRecordPerPage(-1);
    cateQuery.setTopCode("km");
    model.addAttribute("kmCategoryList", new CategoryService().getList(cateQuery));
    
    Resource logo = new ResourceService().get(instance.getLogoId().longValue());
    model.addAttribute("resource", logo);
    
    return "/sys/knowledge/knowledgeEdit";
  }
  
  @RequestMapping({"editAction.do"})
  public String editAction(@ModelAttribute("instance") Knowledge instance, @RequestParam(required=false) Long chooseCategoryId, HttpServletRequest request, Model model)
    throws Exception
  {
    User user = getUser();
    instance.setLastModifyTime(new Date());
    instance.setLastModifyUserId(getUser().getId());
    
    Long thumbId = Long.valueOf(RequestUtil.getLongParameter(request, "thumbId"));
    instance.setLogoId(thumbId);
    
    Resource resourceInstance = ResourceUploadUtil.uploadFile(request, user, "knowledge");
    if (resourceInstance != null)
    {
      instance.setFileId(resourceInstance.getId());
      instance.setFileType(resourceInstance.getFileType());
    }
    JSONArray jsonArray = new JSONArray();
    String[] parameterNames = request.getParameterValues("parameterName");
    String[] parameterValues = request.getParameterValues("parameterValue");
    JSONObject object = null;
    if (parameterNames != null) {
      for (int i = 0; i < parameterNames.length; i++)
      {
        object = new JSONObject();
        object.put("name", parameterNames[i]);
        object.put("value", parameterValues[i]);
        jsonArray.put(object);
      }
    }
    instance.setParameters(jsonArray.toString());
    
    this.service.update(instance);
    model.addAttribute("chooseCategoryId", chooseCategoryId);
    setOperationMessage("修改成功！");
    
    return "redirect:list.do?chooseCategoryId=" + chooseCategoryId;
  }
  
  @RequestMapping({"view.do"})
  public String view(@RequestParam long id, @RequestParam(required=false) Long chooseCategoryId, @RequestParam(required=false, value="isModal") Boolean isModal, Model model)
    throws Exception
  {
    Knowledge instance = this.service.get(id);
    model.addAttribute("instance", instance);
    model.addAttribute("parameters", new JSONArray(instance.getParameters()));
    model.addAttribute("chooseCategoryId", chooseCategoryId);
    model.addAttribute("knowledgeFile", new ResourceService().get(instance.getFileId().longValue()));
    
    model.addAttribute("isModal", Boolean.valueOf(isModal == null ? true : isModal.booleanValue()));
    model.addAttribute("chooseCategoryId", chooseCategoryId);
    Resource logo = new ResourceService().get(instance.getLogoId().longValue());
    model.addAttribute("resource", logo);
    return "/sys/knowledge/knowledgeView";
  }
  
  @RequestMapping({"publishAction.do"})
  public String publishAction(@RequestParam(required=true) long id, @RequestParam(required=false) Long chooseCategoryId, Model model)
    throws Exception
  {
    Knowledge instance = this.service.get(id);
    instance.setStatus(Integer.valueOf(1));
    instance.setLastModifyTime(new Date());
    instance.setLastModifyUserId(getUser().getId());
    this.service.update(instance);
    
    setOperationMessage("发布成功！");
    
    return "redirect:list.do?chooseCategoryId=" + chooseCategoryId;
  }
  
  @RequestMapping({"cancelPublishAction.do"})
  public String cancelPublishAction(@RequestParam(required=true) long id, @RequestParam(required=false) Long chooseCategoryId, Model model)
    throws Exception
  {
    Knowledge instance = this.service.get(id);
    instance.setStatus(Integer.valueOf(0));
    instance.setLastModifyTime(new Date());
    instance.setLastModifyUserId(getUser().getId());
    this.service.update(instance);
    
    setOperationMessage("取消发布成功！");
    
    return "redirect:list.do?chooseCategoryId=" + chooseCategoryId;
  }
  
  @RequestMapping({"deleteAction.do"})
  public String deleteAction(@RequestParam(required=true) long id, @RequestParam(required=false) Long chooseCategoryId, Model model)
    throws Exception
  {
    this.service.delete(id);
    
    setOperationMessage("删除成功！");
    
    return "redirect:list.do?chooseCategoryId=" + (chooseCategoryId == null ? 0L : chooseCategoryId.longValue());
  }
  
  @RequestMapping({"batchDeleteAction.do"})
  public String batchDeleteAction(@RequestParam(value="ids", required=true) String ids, @RequestParam(required=false) Long chooseCategoryId, Model model)
    throws Exception
  {
    this.service.batchDelete(ids);
    
    setOperationMessage("批量删除成功！");
    
    return "redirect:list.do?chooseCategoryId=" + (chooseCategoryId == null ? 0L : chooseCategoryId.longValue());
  }
  
  @RequestMapping({"/downloadAction.do"})
  public String download(@RequestParam long id, @RequestParam long resourceId, HttpServletRequest request, HttpServletResponse response)
  {
    try
    {
      this.service.addDownloadCount(id);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return "redirect:/sys/file/downloadAction.do?resourceId=" + resourceId;
  }
}
