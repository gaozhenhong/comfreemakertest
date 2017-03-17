package com.wiwi.freego.mobile.member.controller;

import com.wiwi.edb.order.hotelOrder.service.HotelOrderService;
import com.wiwi.edb.order.model.Order;
import com.wiwi.edb.order.model.Order.PayStatus;
import com.wiwi.edb.order.model.Order.Status;
import com.wiwi.edb.order.model.OrderDetails;
import com.wiwi.edb.order.model.OrderQ;
import com.wiwi.edb.order.service.OrderDetailsService;
import com.wiwi.freego.hotel.model.Hotel;
import com.wiwi.freego.hotel.service.HotelService;
import com.wiwi.freego.hotel.service.RoomService;
import com.wiwi.freego.hotel.service.RoomTypeService;
import com.wiwi.freego.mobile.member.model.MemberOrder;
import com.wiwi.jsoil.base.BaseController;
import com.wiwi.jsoil.member.model.Member;
import com.wiwi.jsoil.member.service.MemberService;
import com.wiwi.jsoil.sys.model.Resource;
import com.wiwi.jsoil.util.AppConstants;
import com.wiwi.jsoil.util.DateUtils;
import com.wiwi.jsoil.util.HttpClientHandler;
import com.wiwi.jsoil.util.ResourceUploadUtil;
import com.wiwi.jsoil.util.ResourceUtil;
import com.wiwi.jsoil.util.WxUtil;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/site/mobile/member/center/"})
public class MemberCenterController
  extends BaseController
{
  MemberService memberService = new MemberService();
  HotelOrderService orderService = new HotelOrderService();
  OrderDetailsService orderDetailsService = new OrderDetailsService();
  HotelService hotelService = new HotelService();
  RoomTypeService romeTypeService = new RoomTypeService();
  RoomService romeService = new RoomService();
  private static final Logger logger = LoggerFactory.getLogger(MemberCenterController.class);
  
  @RequestMapping({"/home"})
  public String home(Model model, HttpSession session)
    throws Exception
  {
    model.addAttribute("siteCode", getCurrentHotel().getCode());
    Member member = (Member)session.getAttribute("MeMbErLoGiNsEsSiOnKeY");
    model.addAttribute("member", member);
    model.addAttribute("ResourceUtil", ResourceUtil.class);
    return "/site/mobile/web/member_index";
  }
  
  @RequestMapping({"/baseinfoEdit"})
  public String baseinfoEdit(Model model)
    throws Exception
  {
    model.addAttribute("siteCode", getCurrentHotel().getCode());
    Member member = getMember();
    model.addAttribute("member", member);
    model.addAttribute("ResourceUtil", ResourceUtil.class);
    return "/site/mobile/web/member_edit";
  }
  
  @RequestMapping(value={"/baseinfoEditAction.do"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String baseinfoEditAction(@ModelAttribute("member") Member member, Model model, HttpSession session)
    throws Exception
  {
    model.addAttribute("siteCode", getCurrentHotel().getCode());
    this.memberService.update(member);
    member = this.memberService.get(member.getId());
    session.setAttribute("MeMbErLoGiNsEsSiOnKeY", member);
    model.addAttribute("member", member);
    return "redirect:/site/mobile/member/center/home";
  }
  
  @ResponseBody
  @RequestMapping(value={"/uploadLogoAjax"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String uploadMemberLogo(HttpServletRequest request, Model model)
    throws Exception
  {
    model.addAttribute("siteCode", getCurrentHotel().getCode());
    Resource resourceInstance = ResourceUploadUtil.uploadFile(request, null, "memberLogo");
    Long logoId = resourceInstance.getId();
    String resPath = resourceInstance.getResPath();
    return "{\"logoId\":\"" + logoId + "\",\"resPath\":\"" + resPath + "\"}";
  }
  
  @RequestMapping({"/orderList"})
  public String memberOrderList(Model model)
    throws Exception
  {
    model.addAttribute("siteCode", getCurrentHotel().getCode());
    Member member = getMember();
    OrderQ query = new OrderQ();
    query.setMemberId(member.getId());
    query.setRecordPerPage(-1);
    List<MemberOrder> memberOrderList = new ArrayList();
    List<Order> orderList = this.orderService.getList(query);
    for (Order orderObj : orderList)
    {
      Long supplierId = Long.valueOf(orderObj.getSupplierId());
      Hotel hotel = this.hotelService.get(supplierId.longValue());
      MemberOrder memberOrder = new MemberOrder();
      memberOrder.setHotel(hotel);
      memberOrder.setOrderBase(orderObj);
      memberOrderList.add(memberOrder);
    }
    model.addAttribute("member", member);
    model.addAttribute("orderList", memberOrderList);
    model.addAttribute("ResourceUtil", ResourceUtil.class);
    return "/site/mobile/web/member_order_list";
  }
  
  @RequestMapping({"/orderDetail"})
  public String memberOrderDetail(@RequestParam(value="orderId", required=true) long orderId, Model model)
    throws Exception
  {
    model.addAttribute("siteCode", getCurrentHotel().getCode());
    Member member = getMember();
    
    Order order = this.orderService.get(orderId);
    List<OrderDetails> orderDetailList = this.orderDetailsService.getListByOrderId(orderId);
    Long supplyId = Long.valueOf(order.getSupplierId());
    Hotel hotel = this.hotelService.get(supplyId.longValue());
    
    model.addAttribute("order", order);
    model.addAttribute("orderDetails", orderDetailList);
    model.addAttribute("hotel", hotel);
    model.addAttribute("OrderStatus", Order.Status.class);
    model.addAttribute("OrderPayStatus", Order.PayStatus.class);
    model.addAttribute("DateUtils", DateUtils.class);
    model.addAttribute("romeService", this.romeService);
    if (order.getPayStatus() == Order.PayStatus.NO_PAYMENT)
    {
      String openId = member.getOpenId();
      String payAction = "/site/mobile/member/center/order/pay/wxOpenId?orderId=" + order.getId();
      if (("".equals(openId)) || (openId == null))
      {
        String redirectUrl = AppConstants.getProperty("WX_OAUTH2_REDIRECT_PAY_URI") + "/" + order.getId();
        payAction = WxUtil.getOauto2WebUrl(redirectUrl);
      }
      model.addAttribute("payActionUrl", payAction);
    }
    return "/site/mobile/web/member_order_detail";
  }
  
  @RequestMapping({"/order/pay/wxOpenId"})
  public String memberOrderPayByWxOpenId(@RequestParam(value="orderId", required=true) int orderId, Model model)
    throws Exception
  {
    Member member = getMember();
    String openId = member.getOpenId();
    String clientIP = getClientIpAddr(getRequest());
    Order order = this.orderService.get(orderId);
    List<OrderDetails> orderDetailList = this.orderDetailsService.getListByOrderId(orderId);
    Long supplyId = Long.valueOf(order.getSupplierId());
    Hotel hotel = this.hotelService.get(supplyId.longValue());
    
    String productRemark = hotel.getName() + getOrderName(orderDetailList);
    String orderNumber = order.getOrderCode();
    String totalPrice = String.valueOf((int)(order.getTotalPrice().doubleValue() * 100.0D));
    JSONObject jsapiPar = getUnifiedOrderJSAPIPar(productRemark, orderNumber, totalPrice, clientIP, openId);
    
    model.addAttribute("jsapiPar", jsapiPar);
    
    model.addAttribute("order", order);
    model.addAttribute("orderDetails", orderDetailList);
    model.addAttribute("hotel", hotel);
    model.addAttribute("OrderStatus", Order.Status.class);
    model.addAttribute("OrderPayStatus", Order.PayStatus.class);
    model.addAttribute("DateUtils", DateUtils.class);
    model.addAttribute("romeService", this.romeService);
    
    return "/site/mobile/web/pay_index";
  }
  
  @RequestMapping({"/order/pay/wxOauth2/{orderId}"})
  public String memberOrderPayByWxOauth2(@PathVariable long orderId, @RequestParam(value="code", required=true) String code, Model model, HttpSession session)
    throws Exception
  {
    Member member = getMember();
    String token_url = WxUtil.getOauto2AccessTokenUrl(code);
    String accessTokenStr = HttpClientHandler.get(token_url, null);
    System.out.println("---------memberOrderPayByWxOauth2----pay----app-content=" + accessTokenStr);
    JSONObject accessTokenObj = new JSONObject(accessTokenStr);
    String openId = accessTokenObj.getString("openid");
    String clientIP = getClientIpAddr(getRequest());
    member.setOpenId(openId);
    this.memberService.update(member);
    session.setAttribute("MeMbErLoGiNsEsSiOnKeY", member);
    System.out.println("---------orderId=" + orderId);
    Order order = this.orderService.get(orderId);
    List<OrderDetails> orderDetailList = this.orderDetailsService.getListByOrderId(orderId);
    Long supplyId = Long.valueOf(order.getSupplierId());
    Hotel hotel = this.hotelService.get(supplyId.longValue());
    
    String productRemark = hotel.getName() + getOrderName(orderDetailList);
    String orderNumber = order.getOrderCode();
    String totalPrice = String.valueOf((int)(order.getTotalPrice().doubleValue() * 100.0D));
    JSONObject jsapiPar = getUnifiedOrderJSAPIPar(productRemark, orderNumber, totalPrice, clientIP, openId);
    model.addAttribute("jsapiPar", jsapiPar);
    
    model.addAttribute("order", order);
    model.addAttribute("orderDetails", orderDetailList);
    model.addAttribute("hotel", hotel);
    model.addAttribute("OrderStatus", Order.Status.class);
    model.addAttribute("OrderPayStatus", Order.PayStatus.class);
    model.addAttribute("DateUtils", DateUtils.class);
    model.addAttribute("romeService", this.romeService);
    
    return "/site/mobile/web/pay_index";
  }
  
  @RequestMapping({"/order/pay/checkOrder"})
  @ResponseBody
  public String memberOrderPayCheckOrder(@RequestParam(value="orderId", required=true) Long orderId)
    throws Exception
  {
    boolean checkResult = this.orderService.isCanPay(orderId);
    return String.valueOf(checkResult);
  }
  
  @RequestMapping({"/order/pay/success"})
  public String memberOrderPaySuccess(@RequestParam(value="orderId", required=true) long orderId, @RequestParam(value="payPrice", required=true) double payPrice, Model model)
    throws Exception
  {
    model.addAttribute("siteCode", getCurrentHotel().getCode());
    this.orderService.updatePayPrice(orderId, payPrice);
    logger.debug("��������������[orderId={},payPrice={}]", Long.valueOf(orderId), Double.valueOf(payPrice));
    Order order = this.orderService.get(orderId);
    List<OrderDetails> orderDetailList = this.orderDetailsService.getListByOrderId(orderId);
    Long supplyId = Long.valueOf(order.getSupplierId());
    Hotel hotel = this.hotelService.get(supplyId.longValue());
    
    model.addAttribute("order", order);
    model.addAttribute("orderDetails", orderDetailList);
    model.addAttribute("hotel", hotel);
    model.addAttribute("OrderStatus", Order.Status.class);
    model.addAttribute("OrderPayStatus", Order.PayStatus.class);
    model.addAttribute("DateUtils", DateUtils.class);
    model.addAttribute("romeService", this.romeService);
    
    return "/site/mobile/web/member_order_detail";
  }
  
  @RequestMapping({"/order/pay/refund/prepare"})
  public String memberOrderPayRefundPrepare(@RequestParam(value="orderId", required=true) int orderId, Model model)
    throws Exception
  {
    Member member = getMember();
    Order order = this.orderService.get(orderId);
    List<OrderDetails> orderDetailList = this.orderDetailsService.getListByOrderId(orderId);
    Long supplyId = Long.valueOf(order.getSupplierId());
    Hotel hotel = this.hotelService.get(supplyId.longValue());
    
    String productRemark = hotel.getName() + getOrderName(orderDetailList);
    String orderNumber = order.getOrderCode();
    String totalPrice = String.valueOf((int)(order.getTotalPrice().doubleValue() * 100.0D));
    
    model.addAttribute("order", order);
    model.addAttribute("orderDetails", orderDetailList);
    model.addAttribute("hotel", hotel);
    model.addAttribute("OrderStatus", Order.Status.class);
    model.addAttribute("OrderPayStatus", Order.PayStatus.class);
    model.addAttribute("DateUtils", DateUtils.class);
    model.addAttribute("romeService", this.romeService);
    
    return "/site/mobile/web/pay_index";
  }
  
  @RequestMapping({"/order/pay/refund/apply"})
  public String memberOrderPayRefundByorderId(@RequestParam(value="orderId", required=true) int orderId, @RequestParam(value="outTradeNo", required=true) String outTradeNo, @RequestParam(value="outRefundNo", required=true) String outRefundNo, @RequestParam(value="totalPrice", required=true) String totalPrice, @RequestParam(value="refundFee", required=true) String refundFee, Model model)
    throws Exception
  {
    Member member = getMember();
    Order order = this.orderService.get(orderId);
    List<OrderDetails> orderDetailList = this.orderDetailsService.getListByOrderId(orderId);
    Long supplyId = Long.valueOf(order.getSupplierId());
    Hotel hotel = this.hotelService.get(supplyId.longValue());
    
    String productRemark = hotel.getName() + getOrderName(orderDetailList);
    String orderNumber = order.getOrderCode();
    
    JSONObject jsapiPar = sendPayRefundRequest(outTradeNo, outRefundNo, totalPrice, refundFee);
    
    model.addAttribute("jsapiPar", jsapiPar);
    
    model.addAttribute("order", order);
    model.addAttribute("orderDetails", orderDetailList);
    model.addAttribute("hotel", hotel);
    model.addAttribute("OrderStatus", Order.Status.class);
    model.addAttribute("OrderPayStatus", Order.PayStatus.class);
    model.addAttribute("DateUtils", DateUtils.class);
    model.addAttribute("romeService", this.romeService);
    
    return "/site/mobile/web/pay_index";
  }
  
  @RequestMapping({"/order/cancelRule"})
  public String orderCancelRole(Model model)
    throws Exception
  {
    model.addAttribute("siteCode", getCurrentHotel().getCode());
    Member member = getMember();
    model.addAttribute("member", member);
    model.addAttribute("ResourceUtil", ResourceUtil.class);
    return "/site/mobile/web/cancel_rule";
  }
  
  @RequestMapping({"/about"})
  public String aboutFreeGO(Model model)
    throws Exception
  {
    model.addAttribute("siteCode", getCurrentHotel().getCode());
    Member member = getMember();
    model.addAttribute("member", member);
    model.addAttribute("ResourceUtil", ResourceUtil.class);
    return "/site/mobile/web/about";
  }
  
  @RequestMapping({"/customerService"})
  public String customerService(Model model)
    throws Exception
  {
    model.addAttribute("siteCode", getCurrentHotel().getCode());
    Member member = getMember();
    model.addAttribute("member", member);
    model.addAttribute("ResourceUtil", ResourceUtil.class);
    return "/site/mobile/web/customer_service";
  }
  
  private JSONObject getUnifiedOrderJSAPIPar(String productRemark, String orderNumber, String totalPrice, String clientIP, String openId)
    throws Exception
  {
    JSONObject jsapiPar = new JSONObject();
    String key = WxUtil.getPayAPIKey();
    
    List<NameValuePair> params = createUnifiedOrderPair(productRemark, orderNumber, totalPrice, clientIP, openId);
    
    String puoXML = WxUtil.createParamsSignXML(params, key);
    
    String unifiedorderUrl = WxUtil.getUnifiedOrderUrl();
    System.out.println("---------oauth2Login----pay----xml=" + puoXML);
    
    JSONObject returnObj = WxUtil.paraXmlToJsonObj(HttpClientHandler.postString(unifiedorderUrl, puoXML));
    System.out.println("---------oauth2Login----pay----requestContent=" + returnObj.toString());
    String returnCode = returnObj.getString("return_code");
    String returnMsg = returnObj.getString("return_msg");
    if ("SUCCESS".equals(returnCode))
    {
      String resultCode = returnObj.getString("result_code");
      if ("SUCCESS".equals(resultCode))
      {
        String appId = WxUtil.getAppID();
        String signType = WxUtil.getPaySignType();
        
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000L);
        String nonceStr = WxUtil.getRandomStr();
        String packageStr = "prepay_id=" + String.valueOf(returnObj.get("prepay_id"));
        
        jsapiPar.put("appId", appId);
        jsapiPar.put("timeStamp", timestamp);
        jsapiPar.put("nonceStr", nonceStr);
        jsapiPar.put("package", packageStr);
        jsapiPar.put("signType", signType);
        
        String paySign = WxUtil.getMD5SignStrByKey(jsapiPar, key);
        jsapiPar.put("paySign", paySign);
      }
    }
    else
    {
      jsapiPar.put("returnMsg", returnMsg);
    }
    return jsapiPar;
  }
  
  private JSONObject sendPayRefundRequest(String outTradeNo, String outRefundNo, String totalPrice, String refundFee)
    throws Exception
  {
    JSONObject responseJSON = new JSONObject();
    String key = WxUtil.getPayAPIKey();
    
    List<NameValuePair> params = createPayRefundPair(outTradeNo, outRefundNo, totalPrice, refundFee);
    
    String puoXML = WxUtil.createParamsSignXML(params, key);
    
    String payRefundUrl = WxUtil.getPayRefundUrl();
    System.out.println("---------refund----pay----xml=" + puoXML);
    
    JSONObject returnObj = WxUtil.paraXmlToJsonObj(HttpClientHandler.postString(payRefundUrl, puoXML));
    System.out.println("---------refund----pay----requestContent=" + returnObj.toString());
    String returnCode = returnObj.getString("return_code");
    String returnMsg = returnObj.getString("return_msg");
    if ("SUCCESS".equals(returnCode))
    {
      String resultCode = returnObj.getString("result_code");
      if ("SUCCESS".equals(resultCode))
      {
        responseJSON = returnObj;
        responseJSON.put("returnState", "SUCCESS");
      }
      else
      {
        responseJSON.put("returnState", "FAIL");
        responseJSON.put("returnMsg", "������������");
      }
    }
    else
    {
      responseJSON.put("returnState", "FAIL");
      responseJSON.put("returnMsg", returnMsg);
    }
    return responseJSON;
  }
  
  private List<NameValuePair> createUnifiedOrderPair(String productRemark, String orderNumber, String totalPrice, String clientIp, String openId)
    throws Exception
  {
    List<NameValuePair> params = new ArrayList();
    
    params.add(new BasicNameValuePair("appid", WxUtil.getAppID()));
    
    params.add(new BasicNameValuePair("mch_id", WxUtil.getPayMCHID()));
    
    params.add(new BasicNameValuePair("nonce_str", WxUtil.getRandomStr()));
    
    params.add(new BasicNameValuePair("body", productRemark));
    
    params.add(new BasicNameValuePair("out_trade_no", orderNumber));
    
    params.add(new BasicNameValuePair("total_fee", totalPrice));
    
    params.add(new BasicNameValuePair("spbill_create_ip", clientIp));
    
    params.add(new BasicNameValuePair("notify_url", WxUtil.getPayNotifyUrl()));
    
    params.add(new BasicNameValuePair("trade_type", WxUtil.getPayTradeType()));
    
    params.add(new BasicNameValuePair("openid", openId));
    
    return params;
  }
  
  private List<NameValuePair> createPayRefundPair(String outTradeNo, String outRefundNo, String totalPrice, String refundFee)
    throws Exception
  {
    List<NameValuePair> params = new ArrayList();
    
    params.add(new BasicNameValuePair("appid", WxUtil.getAppID()));
    
    params.add(new BasicNameValuePair("mch_id", WxUtil.getPayMCHID()));
    
    params.add(new BasicNameValuePair("nonce_str", WxUtil.getRandomStr()));
    
    params.add(new BasicNameValuePair("out_trade_no", outTradeNo));
    
    params.add(new BasicNameValuePair("out_refund_no", outRefundNo));
    
    params.add(new BasicNameValuePair("total_fee", totalPrice));
    
    params.add(new BasicNameValuePair("refund_fee", refundFee));
    
    params.add(new BasicNameValuePair("op_user_id", WxUtil.getPayMCHID()));
    
    return params;
  }
  
  private String getClientIpAddr(HttpServletRequest request)
  {
    String ip = request.getHeader("x-forwarded-for");
    if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
      ip = request.getHeader("PRoxy-Client-IP");
    }
    if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
      ip = request.getRemoteAddr();
    }
    return ip;
  }
  
  private String getOrderName(List<OrderDetails> orderDetailList)
    throws Exception
  {
    String orderName = "";
    for (OrderDetails orderDetails : orderDetailList) {
      orderName = orderName + "|" + orderDetails.getProductName();
    }
    if (orderName != "") {
      orderName = orderName.substring(1);
    }
    return orderName;
  }
  
  private JSONObject getOrderObjJSON(Long orderId)
    throws Exception
  {
    JSONObject object = new JSONObject();
    JSONArray objectDetailList = new JSONArray();
    Order order = this.orderService.get(orderId.longValue());
    List<OrderDetails> detailList = this.orderDetailsService.getListByOrderId(orderId.longValue());
    Long supplyId = Long.valueOf(order.getSupplierId());
    Hotel hotel = this.hotelService.get(supplyId.longValue());
    String orderTotalRemark = "";
    
    object.put("orderId", orderId);
    object.put("orderCode", order.getOrderCode());
    object.put("orderStatusLabel", order.getStatus().getLabel());
    object.put("orderStatus", order.getStatus());
    object.put("totalPrice", order.getTotalPrice());
    object.put("payStatus", order.getPayStatus());
    object.put("payType", order.getPayType());
    object.put("ordetTime", order.getOrderTime());
    for (OrderDetails objectDetail : detailList)
    {
      JSONObject subObject = new JSONObject();
      subObject.put("orderName", objectDetail.getProductName());
      subObject.put("houseCount", objectDetail.getOrderNumber());
      int nightCount = DateUtils.compareDate(objectDetail.getConsumeEndTime(), objectDetail.getConsumeEndTime());
      subObject.put("nightCount", nightCount);
      subObject.put("countPrice", objectDetail.getTotalPrice());
      objectDetailList.put(subObject);
      orderTotalRemark = orderTotalRemark + "|" + objectDetail.getProductName() + "," + objectDetail.getOrderNumber() + "��" + "," + "��" + nightCount + "��";
    }
    if (orderTotalRemark != "") {
      orderTotalRemark = orderTotalRemark.substring(1);
    }
    object.put("orderDetailList", objectDetailList);
    object.put("orderTotalRemark", orderTotalRemark);
    object.put("checkInDate", order.getPlanCheckinTime());
    object.put("checkOutDate", order.getPlanCheckoutTime());
    object.put("lastArriveTime", "");
    object.put("hotelName", hotel.getName());
    object.put("hotelPhone", hotel.getTelphone());
    object.put("hotelAddress", hotel.getAddress());
    object.put("orderPersonName", order.getOrderName());
    object.put("orderPersonPhone", order.getOrderTelephone());
    return object;
  }
}
