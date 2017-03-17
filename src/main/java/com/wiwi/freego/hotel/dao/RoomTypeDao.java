package com.wiwi.freego.hotel.dao;

import com.wiwi.freego.hotel.model.RoomType;
import com.wiwi.freego.hotel.model.RoomType.Status;
import com.wiwi.jsoil.db.DaoBase;
import com.wiwi.jsoil.db.DbAdapter;
import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.db.Transaction;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import java.util.List;

public class RoomTypeDao extends DaoBase
{
  private String selectSql;
  private String sql;

  public RoomTypeDao()
  {
    this.selectSql = " select m.* from fg_roomType m ";
    this.sql = null; }

  public void insert(RoomType instance) throws DaoException, RenderException {
    DbAdapter.insert2SingleTable(instance, "fg_roomType");
  }

  public void update(RoomType instance) throws DaoException, RenderException {
    DbAdapter.update2SingleTable(instance, "fg_roomType");
  }

  public void delete(String ids) throws DaoException {
    if (ids.startsWith(","))
      ids = ids.substring(1);

    if (ids.indexOf(",") != -1)
      ids = ids.replaceAll(",", "','");

    if (!(ids.startsWith("'")))
      ids = "'" + ids;

    if (!(ids.endsWith("'"))) {
      ids = ids + "'";
    }

    Transaction tran = new Transaction();
    this.sql = "delete from FG_ROOM where roomTypeId in (" + ids + ") ";
    tran.executeUpdate(this.sql);

    this.sql = "DELETE FROM fg_roomType WHERE id in (" + ids + ") ";
    tran.executeUpdate(this.sql);
    tran.commit();
  }

  public RoomType get(long id) throws DaoException, RenderException {
    this.sql = this.selectSql + " where m.id ='" + id + "'";
    return ((RoomType)DbAdapter.get(this.sql, RoomType.class));
  }

  public void changeStatus(long id, RoomType.Status status)
    throws DaoException
  {
    if (id != 0L) {
      this.sql = "update FG_ROOMTYPE set status=? WHERE id=?";
      DbAdapter.executeUpdate(this.sql, new Object[] { status.name(), Long.valueOf(id) });
    }
  }

  public List<RoomType> getList(PageUtil pageUtil) throws DaoException, RenderException
  {
    return DbAdapter.getList(this.selectSql, pageUtil, RoomType.class);
  }
}