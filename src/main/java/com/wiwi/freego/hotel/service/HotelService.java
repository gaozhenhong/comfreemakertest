package com.wiwi.freego.hotel.service;

import com.wiwi.freego.hotel.dao.HotelDao;
import com.wiwi.freego.hotel.model.Hotel;
import com.wiwi.freego.hotel.model.Hotel.Status;
import com.wiwi.freego.hotel.model.HotelBankAccount;
import com.wiwi.freego.hotel.model.HotelQ;
import com.wiwi.freego.hotel.model.HotelUserMapping;
import com.wiwi.freego.hotel.model.Room;
import com.wiwi.freego.hotel.model.RoomType;
import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.sys.model.City;
import com.wiwi.jsoil.sys.model.CityQ;
import com.wiwi.jsoil.sys.model.Organization;
import com.wiwi.jsoil.sys.model.User;
import com.wiwi.jsoil.sys.service.CityService;
import com.wiwi.jsoil.util.SqlUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HotelService
{
  public void insert(Hotel instance)
    throws DaoException, RenderException
  {
    HotelDao dao = new HotelDao();
    dao.insert(instance);
    HotelUserMappingService humService = new HotelUserMappingService();
    HotelUserMapping hum = new HotelUserMapping();
    hum.setHotelId(instance.getId());
    hum.setUserId(instance.getCreatePersonId());
    humService.insert(hum);
  }
  
  public void update(Hotel instance)
    throws DaoException, RenderException
  {
    HotelDao dao = new HotelDao();
    dao.update(instance);
  }
  
  public void delete(long id)
    throws DaoException
  {
    batchDelete(id+"");
  }
  
  public void batchDelete(String ids)
    throws DaoException
  {
    HotelDao dao = new HotelDao();
    dao.delete(ids);
  }
  
  public void closeHotel(long id)
    throws DaoException
  {
    HotelDao dao = new HotelDao();
    dao.changeStatus(id, Hotel.Status.CLOSED);
  }
  
  public void openHotel(long id)
    throws DaoException
  {
    HotelDao dao = new HotelDao();
    dao.changeStatus(id, Hotel.Status.OPENING);
  }
  
  public void shutoutHotel(long id)
    throws DaoException
  {
    HotelDao dao = new HotelDao();
    dao.changeStatus(id, Hotel.Status.SHUTOUT);
  }
  
  public void deleteByAdmin(long id)
    throws DaoException
  {
    batchDeleteByAdmin(id+"");
  }
  
  public void batchDeleteByAdmin(String ids)
    throws DaoException
  {
    HotelDao dao = new HotelDao();
    dao.deleteByAdmin(ids);
  }
  
  public Hotel get(long id)
    throws DaoException, RenderException
  {
    HotelDao dao = new HotelDao();
    return dao.get(id);
  }
  
  public Hotel getByCode(String code)
    throws DaoException, RenderException
  {
    HotelDao dao = new HotelDao();
    return dao.getByCode(code);
  }
  
  public List<Hotel> getList(PageUtil pageUtil)
    throws DaoException, RenderException
  {
    HotelDao dao = new HotelDao();
    return dao.getList(pageUtil);
  }
  
  public List<City> getCityList()
    throws DaoException, RenderException
  {
    CityQ cityQ = new CityQ();
    cityQ.setRecordPerPage(-1);
    String otherCondition = " where id in (select cityId from fg_hotel where (deleteFlag is null or deleteFlag <>1))";
    cityQ.setOtherCondition(otherCondition);
    return new CityService().getList(cityQ);
  }
  
  public List<Hotel> getOpeningList(int cityId)
    throws DaoException, RenderException
  {
    HotelQ hotelQ = new HotelQ();
    hotelQ.setRecordPerPage(-1);
    hotelQ.setCityId(Integer.valueOf(cityId));
    hotelQ.setStatus(Hotel.Status.OPENING.name());
    hotelQ.setDelete(false);
    HotelDao dao = new HotelDao();
    return dao.getList(hotelQ);
  }
  
  public List<Hotel> getHotelListByUserId(User user)
    throws DaoException, RenderException
  {
    HotelQ query = new HotelQ();
    query.setDelete(false);
    query.setOrderByKind("desc");
    query.setOrderByProperty("orderNo");
    String otherCondition = " where id in (select hotelId from fg_hotel_user_mapping where userId=" + user.getId() + ")";
    query.setOtherCondition(otherCondition);
    return getList(query);
  }
  
  public List<Hotel> getOtherUserHotelList(User user)
    throws DaoException, RenderException
  {
    HotelQ query = new HotelQ();
    query.setDelete(false);
    query.setOrderByKind("desc");
    query.setOrderByProperty("orderNo");
    String otherCondition = " where id not in (select hotelId from fg_hotel_user_mapping where userId=" + user.getId() + ")";
    query.setOtherCondition(otherCondition);
    return getList(query);
  }
  
  public List<Hotel> getOtherCompanyHotelList(Integer compnayId)
    throws DaoException, RenderException
  {
    HotelQ query = new HotelQ();
    query.setDelete(false);
    query.setNotCompanyId(compnayId);
    query.setOrderByKind("desc");
    query.setOrderByProperty("orderNo");
    return getList(query);
  }
  
  public Integer getHotelCompanyId(String hotelCode)
  {
    try
    {
      Hotel hotel = getByCode(hotelCode);
      return hotel.getCompany().getId();
    }
    catch (DaoException|RenderException e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  public HotelBankAccount getHotelBankAccount(long hotelId)
    throws DaoException, RenderException
  {
    HotelBankAccountService bankService = new HotelBankAccountService();
    return bankService.getByHotelId(hotelId);
  }
  
  public void deleteHotelCategory(long id)
    throws DaoException
  {
    HotelDao dao = new HotelDao();
    dao.deleteHotelCategory(id);
  }
  
  public List<Hotel> getList(Set<Long> hotelIdSet)
  {
    HotelQ query = new HotelQ();
    query.setRecordPerPage(-1);
    String otherCondition = " where id in (" + SqlUtil.getInSqlStr(hotelIdSet) + ")";
    query.setOtherCondition(otherCondition);
    try
    {
      return getList(query);
    }
    catch (DaoException|RenderException e)
    {
      e.printStackTrace();
    }
    return new ArrayList();
  }
  
  public List<Hotel> getVacantRoomHotelList(Integer cityId, String hotelName, Date beginDate, Date endDate, Double lowPrice, Double heightPrice)
    throws DaoException, RenderException
  {
    Map<Long, Map<RoomType, List<Room>>> hotelMap = new RoomTypeService().getVacantRoomHotelList(cityId, hotelName, beginDate, endDate, lowPrice, heightPrice);
    return getList(hotelMap.keySet());
  }
}
