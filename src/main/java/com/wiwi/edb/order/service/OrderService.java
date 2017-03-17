package com.wiwi.edb.order.service;

import com.wiwi.edb.order.OrderUtil;
import com.wiwi.edb.order.dao.OrderDao;
import com.wiwi.edb.order.model.Order;
import com.wiwi.edb.order.model.Order.Status;
import com.wiwi.edb.order.model.OrderProcess;
import com.wiwi.edb.order.model.OrderProcess.OrderOperate;
import com.wiwi.edb.order.model.OrderQ;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.sys.model.Organization;
import com.wiwi.jsoil.sys.model.OrganizationQ;
import com.wiwi.jsoil.sys.model.User;
import com.wiwi.jsoil.sys.service.OrganizationService;
import java.util.Date;
import java.util.List;

public abstract class OrderService
{
  OrganizationService orgService;

  public OrderService()
  {
    this.orgService = new OrganizationService(); }

  public void insert(Order instance) throws DaoException, RenderException { OrderDao dao = new OrderDao();
    dao.insert(instance);
  }

  public void delete(long id, User user) throws DaoException {
    batchDelete(id+"", user);
  }

  public void batchDelete(String ids, User user) throws DaoException {
    batchDelete(ids, user, false);
  }

  public void batchDelete(String ids, User user, boolean isRollback) throws DaoException {
    OrderDao dao = new OrderDao();
    dao.delete(ids);
    if (isRollback)
    {
      OrderProcess orderProcess = new OrderProcess();
      orderProcess.setOperatorId(user.getId());
      orderProcess.setOperatorName(user.getName());
      orderProcess.setProcessTime(new Date());
      orderProcess.setOrderCode(ids);
      orderProcess.setOperateType(OrderProcess.OrderOperate.DELETE);
      OrderProcessService orderProcessService = new OrderProcessService();
      try {
        orderProcessService.insert(orderProcess);
      } catch (RenderException e) {
        e.printStackTrace();
      }
    }
  }

  public void updateStatusFromOrderDetails(long orderId, Order.Status status, User user, OrderProcess.OrderOperate operateType, String remark)
    throws DaoException
  {
    OrderDao dao = new OrderDao();
    long otherStatusOrderDetails = dao.countOtherStatusOrderDetails(orderId, status);
    if (otherStatusOrderDetails > 0L)
    {
      return;
    }
    dao.setStatusOnlyOrder(orderId, status);

    OrderProcess orderProcess = new OrderProcess();
    orderProcess.setOperatorId(user.getId());
    orderProcess.setOperatorName(user.getName());
    orderProcess.setProcessTime(new Date());
    orderProcess.setOrderId(Long.valueOf(orderId));
    orderProcess.setOperateType(operateType);
    orderProcess.setRemark(remark);
    OrderProcessService orderProcessService = new OrderProcessService();
    try {
      orderProcessService.insert(orderProcess);
    } catch (RenderException e) {
      e.printStackTrace();
    }
  }

  public void setAllStatus(long orderId, Order.Status status, User user, String remark)
    throws DaoException
  {
    OrderDao dao = new OrderDao();
    dao.setAllStatus(orderId, status);

    OrderProcess orderProcess = new OrderProcess();
    orderProcess.setOperatorId(user.getId());
    orderProcess.setOperatorName(user.getName());
    orderProcess.setProcessTime(new Date());
    orderProcess.setOrderId(Long.valueOf(orderId));
    orderProcess.setOperateType(OrderUtil.getOrderOperate(status));
    orderProcess.setRemark(remark);
    OrderProcessService orderProcessService = new OrderProcessService();
    try {
      orderProcessService.insert(orderProcess);
    } catch (RenderException e) {
      e.printStackTrace();
    }
  }

  public void deleteOrder(long orderId, User user, String remark)
    throws DaoException
  {
    OrderDao dao = new OrderDao();
    dao.delete(orderId+"");

    OrderProcess orderProcess = new OrderProcess();
    orderProcess.setOperatorId(user.getId());
    orderProcess.setOperatorName(user.getName());
    orderProcess.setProcessTime(new Date());
    orderProcess.setOrderId(Long.valueOf(orderId));
    orderProcess.setOperateType(OrderProcess.OrderOperate.DELETE);
    orderProcess.setRemark(remark);
    OrderProcessService orderProcessService = new OrderProcessService();
    try {
      orderProcessService.insert(orderProcess);
    } catch (RenderException e) {
      e.printStackTrace();
    }
  }

  public void updatePayPrice(long orderId, double payPrice)
    throws DaoException
  {
    OrderDao dao = new OrderDao();
    dao.updatePayPrice(orderId, payPrice);
  }

  public void plusPayPrice(long orderId, double addPayPrice)
    throws DaoException
  {
    OrderDao dao = new OrderDao();
    dao.plusPayPrice(orderId, addPayPrice);
  }

  public void updateTotalPrice(long orderId, double totalPrice)
    throws DaoException
  {
    OrderDao dao = new OrderDao();
    dao.updateTotalPrice(orderId, totalPrice);
  }

  public void updateOrderBaseInfo(String orderName, String orderTelephone, String origin, long orderId, User user)
    throws DaoException
  {
    OrderDao dao = new OrderDao();
    dao.updateOrderBaseInfo(orderName, orderTelephone, origin, orderId, user);
  }

  public Order get(long id) throws DaoException, RenderException
  {
    OrderDao dao = new OrderDao();
    return dao.get(id);
  }

  public List<Order> getList(OrderQ query) throws DaoException, RenderException {
    OrderDao dao = new OrderDao();
    return dao.getList(query);
  }

  public List<Organization> getSupplierList()
    throws DaoException, RenderException
  {
    OrganizationQ orgQuery = new OrganizationQ();
    orgQuery.setRecordPerPage(-1);
    String otherCondition = " where id in (select supplierId from edb_order)";
    orgQuery.setOtherCondition(otherCondition);
    return this.orgService.getList(orgQuery);
  }

  public int setOrderSettlementInfo(Date settlementTime, Long userId, Long orderId)
    throws DaoException
  {
    OrderDao dao = new OrderDao();
    return dao.setOrderSettlementInfo(settlementTime, userId, orderId);
  }

  public void setStatusOnlyOrder(long orderId, Order.Status status) throws DaoException {
    OrderDao dao = new OrderDao();
    dao.setStatusOnlyOrder(orderId, status);
  }
}