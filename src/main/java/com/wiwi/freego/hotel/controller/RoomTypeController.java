package com.wiwi.freego.hotel.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wiwi.freego.hotel.model.Hotel;
import com.wiwi.freego.hotel.model.RoomType;
import com.wiwi.freego.hotel.model.RoomTypeQ;
import com.wiwi.freego.hotel.model.SpecialTimePrice;
import com.wiwi.freego.hotel.service.HotelService;
import com.wiwi.freego.hotel.service.RoomService;
import com.wiwi.freego.hotel.service.RoomTypeService;
import com.wiwi.freego.hotel.service.SpecialTimePriceService;
import com.wiwi.jsoil.base.BaseController;
import com.wiwi.jsoil.util.CalendarUtils;
import com.wiwi.jsoil.util.DateUtils;
import com.wiwi.jsoil.util.RequestUtil;
import com.wiwi.jsoil.util.ResourceUtil;

@Controller
@RequestMapping({"/hotel/roomType/"})
public class RoomTypeController
  extends BaseController
{
  RoomTypeService service = new RoomTypeService();
  HotelService hotelService = new HotelService();
  
  @RequestMapping({"list.do"})
  public String list(@ModelAttribute("query") RoomTypeQ query, Model model)
    throws Exception
  {
    model.addAttribute("roomTypeList", this.service.getList(query));
    
    model.addAttribute("pager", query);
    model.addAttribute("hotel", new HotelService().get(query.getHotelId().longValue()));
    model.addAttribute("ResourceUtil", ResourceUtil.class);
    model.addAttribute("RoomTypeStatus", RoomType.Status.class);
    processOperationMessage(model);
    
    return "/hotel/room/roomTypeList";
  }
  
  @RequestMapping({"calendarPriceList.do"})
  public String calendarPriceList(@RequestParam(value="hotelId", required=false) Long hotelId, @RequestParam(value="roomTypeId", required=false) Long roomTypeId, @RequestParam(value="month", required=false) @DateTimeFormat(pattern="yyyy-MM") Date month, Model model)
    throws Exception
  {
    processOperationMessage(model);
    List<Hotel> myHotelList = this.hotelService.getHotelListByUserId(getUser());
    if ((myHotelList == null) || (myHotelList.size() < 1)) {
      return "/roomBoard/noHotelInfo";
    }
    Hotel hotel = (Hotel)myHotelList.get(0);
    if (hotelId == null) {
      hotelId = hotel.getId();
    } else {
      hotel = this.hotelService.get(hotelId.longValue());
    }
    List<RoomType> roomTypeList = this.service.getOpeningList(hotelId);
    RoomType roomType = (RoomType)roomTypeList.get(0);
    if (roomTypeId == null) {
      roomTypeId = roomType.getId();
    } else {
      roomType = this.service.get(roomTypeId.longValue());
    }
    model.addAttribute("myHotelList", myHotelList);
    month = month == null ? new Date() : month;
    
    Date firstDay = DateUtils.getFirstDayOfWeek(DateUtils.getFirstDayOfMonth(month));
    Date monthLastDay = DateUtils.getLastDayOfMonth(month);
    Date lastDay = DateUtils.getLastDayOfWeek(monthLastDay);
    
    int days = DateUtils.compareDate(firstDay, lastDay);
    int weeks = (days + 6) / 7;
    model.addAttribute("weeks", Integer.valueOf(weeks));
    model.addAttribute("firstDay", firstDay);
    model.addAttribute("lastDay", lastDay);
    model.addAttribute("month", month);
    model.addAttribute("DateUtils", DateUtils.class);
    model.addAttribute("CalendarUtils", CalendarUtils.class);
    model.addAttribute("roomType", roomType);
    model.addAttribute("roomTypeList", roomTypeList);
    model.addAttribute("hotel", hotel);
    model.addAttribute("ResourceUtil", ResourceUtil.class);
    return "/hotel/room/calendarPriceList";
  }
  
  @RequestMapping({"openAction.do"})
  public String openAction(@RequestParam(required=true) int id, @RequestParam(required=true) int hotelId, Model model)
    throws Exception
  {
    this.service.openRoomType(id);
    return "redirect:list.do?hotelId=" + hotelId;
  }
  
  @RequestMapping({"cancelOpenAction.do"})
  public String cancelOpenAction(@RequestParam(required=true) int id, @RequestParam(required=true) int hotelId, Model model)
    throws Exception
  {
    this.service.cancelOpenRoomType(id);
    return "redirect:list.do?hotelId=" + hotelId;
  }
  
  @RequestMapping({"add.do"})
  public String addView(@RequestParam(value="hotelId", required=true) Long hotelId, Model model)
  {
    RoomType roomType = new RoomType();
    roomType.setHotelId(hotelId);
    roomType.setRoomNumber(Integer.valueOf(1));
    roomType.setAvailableNumber(Integer.valueOf(2));
    model.addAttribute("instance", roomType);
    
    return "/hotel/room/roomTypeAdd";
  }
  
  @RequestMapping({"addAction.do"})
  public String addAction(@ModelAttribute("instance") RoomType instance, HttpServletRequest request, Model model)
    throws Exception
  {
    Long thumbId = Long.valueOf(RequestUtil.getLongParameter(request, "thumbId"));
    instance.setLogo(thumbId);
    String[] roomNos = request.getParameterValues("roomNo");
    instance.setRoomNumber(Integer.valueOf(roomNos == null ? 0 : roomNos.length));
    instance.setStatus(RoomType.Status.EDIT);
    this.service.insert(instance);
    new RoomService().setRooms(instance.getHotelId().longValue(), instance.getId().longValue(), roomNos);
    
    setOperationMessage("����������");
    
    return "redirect:list.do?hotelId=" + instance.getHotelId();
  }
  
  @RequestMapping({"edit.do"})
  public String editView(@RequestParam long id, Model model)
    throws Exception
  {
    model.addAttribute("instance", this.service.get(id));
    model.addAttribute("roomList", new RoomService().getPublishedList(id));
    return "/hotel/room/roomTypeEdit";
  }
  
  @RequestMapping({"editAction.do"})
  public String editAction(@ModelAttribute("instance") RoomType instance, HttpServletRequest request, Model model)
    throws Exception
  {
    Long thumbId = Long.valueOf(RequestUtil.getLongParameter(request, "thumbId"));
    instance.setLogo(thumbId);
    String[] roomNos = request.getParameterValues("roomNo");
    long[] notDeleteRoomIds = RequestUtil.getLongParameters(request, "roomId");
    int roomNumber = roomNos == null ? 0 : roomNos.length;
    if (notDeleteRoomIds != null) {
      roomNumber += notDeleteRoomIds.length;
    }
    instance.setRoomNumber(Integer.valueOf(roomNumber));
    instance.setStatus(RoomType.Status.EDIT);
    
    this.service.update(instance);
    new RoomService().setRooms(instance.getHotelId().longValue(), instance.getId().longValue(), roomNos, notDeleteRoomIds);
    setOperationMessage("����������");
    
    return "redirect:list.do?hotelId=" + instance.getHotelId();
  }
  
  @RequestMapping({"setSpecialTimePriceAction.do"})
  @ResponseBody
  public String setSpecialTimePriceAction(@RequestBody SpecialTimePrice specialTimePrice, Model model)
    throws Exception
  {
    SpecialTimePriceService stpService = new SpecialTimePriceService();
    stpService.insert(specialTimePrice);
    return "success";
  }
  
  @RequestMapping({"setSpecialTimePriceAction2.do"})
  @ResponseBody
  public String setSpecialTimePriceAction2(@RequestParam("roomTypeId") long roomTypeId, @RequestParam("price") float price, @RequestParam("specialTime[]") String[] specialTimeList, Model model)
    throws Exception
  {
    if (specialTimeList != null)
    {
      SpecialTimePriceService stpService = new SpecialTimePriceService();
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      String[] arrayOfString;
      int j = (arrayOfString = specialTimeList).length;
      for (int i = 0; i < j; i++)
      {
        String s = arrayOfString[i];
        SpecialTimePrice specialTimePrice = new SpecialTimePrice();
        specialTimePrice.setPrice(Float.valueOf(price));
        specialTimePrice.setRoomTypeId(Long.valueOf(roomTypeId));
        specialTimePrice.setSpecialTime(sdf.parse(s));
        stpService.insert(specialTimePrice);
      }
    }
    return "success";
  }
  
  @RequestMapping({"view.do"})
  public String view(@RequestParam int id, @RequestParam(required=false, value="isModal") Boolean isModal, Model model)
    throws Exception
  {
    model.addAttribute("instance", this.service.get(id));
    
    model.addAttribute("isModal", Boolean.valueOf(isModal == null ? true : isModal.booleanValue()));
    
    return "/hotel/room/roomTypeView";
  }
  
  @RequestMapping({"deleteAction.do"})
  public String deleteAction(@RequestParam(required=true) int id, @RequestParam(required=true) int hotelId, Model model)
    throws Exception
  {
    this.service.delete(id);
    
    setOperationMessage("����������");
    
    return "redirect:list.do?hotelId=" + hotelId;
  }
  
  @RequestMapping({"batchDeleteAction.do"})
  public String batchDeleteAction(@RequestParam(value="ids", required=true) String ids, Model model)
    throws Exception
  {
    this.service.batchDelete(ids);
    
    setOperationMessage("��������������");
    
    return "redirect:list.do";
  }
}
