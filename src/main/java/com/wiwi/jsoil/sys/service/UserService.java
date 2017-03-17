package com.wiwi.jsoil.sys.service;

import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.sys.dao.UserDao;
import com.wiwi.jsoil.sys.model.User;
import com.wiwi.jsoil.util.MD5Util;
import java.util.List;

public class UserService
{
  public boolean isLoginNameRepeat(String loginName, long id)
    throws DaoException
  {
    UserDao dao = new UserDao();
    return dao.isLoginNameRepeat(loginName, id);
  }

  public void insert(User instance, String defaultPassword) throws DaoException, RenderException {
    instance.setStatus(1);
    UserDao dao = new UserDao();
    dao.insert(instance);
    dao.changePswd(instance.getId(), MD5Util.string2MD5(defaultPassword));
  }

  public void update(User instance) throws DaoException, RenderException {
    User oldUser = get(instance.getId().longValue());

    instance.setStatus(oldUser.getStatus());
    UserDao dao = new UserDao();
    dao.update(instance);
  }

  public void batchDelete(String ids) throws DaoException
  {
    if ((ids == null) || (ids.equalsIgnoreCase("")))
      return;

    UserDao dao = new UserDao();
    dao.delete(ids);
  }

  public void delete(long id) throws DaoException {
    if (id == 0L)
      return;

    UserDao dao = new UserDao();
    dao.delete(id+"");
  }

  public User get(long id) throws DaoException, RenderException {
    UserDao dao = new UserDao();
    return dao.get(id);
  }

  public User getByLoginName(String loginName) throws DaoException, RenderException {
    UserDao dao = new UserDao();
    return dao.getByLoginName(loginName);
  }

  public List<User> getList(PageUtil pageUtil) throws DaoException, RenderException
  {
    UserDao dao = new UserDao();
    return dao.getList(pageUtil);
  }

  public User login(String loginName, String loginPswd) throws DaoException, RenderException
  {
    UserDao dao = new UserDao();
    return dao.login(loginName, MD5Util.string2MD5(loginPswd));
  }

  public User loginByMD5Password(String loginName, String loginPswd)
    throws DaoException, RenderException
  {
    UserDao dao = new UserDao();
    return dao.login(loginName, loginPswd);
  }

  public boolean changePswd(long userId, String oldPswd, String newPswd) throws DaoException
  {
    UserDao dao = new UserDao();
    boolean isOldPswdOk = dao.checkPswd(Long.valueOf(userId), oldPswd);
    if (isOldPswdOk)
      changePswd(userId, newPswd);

    return isOldPswdOk;
  }

  public void changePswd(long userId, String newPswd) throws DaoException {
    UserDao dao = new UserDao();
    dao.changePswd(Long.valueOf(userId), newPswd);
  }

  public boolean checkPswd(long userId, String oldPswd)
  {
    UserDao dao = new UserDao();
    try {
      return dao.checkPswd(Long.valueOf(userId), oldPswd);
    } catch (DaoException e) {
      e.printStackTrace();

      return false; }
  }

  public void setStatus(String ids, String status, String userId) throws DaoException, RenderException {
    UserDao dao = new UserDao();
    dao.setStatus(ids, status, userId);
  }
}