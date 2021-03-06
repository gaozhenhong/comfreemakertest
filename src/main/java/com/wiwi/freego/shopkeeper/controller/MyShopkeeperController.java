package com.wiwi.freego.shopkeeper.controller;

import com.wiwi.freego.shopkeeper.model.Shopkeeper;
import com.wiwi.freego.shopkeeper.model.ShopkeeperQ;
import com.wiwi.freego.shopkeeper.service.ShopkeeperService;
import com.wiwi.jsoil.base.BaseController;
import com.wiwi.jsoil.sys.model.Organization;
import com.wiwi.jsoil.sys.model.User;
import com.wiwi.jsoil.util.RequestUtil;
import com.wiwi.jsoil.util.ResourceUtil;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping({"/shopkeeper/myShopkeeper/"})
public class MyShopkeeperController extends BaseController
{
  ShopkeeperService service;

  public MyShopkeeperController()
  {
    this.service = new ShopkeeperService(); }

  @RequestMapping({"list.do"})
  public String list(@ModelAttribute("query") ShopkeeperQ query, Model model) throws Exception {
    User user = getUser();
    query.setOrgId(user.getOrg().getId());
    model.addAttribute("list", this.service.getList(query));

    model.addAttribute("pager", query);
    model.addAttribute("ResourceUtil", ResourceUtil.class);
    model.addAttribute("user", user);
    
    processOperationMessage(model);
    return "/shopkeeper/myShopkeeper/myShopkeeperList";
  }

  @RequestMapping({"add.do"})
  public String addView(Model model)
  {
    model.addAttribute("instance", new Shopkeeper());

    return "/shopkeeper/myShopkeeper/shopkeeperAdd";
  }

  @RequestMapping({"addAction.do"})
  public String addAction(@ModelAttribute("instance") Shopkeeper instance, HttpServletRequest request, Model model)
    throws Exception
  {
    User user = getUser();
    instance.setOrg(user.getOrg());
    long[] thumbIds = RequestUtil.getLongParameters(request, "thumbId");
    if (thumbIds != null)
      if (thumbIds.length > 1) {
        instance.setLogo(Long.valueOf(thumbIds[0]));
        instance.setQrCodeId(Long.valueOf(thumbIds[1]));
      } else if (thumbIds.length == 1)
        instance.setLogo(Long.valueOf(thumbIds[0]));


    this.service.insert(instance);

    setOperationMessage("添加成功！");

    return "redirect:list.do";
  }

  @RequestMapping({"edit.do"})
  public String editView(@RequestParam long id, Model model)
    throws Exception
  {
    model.addAttribute("instance", this.service.get(id));
    model.addAttribute("ResourceUtil", ResourceUtil.class);
    return "/shopkeeper/myShopkeeper/shopkeeperEdit";
  }

  @RequestMapping({"editAction.do"})
  public String editAction(@ModelAttribute("instance") Shopkeeper instance, HttpServletRequest request, Model model)
    throws Exception
  {
    long[] thumbIds = RequestUtil.getLongParameters(request, "thumbId");
    if (thumbIds != null)
      if (thumbIds.length > 1) {
        instance.setLogo(Long.valueOf(thumbIds[0]));
        instance.setQrCodeId(Long.valueOf(thumbIds[1]));
      } else if (thumbIds.length == 1)
        instance.setLogo(Long.valueOf(thumbIds[0]));


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

    return "/shopkeeper/shopkeeperView";
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
}