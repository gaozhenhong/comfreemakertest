package com.wiwi.edb.order.service;

import com.wiwi.edb.order.dao.OrderPayRecordDao;
import com.wiwi.edb.order.model.OrderPayRecord;
import com.wiwi.edb.order.model.OrderPayRecord.FundType;
import com.wiwi.edb.order.model.OrderPayRecordQ;
import com.wiwi.edb.order.model.OrderProcess;
import com.wiwi.edb.order.model.OrderProcess.OrderOperate;
import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.sys.model.User;
import java.util.Date;
import java.util.List;
import org.json.JSONObject;

public class OrderPayRecordService
{
  public void insert(OrderPayRecord instance, User user)
    throws DaoException, RenderException
  {
    OrderPayRecordDao dao = new OrderPayRecordDao();
    dao.insert(instance);

    OrderProcess orderProcess = new OrderProcess();
    orderProcess.setOperatorId(user.getId());
    orderProcess.setOperatorName(user.getName());
    orderProcess.setProcessTime(new Date());
    orderProcess.setOrderId(instance.getOrderId());
    orderProcess.setOrderDetailsId(instance.getOrderDetailsId());
    OrderProcess.OrderOperate operateType = null;
    if (instance.getFundType() == OrderPayRecord.FundType.DEPOSIT)
      operateType = OrderProcess.OrderOperate.RECEIPT_DEPOSIT;
    else if (instance.getFundType() == OrderPayRecord.FundType.DRAWBACK)
      operateType = OrderProcess.OrderOperate.DRAWBACK_DEPOSIT;
    else
      operateType = OrderProcess.OrderOperate.RECEIPT_FUND;

    orderProcess.setOperateType(operateType);
    orderProcess.setRemark(new JSONObject(instance).toString());
    OrderProcessService orderProcessService = new OrderProcessService();
    try {
      orderProcessService.insert(orderProcess);
    } catch (RenderException e) {
      e.printStackTrace();
    }
  }

  public void delete(long id) throws DaoException {
    batchDelete(id+"");
  }

  public void batchDelete(String ids) throws DaoException {
    OrderPayRecordDao dao = new OrderPayRecordDao();
    dao.delete(ids);
  }

  public OrderPayRecord get(long id) throws DaoException, RenderException {
    OrderPayRecordDao dao = new OrderPayRecordDao();
    return dao.get(id);
  }

  public List<OrderPayRecord> getList(PageUtil pageUtil) throws DaoException, RenderException {
    OrderPayRecordDao dao = new OrderPayRecordDao();
    return dao.getList(pageUtil);
  }

  public OrderPayRecord getDepositByOrder(long orderId, long orderDetailsId)
    throws DaoException, RenderException
  {
    OrderPayRecordQ query = new OrderPayRecordQ();
    query.setRecordPerPage(-1);
    query.setFundType(OrderPayRecord.FundType.DEPOSIT);
    query.setOrderId(Long.valueOf(orderId));
    query.setOrderDetailsId(Long.valueOf(orderDetailsId));
    OrderPayRecordDao dao = new OrderPayRecordDao();
    List list = dao.getList(query);
    return (((list != null) && (list.size() > 0)) ? (OrderPayRecord)list.get(0) : null);
  }
}