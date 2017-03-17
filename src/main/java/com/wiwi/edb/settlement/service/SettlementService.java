package com.wiwi.edb.settlement.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;

import com.wiwi.edb.order.hotelOrder.service.HotelOrderService;
import com.wiwi.edb.order.model.Order;
import com.wiwi.edb.settlement.SettlementUtil;
import com.wiwi.edb.settlement.dao.SettlementDao;
import com.wiwi.edb.settlement.model.Settlement;
import com.wiwi.edb.settlement.model.SettlementDetails;
import com.wiwi.freego.hotel.model.Hotel;
import com.wiwi.freego.hotel.service.HotelService;
import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.sys.model.Organization;
import com.wiwi.jsoil.sys.model.User;

public class SettlementService
{
  public void insert(Settlement instance)
    throws DaoException, RenderException
  {
    SettlementDao dao = new SettlementDao();
    dao.insert(instance);
  }

  public void update(Settlement instance) throws DaoException, RenderException {
    SettlementDao dao = new SettlementDao();
    dao.update(instance);
  }

  public void confirmSettlement(Long settlementId, User user, String confirmRemark)
    throws DaoException, RenderException
  {
    SettlementDao dao = new SettlementDao();
    dao.confirmSettlement(settlementId, user, confirmRemark);
  }

  public void paySettlement(Long settlementId, User user, String evidence, String remark)
    throws DaoException, RenderException
  {
    SettlementDao dao = new SettlementDao();
    dao.paySettlement(settlementId, user, evidence, remark);
  }

  public void delete(long id) throws DaoException {
    batchDelete(id+"");
  }

  public void batchDelete(String ids) throws DaoException {
    SettlementDao dao = new SettlementDao();
    dao.delete(ids);
  }

  public Settlement get(long id) throws DaoException, RenderException {
    SettlementDao dao = new SettlementDao();
    return dao.get(id);
  }

  public List<Settlement> getList(PageUtil pageUtil) throws DaoException, RenderException {
    SettlementDao dao = new SettlementDao();
    return dao.getList(pageUtil);
  }

  public JSONArray getSettlementPeriodList()
    throws DaoException, RenderException
  {
    SettlementDao dao = new SettlementDao();
    return dao.getSettlementPeriodList();
  }

  public List<Settlement> genSettlementList(List<Order> orderList, Date periodDate, double percentage, User user) throws DaoException, RenderException {
    Date now = new Date();

    Map settlementMap = new HashMap();
    Map settlementDetailsMap = new HashMap();
    if ((orderList == null) || (orderList.size() < 1)) return new ArrayList();
    SettlementDetails settlementDetails = null;
    HotelService hotelService = new HotelService();
    Organization receiveCompany = null;
    long receiveSupplierId = 0L;
    double settlementPrice = 0D;
    for (Iterator localIterator = orderList.iterator(); localIterator.hasNext(); ) { Order order = (Order)localIterator.next();
      receiveCompany = order.getCompany();
      receiveSupplierId = order.getSupplierId();
      settlementDetails = new SettlementDetails();
      settlementDetails.setOrderPayPrice(order.getPayPrice());
      settlementDetails.setOrderId(order.getId());
      settlementDetails.setReceiveCompany(receiveCompany);
      settlementDetails.setReceiveSupplierId(Long.valueOf(receiveSupplierId));
      settlementDetails.setOriginCompanyId(order.getCompany().getId());
      settlementDetails.setOriginSupplierId(Long.valueOf(order.getSupplierId()));
      settlementDetails.setPeriod(periodDate);
      settlementDetails.setTitle("房费");
      settlementDetails.setFeeType("hotelRoomFee");
      settlementDetails.setPercentage(Double.valueOf(percentage));
      settlementDetails.setInsertTime(now);
      if (order.selfCome())
      {
        settlementPrice = order.getPayPrice().doubleValue();
      } else {
        settlementPrice = order.getPayPrice().doubleValue() * (1D - percentage);

        Hotel recommentHotel = hotelService.getByCode(order.getOriginDetails());
        Organization receiveOrg = recommentHotel.getCompany();
        SettlementDetails rebateSD = new SettlementDetails();
        rebateSD.setReceiveCompany(receiveOrg);
        rebateSD.setReceiveSupplierId(recommentHotel.getId());
        rebateSD.setAmount(Double.valueOf(order.getPayPrice().doubleValue() * percentage));
        rebateSD.setOrderPayPrice(order.getPayPrice());
        rebateSD.setOrderId(order.getId());
        rebateSD.setOriginCompanyId(order.getCompany().getId());
        rebateSD.setOriginSupplierId(Long.valueOf(order.getSupplierId()));
        rebateSD.setPeriod(periodDate);
        rebateSD.setTitle("房费提成");
        rebateSD.setFeeType("rebateFee");
        rebateSD.setPercentage(Double.valueOf(percentage));
        rebateSD.setInsertTime(now);
        String remark = order.getOriginDetails() + "推荐房源给" + order.getSupplierCode() + "的提成费";
        rebateSD.setRemark(remark);
        updateSettlementAmount(settlementMap, receiveOrg, recommentHotel.getId().longValue(), order.getPayPrice().doubleValue() * percentage, now, periodDate, user);
        if (settlementDetailsMap.get(receiveOrg.getId() + "-" + recommentHotel.getId()) == null)
          settlementDetailsMap.put(receiveOrg.getId() + "-" + recommentHotel.getId(), new ArrayList());

        ((List)settlementDetailsMap.get(receiveOrg.getId() + "-" + recommentHotel.getId())).add(rebateSD);
      }
      settlementDetails.setAmount(Double.valueOf(settlementPrice));
      updateSettlementAmount(settlementMap, receiveCompany, receiveSupplierId, settlementPrice, now, periodDate, user);
      if (settlementDetailsMap.get(receiveCompany.getId() + "-" + receiveSupplierId) == null)
        settlementDetailsMap.put(receiveCompany.getId() + "-" + receiveSupplierId, new ArrayList());

      ((List)settlementDetailsMap.get(receiveCompany.getId() + "-" + receiveSupplierId)).add(settlementDetails);
    }
    HotelOrderService hotelOrderService = new HotelOrderService();
    for (Iterator recommentHotel = orderList.iterator(); recommentHotel.hasNext(); ) { 
    	 Order order = (Order)recommentHotel.next();
      hotelOrderService.setOrderSettlementInfo(now, user.getId(), order.getId());
    }
    SettlementDao dao = new SettlementDao();
    return dao.genSettlementList(settlementMap, settlementDetailsMap, periodDate);
  }

  private void updateSettlementAmount(Map<String, Settlement> settlementMap, Organization receiveCompany, long receiveSupplierId, double price, Date settlementTime, Date periodDate, User user)
  {
    String key = receiveCompany.getId() + "-" + receiveSupplierId;
    if (settlementMap == null)
      settlementMap = new HashMap();

    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    if (settlementMap.get(key) == null) {
      Settlement settlement = new Settlement();
      settlement.setToCompany(receiveCompany);
      settlement.setToSupplierId(Long.valueOf(receiveSupplierId));
      settlement.setSettlementTime(settlementTime);
      settlement.setSettlementPeriod(periodDate);
      settlement.setSettlementUserId(user.getId());
      settlement.setSettlementTitle(sdf.format(periodDate) + "-" + receiveCompany.getName() + "[" + receiveCompany.getId() + "]-" + receiveSupplierId);
      settlement.setSettlementCode(SettlementUtil.getSettlementCode(periodDate, receiveCompany.getId(), Long.valueOf(receiveSupplierId)));
      settlementMap.put(key, settlement);
    }
    Settlement settlement = (Settlement)settlementMap.get(key);
    settlement.setSettlementAmount(Double.valueOf(settlement.getSettlementAmount().doubleValue() + price));
  }
}