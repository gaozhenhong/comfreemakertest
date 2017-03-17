package com.wiwi.freego.hotel.controller;

import com.wiwi.freego.hotel.HotelUtil;
import com.wiwi.freego.hotel.model.Hotel;
import com.wiwi.freego.hotel.model.HotelQ;
import com.wiwi.freego.hotel.service.HotelService;
import com.wiwi.freego.hotel.service.RoomService;
import com.wiwi.jsoil.base.BaseController;
import com.wiwi.jsoil.util.ResourceUtil;
import com.wiwi.jsoil.util.SqlUtil;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONArray;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/hotel/"})
public class HotelController extends BaseController
{
  HotelService service;
  RoomService roomService;

  public HotelController()
  {
    this.service = new HotelService();
    this.roomService = new RoomService();
  }

  @RequestMapping({"list.do"})
  public String list(@ModelAttribute("query") HotelQ query, Model model) throws Exception {
    model.addAttribute("list", this.service.getList(query));

    model.addAttribute("pager", query);

    processOperationMessage(model);

    return "/hotel/hotelList";
  }

  @RequestMapping({"view.do"})
  public String view(@RequestParam int id, HttpServletRequest request, @RequestParam(required=false, value="isModal") Boolean isModal, Model model)
    throws Exception
  {
    Hotel hotel = this.service.get(id);
    model.addAttribute("instance", hotel);

    model.addAttribute("isModal", Boolean.valueOf(isModal.booleanValue()));
    model.addAttribute("ResourceUtil", ResourceUtil.class);
    String qrCodeUrl = HotelUtil.getHotelQrCodeUrl(request, hotel);
    model.addAttribute("qrCodeUrl", qrCodeUrl);
    return "/hotel/hotelView";
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

  @RequestMapping({"getCommentHotels.do"})
  @ResponseBody
  public List<Hotel> getCommentHotels(@RequestBody Map<String, String> map, Model model)
    throws Exception
  {
    String hotelCodes = (String)map.get("hotelCodes");
    HotelQ orderQuery = new HotelQ();
    orderQuery.setRecordPerPage(-1);
    String otherCondition = " where code in (" + SqlUtil.getInSqlStr(hotelCodes) + ") ";
    orderQuery.setOtherCondition(otherCondition);
    List hotelList = this.service.getList(orderQuery);
    return hotelList;
  }

  @RequestMapping({"getHotelByIds.do"})
  @ResponseBody
  public List<Hotel> getHotelByIds(@RequestBody Map<String, String> map, Model model)
    throws Exception
  {
    String hotelIds = (String)map.get("hotelIds");
    HotelQ orderQuery = new HotelQ();
    orderQuery.setRecordPerPage(-1);
    String otherCondition = " where id in (" + SqlUtil.getInSqlStr(hotelIds) + ") ";
    orderQuery.setOtherCondition(otherCondition);
    List hotelList = this.service.getList(orderQuery);
    return hotelList;
  }

  @RequestMapping({"getHotelRoomNumber.do"})
  @ResponseBody
  public String getHotelRoomNumber(@RequestBody Map<String, String> map, Model model)
    throws Exception
  {
    String allIds = (String)map.get("allIds");
    return this.roomService.getHotelRoomNumber(allIds).toString();
  }

  @RequestMapping({"getHotelVacantRoomNumber.do"})
  @ResponseBody
  public String getHotelVacantRoomNumber(@RequestBody Map<String, String> map, Model model)
    throws Exception
  {
    String allIds = (String)map.get("allIds");
    return this.roomService.getHotelVacantRoomNumber(allIds).toString();
  }

  @RequestMapping({"getHotelVacantRoomNumberOnDate.do"})
  @ResponseBody
  public long getHotelVacantRoomNumberOnDate(@RequestParam(value="hotelId", required=true) Long hotelId, @RequestParam(value="currentDate", required=true) @DateTimeFormat(pattern="yyyy-MM-dd") Date currentDate, Model model)
    throws Exception
  {
    return this.roomService.getHotelVacantRoomNumberOnDate(hotelId.longValue(), currentDate);
  }
}