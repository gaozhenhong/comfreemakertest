package com.wiwi.jsoil.sys.controller;

import com.wiwi.jsoil.base.BaseController;
import com.wiwi.jsoil.sys.model.Organization;
import com.wiwi.jsoil.sys.model.PlatformProperty;
import com.wiwi.jsoil.sys.model.PlatformPropertyQ;
import com.wiwi.jsoil.sys.model.User;
import com.wiwi.jsoil.sys.service.PlatformPropertyService;
import com.wiwi.jsoil.util.RequestUtil;
import com.wiwi.jsoil.util.SqlUtil;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/sys/platformProperty/"})
public class PlatformPropertyController extends BaseController
{
  PlatformPropertyService service;

  public PlatformPropertyController()
  {
    this.service = new PlatformPropertyService();
  }

  @RequestMapping({"list.do"})
  public String list(@ModelAttribute("query") PlatformPropertyQ query, Model model) throws Exception {
    model.addAttribute("list", this.service.getList(query));

    model.addAttribute("pager", query);

    processOperationMessage(model);

    return "/sys/platformProperty/platformPropertyList";
  }

  @RequestMapping({"add.do"})
  public String addView(Model model)
  {
    model.addAttribute("instance", new PlatformProperty());

    return "/sys/platformProperty/platformPropertyAdd";
  }

  @RequestMapping({"addAction.do"})
  public String addAction(@ModelAttribute("instance") PlatformProperty instance, Model model)
    throws Exception
  {
    this.service.insert(instance);

    setOperationMessage("添加成功！");

    return "redirect:list.do";
  }

  @RequestMapping({"edit.do"})
  public String editView(@RequestParam long id, Model model)
    throws Exception
  {
    model.addAttribute("instance", this.service.get(id));

    return "/sys/platformProperty/platformPropertyEdit";
  }

  @RequestMapping({"platformPropertyConfig.do"})
  public String platformPropertyConfig(Model model)
    throws Exception
  {
    return "/sys/platformProperty/platformPropertyConfig";
  }

  @RequestMapping({"platformPropertyConfigAction.do"})
  public String platformPropertyConfigAction(HttpServletRequest request, Model model)
    throws Exception
  {
    User user = getUser();
    Integer companyId = getCompany().getId();
    long[] propertyIds = RequestUtil.getLongParameters(request, "propertyId");
    String[] propertyNames = request.getParameterValues("propertyName");
    String[] propertyValues = request.getParameterValues("propertyValue");
    String[] propertyCodes = request.getParameterValues("propertyCode");
    PlatformProperty pp = null;
    for (int i = 0; i < propertyCodes.length; ++i) {
      pp = new PlatformProperty();
      pp.setCode(propertyCodes[i]);
      pp.setName(propertyNames[i]);
      pp.setValue(propertyValues[i]);
      pp.setLastModifyTime(new Date());
      pp.setLastModifyUser(user.getId());
      pp.setOrgId(user.getOrg().getId());
      pp.setCompanyId(companyId);
      if (propertyIds[i] == 0L)
        this.service.insert(pp);
      else
        this.service.update(pp);
    }

    return "/sys/platformProperty/platformPropertyConfig";
  }

  @RequestMapping({"editAction.do"})
  public String editAction(@ModelAttribute("instance") PlatformProperty instance, Model model)
    throws Exception
  {
    this.service.update(instance);

    setOperationMessage("修改成功！");

    return "redirect:list.do";
  }

  @RequestMapping({"view.do"})
  public String view(@RequestParam long id, @RequestParam(required=false, value="isModal") Boolean isModal, Model model)
    throws Exception
  {
    model.addAttribute("instance", this.service.get(id));

    model.addAttribute("isModal", Boolean.valueOf(isModal.booleanValue()));

    return "/sys/platformProperty/platformPropertyView";
  }

  @RequestMapping({"deleteAction.do"})
  public String deleteAction(@RequestParam(required=true) long id, Model model)
    throws Exception
  {
    this.service.delete(id);

    setOperationMessage("删除成功！");

    return "redirect:list.do";
  }

  @RequestMapping({"batchDeleteAction.do"})
  public String batchDeleteAction(@RequestParam(value="ids", required=true) String ids, Model model)
    throws Exception
  {
    this.service.batchDelete(ids);

    setOperationMessage("批量删除成功！");

    return "redirect:list.do";
  }

  @RequestMapping({"getProperyList.do"})
  @ResponseBody
  public List<PlatformProperty> getCommentHotels(@RequestBody Map<String, String> map, Model model)
    throws Exception
  {
    String codes = (String)map.get("codes");
    PlatformPropertyQ query = new PlatformPropertyQ();
    query.setRecordPerPage(-1);
    String otherCondition = " where code in (" + SqlUtil.getInSqlStr(codes) + ") ";
    query.setOtherCondition(otherCondition);
    List list = this.service.getList(query);
    return list;
  }
}