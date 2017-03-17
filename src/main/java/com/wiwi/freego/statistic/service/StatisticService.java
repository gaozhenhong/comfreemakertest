package com.wiwi.freego.statistic.service;

import com.wiwi.freego.statistic.dao.StatisticDao;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import org.json.JSONArray;
import org.json.JSONObject;

public class StatisticService
{
  public JSONArray getAllStatisticList()
    throws DaoException, RenderException
  {
    StatisticDao dao = new StatisticDao();
    return dao.getStatisticList(null);
  }

  public JSONObject getAllSettlementStatistic() throws DaoException, RenderException {
    StatisticDao dao = new StatisticDao();
    JSONArray jsonArray = dao.getStatisticList(null, true);
    return (((jsonArray == null) || (jsonArray.length() < 1)) ? null : (JSONObject)jsonArray.get(0));
  }

  public JSONArray getStatisticList(Integer companyId)
    throws DaoException, RenderException
  {
    StatisticDao dao = new StatisticDao();
    return dao.getStatisticList(companyId);
  }

  public JSONObject getSettlementStatistic(Integer companyId) throws DaoException, RenderException
  {
    StatisticDao dao = new StatisticDao();
    JSONArray jsonArray = dao.getStatisticList(companyId, true);
    return (((jsonArray == null) || (jsonArray.length() < 1)) ? null : (JSONObject)jsonArray.get(0));
  }

  public JSONObject getRebateStatistic()
    throws DaoException, RenderException
  {
    StatisticDao dao = new StatisticDao();
    return dao.getRebateStatic();
  }
}