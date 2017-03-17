package com.wiwi.freego.areamanager.dao;

import com.wiwi.freego.areamanager.model.AreaUserMapping;
import com.wiwi.jsoil.db.DaoBase;
import com.wiwi.jsoil.db.DbAdapter;
import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import java.util.List;

public class AreaUserMappingDao extends DaoBase
{
  private String sql;

  public AreaUserMappingDao()
  {
    this.sql = null; }

  public void insert(AreaUserMapping instance) throws DaoException, RenderException {
    DbAdapter.insert2SingleTable(instance, "fg_area_user_mapping");
  }

  public void update(AreaUserMapping instance) throws DaoException, RenderException {
    DbAdapter.update2SingleTable(instance, "fg_area_user_mapping");
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

    this.sql = "DELETE FROM fg_area_user_mapping WHERE id in (" + ids + ") ";
    DbAdapter.executeUpdate(this.sql);
  }

  public AreaUserMapping get(long id) throws DaoException, RenderException {
    this.sql = "select * FROM fg_area_user_mapping WHERE id ='" + id + "'";
    return ((AreaUserMapping)DbAdapter.get(this.sql, AreaUserMapping.class));
  }

  public List<AreaUserMapping> getList(PageUtil pageUtil) throws DaoException, RenderException
  {
    this.sql = "select * FROM fg_area_user_mapping";
    return DbAdapter.getList(this.sql, pageUtil, AreaUserMapping.class);
  }

  public void removeUserFromArea(int areaId, Long[] userIds) throws DaoException {
    Long[] arrayOfLong = userIds; int j = userIds.length; for (int i = 0; i < j; ++i) { Long userId = arrayOfLong[i];
      this.sql = "delete from fg_area_user_mapping where areaId=? and userId=?";
      DbAdapter.executeUpdate(this.sql, new Object[] { Integer.valueOf(areaId), userId });
    }
  }
}