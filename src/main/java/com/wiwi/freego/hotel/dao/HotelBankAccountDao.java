package com.wiwi.freego.hotel.dao;

import com.wiwi.freego.hotel.model.HotelBankAccount;
import com.wiwi.jsoil.db.DaoBase;
import com.wiwi.jsoil.db.DbAdapter;
import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import java.util.List;

public class HotelBankAccountDao extends DaoBase
{
  private String sql;

  public HotelBankAccountDao()
  {
    this.sql = null; }

  public void insert(HotelBankAccount instance) throws DaoException, RenderException {
    DbAdapter.insert2SingleTable(instance, "fg_hotel_bank_account");
  }

  public void update(HotelBankAccount instance) throws DaoException, RenderException {
    DbAdapter.update2SingleTable(instance, "fg_hotel_bank_account");
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

    this.sql = "DELETE FROM fg_hotel_bank_account WHERE id in (" + ids + ") ";
    DbAdapter.executeUpdate(this.sql);
  }

  public HotelBankAccount get(long id) throws DaoException, RenderException {
    this.sql = "select b.*,h.name as hotelName FROM fg_hotel_bank_account b, fg_hotel h WHERE b.hotelId = h.id  and b.id ='" + 
      id + "'";
    return ((HotelBankAccount)DbAdapter.get(this.sql, HotelBankAccount.class));
  }

  public HotelBankAccount getByHotelId(long hotelId) throws DaoException, RenderException
  {
    this.sql = "select b.*,h.name as hotelName FROM fg_hotel_bank_account b, fg_hotel h WHERE b.hotelId = h.id  and b.hotelId ='" + 
      hotelId + "'";
    return ((HotelBankAccount)DbAdapter.get(this.sql, HotelBankAccount.class));
  }

  public List<HotelBankAccount> getList(PageUtil pageUtil) throws DaoException, RenderException
  {
    this.sql = "select b.*,h.name as hotelName FROM fg_hotel_bank_account b, fg_hotel h WHERE b.hotelId = h.id ";

    return DbAdapter.getList(this.sql, pageUtil, HotelBankAccount.class);
  }
}