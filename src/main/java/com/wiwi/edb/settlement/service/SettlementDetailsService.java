package com.wiwi.edb.settlement.service;

import com.wiwi.edb.settlement.dao.SettlementDetailsDao;
import com.wiwi.edb.settlement.model.SettlementDetails;
import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import java.util.Date;
import java.util.List;

public class SettlementDetailsService
{
  public void insert(SettlementDetails instance)
    throws DaoException, RenderException
  {
    SettlementDetailsDao dao = new SettlementDetailsDao();
    dao.insert(instance);
  }

  public void update(SettlementDetails instance) throws DaoException, RenderException {
    SettlementDetailsDao dao = new SettlementDetailsDao();
    dao.update(instance);
  }

  public void delete(long id) throws DaoException {
    batchDelete(id+"");
  }

  public void batchDelete(String ids) throws DaoException {
    SettlementDetailsDao dao = new SettlementDetailsDao();
    dao.delete(ids);
  }

  public SettlementDetails get(long id) throws DaoException, RenderException {
    SettlementDetailsDao dao = new SettlementDetailsDao();
    return dao.get(id);
  }

  public List<SettlementDetails> getList(PageUtil pageUtil) throws DaoException, RenderException {
    SettlementDetailsDao dao = new SettlementDetailsDao();
    return dao.getList(pageUtil);
  }

  public void deleteByPeriod(Date periodDate)
    throws DaoException
  {
    SettlementDetailsDao dao = new SettlementDetailsDao();
    dao.deleteByPeriod(periodDate);
  }
}