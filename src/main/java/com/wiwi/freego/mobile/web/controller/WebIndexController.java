package com.wiwi.freego.mobile.web.controller;

import com.wiwi.edb.order.hotelOrder.HotelOrderUtil;
import com.wiwi.edb.order.hotelOrder.model.HotelOrder;
import com.wiwi.edb.order.hotelOrder.model.HotelOrderDetails;
import com.wiwi.edb.order.hotelOrder.service.HotelOrderService;
import com.wiwi.edb.order.model.Order;
import com.wiwi.edb.order.model.Order.PayStatus;
import com.wiwi.edb.order.model.Order.Status;
import com.wiwi.edb.order.service.OrderDetailsService;
import com.wiwi.freego.hotel.model.Hotel;
import com.wiwi.freego.hotel.model.ReservationRoom;
import com.wiwi.freego.hotel.model.RoomType;
import com.wiwi.freego.hotel.service.HotelService;
import com.wiwi.freego.hotel.service.ReservationRoomService;
import com.wiwi.freego.hotel.service.RoomService;
import com.wiwi.freego.hotel.service.RoomTypeService;
import com.wiwi.freego.shopkeeper.model.Shopkeeper;
import com.wiwi.jsoil.base.BaseController;
import com.wiwi.jsoil.member.model.Member;
import com.wiwi.jsoil.sys.model.City;
import com.wiwi.jsoil.sys.service.CityService;
import com.wiwi.jsoil.util.AppConstants;
import com.wiwi.jsoil.util.DateUtils;
import com.wiwi.jsoil.util.ResourceUtil;
import com.wiwi.jsoil.util.WxUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/site/mobile/web/{siteCode}/"})
public class WebIndexController extends BaseController
{
  CityService cityService;
  HotelService hotelService;
  HotelOrderService orderService;
  RoomTypeService romeTypeService;
  RoomService romeService;

  public WebIndexController()
  {
    this.cityService = new CityService();
    this.hotelService = new HotelService();
    this.orderService = new HotelOrderService();
    this.romeTypeService = new RoomTypeService();
    this.romeService = new RoomService();
  }

  @RequestMapping({"/index"})
  public String home(@PathVariable String siteCode, Model model, HttpSession session)
    throws Exception
  {
    List arrayCityList = getCityList();

    Hotel hotel = this.hotelService.getByCode(siteCode);

    session.setAttribute("CuRrEnThOtEl", hotel);
    if (hotel == null)
      hotel = new Hotel();

    model.addAttribute("hotel", hotel);

    model.addAttribute("siteCode", siteCode);
    model.addAttribute("arrayCityList", arrayCityList);
    return "/site/mobile/web/index";
  }

  @RequestMapping({"/map"})
  public String indexMap(@PathVariable String siteCode, Model model)
    throws Exception
  {
    model.addAttribute("siteCode", siteCode);
    return "/site/mobile/web/pano/project/sl/preview";
  }

  @RequestMapping({"/bdnav/map"})
  public String bdNavMap(@PathVariable String siteCode, @RequestParam(value="hotelId", required=true) long hotelId, Model model)
    throws Exception
  {
    model.addAttribute("siteCode", siteCode);
    Hotel hotel = this.hotelService.get(hotelId);
    model.addAttribute("hotel", hotel);
    model.addAttribute("lon", hotel.getLongitude());
    model.addAttribute("lat", hotel.getLatitude());
    model.addAttribute("hotelLogo", ResourceUtil.getPath(hotel.getLogo().longValue(), "/resources/mobile/images/user.jpg"));
    model.addAttribute("hotelPerson", ResourceUtil.getPath(hotel.getShopkeeper().getLogo().longValue(), "/resources/mobile/images/user.jpg"));
    model.addAttribute("bdMapJsUrl", "http://api.map.baidu.com/api?v=2.0&ak=KYho6f6gFSRiRsXqRPYX5DEg");
    return "/site/mobile/web/navigator_map";
  }

  @RequestMapping({"/nav_zb"})
  public String indexNavZB(@PathVariable String siteCode, Model model)
    throws Exception
  {
    model.addAttribute("siteCode", siteCode);
    Hotel currentHotel = getCurrentHotel();

    List cityList = this.hotelService.getCityList();
    model.addAttribute("cityList", cityList);
    model.addAttribute("currentHotel", currentHotel);
    return "/site/mobile/web/zb_index";
  }

  @RequestMapping({"/hotel/home"})
  public String hotelIndex(@PathVariable String siteCode, @RequestParam(value="hotelId", required=true) int hotelId, Model model)
    throws Exception
  {
    Hotel hotel = this.hotelService.get(hotelId);
    if (hotel == null) {
      hotel = new Hotel();
    }

    List arrayCityList = getCityList();
    model.addAttribute("siteCode", siteCode);
    model.addAttribute("hotel", hotel);
    model.addAttribute("ResourceUtil", ResourceUtil.class);
    model.addAttribute("arrayCityList", arrayCityList);
    return "/site/mobile/web/hotel_index";
  }

  @RequestMapping({"/hotel/info"})
  public String hotelInfo(@PathVariable String siteCode, Model model)
    throws Exception
  {
    Hotel hotel = getCurrentHotel();
    if (hotel == null) {
      hotel = new Hotel();
    }

    List arrayCityList = getCityList();
    model.addAttribute("ResourceUtil", ResourceUtil.class);
    model.addAttribute("siteCode", siteCode);
    model.addAttribute("hotel", hotel);
    model.addAttribute("arrayCityList", arrayCityList);

    return "/site/mobile/web/hotel_index";
  }

  @RequestMapping({"/hotel/list"})
  public String hotelList(@PathVariable String siteCode, @RequestParam(value="cityId", required=true) int cityId, Model model)
    throws Exception
  {
    model.addAttribute("siteCode", siteCode);

    Hotel currentHotel = getCurrentHotel();
    model.addAttribute("cityId", Integer.valueOf(cityId));
    model.addAttribute("city", this.cityService.get(cityId));
    List hotelList = this.hotelService.getOpeningList(cityId);
    model.addAttribute("hotelList", hotelList);
    model.addAttribute("ResourceUtil", ResourceUtil.class);
    model.addAttribute("currentHotel", currentHotel);
    return "/site/mobile/web/hotel_list";
  }

  @RequestMapping({"/cityhotel/home"})
  public String cityHotelIndex(@PathVariable String siteCode, @RequestParam(value="hotelId", required=true) int hotelId, Model model)
    throws Exception
  {
    Hotel hotel = this.hotelService.get(hotelId);
    if (hotel == null) {
      hotel = new Hotel();
    }

    List arrayCityList = getCityList();
    model.addAttribute("siteCode", siteCode);
    model.addAttribute("hotel", hotel);
    model.addAttribute("ResourceUtil", ResourceUtil.class);
    model.addAttribute("arrayCityList", arrayCityList);
    return "/site/mobile/web/hotel_index";
  }

  @RequestMapping({"/hotel/house"})
  public String hotelHouse(@PathVariable String siteCode, @RequestParam(value="hotelId", required=true) Long hotelId, @RequestParam(value="startDate", required=false) @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate, @RequestParam(value="endDate", required=false) @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate, Model model)
    throws Exception
  {
    Date nowDate = new Date();
    if (startDate == null)
      startDate = nowDate;

    if (endDate == null) {
      endDate = DateUtils.addDay(nowDate, 1);
    }

    model.addAttribute("siteCode", siteCode);

    Hotel hotel = this.hotelService.get(hotelId.longValue());
    Map roomTypeVacantRoomMap = this.romeTypeService.getVacantRoomTypeList(hotelId.longValue(), startDate, endDate);
    Set roomTypeSet = roomTypeVacantRoomMap.keySet();

    model.addAttribute("roomTypeSet", roomTypeSet);
    model.addAttribute("hotelId", hotelId);
    model.addAttribute("hotel", hotel);
    model.addAttribute("startDate", startDate);
    model.addAttribute("endDate", endDate);
    model.addAttribute("ResourceUtil", ResourceUtil.class);

    return "/site/mobile/web/hotel_house";
  }

  @RequestMapping({"/hotel/order"})
  public String hotelOrderView(@PathVariable String siteCode, @RequestParam(value="houseTypeId", required=true) Long houseTypeId, @RequestParam(value="startDate", required=false) @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate, @RequestParam(value="endDate", required=false) @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate, Model model)
    throws Exception
  {
    model.addAttribute("siteCode", siteCode);

    Member member = getMember();

    RoomType roomType = this.romeTypeService.get(houseTypeId.longValue());
    Hotel hotel = this.hotelService.get(roomType.getHotelId().longValue());
    model.addAttribute("hotel", hotel);
    model.addAttribute("member", member);
    model.addAttribute("roomType", roomType);
    model.addAttribute("startDate", startDate);
    model.addAttribute("endDate", endDate);

    int consumeDay = DateUtils.compareDate(startDate, endDate);
    model.addAttribute("consumeDay", Integer.valueOf(consumeDay));

    HotelOrderDetails orderDetail = new HotelOrderDetails();
    orderDetail.setConsumeBeginDate(startDate);
    orderDetail.setConsumeDay(consumeDay);
    orderDetail.setRoomTypeId(houseTypeId);

    orderDetail.setOrderNumber(Integer.valueOf(1));

    List roomList = this.romeService.getVacantRoomListByRoomTypeId(null, houseTypeId, startDate, endDate);
    int roomCount = 0;
    double totalPrice = 0D;

    if (((roomList != null) ? 1 : 0) != ((roomList.size() > 0) ? 1 : 0)) {
      roomCount = roomList.size();
      JSONObject priceObject = HotelOrderUtil.getPrice(orderDetail);
      totalPrice = priceObject.getDouble("totalPrice");
    }
    else {
      return "/site/mobile/web/hotel_order_noroom";
    }

    model.addAttribute("roomCount", Integer.valueOf(roomCount));
    model.addAttribute("totalPrice", Double.valueOf(totalPrice));
    return "/site/mobile/web/hotel_order";
  }

  @RequestMapping({"/hotel/priceOrder"})
  @ResponseBody
  public String priceHotelOrder(@PathVariable String siteCode, @RequestParam(value="houseTypeId", required=true) Long houseTypeId, @RequestParam(value="startDate", required=false) String startDate, @RequestParam(value="consumeDay", required=false) int consumeDay, @RequestParam(value="orderNumber", required=false) int orderNumber)
    throws Exception
  {
    HotelOrderDetails orderDetail = new HotelOrderDetails();
    orderDetail.setConsumeBeginDate(DateUtils.strToDate(startDate));
    orderDetail.setConsumeDay(consumeDay);
    orderDetail.setRoomTypeId(houseTypeId);
    orderDetail.setOrderNumber(Integer.valueOf(orderNumber));

    JSONObject priceObject = HotelOrderUtil.getPrice(orderDetail);

    return priceObject.toString();
  }

  @RequestMapping({"/hotel/submitOrder"})
  public String submitHotelOrder(@PathVariable String siteCode, @RequestParam(value="totalPrice", required=true) double totalPrice, @RequestParam(value="houseTypeId", required=true) Long houseTypeId, @RequestParam(value="startDate", required=false) @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate, @RequestParam(value="endDate", required=false) @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate, @RequestParam(value="consumeDay", required=false) int consumeDay, @RequestParam(value="orderName", required=false) String orderName, @RequestParam(value="orderTelephone", required=false) String orderTelephone, @RequestParam(value="orderNumber", required=false) int orderNumber, Model model)
    throws Exception
  {
    model.addAttribute("siteCode", siteCode);
    RoomType roomType = this.romeTypeService.get(houseTypeId.longValue());
    Hotel hotel = this.hotelService.get(roomType.getHotelId().longValue());

    Member member = getMember();
    String openId = member.getOpenId();

    HotelOrder hotelOrder = new HotelOrder();
    hotelOrder.setHotelId(hotel.getId());
    hotelOrder.setMicroSiteCode(siteCode);
    hotelOrder.setMember(member);
    hotelOrder.setOrderName(orderName);
    hotelOrder.setOrderTelephone(orderTelephone);
    List hotelOrderDetailsList = hotelOrder.getOrderDetails();
    HotelOrderDetails orderDetail = new HotelOrderDetails();
    orderDetail.setConsumeBeginDate(startDate);
    orderDetail.setConsumeDay(consumeDay);
    orderDetail.setRoomTypeId(houseTypeId);
    orderDetail.setOrderNumber(Integer.valueOf(orderNumber));

    hotelOrderDetailsList.add(orderDetail);

    hotelOrder.setOrderDetails(hotelOrderDetailsList);
    hotelOrder.setOrigin(HotelOrder.ORDER_ORIGIN_FREEGO);
    Order order = HotelOrderUtil.order(hotelOrder);
    model.addAttribute("hotel", hotel);
    model.addAttribute("orderNumber", Integer.valueOf(orderNumber));
    model.addAttribute("roomType", roomType);
    model.addAttribute("consumeDay", Integer.valueOf(consumeDay));
    model.addAttribute("startDate", startDate);
    model.addAttribute("endDate", endDate);
    model.addAttribute("member", member);
    if (order == null)
      return "/site/mobile/web/hotel_order_noroom";

    String payAction = "/site/mobile/member/center/order/pay/wxOpenId?orderId=" + order.getId();
    if (("".equals(openId)) || (openId == null)) {
      String redirectUrl = AppConstants.getProperty("WX_OAUTH2_REDIRECT_PAY_URI") + "/" + order.getId();
      payAction = WxUtil.getOauto2WebUrl(redirectUrl);
    }
    model.addAttribute("payActionUrl", payAction);
    model.addAttribute("order", order);
    model.addAttribute("orderNumber", Integer.valueOf(orderNumber));
    model.addAttribute("OrderStatus", Order.Status.class);
    model.addAttribute("OrderPayStatus", Order.PayStatus.class);
    return "/site/mobile/web/hotel_order_detail";
  }

  @RequestMapping({"/hotel/viewOrder"})
  public String viewOrder(@RequestParam long id, Model model)
    throws Exception
  {
    List reservationRoomList = new ReservationRoomService().getListByOrderId(id);
    Map reservationRoomMap = new HashMap();
    List list = null;

    for (Iterator localIterator = reservationRoomList.iterator(); localIterator.hasNext(); ) { ReservationRoom room = (ReservationRoom)localIterator.next();
      list = (List)reservationRoomMap.get(room.getOrderDetailsId());
      if (list == null)
        reservationRoomMap.put(room.getOrderDetailsId(), new ArrayList());

      ((List)reservationRoomMap.get(room.getOrderDetailsId())).add(room);
    }

    model.addAttribute("instance", this.orderService.get(id));
    model.addAttribute("orderDetailsList", new OrderDetailsService().getListByOrderId(id));
    model.addAttribute("reservationRoomMap", reservationRoomMap);
    model.addAttribute("OrderStatus", Order.Status.class);

    return "/order/orderView";
  }

  @RequestMapping({"/hotel/product"})
  public String hotelProduct(@PathVariable String siteCode, Model model)
    throws Exception
  {
    model.addAttribute("siteCode", siteCode);
    return "/site/mobile/web/hotel_product";
  }

  private List<List<City>> getCityList()
    throws Exception
  {
    List cityList = this.hotelService.getCityList();
    List arrayCityList = new ArrayList();

    int listSize = cityList.size();
    int listPage = (listSize == 4) ? listSize / 4 : listSize / 4 + 1;

    for (int i = 0; i < listPage; ++i) {
      int indexStart = i * 4;
      arrayCityList.add(cityList.subList(indexStart, indexStart + 4));
    }

    return arrayCityList;
  }
}