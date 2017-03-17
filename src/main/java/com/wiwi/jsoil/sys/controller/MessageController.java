package com.wiwi.jsoil.sys.controller;

import com.wiwi.jsoil.base.BaseController;
import com.wiwi.jsoil.sys.model.Message;
import com.wiwi.jsoil.sys.model.MessageQ;
import com.wiwi.jsoil.sys.model.User;
import com.wiwi.jsoil.sys.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/sys/message/"})
public class MessageController extends BaseController
{
  private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
  MessageService service;

  public MessageController()
  {
    this.service = new MessageService();
  }

  @RequestMapping({"list.do"})
  public String list(@ModelAttribute("query") MessageQ query, Model model) throws Exception
  {
    User user = getUser();
    query.setUserId(user.getId());
    model.addAttribute("list", this.service.getList(query));

    model.addAttribute("pager", query);
    long unreadNumber = this.service.getUnreadNumber(user.getId()).longValue();
    model.addAttribute("unreadNumber", Long.valueOf(unreadNumber));

    processOperationMessage(model);

    return "/sys/message/messageList";
  }

  @RequestMapping({"sentList.do"})
  public String sentList(@ModelAttribute("query") MessageQ query, Model model)
    throws Exception
  {
    User user = getUser();
    query.setSentPersonId(user.getId());
    model.addAttribute("list", this.service.getList(query));

    model.addAttribute("pager", query);

    processOperationMessage(model);

    return "/sys/message/messageSentList";
  }

  @RequestMapping({"add.do"})
  public String addView(Model model)
  {
    model.addAttribute("instance", new Message());

    return "/sys/message/messageAdd";
  }

  @RequestMapping({"addAction.do"})
  public String addAction(@ModelAttribute("instance") Message instance, Model model)
    throws Exception
  {
    this.service.insert(instance);

    setOperationMessage("添加成功！");

    return "redirect:list.do";
  }

  @RequestMapping({"edit.do"})
  public String editView(@RequestParam String id, Model model)
    throws Exception
  {
    model.addAttribute("instance", this.service.get(id));

    return "/sys/message/messageEdit";
  }

  @RequestMapping({"editAction.do"})
  public String editAction(@ModelAttribute("instance") Message instance, Model model)
    throws Exception
  {
    this.service.update(instance);

    setOperationMessage("修改成功！");

    return "redirect:list.do";
  }

  @RequestMapping({"view.do"})
  public String view(@RequestParam String id, @RequestParam(required=false, value="isModal") Boolean isModal, Model model)
    throws Exception
  {
    this.service.markToRead(id);
    model.addAttribute("instance", this.service.get(id));

    model.addAttribute("isModal", Boolean.valueOf(isModal.booleanValue()));

    return "/sys/message/messageView";
  }

  @RequestMapping({"deleteAction.do"})
  public String deleteAction(@RequestParam(required=true) String id, Model model)
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

  @RequestMapping({"markToReadAction.do"})
  @ResponseBody
  public String markToReadAction(@RequestParam(value="ids", required=true) String ids, Model model)
    throws Exception
  {
    this.service.markToRead(ids);

    setOperationMessage("标记为已读！");

    return "success";
  }
}