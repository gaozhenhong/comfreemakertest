package com.wiwi.edb.settlement;

import com.wiwi.jsoil.util.PlatformPropertyUtil;
import com.wiwi.jsoil.util.ToolsUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SettlementUtil
{
  public static Date getCurrentSettlementPeriodDate()
  {
    int day = PlatformPropertyUtil.getIntegerValue("settlementPeriod").intValue();
    Calendar now = Calendar.getInstance();
    now.set(5, day);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    try {
      return sdf.parse(sdf.format(now.getTime()));
    } catch (ParseException e) {
      e.printStackTrace();

      return null;
    }
  }

  public static String getSettlementCode(Date periodDate, Integer companyId, Long supplierId)
  {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    return ToolsUtils.appendZero(companyId.intValue(), 4) + 
      "-" + ToolsUtils.appendZero(supplierId.longValue(), 5) + "-" + sdf.format(periodDate);
  }

  public static double getPercentage()
  {
    return PlatformPropertyUtil.getDoubleValue("hotelPercentage").doubleValue();
  }
}