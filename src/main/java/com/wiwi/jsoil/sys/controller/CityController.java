package com.wiwi.jsoil.sys.controller;

import com.wiwi.jsoil.base.BaseController;
import com.wiwi.jsoil.sys.model.CityQ;
import com.wiwi.jsoil.sys.service.CityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping({"/sys/city/"})
public class CityController extends BaseController
{
  CityService service;

  public CityController()
  {
    this.service = new CityService(); }

  @RequestMapping({"getCityList.do"})
  public String getCityList(@RequestParam long parentCityId, Model model) throws Exception {
    CityQ cityQ = new CityQ();
    cityQ.setParentIds(parentCityId+"");
    cityQ.setRecordPerPage(-1);
    model.addAttribute("cityList", this.service.getList(cityQ));
    return "sys/organization/cityFragment :: cityFragment";
  }
}