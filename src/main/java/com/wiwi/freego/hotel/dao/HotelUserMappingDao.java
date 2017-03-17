package com.wiwi.freego.hotel.dao;

import com.wiwi.freego.hotel.model.HotelUserMapping;
import com.wiwi.jsoil.db.DaoBase;
import com.wiwi.jsoil.db.DbAdapter;
import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import java.util.List;

public class HotelUserMappingDao extends DaoBase
{
  private String sql;

  public HotelUserMappingDao()
  {
    this.sql = null; }

  public void insert(HotelUserMapping instance) throws DaoException, RenderException {
    DbAdapter.insert2SingleTable(instance, "fg_hotel_user_mapping");
  }

  public void update(HotelUserMapping instance) throws DaoException, RenderException {
    DbAdapter.update2SingleTable(instance, "fg_hotel_user_mapping");
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

    this.sql = "DELETE FROM fg_hotel_user_mapping WHERE id in (" + ids + ") ";
    DbAdapter.executeUpdate(this.sql);
  }

  public HotelUserMapping get(int id) throws DaoException, RenderException {
    this.sql = "select * FROM fg_hotel_user_mapping WHERE id ='" + id + "'";
    return ((HotelUserMapping)DbAdapter.get(this.sql, HotelUserMapping.class));
  }

  public List<HotelUserMapping> getList(PageUtil pageUtil) throws DaoException, RenderException
  {
    this.sql = "select * FROM fg_hotel_user_mapping";
    return DbAdapter.getList(this.sql, pageUtil, HotelUserMapping.class);
  }
}