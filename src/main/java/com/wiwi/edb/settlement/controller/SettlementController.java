package com.wiwi.edb.settlement.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wiwi.edb.order.hotelOrder.model.HotelOrder;
import com.wiwi.edb.order.hotelOrder.service.HotelOrderService;
import com.wiwi.edb.order.model.Order;
import com.wiwi.edb.order.model.OrderQ;
import com.wiwi.edb.settlement.SettlementUtil;
import com.wiwi.edb.settlement.model.Settlement;
import com.wiwi.edb.settlement.model.SettlementDetails;
import com.wiwi.edb.settlement.model.SettlementDetailsQ;
import com.wiwi.edb.settlement.model.SettlementQ;
import com.wiwi.edb.settlement.service.SettlementDetailsService;
import com.wiwi.edb.settlement.service.SettlementService;
import com.wiwi.freego.hotel.service.HotelService;
import com.wiwi.jsoil.base.BaseController;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.sys.model.User;
import com.wiwi.jsoil.util.DateUtils;
import com.wiwi.jsoil.util.JSONUtil;
import com.wiwi.jsoil.util.RequestUtil;

@Controller
@RequestMapping({"/settlement/"})
public class SettlementController extends BaseController
{
  SettlementService service;
  HotelOrderService hotelOrderService;
  SettlementDetailsService settlementDetailsService;

  public SettlementController()
  {
    this.service = new SettlementService();
    this.hotelOrderService = new HotelOrderService();
    this.settlementDetailsService = new SettlementDetailsService();
  }

  @RequestMapping({"settlementHome.do"})
  public String settlementHome(@ModelAttribute("query") SettlementQ query, Model model) throws Exception
  {
    JSONArray periodList = this.service.getSettlementPeriodList();
    Map periodMap = new HashMap();
    Date currentPeriodDate = SettlementUtil.getCurrentSettlementPeriodDate();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat sdfMonth = new SimpleDateFormat("yyyy-MM");
    boolean currentMonthHadData = false;
    for (int i = 0; i < periodList.length(); ++i) {
      JSONObject period = (JSONObject)periodList.get(i);
      Date periodDate = JSONUtil.getDateValue(period, "settlementPeriod");
      if (sdfMonth.format(periodDate).equals(sdfMonth.format(currentPeriodDate)))
        currentMonthHadData = true;

      String key = sdf.format(periodDate);
      if (periodMap.get(key) == null)
        periodMap.put(key, new ArrayList());

      ((List)periodMap.get(key)).add(period);
    }

    int span = DateUtils.compareDate(new Date(), currentPeriodDate);
    model.addAttribute("currentMonthHadData", Boolean.valueOf(currentMonthHadData));
    model.addAttribute("currentPeriodDate", currentPeriodDate);
    model.addAttribute("periodMap", periodMap);
    model.addAttribute("span", Integer.valueOf(span));
    model.addAttribute("pager", query);
    model.addAttribute("JSONUtil", JSONUtil.class);
    processOperationMessage(model);
    return "/settlement/settlementHome";
  }

  @RequestMapping({"genSettlementDetailsAction.do"})
  public String genSettlementDetailsAction(Model model)
    throws Exception
  {
    User user = getUser();
    Date endFinishTime = SettlementUtil.getCurrentSettlementPeriodDate();
    List canSettlementOrderList = this.hotelOrderService.getAllCanSettlementOrderList(null, endFinishTime);
    double percentage = SettlementUtil.getPercentage();
    this.service.genSettlementList(canSettlementOrderList, endFinishTime, percentage, user);
    model.addAttribute("user", user);
    processOperationMessage(model);
    return "redirect:settlementPeriodHome.do?periodDate=" + endFinishTime;
  }

  @RequestMapping({"settlementPeriodHome.do"})
  public String settlementPeriodHome(@RequestParam(required=true) @DateTimeFormat(pattern="yyyy-MM-dd") Date periodDate, Model model)
    throws Exception
  {
    SettlementQ settlementQuery = new SettlementQ();
    settlementQuery.setRecordPerPage(-1);
    settlementQuery.setSettlementPeriod(periodDate);
    List settlementList = this.service.getList(settlementQuery);
    Map settlementMap = new HashMap();
    for (Iterator localIterator1 = settlementList.iterator(); localIterator1.hasNext(); ) { Settlement settlement = (Settlement)localIterator1.next();
      String key = settlement.getToCompany().getId() + "#" + settlement.getToCompany().getName();
      if (settlementMap.get(key) == null)
        settlementMap.put(key, new ArrayList());

      ((List)settlementMap.get(key)).add(settlement);
    }

    SettlementDetailsQ sdQuery = new SettlementDetailsQ();
    sdQuery.setRecordPerPage(-1);
    String otherCondition = " where settlementId in (select id from edb_settlement where date(settlementPeriod)= '" + 
      new SimpleDateFormat("yyyy-MM-dd").format(periodDate) + "') ";
    sdQuery.setOtherCondition(otherCondition);
    List settlementDetailList = this.settlementDetailsService.getList(sdQuery);
    Map settlementDetailsMap = new HashMap();
    for (Iterator localIterator2 = settlementDetailList.iterator(); localIterator2.hasNext(); ) { SettlementDetails settlementDetails = (SettlementDetails)localIterator2.next();
      if (settlementDetailsMap.get(settlementDetails.getSettlementId()) == null)
        settlementDetailsMap.put(settlementDetails.getSettlementId(), new ArrayList());

      ((List)settlementDetailsMap.get(settlementDetails.getSettlementId())).add(settlementDetails);
    }
    model.addAttribute("periodDate", periodDate);
    model.addAttribute("settlementMap", settlementMap);
    model.addAttribute("settlementDetailsMap", settlementDetailsMap);
    model.addAttribute("JSONUtil", JSONUtil.class);
    processOperationMessage(model);
    return "/settlement/settlementPeriodHome";
  }

  @RequestMapping({"list.do"})
  public String list(@ModelAttribute("query") SettlementQ query, Model model)
    throws Exception
  {
	User user = getUser();
    model.addAttribute("list", this.service.getList(query));
    model.addAttribute("pager", query);
    model.addAttribute("user", user);
    processOperationMessage(model);
    return "/settlement/settlementList";
  }

  @RequestMapping({"allCanSettlementSupplierList.do"})
  public String allCanSettlementSupplierList(@ModelAttribute("query") OrderQ orderQuery, @RequestParam(required=false) @DateTimeFormat(pattern="yyyy-MM-dd") Date beginDate, @RequestParam(required=false) @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate, Model model)
    throws Exception
  {
    JSONArray canSettlementSupplierList = this.hotelOrderService.getAllCanSettlementSupplierList(beginDate, endDate);
    model.addAttribute("canSettlementSupplierList", canSettlementSupplierList);
    model.addAttribute("JSONUtil", JSONUtil.class);
    model.addAttribute("OrderStatus", Order.Status.class);
    model.addAttribute("PayStatus", Order.PayStatus.class);
    model.addAttribute("supplierList", this.hotelOrderService.getSupplierList());
    return "/settlement/allCanSettlementSupplierList";
  }

  @RequestMapping({"allWaitForConfirmSettlementList.do"})
  public String allWaitForConfirmSettlement(@ModelAttribute("query") SettlementQ query, Model model) throws Exception
  {
    query.setStatus(Integer.valueOf(1));
    model.addAttribute("list", this.service.getList(query));
    model.addAttribute("pager", query);
    processOperationMessage(model);

    return "/settlement/allWaitForConfirmSettlementList";
  }

  @RequestMapping({"add.do"})
  public String addView(@RequestParam int companyId, Model model) throws DaoException, RenderException
  {
    List canSettlementOrderList = this.hotelOrderService.getAllCanSettlementOrderListByCompanyIdAndSupplierId(Integer.valueOf(companyId), null);
    JSONObject supplierSettlementInfo = this.hotelOrderService.getCanSettlementSupplierInfo(Integer.valueOf(companyId), null);
    model.addAttribute("canSettlementOrderList", canSettlementOrderList);
    model.addAttribute("supplierSettlementInfo", supplierSettlementInfo);

    model.addAttribute("instance", new Settlement());
    model.addAttribute("JSONUtil", JSONUtil.class);
    model.addAttribute("Freego", HotelOrder.ORDER_ORIGIN_FREEGO);
    return "/settlement/settlementAdd";
  }

  @RequestMapping({"addAction.do"})
  public String addAction(@ModelAttribute("instance") Settlement instance, HttpServletRequest request, Model model) throws Exception
  {
    User user = getUser();
    Long thumbId = Long.valueOf(RequestUtil.getLongParameter(request, "thumbId"));
    instance.setEvidence(thumbId+"");
    instance.setSettlementUserId(user.getId());
    instance.setSettlementTime(new Date());

    this.service.insert(instance);

    setOperationMessage("添加成功！");

    return "redirect:list.do";
  }

  @RequestMapping({"edit.do"})
  public String editView(@RequestParam long id, Model model)
    throws Exception
  {
    SettlementDetailsQ sdQuery = new SettlementDetailsQ();
    sdQuery.setRecordPerPage(-1);
    sdQuery.setSettlementId(Long.valueOf(id));

    Settlement settlement = this.service.get(id);
    long hotelId = settlement.getToSupplierId().longValue();

    model.addAttribute("hotelBankInfo", new HotelService().getHotelBankAccount(hotelId));
    model.addAttribute("instance", settlement);
    model.addAttribute("settlementDetailsList", this.settlementDetailsService.getList(sdQuery));
    model.addAttribute("JSONUtil", JSONUtil.class);
    model.addAttribute("Freego", HotelOrder.ORDER_ORIGIN_FREEGO);
    model.addAttribute("instance", this.service.get(id));

    return "/settlement/settlementEdit";
  }

  @RequestMapping({"editAction.do"})
  public String editAction(HttpServletRequest request, Model model) throws Exception
  {
    Long id = Long.valueOf(RequestUtil.getLongParameter(request, "id"));
    Long thumbId = Long.valueOf(RequestUtil.getLongParameter(request, "thumbId"));
    String remark = request.getParameter("remark");
    this.service.paySettlement(id, getUser(), thumbId+"", remark);

    setOperationMessage("付款成功！");
    return "redirect:list.do";
  }

  @RequestMapping({"view.do"})
  public String view(@RequestParam long id, Model model)
    throws Exception
  {
    SettlementDetailsQ sdQuery = new SettlementDetailsQ();
    sdQuery.setRecordPerPage(-1);
    sdQuery.setSettlementId(Long.valueOf(id));

    Settlement settlement = this.service.get(id);
    model.addAttribute("instance", settlement);
    model.addAttribute("settlementDetailsList", this.settlementDetailsService.getList(sdQuery));
    model.addAttribute("JSONUtil", JSONUtil.class);
    model.addAttribute("Freego", HotelOrder.ORDER_ORIGIN_FREEGO);
    model.addAttribute("instance", this.service.get(id));
    return "/settlement/settlementView";
  }

  @RequestMapping({"deleteAction.do"})
  public String deleteAction(@RequestParam(required=true) long id, Model model)
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