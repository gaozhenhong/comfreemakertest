package com.wiwi.freego.hotel.dao;

import com.wiwi.freego.hotel.model.Guest;
import com.wiwi.jsoil.db.DaoBase;
import com.wiwi.jsoil.db.DbAdapter;
import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import java.util.List;

public class GuestDao extends DaoBase
{
  private String sql;

  public GuestDao()
  {
    this.sql = null; }

  public void insert(Guest instance) throws DaoException, RenderException {
    DbAdapter.insert2SingleTable(instance, "fg_guest");
  }

  public void update(Guest instance) throws DaoException, RenderException {
    DbAdapter.update2SingleTable(instance, "fg_guest");
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

    this.sql = "DELETE FROM fg_guest WHERE id in (" + ids + ") ";
    DbAdapter.executeUpdate(this.sql);
  }

  public Guest get(long id) throws DaoException, RenderException {
    this.sql = "select * FROM fg_guest WHERE id ='" + id + "'";
    return ((Guest)DbAdapter.get(this.sql, Guest.class));
  }

  public List<Guest> getList(PageUtil pageUtil) throws DaoException, RenderException
  {
    this.sql = "select * FROM fg_guest";
    return DbAdapter.getList(this.sql, pageUtil, Guest.class);
  }

  public Guest getGuestByIdCard(String idCardType, String idCardNo) throws DaoException, RenderException {
    this.sql = "select * FROM fg_guest WHERE idCardType =? and idCardNo = ?";
    return ((Guest)DbAdapter.get(this.sql, Guest.class, new String[] { idCardType, idCardNo }));
  }
}