package com.wiwi.jsoil.sys.dao;

import com.wiwi.jsoil.db.DaoBase;
import com.wiwi.jsoil.db.DbAdapter;
import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.sys.model.OrgBankAccount;
import java.util.List;

public class OrgBankAccountDao extends DaoBase
{
  private String sql;

  public OrgBankAccountDao()
  {
    this.sql = null; }

  public void insert(OrgBankAccount instance) throws DaoException, RenderException {
    DbAdapter.insert2SingleTable(instance, "s_org_bank_account");
  }

  public void update(OrgBankAccount instance) throws DaoException, RenderException {
    DbAdapter.update2SingleTable(instance, "s_org_bank_account");
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

    this.sql = "DELETE FROM s_org_bank_account WHERE id in (" + ids + ") ";
    DbAdapter.executeUpdate(this.sql);
  }

  public OrgBankAccount get(long id) throws DaoException, RenderException {
    this.sql = "select b.*, org.name as orgName FROM s_org_bank_account b, s_organization org  WHERE b.orgId = org.id and b.id ='" + 
      id + "'";
    return ((OrgBankAccount)DbAdapter.get(this.sql, OrgBankAccount.class));
  }

  public List<OrgBankAccount> getList(PageUtil pageUtil) throws DaoException, RenderException
  {
    this.sql = "select b.*, org.name as orgName FROM s_org_bank_account b, s_organization org  WHERE b.orgId = org.id";

    return DbAdapter.getList(this.sql, pageUtil, OrgBankAccount.class);
  }
}