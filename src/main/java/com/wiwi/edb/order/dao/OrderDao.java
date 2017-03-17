package com.wiwi.edb.order.dao;

import com.wiwi.edb.order.hotelOrder.HotelOrderUtil;
import com.wiwi.edb.order.hotelOrder.model.HotelOrder;
import com.wiwi.edb.order.model.Order;
import com.wiwi.edb.order.model.Order.Status;
import com.wiwi.jsoil.db.DaoBase;
import com.wiwi.jsoil.db.DbAdapter;
import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.db.Transaction;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.sys.model.User;
import com.wiwi.jsoil.util.SqlUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.json.JSONArray;

public class OrderDao extends DaoBase
{
  private String sql;

  public OrderDao()
  {
    this.sql = null; }

  public void insert(Order instance) throws DaoException, RenderException {
    DbAdapter.insert2SingleTable(instance, "edb_order");
  }

  public void update(Order instance) throws DaoException, RenderException {
    DbAdapter.update2SingleTable(instance, "edb_order");
  }

  public void setStatusOnlyOrder(long orderId, Order.Status status)
    throws DaoException
  {
    if (status == null)
      return;
    if ((status == Order.Status.CHECKOUT) || (status == Order.Status.ON_TIME_CANCEL)) {
      this.sql = "UPDATE EDB_ORDER set status=?,finishTime=? where id=?";
      DbAdapter.executeUpdate(this.sql, new Object[] { status.name(), new Date(), Long.valueOf(orderId) });
    } else {
      this.sql = "UPDATE EDB_ORDER set status=? where id=?";
      DbAdapter.executeUpdate(this.sql, new Object[] { status.name(), Long.valueOf(orderId) });
    }
  }

  public int setOrderSettlementInfo(Date settlementTime, Long userId, Long orderId)
    throws DaoException
  {
    if ((settlementTime == null) || (userId == null) || (orderId == null))
      return 0;
    this.sql = "UPDATE EDB_ORDER  set isSettlement=1,settlementTime = ?,settlementUserId=?  where id=?";
    DbAdapter.executeUpdate(this.sql, new Object[] { settlementTime, userId, orderId });
    return 1;
  }

  public void setAllStatus(long orderId, Order.Status status)
    throws DaoException
  {
    if ((status == null) || (orderId == 0L))
      return;

    Transaction tran = new Transaction();
    this.sql = "UPDATE EDB_ORDER_DETAILS set status='" + status + "' where orderId = '" + orderId + "'";
    tran.executeUpdate(this.sql);

    this.sql = "UPDATE FG_RESERVATION_ROOM set status='" + status + "' where orderId = '" + orderId + "'";
    tran.executeUpdate(this.sql);

    if ((status == Order.Status.CHECKOUT) || (status == Order.Status.ON_TIME_CANCEL))
      this.sql = "UPDATE EDB_ORDER set status='" + status + "',finishTime=now() where id = '" + orderId + "'";
    else
      this.sql = "UPDATE EDB_ORDER set status='" + status + "' where id = '" + orderId + "'";

    tran.executeUpdate(this.sql);
    tran.commit();
  }

  public long countOtherStatusOrderDetails(long orderId, Order.Status status)
    throws DaoException
  {
    this.sql = "select count(*) from EDB_ORDER_DETAILS where orderId = ? and status <> ?";
    return DbAdapter.count(this.sql, new Object[] { Long.valueOf(orderId), status.name() });
  }

  public void updatePayPrice(long orderId, double payPrice)
    throws DaoException
  {
    this.sql = "UPDATE EDB_ORDER set payPrice=?,payStatus= if( ?>totalPrice,'EXCESS', \t\tif(?=totalPrice,'PAY_IN_FULL', \t\t\tif(?=0,'NO_PAYMENT','DEBT') \t\t) ) where id=?";

    DbAdapter.executeUpdate(this.sql, new Object[] { Double.valueOf(payPrice), Double.valueOf(payPrice), Double.valueOf(payPrice), Double.valueOf(payPrice), Long.valueOf(orderId) });
  }

  public void plusPayPrice(long orderId, double addPayPrice)
    throws DaoException
  {
    if (addPayPrice == 0D) return;
    this.sql = "UPDATE EDB_ORDER set payPrice= payPrice + ?,payStatus= if( (payPrice + ?)>totalPrice,'EXCESS', \t\tif((payPrice + ?)=totalPrice,'PAY_IN_FULL', \t\t\tif((payPrice + ?)=0,'NO_PAYMENT','DEBT') \t\t) ) where id=?";

    DbAdapter.executeUpdate(this.sql, new Object[] { Double.valueOf(addPayPrice), Double.valueOf(addPayPrice), Double.valueOf(addPayPrice), Double.valueOf(addPayPrice), Long.valueOf(orderId) });
  }

  public void updateTotalPrice(long orderId, double totalPrice)
    throws DaoException
  {
    this.sql = "UPDATE EDB_ORDER set totalPrice=?,payStatus= if( payPrice>?,'EXCESS', \t\tif(payPrice=?,'PAY_IN_FULL', \t\t\tif(payPrice=0,'NO_PAYMENT','DEBT') \t\t) ) where id=?";

    DbAdapter.executeUpdate(this.sql, new Object[] { Double.valueOf(totalPrice), Double.valueOf(totalPrice), Double.valueOf(totalPrice), Long.valueOf(orderId) });
  }

  public void updateOrderBaseInfo(String orderName, String orderTelephone, String origin, long orderId, User user)
    throws DaoException
  {
    this.sql = "UPDATE EDB_ORDER set orderName=?,orderTelephone=?,origin=?,lastModifyUserId=? ,lastModifyTime=now() where id=?";
    DbAdapter.executeUpdate(this.sql, new Object[] { orderName, orderTelephone, origin, Long.valueOf(user.getId().longValue()), Long.valueOf(orderId) });
  }

  public void delete(String ids) throws DaoException
  {
    if (ids.startsWith(","))
      ids = ids.substring(1);

    if (ids.indexOf(",") != -1)
      ids = ids.replaceAll(",", "','");

    if (!(ids.startsWith("'")))
      ids = "'" + ids;

    if (!(ids.endsWith("'"))) {
      ids = ids + "'";
    }

    Transaction tran = new Transaction();

    this.sql = "DELETE from FG_RESERVATION_ROOM  where orderId in (" + ids + ") ";
    tran.executeUpdate(this.sql);

    this.sql = "DELETE from EDB_ORDER_DETAILS  where orderId in (" + ids + ") ";
    tran.executeUpdate(this.sql);

    this.sql = "DELETE from EDB_ORDER  WHERE id in (" + ids + ") ";
    tran.executeUpdate(this.sql);
    tran.commit();
  }

  public Order get(long id) throws DaoException, RenderException {
    this.sql = "select o.*, org.name as companyName FROM edb_order o, s_organization org WHERE o.companyId = org.id and o.id ='" + id + "'";
    return ((Order)DbAdapter.get(this.sql, Order.class));
  }

  public List<Order> getList(PageUtil pageUtil) throws DaoException, RenderException
  {
    this.sql = "select o.*, org.name as companyName FROM edb_order o, s_organization org WHERE o.companyId = org.id ";
    return DbAdapter.getList(this.sql, pageUtil, Order.class);
  }

  public JSONArray getCanSettlementSupplierList(long selfCompanyId, Date beginDate, Date endDate)
    throws DaoException, RenderException
  {
    String statusInSql = SqlUtil.getInSqlStr(HotelOrderUtil.getCanSettlementStatus());
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    this.sql = "select o.companyId,org.name as companyName,count(*) as orderNumber,sum(totalPrice)as sales ,sum(payPrice) as returnAmount from edb_order o ,s_organization org where o.companyId = org.id and  (o.deleteFlag is null OR o.deleteFlag<>1) and  o.isSettlement=0  and o.origin='" + 
      HotelOrder.ORDER_ORIGIN_FREEGO + "'" + 
      " and o.status in (" + statusInSql + ")";
    if ((beginDate != null) && (endDate != null))
      this.sql = this.sql + " and date(o.finishTime) between '" + sdf.format(beginDate) + "' and '" + sdf.format(endDate) + "'";

    this.sql = this.sql + " and o.originDetails not in (select code from fg_hotel where companyId = '" + selfCompanyId + "' )" + " group by o.supplierId";
    return DbAdapter.getJSONArray(this.sql);
  }

  public JSONArray getSettlementInfoList(Integer companyId, Long supplierId, Date beginDate, Date endDate)
    throws DaoException, RenderException
  {
    String statusInSql = SqlUtil.getInSqlStr(HotelOrderUtil.getCanSettlementStatus());
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    this.sql = "select o.companyId,org.name as companyName,count(*) as orderNumber,sum(totalPrice)as sales ,sum(payPrice) as returnAmount from edb_order o ,s_organization org where o.companyId = org.id and (o.deleteFlag is null OR o.deleteFlag<>1) and o.isSettlement=0  and o.origin='" + 
      HotelOrder.ORDER_ORIGIN_FREEGO + "'" + 
      " and o.status in (" + statusInSql + ")";
    if ((companyId != null) && (companyId.intValue() != 0))
      this.sql = this.sql + "and o.companyId ='" + companyId + "'";

    if ((supplierId != null) && (supplierId.longValue() != 0L))
      this.sql = this.sql + "and o.supplierId ='" + supplierId + "'";

    if ((beginDate != null) && (endDate != null))
      this.sql = this.sql + " and date(o.finishTime) between '" + sdf.format(beginDate) + "' and '" + sdf.format(endDate) + "'";

    this.sql = this.sql + " group by o.supplierId";
    return DbAdapter.getJSONArray(this.sql);
  }
}