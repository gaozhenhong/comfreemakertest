package com.wiwi.freego.statistic.dao;

import com.wiwi.jsoil.db.DbAdapter;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import org.json.JSONArray;
import org.json.JSONObject;

public class StatisticDao
{
  public JSONArray getStatisticList(Integer companyId)
    throws DaoException, RenderException
  {
    return getStatisticList(companyId, false);
  }

  public JSONArray getStatisticList(Integer companyId, boolean isSettlement)
    throws DaoException, RenderException
  {
    String sql = "select origin, sum(totalPrice) as sales ,sum(payPrice) as returnAmount, count(*)  as orderCount  from edb_order  where (deleteFlag is null OR deleteFlag<>1 )";

    if ((companyId != null) && (companyId.intValue() != 0))
      sql = sql + " and companyId='" + companyId + "'";

    if (isSettlement)
      sql = sql + " and isSettlement=1";

    sql = sql + " group by origin" + 
      " order by orderCount desc";
    return DbAdapter.getJSONArray(sql);
  }

  public JSONObject getRebateStatic()
    throws DaoException, RenderException
  {
    String sql = "select count(*) as rebateOrderCount,sum(amount) as rebateAmount, sum(orderPayPrice) as allOrderReturnAmount from edb_settlement_details where feeType='rebateFee'";

    return DbAdapter.getJSONObject(sql);
  }
}