package com.wiwi.jsoil.sys.controller;

import com.wiwi.jsoil.base.BaseController;
import com.wiwi.jsoil.sys.model.Collect;
import com.wiwi.jsoil.sys.model.CollectQ;
import com.wiwi.jsoil.sys.model.User;
import com.wiwi.jsoil.sys.service.CollectService;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/sys/collect/"})
public class CollectController extends BaseController
{
  private static final Logger logger = LoggerFactory.getLogger(CollectController.class);
  CollectService service;

  public CollectController()
  {
    this.service = new CollectService();
  }

  @RequestMapping({"list.do"})
  public String list(@ModelAttribute("query") CollectQ query, Model model) throws Exception {
    model.addAttribute("list", this.service.getList(query));

    model.addAttribute("pager", query);

    processOperationMessage(model);

    return "/sys/collect/collectList";
  }

  @RequestMapping({"addAction.do"})
  public String addAction(Model model, HttpServletRequest request) throws Exception
  {
    String collectBeanId = request.getParameter("collectBeanId");
    String collectModule = request.getParameter("collectModule");
    String title = request.getParameter("title");
    String redirectPath = request.getParameter("redirectPath");

    Collect instance = this.service.getCollect(collectBeanId, collectModule);
    if (instance != null) {
      this.service.delete(instance.getId().longValue());
      setOperationMessage("取消收藏！");
      return "redirect:list.do";
    }
    instance = new Collect();
    instance.setCollectTitle(title);
    instance.setCollectBeanId(collectBeanId);
    instance.setCollectModule(collectModule);
    instance.setRedirectPath(redirectPath);

    User user = getUser();
    instance.setUser(user);
    instance.setCollectTime(new Date());
    this.service.insert(instance);

    setOperationMessage("收藏成功！");
    if ((redirectPath != null) && (redirectPath != ""))
      return "redirect:" + redirectPath + "?id=" + collectBeanId;

    return "redirect:list.do";
  }

  @RequestMapping({"collectAction.do"})
  @ResponseBody
  public boolean collectAction(HttpServletRequest request)
    throws Exception
  {
    String collectBeanId = request.getParameter("collectBeanId");
    String collectModule = request.getParameter("collectModule");
    String title = request.getParameter("title");
    String redirectPath = request.getParameter("redirectPath");

    Collect instance = this.service.getCollect(collectBeanId, collectModule);
    if (instance != null) {
      this.service.delete(instance.getId().longValue());
      setOperationMessage("取消收藏！");
      return false;
    }
    instance = new Collect();
    instance.setCollectTitle(title);
    instance.setCollectBeanId(collectBeanId);
    instance.setCollectModule(collectModule);
    instance.setRedirectPath(redirectPath);

    User user = getUser();
    instance.setUser(user);
    instance.setCollectTime(new Date());
    this.service.insert(instance);

    setOperationMessage("收藏成功！");
    return true;
  }

  @RequestMapping({"view.do"})
  public String view(@RequestParam long id, @RequestParam(required=false, value="isModal") Boolean isModal, Model model)
    throws Exception
  {
    model.addAttribute("isModal", Boolean.valueOf(isModal.booleanValue()));

    Collect collect = this.service.get(id);
    if ((collect.getRedirectPath() != null) && (!("".equals(collect.getRedirectPath()))))
      return "redirect:" + collect.getRedirectPath() + "?id=" + collect.getCollectBeanId();

    return "redirect:list.do";
  }

  @RequestMapping({"deleteAction.do"})
  public String deleteAction(@RequestParam(required=true) long id, Model model) throws Exception
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