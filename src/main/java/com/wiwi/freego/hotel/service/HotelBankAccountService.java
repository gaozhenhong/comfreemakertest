// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   HotelBankAccountService.java

package com.wiwi.freego.hotel.service;

import com.wiwi.freego.hotel.dao.HotelBankAccountDao;
import com.wiwi.freego.hotel.model.HotelBankAccount;
import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import java.util.List;

public class HotelBankAccountService
{

	public HotelBankAccountService()
	{
	}

	public void insert(HotelBankAccount instance)
		throws DaoException, RenderException
	{
		HotelBankAccountDao dao = new HotelBankAccountDao();
		dao.insert(instance);
	}

	public void update(HotelBankAccount instance)
		throws DaoException, RenderException
	{
		HotelBankAccountDao dao = new HotelBankAccountDao();
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
		HotelBankAccountDao dao = new HotelBankAccountDao();
		dao.delete(ids);
	}

	public HotelBankAccount get(long id)
		throws DaoException, RenderException
	{
		HotelBankAccountDao dao = new HotelBankAccountDao();
		return dao.get(id);
	}

	public List<HotelBankAccount>getList(PageUtil pageUtil)
		throws DaoException, RenderException
	{
		HotelBankAccountDao dao = new HotelBankAccountDao();
		return dao.getList(pageUtil);
	}

	public HotelBankAccount getByHotelId(long hotelId)
		throws DaoException, RenderException
	{
		HotelBankAccountDao dao = new HotelBankAccountDao();
		return dao.getByHotelId(hotelId);
	}
}
