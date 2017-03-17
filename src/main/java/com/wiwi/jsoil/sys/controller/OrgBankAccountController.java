package com.wiwi.jsoil.sys.controller;

import java.util.Date;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wiwi.jsoil.base.BaseController;
import com.wiwi.jsoil.sys.model.City;
import com.wiwi.jsoil.sys.model.OrgBankAccount;
import com.wiwi.jsoil.sys.model.OrgBankAccountQ;
import com.wiwi.jsoil.sys.model.User;
import com.wiwi.jsoil.sys.service.CityService;
import com.wiwi.jsoil.sys.service.OrgBankAccountService;
import com.wiwi.jsoil.util.BankUtil;

@Controller
@RequestMapping({"/sys/organization/orgBank/"})
public class OrgBankAccountController extends BaseController
{
  OrgBankAccountService service;

  public OrgBankAccountController()
  {
    this.service = new OrgBankAccountService(); }

  @RequestMapping({"list.do"})
  public String list(@ModelAttribute("query") OrgBankAccountQ query, Model model) throws Exception {
    User user = getUser();
    query.setOrgId(user.getOrg().getId());
    model.addAttribute("list", this.service.getList(query));

    model.addAttribute("pager", query);

    processOperationMessage(model);

    return "/sys/organization/orgBank/orgBankAccountList";
  }

  @RequestMapping({"edit.do"})
  public String editView(Model model)
    throws Exception
  {
    OrgBankAccountQ query = new OrgBankAccountQ();
    User user = getUser();

    query.setOrgId(user.getOrg().getId());
    List list = this.service.getList(query);
    OrgBankAccount instance = null;
    if ((list == null) || (list.size() < 1)) {
      instance = new OrgBankAccount();
      instance.setBankAccountType(Integer.valueOf(1));
    }
    else instance = (OrgBankAccount)list.get(0);

    model.addAttribute("instance", instance);
    City bankCity = null;
    if ((instance.getBankCityId() != null) && (instance.getBankCityId().intValue() != 0))
      bankCity = new CityService().get(instance.getBankCityId().intValue());

    model.addAttribute("bankCity", bankCity);
    model.addAttribute("bankProvince", new CityService().get(bankCity==null?0:bankCity.getParentId().intValue()));
    model.addAttribute("BANK", com.wiwi.jsoil.util.BankUtil.BANK.class);
    processOperationMessage(model);
    return "/sys/organization/orgBank/orgBankAccountEdit";
  }

  @RequestMapping({"editAction.do"})
  public String editAction(@ModelAttribute("instance") OrgBankAccount instance, Model model) throws Exception
  {
    instance.setLastModifyDate(new Date());
    instance.setLastModifyUserId(getUser().getId());
    if ((instance.getId() == null) || (instance.getId().longValue() == 0L))
      this.service.insert(instance);
    else
      this.service.update(instance);

    setOperationMessage("保存成功！");
    model.addAttribute("instance", instance);
    return "redirect:edit.do";
  }

  @RequestMapping({"view.do"})
  public String view(@RequestParam long id, @RequestParam(required=false, value="isModal") Boolean isModal, Model model) throws Exception
  {
    model.addAttribute("instance", this.service.get(id));

    model.addAttribute("isModal", Boolean.valueOf(isModal.booleanValue()));

    return "/sys/organization/orgBank/orgBankAccountView";
  }
}