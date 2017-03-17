package com.wiwi.edb.settlement.dao;

import com.wiwi.edb.settlement.model.SettlementDetails;
import com.wiwi.jsoil.db.DaoBase;
import com.wiwi.jsoil.db.DbAdapter;
import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import java.util.Date;
import java.util.List;

public class SettlementDetailsDao extends DaoBase
{
  private String sql;

  public SettlementDetailsDao()
  {
    this.sql = null; }

  public void insert(SettlementDetails instance) throws DaoException, RenderException {
    DbAdapter.insert2SingleTable(instance, "edb_settlement_details");
  }

  public void update(SettlementDetails instance) throws DaoException, RenderException {
    DbAdapter.update2SingleTable(instance, "edb_settlement_details");
  }

  public void delete(String ids) throws DaoException {
    if (ids.startsWith(","))
      ids = ids.substring(1);

    if (ids.indexOf(",") != -1)
      ids = ids.replaceAll(",", "','");

    if (!(ids.startsWith("'")))
      ids = "'" + ids;

    if (!(ids.endsWith("'")))
      ids = ids + "'";

    this.sql = "DELETE FROM edb_settlement_details WHERE id in (" + ids + ") ";
    DbAdapter.executeUpdate(this.sql);
  }

  public void deleteByPeriod(Date periodDate)
    throws DaoException
  {
    if (periodDate == null)
      return;
    this.sql = "DELETE FROM edb_settlement_details WHERE period =? ";
    DbAdapter.executeUpdate(this.sql, new Date[] { periodDate });
  }

  public SettlementDetails get(long id) throws DaoException, RenderException {
    this.sql = "select * FROM edb_settlement_details WHERE id ='" + id + "'";
    return ((SettlementDetails)DbAdapter.get(this.sql, SettlementDetails.class));
  }

  public List<SettlementDetails> getList(PageUtil pageUtil) throws DaoException, RenderException
  {
    this.sql = "select * FROM edb_settlement_details";
    return DbAdapter.getList(this.sql, pageUtil, SettlementDetails.class);
  }
}