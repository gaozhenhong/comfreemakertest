package com.wiwi.freego.hotel.controller;

import com.wiwi.freego.hotel.HotelUtil;
import com.wiwi.freego.hotel.model.Hotel;
import com.wiwi.freego.hotel.model.Hotel.Status;
import com.wiwi.freego.hotel.model.HotelQ;
import com.wiwi.freego.hotel.service.HotelService;
import com.wiwi.freego.hotel.service.HotelUserMappingService;
import com.wiwi.freego.shopkeeper.service.ShopkeeperService;
import com.wiwi.jsoil.base.BaseController;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.NoLoginException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.sys.model.CityQ;
import com.wiwi.jsoil.sys.model.Organization;
import com.wiwi.jsoil.sys.model.User;
import com.wiwi.jsoil.sys.model.UserQ;
import com.wiwi.jsoil.sys.service.CityService;
import com.wiwi.jsoil.sys.service.UserService;
import com.wiwi.jsoil.util.RequestUtil;
import com.wiwi.jsoil.util.ResourceUtil;
import java.io.PrintStream;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/myHotel/"})
public class MyHotelController extends BaseController
{
  HotelService service;

  public MyHotelController()
  {
    this.service = new HotelService();
  }

  @RequestMapping({"list.do"})
  public String list(@ModelAttribute("query") HotelQ query, Model model) throws Exception {
    List myHotelList = this.service.getHotelListByUserId(getUser());
    model.addAttribute("myHotelList", myHotelList);
    model.addAttribute("pager", query);
    model.addAttribute("ResourceUtil", ResourceUtil.class);
    model.addAttribute("HotelStatus", Hotel.Status.class);
    processOperationMessage(model);
    return "/hotel/myHotel/myHotelList";
  }

  @RequestMapping({"myHotelUserList.do"})
  public String myHotelUserList(@ModelAttribute("query") UserQ userQ, @RequestParam(required=true, value="hotelId") Long hotelId, Model model)
    throws Exception
  {
    Hotel hotel = new HotelService().get(hotelId.longValue());
    userQ.setStatus("1");
    String otherCondition = " where id in (select userId from fg_hotel_user_mapping where hotelId=" + hotelId + ")";
    userQ.setOtherCondition(otherCondition);
    model.addAttribute("userList", new UserService().getList(userQ));
    model.addAttribute("pager", userQ);
    model.addAttribute("hotel", hotel);
    model.addAttribute("ResourceUtil", ResourceUtil.class);
    model.addAttribute("HotelStatus", Hotel.Status.class);
    processOperationMessage(model);
    return "/hotel/myHotel/myHotelUserList";
  }

  @RequestMapping({"notHotelUserList.do"})
  public String notHotelUserList(@ModelAttribute("query") UserQ userQ, @RequestParam(required=true, value="hotelId") Long hotelId, Model model)
    throws Exception
  {
    Hotel hotel = new HotelService().get(hotelId.longValue());
    userQ.setStatus("1");
    userQ.setOrgId(hotel.getOrgId()+"");
    String otherCondition = " where id not in (select userId from fg_hotel_user_mapping where hotelId=" + hotelId + ")";
    userQ.setOtherCondition(otherCondition);
    model.addAttribute("userList", new UserService().getList(userQ));
    model.addAttribute("pager", userQ);
    model.addAttribute("hotel", hotel);
    model.addAttribute("ResourceUtil", ResourceUtil.class);
    model.addAttribute("HotelStatus", Hotel.Status.class);
    processOperationMessage(model);
    return "/hotel/myHotel/notHotelUserList";
  }

  @RequestMapping({"addUserToHotelAction.do"})
  @ResponseBody
  public String addUserToHotelAction(@RequestParam(value="ids", required=true) String ids, @RequestParam(value="hotelId", required=true) int hotelId, Model model)
    throws Exception
  {
    if (ids == null)
      ids = "";
    if (ids.startsWith(","))
      ids = ids.substring(1);
    if (ids.endsWith(","))
      ids = ids.substring(0, ids.length() - 1);
    String[] useIdArray = ids.split(",");
    Long[] idLongs = new Long[useIdArray.length];
    for (int i = 0; i < useIdArray.length; ++i)
      idLongs[i] = Long.valueOf(Long.parseLong(useIdArray[i]));

    HotelUserMappingService humService = new HotelUserMappingService();
    humService.addUserToHotel(hotelId, idLongs);
    return "success";
  }

  @RequestMapping({"add.do"})
  public String addView(Model model) throws DaoException, RenderException, NoLoginException {
    User user = getUser();
    List shopkeeperList = new ShopkeeperService().getListByOrgId(user.getOrg().getId());
    model.addAttribute("shopkeeperList", shopkeeperList);

    CityQ cityQ = new CityQ();
    cityQ.setRecordPerPage(-1);
    model.addAttribute("cityList", new CityService().getList(cityQ));
    model.addAttribute("instance", new Hotel());
    return "/hotel/myHotel/hotelAdd";
  }

  @RequestMapping({"addAction.do"})
  public String addAction(@ModelAttribute("instance") Hotel instance, HttpServletRequest request, Model model)
    throws Exception
  {
    User user = getUser();
    instance.setCreateTime(new Date());
    instance.setCreatePersonId(user.getId());
    instance.setOrgId(user.getOrg().getId());
    instance.setCompany(getCompany());
    instance.setStatus(Hotel.Status.EDIT);
    Long thumbId = Long.valueOf(RequestUtil.getLongParameter(request, "thumbId"));
    instance.setLogo(thumbId);
    instance.setCode(HotelUtil.genHotelCode(instance));
    this.service.insert(instance);

    setOperationMessage("添加成功！");

    return "redirect:list.do";
  }

  @RequestMapping({"edit.do"})
  public String editView(@RequestParam int id, Model model)
    throws Exception
  {
    User user = getUser();
    List shopkeeperList = new ShopkeeperService().getListByOrgId(user.getOrg().getId());
    model.addAttribute("shopkeeperList", shopkeeperList);

    CityQ cityQ = new CityQ();
    cityQ.setRecordPerPage(-1);
    model.addAttribute("cityList", new CityService().getList(cityQ));
    model.addAttribute("instance", this.service.get(id));

    return "/hotel/myHotel/hotelEdit";
  }

  @RequestMapping({"editAction.do"})
  public String editAction(@ModelAttribute("instance") Hotel instance, HttpServletRequest request, Model model)
    throws Exception
  {
    System.out.println("edit.............." + instance.getStatus());
    Long thumbId = Long.valueOf(RequestUtil.getLongParameter(request, "thumbId"));
    instance.setLogo(thumbId);
    this.service.update(instance);

    setOperationMessage("修改成功！");

    return "redirect:list.do";
  }

  @RequestMapping({"view.do"})
  public String view(@RequestParam int id, @RequestParam(required=false, value="isModal") Boolean isModal, Model model)
    throws Exception
  {
    model.addAttribute("instance", this.service.get(id));

    model.addAttribute("isModal", Boolean.valueOf(isModal.booleanValue()));

    return "/hotel/hotelView";
  }

  @RequestMapping({"openAction.do"})
  public String openAction(@RequestParam(required=true) int id, Model model)
    throws Exception
  {
    this.service.openHotel(id);
    setOperationMessage("开业成功！");
    return "redirect:list.do";
  }

  @RequestMapping({"shutoutAction.do"})
  public String shutoutAction(@RequestParam(required=true) int id, Model model)
    throws Exception
  {
    this.service.shutoutHotel(id);
    setOperationMessage("停业操作成功！");
    return "redirect:list.do";
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

  @RequestMapping({"baiduMap.do"})
  public String baiduMap(Model model)
    throws Exception
  {
    return "/hotel/myHotel/baiduMap";
  }
}