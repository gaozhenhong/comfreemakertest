// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RoomTypeService.java

package com.wiwi.freego.hotel.service;

import com.wiwi.freego.hotel.dao.RoomTypeDao;
import com.wiwi.freego.hotel.model.RoomType;
import com.wiwi.freego.hotel.model.RoomTypeQ;
import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.util.DateUtils;
import java.util.*;

// Referenced classes of package com.wiwi.freego.hotel.service:
//			RoomService

public class RoomTypeService
{

	public RoomTypeService()
	{
	}

	public void insert(RoomType instance)
		throws DaoException, RenderException
	{
		RoomTypeDao dao = new RoomTypeDao();
		dao.insert(instance);
	}

	public void update(RoomType instance)
		throws DaoException, RenderException
	{
		RoomTypeDao dao = new RoomTypeDao();
		dao.update(instance);
	}

	public void delete(long id)
		throws DaoException
	{
		batchDelete((new StringBuilder(String.valueOf(id))).toString());
	}

	public void batchDelete(String ids)
		throws DaoException
	{
		RoomTypeDao dao = new RoomTypeDao();
		dao.delete(ids);
	}

	public RoomType get(long id)
		throws DaoException, RenderException
	{
		RoomTypeDao dao = new RoomTypeDao();
		return dao.get(id);
	}

	public List getList(PageUtil pageUtil)
		throws DaoException, RenderException
	{
		RoomTypeDao dao = new RoomTypeDao();
		return dao.getList(pageUtil);
	}

	public void openRoomType(long id)
		throws DaoException
	{
		RoomTypeDao dao = new RoomTypeDao();
		dao.changeStatus(id, com.wiwi.freego.hotel.model.RoomType.Status.OPENING);
	}

	public void cancelOpenRoomType(long id)
		throws DaoException
	{
		RoomTypeDao dao = new RoomTypeDao();
		dao.changeStatus(id, com.wiwi.freego.hotel.model.RoomType.Status.EDIT);
	}

	public List getOpeningList(Long hotelId)
		throws DaoException, RenderException
	{
		return getOpeningList(null, hotelId, null);
	}

	public List getOpeningList(Integer cityId, Long hotelId, String hotelName)
		throws DaoException, RenderException
	{
		RoomTypeDao dao = new RoomTypeDao();
		RoomTypeQ roomTypeQ = new RoomTypeQ();
		roomTypeQ.setRecordPerPage(-1);
		if (hotelId != null && hotelId.longValue() > 0L)
			roomTypeQ.setHotelId(hotelId);
		if (cityId != null && cityId.intValue() > 0)
			roomTypeQ.setCityId(cityId);
		if (hotelName != null && hotelName.trim().length() > 0)
			roomTypeQ.setHotelName(hotelName);
		roomTypeQ.setStatus(com.wiwi.freego.hotel.model.RoomType.Status.OPENING.name());
		return dao.getList(roomTypeQ);
	}

	public boolean haveVacantTypeRoom(long hotelId, Date beginDate, Date endDate)
	{
		return (new RoomService()).haveVacantRoomByRoomTypeId(Long.valueOf(hotelId), null, beginDate, endDate);
	}

	public Map getVacantRoomTypeList(long hotelId, Date beginDate, Date endDate)
		throws DaoException, RenderException
	{
		return getVacantRoomTypeList(Long.valueOf(hotelId), null, beginDate, endDate);
	}

	public Map getVacantRoomTypeList(Long hotelId, String hotelName, Date beginDate, Date endDate)
		throws DaoException, RenderException
	{
		return getVacantRoomTypeList(((Integer) (null)), hotelId, hotelName, beginDate, endDate);
	}

	public Map getVacantRoomTypeList(Integer cityId, Long hotelId, String hotelName, Date beginDate, Date endDate)
		throws DaoException, RenderException
	{
		List roomTypeList = getOpeningList(cityId, hotelId, hotelName);
		Map roomTypeMap = new HashMap();
		RoomService roomService = new RoomService();
		RoomType roomType;
		List tempRooms;
		for (Iterator iterator = roomTypeList.iterator(); iterator.hasNext(); roomTypeMap.put(roomType, tempRooms))
		{
			roomType = (RoomType)iterator.next();
			tempRooms = roomService.getVacantRoomListByRoomTypeId(null, roomType.getId(), beginDate, endDate);
			if (tempRooms == null)
				tempRooms = new ArrayList();
		}

		return roomTypeMap;
	}

	public Map getVacantRoomTypeList(String hotelName, Date beginDate, Date endDate, Double lowPrice, Double heightPrice)
		throws DaoException, RenderException
	{
		return getVacantRoomTypeList(null, null, hotelName, beginDate, endDate, lowPrice, heightPrice);
	}

	public Map getVacantRoomTypeListOnCity(Integer cityId, String hotelName, Date beginDate, Date endDate, Double lowPrice, Double heightPrice)
		throws DaoException, RenderException
	{
		return getVacantRoomTypeList(cityId, null, hotelName, beginDate, endDate, lowPrice, heightPrice);
	}

	public Map getVacantRoomTypeList(Integer cityId, Long hotelId, String hotelName, Date beginDate, Date endDate, Double lowPrice, Double heightPrice)
		throws DaoException, RenderException
	{
		Map roomTypeMap = new HashMap();
		Map allRoomTypeOfHaveVacantRoomMap = getVacantRoomTypeList(cityId, hotelId, hotelName, beginDate, endDate);
		for (Iterator iterator = allRoomTypeOfHaveVacantRoomMap.keySet().iterator(); iterator.hasNext();)
		{
			RoomType roomType = (RoomType)iterator.next();
			boolean haveRoom = true;
			if (lowPrice != null || heightPrice != null)
			{
				int span = DateUtils.compareDate(beginDate, endDate);
				for (int i = 0; i < span; i++)
				{
					double price = roomType.getPrice(DateUtils.addDay(beginDate, i));
					if (lowPrice != null && price < lowPrice.doubleValue())
					{
						haveRoom = false;
						break;
					}
					if (heightPrice == null || price <= heightPrice.doubleValue())
						continue;
					haveRoom = false;
					break;
				}

			}
			if (haveRoom)
				roomTypeMap.put(roomType, (List)allRoomTypeOfHaveVacantRoomMap.get(roomType));
		}

		return roomTypeMap;
	}

	public Map getVacantRoomHotelList(Integer cityId, String hotelName, Date beginDate, Date endDate, Double lowPrice, Double heightPrice)
		throws DaoException, RenderException
	{
		Map hotelMap = new HashMap();
		Map roomTypeMap = getVacantRoomTypeListOnCity(cityId, hotelName, beginDate, endDate, lowPrice, heightPrice);
		RoomType roomType;
		for (Iterator iterator = roomTypeMap.keySet().iterator(); iterator.hasNext(); ((Map)hotelMap.get(roomType.getHotelId())).put(roomType, (List)roomTypeMap.get(roomType)))
		{
			roomType = (RoomType)iterator.next();
			if (hotelMap.get(roomType.getHotelId()) == null)
				hotelMap.put(roomType.getHotelId(), new HashMap());
		}

		return hotelMap;
	}
}
