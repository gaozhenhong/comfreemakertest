package com.wiwi.freego.hotel.service;

import com.wiwi.freego.hotel.dao.SpecialTimePriceDao;
import com.wiwi.freego.hotel.model.SpecialTimePrice;
import com.wiwi.freego.hotel.model.SpecialTimePriceQ;
import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import java.util.Date;
import java.util.List;

public class SpecialTimePriceService
{
  public void insert(SpecialTimePrice instance)
    throws DaoException, RenderException
  {
    SpecialTimePriceDao dao = new SpecialTimePriceDao();
    dao.insert(instance);
  }
  
  public void update(SpecialTimePrice instance)
    throws DaoException, RenderException
  {
    SpecialTimePriceDao dao = new SpecialTimePriceDao();
    dao.update(instance);
  }
  
  public void delete(int id)
    throws DaoException
  {
    batchDelete(id+"");
  }
  
  public void batchDelete(String ids)
    throws DaoException
  {
    SpecialTimePriceDao dao = new SpecialTimePriceDao();
    dao.delete(ids);
  }
  
  public SpecialTimePrice get(int id)
    throws DaoException, RenderException
  {
    SpecialTimePriceDao dao = new SpecialTimePriceDao();
    return dao.get(id);
  }
  
  public List<SpecialTimePrice> getList(PageUtil pageUtil)
    throws DaoException, RenderException
  {
    SpecialTimePriceDao dao = new SpecialTimePriceDao();
    return dao.getList(pageUtil);
  }
  
  public SpecialTimePrice getPrice(long roomTypeId, Date specialTime)
  {
    SpecialTimePriceDao dao = new SpecialTimePriceDao();
    SpecialTimePriceQ specialTimePriceQ = new SpecialTimePriceQ();
    specialTimePriceQ.setRecordPerPage(-1);
    specialTimePriceQ.setRoomTypeId(Long.valueOf(roomTypeId));
    specialTimePriceQ.setSpecialTime(specialTime);
    try
    {
      List<SpecialTimePrice> list = dao.getList(specialTimePriceQ);
      if ((list == null) || (list.size() < 1)) {
        return null;
      }
      return (SpecialTimePrice)list.get(0);
    }
    catch (DaoException e)
    {
      e.printStackTrace();
    }
    catch (RenderException e)
    {
      e.printStackTrace();
    }
    return null;
  }
}
