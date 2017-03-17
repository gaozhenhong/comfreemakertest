package com.wiwi.freego.hotel.dao;

import com.wiwi.freego.hotel.model.Hotel;
import com.wiwi.freego.hotel.model.Hotel.Status;
import com.wiwi.jsoil.db.DaoBase;
import com.wiwi.jsoil.db.DbAdapter;
import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import java.util.List;

public class HotelDao
  extends DaoBase
{
  private String selectSql = "select m.*, k.name as shopkeeperName,k.logo as shopkeeperLogo,k.weixin as shopkeeperWeixin, k.telephone as shopkeeperTelephone,k.qrCodeId as shopkeeperQrCodeId ,k.description as shopkeeperDescription FROM ( select m.*,o.name as companyName from fg_hotel m ,s_organization o where m.companyId=o.id ) m left join fg_shopkeeper k  on  m.shopkeeperId = k.id";
  private String sql = null;
  
  public void insert(Hotel instance)
    throws DaoException, RenderException
  {
    DbAdapter.insert2SingleTable(instance, "fg_hotel");
  }
  
  public void update(Hotel instance)
    throws DaoException, RenderException
  {
    DbAdapter.update2SingleTable(instance, "fg_hotel");
  }
  
  public void delete(String ids)
    throws DaoException
  {
    if (ids.startsWith(",")) {
      ids = ids.substring(1);
    }
    if (ids.indexOf(",") != -1) {
      ids = ids.replaceAll(",", "','");
    }
    if (!ids.startsWith("'")) {
      ids = "'" + ids;
    }
    if (!ids.endsWith("'")) {
      ids = ids + "'";
    }
    this.sql = ("update fg_hotel set deleteFlag=1  WHERE id in (" + ids + ") ");
    DbAdapter.executeUpdate(this.sql);
  }
  
  public void deleteByAdmin(String ids)
    throws DaoException
  {
    if (ids.startsWith(",")) {
      ids = ids.substring(1);
    }
    if (ids.indexOf(",") != -1) {
      ids = ids.replaceAll(",", "','");
    }
    if (!ids.startsWith("'")) {
      ids = "'" + ids;
    }
    if (!ids.endsWith("'")) {
      ids = ids + "'";
    }
    this.sql = ("DELETE from fg_hotel  WHERE id in (" + ids + ") ");
    DbAdapter.executeUpdate(this.sql);
  }
  
  public void changeStatus(long id, Hotel.Status status)
    throws DaoException
  {
    if (id != 0L)
    {
      this.sql = "update fg_hotel set status=? WHERE id=?";
      DbAdapter.executeUpdate(this.sql, new Object[] { status.name(), Long.valueOf(id) });
    }
  }
  
  public Hotel get(long id)
    throws DaoException, RenderException
  {
    this.sql = (this.selectSql + " where m.id ='" + id + "'");
    return (Hotel)DbAdapter.get(this.sql, Hotel.class);
  }
  
  public Hotel getByCode(String code)
    throws DaoException, RenderException
  {
    this.sql = (this.selectSql + " where m.code ='" + code + "'");
    return (Hotel)DbAdapter.get(this.sql, Hotel.class);
  }
  
  public List<Hotel> getList(PageUtil pageUtil)
    throws DaoException, RenderException
  {
    this.sql = this.selectSql;
    return DbAdapter.getList(this.sql, pageUtil, Hotel.class);
  }
  
  public void deleteHotelCategory(long id)
    throws DaoException
  {
    this.sql = "update fg_hotel set categoryId=null WHERE id=?";
    
    DbAdapter.executeUpdate(this.sql, new Object[] { Long.valueOf(id) });
  }
}
