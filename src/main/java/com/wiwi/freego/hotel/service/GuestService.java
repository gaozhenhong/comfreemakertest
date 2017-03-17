// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GuestService.java

package com.wiwi.freego.hotel.service;

import com.wiwi.edb.order.model.Order;
import com.wiwi.freego.hotel.dao.GuestDao;
import com.wiwi.freego.hotel.model.Guest;
import com.wiwi.freego.hotel.orderGuest.model.OrderGuestMapping;
import com.wiwi.freego.hotel.orderGuest.service.OrderGuestMappingService;
import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.sys.model.User;
import java.util.*;

public class GuestService
{

	public GuestService()
	{
	}

	public void insert(Guest instance)
		throws DaoException, RenderException
	{
		GuestDao dao = new GuestDao();
		dao.insert(instance);
	}

	public void update(Guest instance)
		throws DaoException, RenderException
	{
		GuestDao dao = new GuestDao();
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
		GuestDao dao = new GuestDao();
		dao.delete(ids);
	}

	public Guest get(long id)
		throws DaoException, RenderException
	{
		GuestDao dao = new GuestDao();
		return dao.get(id);
	}

	public Guest getGuestByIdCard(String idCardType, String idCardNo)
		throws DaoException, RenderException
	{
		GuestDao dao = new GuestDao();
		return dao.getGuestByIdCard(idCardType, idCardNo);
	}

	public List getList(PageUtil pageUtil)
		throws DaoException, RenderException
	{
		GuestDao dao = new GuestDao();
		return dao.getList(pageUtil);
	}

	public void batchUpdateOrderGuest(Order order, List guestList, User user)
	{
		if (order == null || guestList == null || guestList.size() < 1)
			return;
		Guest persistentGuest = null;
		OrderGuestMapping ogm = null;
		OrderGuestMappingService ogmService = new OrderGuestMappingService();
		Date now = new Date();
		for (Iterator iterator = guestList.iterator(); iterator.hasNext();)
		{
			Guest guest = (Guest)iterator.next();
			try
			{
				persistentGuest = getGuestByIdCard(guest.getIdCardType(), guest.getIdCardNo());
				if (persistentGuest == null)
				{
					insert(guest);
					persistentGuest = guest;
				}
				if (persistentGuest != null)
				{
					ogm = new OrderGuestMapping(order.getId(), guest.getId());
					ogm.setCheckinRegisterUserId(user.getId());
					ogm.setCheckinTime(now);
					ogmService.insert(ogm);
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

	}
}
