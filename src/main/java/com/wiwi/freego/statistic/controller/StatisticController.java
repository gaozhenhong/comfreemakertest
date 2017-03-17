package com.wiwi.freego.statistic.controller;

import com.wiwi.edb.order.hotelOrder.model.HotelOrder;
import com.wiwi.freego.statistic.service.StatisticService;
import com.wiwi.jsoil.base.BaseController;
import com.wiwi.jsoil.sys.model.DictionaryOption;
import com.wiwi.jsoil.util.DictionaryUtil;
import com.wiwi.jsoil.util.JSONUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/statistic/"})
public class StatisticController extends BaseController
{
  public static String[] colorArray = { "#68BC31", "#2091CF", "#AF4E96", "#DA5430", "#FEE074" };
  StatisticService service;

  public StatisticController()
  {
    this.service = new StatisticService();
  }

  @RequestMapping({"statisticList.do"})
  public String statisticList(Model model) throws Exception {
    double totalSales = 0D;
    double totalReturnAmount = 0D;
    long totalOrderCount = 0L;
    double sales = 0D;
    double returnAmount = 0D;
    long orderCount = 0L;
    JSONArray jsonArray = this.service.getAllStatisticList();

    JSONArray newJsonArray = new JSONArray();
    if (jsonArray != null) {
      JSONObject obj = null;
      JSONObject oldObj = null;
      String label = "";
      for (int i = 0; i < jsonArray.length(); ++i) {
        oldObj = (JSONObject)jsonArray.get(i);
        sales = JSONUtil.getDoubleValue(oldObj, "sales").doubleValue();
        returnAmount = JSONUtil.getDoubleValue(oldObj, "returnAmount").doubleValue();
        orderCount = JSONUtil.getLongValue(oldObj, "orderCount").longValue();
        obj = new JSONObject();
        label = DictionaryUtil.getOptionName(oldObj.getString("origin")).getName();
        oldObj.put("label", label);
        obj.put("label", label);
        obj.put("data", orderCount);
        obj.put("color", "");
        newJsonArray.put(obj);

        totalSales = totalSales + sales;
        totalReturnAmount = totalReturnAmount + returnAmount;
        totalOrderCount = totalOrderCount + orderCount;
      }
    }

    JSONObject rebateStatistic = this.service.getRebateStatistic();
    model.addAttribute("totalSales", Double.valueOf(totalSales));
    model.addAttribute("totalReturnAmount", Double.valueOf(totalReturnAmount));
    model.addAttribute("totalOrderCount", Long.valueOf(totalOrderCount));
    model.addAttribute("allStatistic", jsonArray);
    model.addAttribute("rebateStatistic", rebateStatistic);
    model.addAttribute("pieChartData", newJsonArray);
    model.addAttribute("settlementStatistic", this.service.getAllSettlementStatistic());
    model.addAttribute("JSONUtil", JSONUtil.class);
    model.addAttribute("Freego", HotelOrder.ORDER_ORIGIN_FREEGO);
    return "/statistic/statisticList";
  }
}