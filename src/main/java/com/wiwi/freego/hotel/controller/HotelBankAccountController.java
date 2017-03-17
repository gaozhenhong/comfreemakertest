package com.wiwi.freego.hotel.controller;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wiwi.freego.hotel.model.Hotel;
import com.wiwi.freego.hotel.model.HotelBankAccount;
import com.wiwi.freego.hotel.model.HotelBankAccountQ;
import com.wiwi.freego.hotel.service.HotelBankAccountService;
import com.wiwi.freego.hotel.service.HotelService;
import com.wiwi.jsoil.base.BaseController;
import com.wiwi.jsoil.sys.model.City;
import com.wiwi.jsoil.sys.service.CityService;
import com.wiwi.jsoil.util.BankUtil;

@Controller
@RequestMapping({"/hotel/bankAccount/"})
public class HotelBankAccountController extends BaseController
{
  HotelBankAccountService service = new HotelBankAccountService();

  @RequestMapping({"list.do"})
  public String list(@ModelAttribute("query") HotelBankAccountQ query, Model model)
    throws Exception
  {
    model.addAttribute("list", this.service.getList(query));

    model.addAttribute("pager", query);

    processOperationMessage(model);

    return "/hotel/bankAccount/hotelBankAccountList";
  }

  @RequestMapping({"edit.do"})
  public String editView(@RequestParam(required=false) Long hotelId, Model model) throws Exception
  {
    City bankCity = null;
    HotelBankAccount instance = null;
    Hotel chooseHotel = null;
    HotelService hotelServide = new HotelService();
    List<Hotel>myHotelList = hotelServide.getHotelListByUserId(getUser());
    if ((myHotelList != null) && (myHotelList.size() > 0)) {
      if (hotelId == null) {
        chooseHotel = myHotelList.get(0);
        hotelId = chooseHotel.getId();
      } else {
        for (Hotel hotel : myHotelList) {
          if (hotel.getId().longValue() == hotelId.longValue()) {
            chooseHotel = hotel;
            break;
          }
        }
      }
      if (chooseHotel != null) {
        HotelBankAccountQ query = new HotelBankAccountQ();
        query.setHotelId(hotelId);
        List<HotelBankAccount>list = this.service.getList(query);

        if ((list == null) || (list.size() < 1)) {
          instance = new HotelBankAccount();
          instance.setBankAccountType(Integer.valueOf(2));
          instance.setHotel(chooseHotel);
        } else {
          instance = list.get(0);
        }

        if ((instance.getBankCityId() != null) && (instance.getBankCityId().intValue() != 0)) {
          bankCity = new CityService().get(instance.getBankCityId().intValue());
        }
      }
      processOperationMessage(model);
    }

    model.addAttribute("instance", instance);
    model.addAttribute("chooseHotel", chooseHotel);
    model.addAttribute("bankCity", bankCity);
    model.addAttribute("myHotelList", myHotelList);
    model.addAttribute("bankProvince", bankCity == null ? null : new CityService().get(bankCity.getParentId().intValue()));
    model.addAttribute("BANK", BankUtil.BANK.class);
    return "/hotel/bankAccount/hotelBankAccountEdit";
  }

  @RequestMapping({"editAction.do"})
  public String editAction(@ModelAttribute("instance") HotelBankAccount instance, Model model) throws Exception
  {
    instance.setLastModifyDate(new Date());
    instance.setLastModifyUserId(getUser().getId());
    if ((instance.getId() == null) || (instance.getId().longValue() == 0L))
      this.service.insert(instance);
    else {
      this.service.update(instance);
    }
    setOperationMessage("保存成功！");
    model.addAttribute("instance", instance);
    return "redirect:edit.do?hotelId=" + instance.getHotel().getId();
  }

  @RequestMapping({"view.do"})
  public String view(@RequestParam long id, @RequestParam(required=false, value="isModal") Boolean isModal, Model model)
    throws Exception
  {
    model.addAttribute("instance", this.service.get(id));

    model.addAttribute("isModal", Boolean.valueOf(isModal == null ? true : isModal.booleanValue()));

    return "/hotel/bankAccount/hotelBankAccountView";
  }
}