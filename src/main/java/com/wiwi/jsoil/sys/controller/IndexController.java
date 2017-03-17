package com.wiwi.jsoil.sys.controller;

import com.wiwi.jsoil.base.BaseController;
import com.wiwi.jsoil.util.AppConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController extends BaseController
{
  @RequestMapping(value={"/"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String index(Model model)
  {
    model.addAttribute("appName", AppConstants.APP_NAME);
    model.addAttribute("companyName", AppConstants.COMPNAY_NAME);
    processOperationMessage(model);
    String indexUrl = AppConstants.getProperty("INDEX_URL");
    if ((indexUrl == null) || (indexUrl.trim().length() < 1))
      indexUrl = "redirect:/login";

    return indexUrl;
  }
}