package com.wiwi.edb.order;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.wiwi.edb.order.model.Order;
import com.wiwi.edb.order.model.OrderProcess;

public class OrderUtil
{
  public static String genOrderCode(Order.OrderType orderType)
  {
    SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
    String orderCode = "F" + orderType.getCode() + sdf.format(new Date());
    return orderCode + '-' + String.format("%05d", new Object[] { Long.valueOf(Math.round(Math.random() * 100000.0D)) });
  }

  public static OrderProcess.OrderOperate getOrderOperate(Order.Status status)
  {
    switch (status.ordinal())
    {
    case 1:
      return OrderProcess.OrderOperate.RESERVATION;
    case 2:
      return OrderProcess.OrderOperate.CANCEL_RESERVATION;
    case 3:
      return OrderProcess.OrderOperate.ON_TIME_CANCEL;
    case 4:
      return OrderProcess.OrderOperate.REJECT;
    case 5:
      return OrderProcess.OrderOperate.CONFIRM;
    case 6:
      return OrderProcess.OrderOperate.CHECKIN;
    case 7:
      return OrderProcess.OrderOperate.CHECKOUT;
    case 8:
      return OrderProcess.OrderOperate.CLOSED;
    case 9:
      return OrderProcess.OrderOperate.CANCEL_ORDER;
    case 10:
      return OrderProcess.OrderOperate.CONFIRM_CANCEL;
    }
    return null;
  }
}