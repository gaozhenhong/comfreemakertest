// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   HotelUserMappingService.java

package com.wiwi.freego.hotel.service;

import com.wiwi.freego.hotel.dao.HotelUserMappingDao;
import com.wiwi.freego.hotel.model.HotelUserMapping;
import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import java.util.List;

public class HotelUserMappingService
{

	public HotelUserMappingService()
	{
	}

	public void insert(HotelUserMapping instance)
		throws DaoException, RenderException
	{
		HotelUserMappingDao dao = new HotelUserMappingDao();
		dao.insert(instance);
	}

	public void addUserToHotel(long hotelId, Long userIds[])
		throws DaoException, RenderException
	{
		HotelUserMappingDao dao = new HotelUserMappingDao();
		Long along[];
		int j = (along = userIds).length;
		for (int i = 0; i < j; i++)
		{
			Long userId = along[i];
			HotelUserMapping hum = new HotelUserMapping();
			hum.setHotelId(Long.valueOf(hotelId));
			hum.setUserId(userId);
			dao.insert(hum);
		}

	}

	public void update(HotelUserMapping instance)
		throws DaoException, RenderException
	{
		HotelUserMappingDao dao = new HotelUserMappingDao();
		dao.update(instance);
	}

	public void delete(int id)
		throws DaoException
	{
		batchDelete((new StringBuilder(String.valueOf(id))).toString());
	}

	public void batchDelete(String ids)
		throws DaoException
	{
		HotelUserMappingDao dao = new HotelUserMappingDao();
		dao.delete(ids);
	}

	public HotelUserMapping get(int id)
		throws DaoException, RenderException
	{
		HotelUserMappingDao dao = new HotelUserMappingDao();
		return dao.get(id);
	}

	public List getList(PageUtil pageUtil)
		throws DaoException, RenderException
	{
		HotelUserMappingDao dao = new HotelUserMappingDao();
		return dao.getList(pageUtil);
	}
}
