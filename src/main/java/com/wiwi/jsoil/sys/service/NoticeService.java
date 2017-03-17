package com.wiwi.jsoil.sys.service;

import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.sys.dao.NoticeDao;
import com.wiwi.jsoil.sys.model.Notice;
import java.util.List;

public class NoticeService
{
  public void insert(Notice instance)
    throws DaoException, RenderException
  {
    NoticeDao dao = new NoticeDao();
    dao.insert(instance);
  }

  public void update(Notice instance) throws DaoException, RenderException {
    NoticeDao dao = new NoticeDao();
    dao.update(instance);
  }

  public void delete(long id) throws DaoException {
    batchDelete(id+"");
  }

  public void batchDelete(String ids) throws DaoException {
    NoticeDao dao = new NoticeDao();
    dao.delete(ids);
  }

  public Notice get(long id) throws DaoException, RenderException {
    NoticeDao dao = new NoticeDao();
    return dao.get(id);
  }

  public List<Notice> getList(PageUtil pageUtil) throws DaoException, RenderException {
    NoticeDao dao = new NoticeDao();
    return dao.getList(pageUtil);
  }
}