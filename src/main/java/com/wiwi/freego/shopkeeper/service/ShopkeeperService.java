package com.wiwi.freego.shopkeeper.service;

import com.wiwi.freego.shopkeeper.dao.ShopkeeperDao;
import com.wiwi.freego.shopkeeper.model.Shopkeeper;
import com.wiwi.freego.shopkeeper.model.ShopkeeperQ;
import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import java.util.List;

public class ShopkeeperService
{
  public void insert(Shopkeeper instance)
    throws DaoException, RenderException
  {
    ShopkeeperDao dao = new ShopkeeperDao();
    dao.insert(instance);
  }

  public void update(Shopkeeper instance) throws DaoException, RenderException {
    ShopkeeperDao dao = new ShopkeeperDao();
    dao.update(instance);
  }

  public void delete(long id) throws DaoException {
    batchDelete(id+"");
  }

  public void batchDelete(String ids) throws DaoException {
    ShopkeeperDao dao = new ShopkeeperDao();
    dao.delete(ids);
  }

  public Shopkeeper get(long id) throws DaoException, RenderException {
    ShopkeeperDao dao = new ShopkeeperDao();
    return dao.get(id);
  }

  public List<Shopkeeper> getList(PageUtil pageUtil) throws DaoException, RenderException {
    ShopkeeperDao dao = new ShopkeeperDao();
    return dao.getList(pageUtil);
  }

  public List<Shopkeeper> getListByOrgId(Integer orgId)
    throws DaoException, RenderException
  {
    ShopkeeperQ pageUtil = new ShopkeeperQ();
    pageUtil.setOrgId(orgId);
    pageUtil.setRecordPerPage(-1);
    ShopkeeperDao dao = new ShopkeeperDao();
    return dao.getList(pageUtil);
  }
}