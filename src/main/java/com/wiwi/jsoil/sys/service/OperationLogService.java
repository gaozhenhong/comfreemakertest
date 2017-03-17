package com.wiwi.jsoil.sys.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.sys.dao.OperationLogDao;
import com.wiwi.jsoil.sys.model.OperationLog;
import com.wiwi.jsoil.sys.model.User;

public class OperationLogService
{
  private static final Logger logger = LoggerFactory.getLogger(OperationLogService.class);
  
  public void insert(OperationLog instance)
    throws DaoException, RenderException
  {
    OperationLogDao dao = new OperationLogDao();
    dao.insert(instance);
  }
  
  public void update(OperationLog instance)
    throws DaoException, RenderException
  {
    OperationLogDao dao = new OperationLogDao();
    dao.update(instance);
  }
  
  public void delete(String id)
    throws DaoException
  {
    batchDelete(id);
  }
  
  public void batchDelete(String ids)
    throws DaoException
  {
    OperationLogDao dao = new OperationLogDao();
    dao.delete(ids);
  }
  
  public OperationLog get(String id)
    throws DaoException, RenderException
  {
    OperationLogDao dao = new OperationLogDao();
    return dao.get(id);
  }
  
  public List<OperationLog> getList(PageUtil pageUtil)
    throws DaoException, RenderException
  {
    OperationLogDao dao = new OperationLogDao();
    return dao.getList(pageUtil);
  }
  
  public static boolean logOperateLog(HttpServletRequest request, User user)
  {
    OperationLog instance = new OperationLog();
    
    String ip = request.getHeader("x-forwarded-for");
    if (ip == null) {
      ip = request.getRemoteAddr();
    }
    instance.setIp(ip);
    instance.setUrl(request.getRequestURL().toString());
    instance.setOperateTime(new Date());
    instance.setParameter(request.getParameterMap().toString());
    instance.setUserId(user.getId());
    instance.setUserName(user.getName());
    instance.setUserOrgId(user.getOrg().getId());
    instance.setUserOrgName(user.getOrg().getName());
    String uri = request.getRequestURI();
    instance.setOperateType(uri.substring(uri.lastIndexOf("/")));
    instance.setBrowser(request.getHeader("User-Agent"));
    OperationLogDao dao = new OperationLogDao();
    try
    {
      dao.insert(instance);
    }
    catch (DaoException|RenderException e)
    {
      e.printStackTrace();
      logger.error("记录操作日志失败，错误原因:{}", e.getMessage(), e);
      return false;
    }
    return true;
  }
}
