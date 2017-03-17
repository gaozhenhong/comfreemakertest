package com.wiwi.jsoil.sys.service;

import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.sys.dao.OrgBankAccountDao;
import com.wiwi.jsoil.sys.model.OrgBankAccount;
import java.util.List;

public class OrgBankAccountService
{
  public void insert(OrgBankAccount instance)
    throws DaoException, RenderException
  {
    OrgBankAccountDao dao = new OrgBankAccountDao();
    dao.insert(instance);
  }

  public void update(OrgBankAccount instance) throws DaoException, RenderException {
    OrgBankAccountDao dao = new OrgBankAccountDao();
    dao.update(instance);
  }

  public void delete(long id) throws DaoException {
    batchDelete(id+"");
  }

  public void batchDelete(String ids) throws DaoException {
    OrgBankAccountDao dao = new OrgBankAccountDao();
    dao.delete(ids);
  }

  public OrgBankAccount get(long id) throws DaoException, RenderException {
    OrgBankAccountDao dao = new OrgBankAccountDao();
    return dao.get(id);
  }

  public List<OrgBankAccount> getList(PageUtil pageUtil) throws DaoException, RenderException {
    OrgBankAccountDao dao = new OrgBankAccountDao();
    return dao.getList(pageUtil);
  }
}