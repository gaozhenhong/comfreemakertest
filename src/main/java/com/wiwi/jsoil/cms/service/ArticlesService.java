package com.wiwi.jsoil.cms.service;

import com.wiwi.jsoil.cms.dao.ArticlesDao;
import com.wiwi.jsoil.cms.model.Articles;
import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.sys.model.User;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ArticlesService
{
  public void insert(Articles instance)
    throws DaoException, RenderException
  {
    ArticlesDao dao = new ArticlesDao();
    dao.insert(instance);
  }

  public void update(Articles instance) throws DaoException, RenderException {
    ArticlesDao dao = new ArticlesDao();
    dao.update(instance);
  }

  public void delete(long id) throws DaoException {
    batchDelete(id+"");
  }

  public void batchDelete(String ids) throws DaoException {
    ArticlesDao dao = new ArticlesDao();
    dao.delete(ids);
  }

  public Articles get(long id) throws DaoException, RenderException {
    ArticlesDao dao = new ArticlesDao();
    return dao.get(id);
  }

  public List<Articles> getList(PageUtil pageUtil) throws DaoException, RenderException {
    ArticlesDao dao = new ArticlesDao();
    return dao.getList(pageUtil);
  }

  public boolean isShowCommitIcon(int status, User user)
  {
    return ((status == 2) || 
      (status == 0));
  }

  public boolean isShowApprovedIcon(int status, User user, String method)
  {
    return ((status == 1) && 
      ("approve".equals(method)));
  }

  public boolean isShowConcelIcon(int status, User user, String method)
  {
    return ((status == 3) && 
      ("approve".equals(method)));
  }

  public boolean isShowEditIcon(int status, User user)
  {
    return ((status != 3) && 
      (status != 4));
  }

  public boolean isShowDeleteIcon(int status, User user)
  {
    return ((status != 3) && 
      (status != 4));
  }
}