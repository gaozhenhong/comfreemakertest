package com.wiwi.jsoil.sys.service;

import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.sys.dao.LoginLogDao;
import com.wiwi.jsoil.sys.model.LoginLog;
import java.util.List;

public class LoginLogService
{
  public void add(LoginLog loginLog)
    throws Exception
  {
    LoginLogDao loginInfoDao = new LoginLogDao();
    loginInfoDao.add(loginLog);
  }
  
  public List<LoginLog> getList(PageUtil pageUtil)
    throws Exception
  {
    LoginLogDao loginInfoDao = new LoginLogDao();
    return loginInfoDao.getList(pageUtil);
  }
  
  public void delete(String ids)
    throws Exception
  {
    LoginLogDao loginInfoDao = new LoginLogDao();
    loginInfoDao.delete(ids);
  }
}
