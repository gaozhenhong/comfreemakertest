package com.wiwi.edb.settlement.dao;

import com.wiwi.edb.settlement.model.Settlement;
import com.wiwi.edb.settlement.model.SettlementDetails;
import com.wiwi.edb.settlement.service.SettlementDetailsService;
import com.wiwi.jsoil.db.DaoBase;
import com.wiwi.jsoil.db.DbAdapter;
import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.sys.model.User;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;

public class SettlementDao extends DaoBase
{
  private String selectSql;
  private String sql;

  public SettlementDao()
  {
    this.selectSql = "select m.*,toCompany.name as toCompanyName  FROM edb_settlement m  ,s_organization toCompany where  m.toCompanyId=toCompany.id";

    this.sql = null; }

  public void insert(Settlement instance) throws DaoException, RenderException {
    DbAdapter.insert2SingleTable(instance, "edb_settlement");
  }

  public void update(Settlement instance) throws DaoException, RenderException {
    DbAdapter.update2SingleTable(instance, "edb_settlement");
  }

  public void confirmSettlement(Long settlementId, User user, String confirmRemark)
    throws DaoException, RenderException
  {
    this.sql = "update edb_settlement set status=2, confirmUserId = ? ,confirmTime=?,confirmRemark=?  WHERE id =? ";
    DbAdapter.executeUpdate(this.sql, new Object[] { user.getId(), new Date(), confirmRemark, settlementId });
  }

  public void paySettlement(Long settlementId, User user, String evidence, String remark)
    throws DaoException, RenderException
  {
    this.sql = "update edb_settlement set status=1, settlementUserId = ? ,settlementTime=?,evidence=? ,remark=? WHERE id =? ";
    DbAdapter.executeUpdate(this.sql, new Object[] { user.getId(), new Date(), evidence, remark, settlementId });
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

    this.sql = "DELETE FROM edb_settlement WHERE id in (" + ids + ") ";
    DbAdapter.executeUpdate(this.sql);
  }

  public Settlement get(long id) throws DaoException, RenderException {
    this.sql = this.selectSql + " and m.id ='" + id + "'";
    return ((Settlement)DbAdapter.get(this.sql, Settlement.class));
  }

  public List<Settlement> getList(PageUtil pageUtil) throws DaoException, RenderException
  {
    this.sql = this.selectSql;
    return DbAdapter.getList(this.sql, pageUtil, Settlement.class);
  }

  public JSONArray getSettlementPeriodList()
    throws DaoException, RenderException
  {
    this.sql = "select settlementPeriod ,status,count(*) as supplierCount,sum(settlementAmount) as settlementAmount from edb_settlement group by settlementPeriod,status order by settlementPeriod desc";

    return DbAdapter.getJSONArray(this.sql);
  }

  public void deleteByPeriod(Date periodDate)
    throws DaoException
  {
    if (periodDate == null)
      return;
    this.sql = "DELETE FROM edb_settlement WHERE settlementPeriod =? ";
    DbAdapter.executeUpdate(this.sql, new Date[] { periodDate });
  }

  public List<Settlement> genSettlementList(Map<String, Settlement> settlementMap, Map<String, List<SettlementDetails>> settlementDetailsMap, Date periodDate) throws DaoException, RenderException {
    deleteByPeriod(periodDate);
    List settlementList = new ArrayList();
    SettlementDetailsService settlementDetailsService = new SettlementDetailsService();
    settlementDetailsService.deleteByPeriod(periodDate);
    for (Iterator localIterator1 = settlementMap.keySet().iterator(); localIterator1.hasNext(); ) { String key = (String)localIterator1.next();
      Settlement settlement = (Settlement)settlementMap.get(key);
      insert(settlement);

      List settlementDetailsList = (List)settlementDetailsMap.get(key);
      for (Iterator localIterator2 = settlementDetailsList.iterator(); localIterator2.hasNext(); ) { SettlementDetails settlementDetails = (SettlementDetails)localIterator2.next();
        settlementDetails.setSettlementId(settlement.getId());
        settlementDetails.setInsertTime(new Date());
        settlementDetailsService.insert(settlementDetails);
      }
      settlementList.add(settlement);
    }
    return settlementList;
  }
}