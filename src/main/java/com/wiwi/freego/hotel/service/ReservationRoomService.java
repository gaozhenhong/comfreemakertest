// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ReservationRoomService.java

package com.wiwi.freego.hotel.service;

import com.wiwi.edb.order.hotelOrder.HotelOrderUtil;
import com.wiwi.edb.order.hotelOrder.service.HotelOrderService;
import com.wiwi.edb.order.model.Order;
import com.wiwi.edb.order.model.OrderDetails;
import com.wiwi.freego.hotel.dao.ReservationRoomDao;
import com.wiwi.freego.hotel.model.*;
import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.util.DateUtils;
import java.math.BigDecimal;
import java.util.*;
import org.json.JSONObject;

// Referenced classes of package com.wiwi.freego.hotel.service:
//			RoomService

public class ReservationRoomService
{

	public ReservationRoomService()
	{
	}

	public void insert(ReservationRoom instance)
		throws DaoException, RenderException
	{
		ReservationRoomDao dao = new ReservationRoomDao();
		dao.insert(instance);
	}

	public void update(ReservationRoom instance)
		throws DaoException, RenderException
	{
		ReservationRoomDao dao = new ReservationRoomDao();
		dao.update(instance);
	}

	public void deleteByOrderId(long orderId)
		throws DaoException
	{
		ReservationRoomDao dao = new ReservationRoomDao();
		dao.deleteByOrderId(Long.valueOf(orderId));
	}

	public void delete(long id)
		throws DaoException
	{
		batchDelete((new StringBuilder(String.valueOf(id))).toString());
	}

	public void batchDelete(String ids)
		throws DaoException
	{
		ReservationRoomDao dao = new ReservationRoomDao();
		dao.delete(ids);
	}

	public ReservationRoom get(long id)
		throws DaoException, RenderException
	{
		ReservationRoomDao dao = new ReservationRoomDao();
		return dao.get(id);
	}

	public List getList(PageUtil pageUtil)
		throws DaoException, RenderException
	{
		ReservationRoomDao dao = new ReservationRoomDao();
		return dao.getList(pageUtil);
	}

	public List getListByOrderId(long orderId)
		throws DaoException, RenderException
	{
		ReservationRoomQ query = new ReservationRoomQ();
		query.setRecordPerPage(-1);
		query.setOrderId(Long.valueOf(orderId));
		query.setOrderByKind("ASC");
		query.setOrderByProperty("consumeDate");
		ReservationRoomDao dao = new ReservationRoomDao();
		return dao.getList(query);
	}

	public List getList(Long hotelId, Date beginConsumeDate, Date endConsumeDate)
		throws DaoException, RenderException
	{
		ReservationRoomDao dao = new ReservationRoomDao();
		ReservationRoomQ query = new ReservationRoomQ();
		query.setBeginConsumeDate(beginConsumeDate);
		query.setEndConsumeDate(endConsumeDate);
		query.setHotelId(hotelId);
		query.setNotStatus(HotelOrderUtil.getNotReservationStatus());
		return dao.getList(query);
	}

	public synchronized void genReservationRooms(OrderDetails orderDetails, Order order)
		throws DaoException, RenderException
	{
		if (orderDetails == null)
			return;
		if (order == null)
			order = (new HotelOrderService()).get(orderDetails.getOrderId().longValue());
		int number = orderDetails.getOrderNumber().intValue();
		Long roomId = orderDetails.getProductId();
		Room room = (new RoomService()).get(roomId.longValue());
		Date consumeBeginDate = orderDetails.getConsumeBeginTime();
		ReservationRoom reservationRoom = null;
		String orderCode = orderDetails.getOrderCode();
		Long orderId = orderDetails.getOrderId();
		Long orderDetailsId = orderDetails.getId();
		double totalPrice = orderDetails.getOrderPrice().doubleValue();
		double havePrice = 0.0D;
		double price = 0.0D;
		BigDecimal precisionAveragePrice = new BigDecimal(totalPrice / (double)number);
		price = precisionAveragePrice.setScale(1, 4).doubleValue();
		Date consumeDate = null;
		for (int j = 0; j < number; j++)
		{
			reservationRoom = new ReservationRoom();
			reservationRoom.setOrderCode(orderCode);
			reservationRoom.setOrderId(orderId);
			reservationRoom.setRoomTypeId(room.getRoomType().getId());
			reservationRoom.setRoomId(roomId);
			if (j == number - 1)
			{
				price = totalPrice - havePrice;
				price = (new BigDecimal(price)).setScale(1, 4).doubleValue();
			}
			reservationRoom.setPrice(Double.valueOf(price));
			consumeDate = DateUtils.addDay(consumeBeginDate, j);
			reservationRoom.setConsumeDate(consumeDate);
			reservationRoom.setRoomNo(room.getRoomNo());
			reservationRoom.setName((new StringBuilder(String.valueOf(room.getRoomType().getName()))).append(room.getRoomNo()).toString());
			reservationRoom.setOrderDetailsId(orderDetailsId);
			reservationRoom.setStatus(order.getStatus());
			reservationRoom.setRemark((new JSONObject(order)).toString());
			reservationRoom.setHotelId(room.getHotelId());
			insert(reservationRoom);
		}

	}

	public void checkin(Long reservationRoomId)
		throws DaoException
	{
		setStatus(reservationRoomId, com.wiwi.edb.order.model.Order.Status.CHECKIN);
	}

	public void checkin(OrderDetails orderDetails)
		throws DaoException
	{
		setStatus(orderDetails, com.wiwi.edb.order.model.Order.Status.CHECKIN);
	}

	public void checkout(Long reservationRoomId)
		throws DaoException
	{
		setStatus(reservationRoomId, com.wiwi.edb.order.model.Order.Status.CHECKOUT);
	}

	public void checkout(OrderDetails orderDetails)
		throws DaoException
	{
		setStatus(orderDetails, com.wiwi.edb.order.model.Order.Status.CHECKOUT);
	}

	private void setStatus(Long reservationRoomId, com.wiwi.edb.order.model.Order.Status status)
		throws DaoException
	{
		if (reservationRoomId == null || reservationRoomId.longValue() == 0L)
		{
			return;
		} else
		{
			ReservationRoomDao dao = new ReservationRoomDao();
			dao.setStatus(reservationRoomId.longValue(), status);
			return;
		}
	}

	private void setStatus(OrderDetails orderDetails, com.wiwi.edb.order.model.Order.Status status)
		throws DaoException
	{
		if (orderDetails == null)
		{
			return;
		} else
		{
			ReservationRoomDao dao = new ReservationRoomDao();
			dao.setStatusByOrderDetails(orderDetails, status);
			return;
		}
	}

	public Map getOrderReservationRooms(long orderId)
		throws DaoException, RenderException
	{
		List reservationRoomList = (new ReservationRoomService()).getListByOrderId(orderId);
		Map reservationRoomMap = new HashMap();
		List list = null;
		ReservationRoom room;
		for (Iterator iterator = reservationRoomList.iterator(); iterator.hasNext(); ((List)reservationRoomMap.get(room.getOrderDetailsId())).add(room))
		{
			room = (ReservationRoom)iterator.next();
			list = (List)reservationRoomMap.get(room.getOrderDetailsId());
			if (list == null)
				reservationRoomMap.put(room.getOrderDetailsId(), new ArrayList());
		}

		return reservationRoomMap;
	}
}
