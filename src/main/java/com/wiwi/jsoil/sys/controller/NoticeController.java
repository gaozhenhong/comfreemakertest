package com.wiwi.jsoil.sys.controller;

import com.wiwi.jsoil.base.BaseController;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.sys.model.Notice;
import com.wiwi.jsoil.sys.model.NoticeQ;
import com.wiwi.jsoil.sys.model.User;
import com.wiwi.jsoil.sys.service.MessageService;
import com.wiwi.jsoil.sys.service.NoticeService;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping({"/sys/notice/"})
public class NoticeController extends BaseController
{
  private static final Logger logger = LoggerFactory.getLogger(NoticeController.class);
  NoticeService service;

  public NoticeController()
  {
    this.service = new NoticeService();
  }

  @RequestMapping({"list.do"})
  public String list(@ModelAttribute("query") NoticeQ query, Model model)
    throws Exception
  {
    model.addAttribute("list", this.service.getList(query));

    model.addAttribute("pager", query);

    processOperationMessage(model);

    return "/sys/notice/noticeList";
  }

  @RequestMapping({"add.do"})
  public String addView(Model model)
  {
    model.addAttribute("instance", new Notice());

    return "/sys/notice/noticeAdd";
  }

  @RequestMapping({"addAction.do"})
  public String addAction(@ModelAttribute("instance") Notice instance, Model model)
    throws Exception
  {
    instance.setCreatePersonId(getUser().getId());
    instance.setCreateTime(new Date());
    String[] scopeContent = getRequest().getParameterValues("scope_content");
    if ((scopeContent != null) && (scopeContent.length > 0)) {
      instance.setScopeDetails(new JSONArray(scopeContent).toString());
    }

    this.service.insert(instance);

    setOperationMessage("添加成功！");

    return "redirect:list.do";
  }

  @RequestMapping({"edit.do"})
  public String editView(@RequestParam long id, Model model)
    throws Exception
  {
    model.addAttribute("instance", this.service.get(id));

    return "/sys/notice/noticeEdit";
  }

  @RequestMapping({"editAction.do"})
  public String editAction(@ModelAttribute("instance") Notice instance, Model model)
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

    return "/sys/notice/noticeView";
  }

  @RequestMapping({"publishAction.do"})
  public String publishAction(@RequestParam(required=true) long id, Model model)
    throws Exception
  {
    User user = getUser();
    Notice instance = this.service.get(id);
    instance.setStatus(Integer.valueOf(1));
    instance.setPublishPersonId(user.getId());
    instance.setPublishTime(new Date());
    this.service.update(instance);
    sentMessage(instance, user);
    setOperationMessage("发布成功！");

    return "redirect:list.do";
  }

  private static void sentMessage(Notice notice, User sentUser)
    throws DaoException
  {
    String messageType = "系统公告";
    if ((notice.getType() != null) && (notice.getType().trim().length() > 0))
      messageType = messageType + "-" + notice.getType();

    String originName = "系统公告";
    String link = "/sys/notice/view.do?isModel=true&id=" + notice.getId();
    String otherCondition = "";
    if ((notice.getScope().equals("allUser")) || (notice.getScope().equals("guest")))
      otherCondition = ""; else {
      String groupIds;
      if (notice.getScope().equals("group")) {
        groupIds = notice.getScopeDetails();
        groupIds = groupIds.replace("[", "");
        groupIds = groupIds.replace("]", "");
        otherCondition = " and id in (select userId from s_groupuser where groupId in (" + groupIds + ")) ";
      } else if (notice.getScope().equals("user")) {
        groupIds = notice.getScopeDetails();
        groupIds = groupIds.replace("[", "");
        groupIds = groupIds.replace("]", "");
        otherCondition = " and id in (" + groupIds + ") ";
      } else if (notice.getScope().equals("org")) {
        groupIds = notice.getScopeDetails();
        groupIds = groupIds.replace("[", "");
        groupIds = groupIds.replace("]", "");
        otherCondition = " and orgId in (" + groupIds + ") "; }
    }
    MessageService mService = new MessageService();
    mService.batchAddMessageFromUser(messageType, originName, notice.getTitle(), 
      link, notice.getContent(), sentUser, otherCondition);
  }

  @RequestMapping({"cancelPublishAction.do"})
  public String cancelPublishAction(@RequestParam(required=true) long id, @RequestParam(required=false) Long chooseCategoryId, Model model)
    throws Exception
  {
    Notice instance = this.service.get(id);
    instance.setStatus(Integer.valueOf(0));

    this.service.update(instance);

    setOperationMessage("取消发布成功！");

    return "redirect:list.do";
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