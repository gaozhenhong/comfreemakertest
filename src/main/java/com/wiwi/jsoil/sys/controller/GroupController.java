package com.wiwi.jsoil.sys.controller;

import com.wiwi.jsoil.base.BaseController;
import com.wiwi.jsoil.sys.model.Group;
import com.wiwi.jsoil.sys.model.GroupQ;
import com.wiwi.jsoil.sys.model.UserQ;
import com.wiwi.jsoil.sys.service.GroupService;
import com.wiwi.jsoil.sys.service.UserService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/sys/group/"})
public class GroupController extends BaseController
{
  GroupService service;

  public GroupController()
  {
    this.service = new GroupService();
  }

  @RequestMapping({"list.do"})
  public String list(@ModelAttribute("query") GroupQ query, Model model) throws Exception {
    List groupList = this.service.getList(query);
    model.addAttribute("list", groupList);

    model.addAttribute("pager", query);

    processOperationMessage(model);

    return "/sys/group/groupList";
  }

  @RequestMapping({"groupChooseList.do"})
  public String groupChooseList(@ModelAttribute("query") GroupQ query, Model model)
    throws Exception
  {
    List groupList = this.service.getList(query);
    model.addAttribute("list", groupList);
    model.addAttribute("pager", query);
    return "/sys/group/groupChooseList";
  }

  @RequestMapping({"groupUserManagerList.do"})
  public String groupUserManagerList(@ModelAttribute("query") GroupQ query, Model model)
    throws Exception
  {
    query.setRecordPerPage(-1);
    List groupList = this.service.getList(query);
    model.addAttribute("list", groupList);

    model.addAttribute("pager", query);
    Group chooseGroup = (Group)groupList.get(0);

    model.addAttribute("chooseGroup", chooseGroup);

    processOperationMessage(model);

    return "/sys/group/groupUserManagerList";
  }

  @RequestMapping({"groupUserList.do"})
  public String grouUserlist(@ModelAttribute("query") UserQ query, Model model)
    throws Exception
  {
    UserService userService = new UserService();
    model.addAttribute("list", userService.getList(query));

    model.addAttribute("pager", query);
    Group chooseGroup = this.service.get(new Integer(query.getGroupId()).intValue());
    model.addAttribute("chooseGroup", chooseGroup);
    return "/sys/group/groupUserList";
  }

  @RequestMapping({"notGroupUserList.do"})
  public String notGroupUserList(@ModelAttribute("query") UserQ query, Model model)
    throws Exception
  {
    UserService userService = new UserService();
    model.addAttribute("list", userService.getList(query));

    model.addAttribute("pager", query);
    Group chooseGroup = this.service.get(new Integer(query.getGroupIdNotIn()).intValue());
    model.addAttribute("chooseGroup", chooseGroup);
    return "/sys/group/notGroupUserList";
  }

  @RequestMapping({"addUserToGroupAction.do"})
  @ResponseBody
  public String addUserToGroupAction(@RequestParam(value="ids", required=true) String ids, @RequestParam(value="groupId", required=true) int groupId, Model model)
    throws Exception
  {
    this.service.addUserToGroup(groupId, ids);
    model.addAttribute("groupId", Integer.valueOf(groupId));
    model.addAttribute("chooseGroup", this.service.get(groupId));
    return "success";
  }

  @RequestMapping({"removeUserFromGroupAction.do"})
  public String removeUserFromGroupAction(@RequestParam(value="ids", required=true) String ids, @RequestParam(value="groupId", required=true) int groupId, Model model) throws Exception
  {
    this.service.removeUserFromGroup(groupId, ids);
    model.addAttribute("groupId", Integer.valueOf(groupId));
    model.addAttribute("chooseGroup", this.service.get(groupId));
    setOperationMessage("从用户组中删除用户操作成功！");
    return "redirect:groupUserList.do";
  }

  @RequestMapping({"groupModuleList.do"})
  public String groupModuleList(@RequestParam(value="groupId", required=false) Integer groupId, Model model)
    throws Exception
  {
    GroupQ groupQ = new GroupQ();
    groupQ.setRecordPerPage(-1);
    List groupList = this.service.getList(groupQ);
    model.addAttribute("list", groupList);

    Group group = null;
    if ((groupId == null) || (groupId.intValue() == 0))
      group = (Group)groupList.get(0);
    else
      group = this.service.get(groupId.intValue());

    model.addAttribute("chooseGroup", group);
    processOperationMessage(model);
    return "/sys/group/groupModuleList";
  }

  @RequestMapping({"addModuleToGroupAction.do"})
  public String addModuleToGroupAction(@RequestParam(value="choosedNode", required=true) String choosedNode, @RequestParam(value="chooseGroupId", required=true) int groupId, Model model)
    throws Exception
  {
    this.service.updateGroupModule(groupId, choosedNode);
    model.addAttribute("groupId", Integer.valueOf(groupId));
    model.addAttribute("chooseGroup", this.service.get(groupId));
    setOperationMessage("权限修改成功！");
    return "redirect:groupModuleList.do";
  }

  @RequestMapping({"add.do"})
  public String addView(Model model)
  {
    model.addAttribute("instance", new Group());

    return "/sys/group/groupAdd";
  }

  @RequestMapping({"addAction.do"})
  public String addAction(@ModelAttribute("instance") Group instance, Model model)
    throws Exception
  {
    this.service.insert(instance);

    setOperationMessage("添加成功！");
    return "redirect:list.do";
  }

  @RequestMapping({"edit.do"})
  public String editView(@RequestParam int id, Model model)
    throws Exception
  {
    model.addAttribute("instance", this.service.get(id));

    return "/sys/group/groupEdit";
  }

  @RequestMapping({"editAction.do"})
  public String editAction(@ModelAttribute("instance") Group instance, Model model)
    throws Exception
  {
    this.service.update(instance);

    setOperationMessage("修改成功！");
    return "redirect:list.do";
  }

  @RequestMapping({"view.do"})
  public String view(@RequestParam int id, Model model)
    throws Exception
  {
    model.addAttribute("instance", this.service.get(id));

    return "/sys/group/groupView";
  }

  @RequestMapping({"deleteAction.do"})
  public String deleteAction(@RequestParam(required=true) int id, Model model)
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