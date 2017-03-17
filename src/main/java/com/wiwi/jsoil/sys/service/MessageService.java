package com.wiwi.jsoil.sys.service;

import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.sys.dao.MessageDao;
import com.wiwi.jsoil.sys.model.Message;
import com.wiwi.jsoil.sys.model.User;
import java.util.List;

public class MessageService
{
  public void insert(Message instance)
    throws DaoException, RenderException
  {
    MessageDao dao = new MessageDao();
    dao.insert(instance);
  }

  public void update(Message instance) throws DaoException, RenderException {
    MessageDao dao = new MessageDao();
    dao.update(instance);
  }

  public Long getUnreadNumber(Long userId)
    throws DaoException
  {
    MessageDao dao = new MessageDao();
    return Long.valueOf(dao.getUnreadNumber(userId)); }

  public void delete(String id) throws DaoException {
    batchDelete(id);
  }

  public void batchDelete(String ids) throws DaoException {
    MessageDao dao = new MessageDao();
    dao.delete(ids);
  }

  public void markToRead(String ids) throws DaoException {
    MessageDao dao = new MessageDao();
    dao.markToRead(ids);
  }

  public Message get(String id) throws DaoException, RenderException {
    MessageDao dao = new MessageDao();
    return dao.get(id);
  }

  public List<Message> getList(PageUtil pageUtil) throws DaoException, RenderException {
    MessageDao dao = new MessageDao();
    return dao.getList(pageUtil);
  }

  public void batchAddMessageFromUser(String messageType, String originName, String messageTitle, String link, String message, User sentUser, String otherCondition)
    throws DaoException
  {
    MessageDao dao = new MessageDao();
    dao.batchAddMessageFromUser(messageType, originName, messageTitle, link, message, sentUser, otherCondition);
  }
}