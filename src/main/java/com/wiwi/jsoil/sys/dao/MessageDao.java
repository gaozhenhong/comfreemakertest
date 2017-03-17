package com.wiwi.jsoil.sys.dao;

import com.wiwi.jsoil.db.CustomSqlTools;
import com.wiwi.jsoil.db.DaoBase;
import com.wiwi.jsoil.db.DbAdapter;
import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.sys.model.Message;
import com.wiwi.jsoil.sys.model.User;
import java.util.Date;
import java.util.List;

public class MessageDao extends DaoBase
{
  private String sql;

  public MessageDao()
  {
    this.sql = null; }

  public void insert(Message instance) throws DaoException, RenderException {
    DbAdapter.insert2SingleTable(instance, "s_message");
  }

  public void update(Message instance) throws DaoException, RenderException {
    DbAdapter.update2SingleTable(instance, "s_message");
  }

  public void delete(String ids) throws DaoException {
    if (ids.startsWith(","))
      ids = ids.substring(1);

    if (ids.indexOf(",") != -1)
      ids = ids.replaceAll(",", "','");

    if (!(ids.startsWith("'")))
      ids = "'" + ids;

    if (!(ids.endsWith("'")))
      ids = ids + "'";

    this.sql = "update S_MESSAGE set deleteFlag = 1 WHERE id in (" + ids + ") ";
    DbAdapter.executeUpdate(this.sql);
  }

  public long getUnreadNumber(Long userId)
    throws DaoException
  {
    this.sql = "select count(*) from s_message where (deleteFlag is null OR deleteFlag <>1) and (readStatus is null OR readStatus <>1) and userId=? ";
    return DbAdapter.count(this.sql, new Long[] { userId });
  }

  public void markToRead(String ids) throws DaoException
  {
    if ((ids == null) || (ids.length() < 1))
      return;

    if (ids.startsWith(","))
      ids = ids.substring(1);

    if (ids.indexOf(",") != -1)
      ids = ids.replaceAll(",", "','");

    if (!(ids.startsWith("'")))
      ids = "'" + ids;

    if (!(ids.endsWith("'")))
      ids = ids + "'";

    this.sql = "update S_MESSAGE  set readStatus = 1 , readTime = ? WHERE id in (" + ids + ") ";
    DbAdapter.executeUpdate(this.sql, new Object[] { new Date() });
  }

  public void batchAddMessageFromUser(String messageType, String originName, String messageTitle, String link, String message, User sentUser, String otherCondition)
    throws DaoException
  {
    this.sql = CustomSqlTools.getSysCustomSql("SYS_MESSAGE", "BATCH_INSERT_MESSAGE");
    if ((otherCondition != null) && (otherCondition.trim().length() < 0))
      this.sql = this.sql + otherCondition + ";";

    DbAdapter.executeUpdate(this.sql, new Object[] { new Date(), messageType, originName, messageTitle, link, message, sentUser.getId() });
  }

  public Message get(String id) throws DaoException, RenderException {
    this.sql = "select m.* ,u.name as sentPersonName, u.logoId as sentPersonLogoId FROM s_message m, s_user u  WHERE m.sentPersonId = u.id  and m.id ='" + 
      id + "'";
    return ((Message)DbAdapter.get(this.sql, Message.class));
  }

  public List<Message> getList(PageUtil pageUtil)
    throws DaoException, RenderException
  {
    this.sql = "select m.* ,u.name as sentPersonName, u.logoId as sentPersonLogoId FROM s_message m, s_user u  WHERE m.sentPersonId = u.id ";

    return DbAdapter.getList(this.sql, pageUtil, Message.class);
  }
}