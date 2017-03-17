package com.wiwi.jsoil.sys.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wiwi.jsoil.base.BaseController;
import com.wiwi.jsoil.sys.CategoryUtil;
import com.wiwi.jsoil.sys.model.Organization;
import com.wiwi.jsoil.sys.model.OrganizationQ;
import com.wiwi.jsoil.sys.service.CategoryService;
import com.wiwi.jsoil.sys.service.OrganizationService;

@Controller
@RequestMapping(value = "/sys/organization/")
public class OrganizationController extends BaseController{


    private static final Logger logger = LoggerFactory.getLogger(OrganizationController.class);

    OrganizationService service = new OrganizationService();



    @RequestMapping(value = "list.do")
    public String list(@ModelAttribute(value="query") OrganizationQ query,Model model) throws Exception {

        model.addAttribute("list", service.getList(query));
        model.addAttribute("categoryList",new CategoryService().getListByCode(CategoryUtil.ORG_TYPE_CODE));
        model.addAttribute("pager", query);

        processOperationMessage(model);

        return "/sys/organization/organizationList";

    }


    @RequestMapping(value = "add.do")
    public String addView(Model model) throws Exception{

        model.addAttribute("instance", new Organization() );
        model.addAttribute("categoryList",new CategoryService().getListByCode(CategoryUtil.ORG_TYPE_CODE));
        model.addAttribute("orgList",new OrganizationService().getList(new OrganizationQ()));
        return "/sys/organization/organizationAdd";

    }


    @RequestMapping(value = "addAction.do")
    public String addAction(@ModelAttribute(value="instance") Organization instance,Model model) throws Exception {
        service.insert(instance);

        setOperationMessage("添加成功！");

        return "redirect:list.do";

    }


    @RequestMapping(value = "edit.do")
    public String editView(@RequestParam int id,Model model)  throws Exception {

        model.addAttribute("instance", service.get(id) );
        model.addAttribute("categoryList",new CategoryService().getListByCode(CategoryUtil.ORG_TYPE_CODE));
        model.addAttribute("orgList",new OrganizationService().getList(new OrganizationQ()));
        return "/sys/organization/organizationEdit";

    }


    @RequestMapping(value = "editAction.do")
    public String editAction(@ModelAttribute(value="instance") Organization instance,Model model) throws Exception {

        service.update(instance);

        setOperationMessage("修改成功！");

        return "redirect:list.do";

    }


    @RequestMapping(value = "view.do")
    public String view(@RequestParam int id,Model model) throws Exception  {

        model.addAttribute("instance", service.get(id) );

        return "/sys/organization/organizationView";

    }


    @RequestMapping(value = "deleteAction.do")
    public String deleteAction(@RequestParam(required=true) int id,Model model) throws Exception {

        service.delete(id);

        setOperationMessage("删除成功！");

        return "redirect:list.do";

    }


    @RequestMapping(value = "batchDeleteAction.do")
    public String batchDeleteAction(@RequestParam(value="ids",required=true) String ids,Model model) throws Exception {

        service.batchDelete(ids);

        setOperationMessage("批量删除成功！");

        return "redirect:list.do";

    }

}