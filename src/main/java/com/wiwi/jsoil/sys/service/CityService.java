package com.wiwi.jsoil.sys.service;

import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.exception.RepeatException;
import com.wiwi.jsoil.sys.dao.CityDao;
import com.wiwi.jsoil.sys.model.City;
import com.wiwi.jsoil.sys.model.CityQ;
import com.wiwi.jsoil.util.ToolsUtils;
import java.util.List;

public class CityService
{
  public static int code_length = 3;
  
  public List getList(PageUtil pageUtil)
    throws DaoException, RenderException
  {
    CityDao dao = new CityDao();
    return dao.getList(pageUtil);
  }
  
  public List getList(int parentId)
    throws DaoException
  {
    CityDao dao = new CityDao();
    return dao.getList(parentId);
  }
  
  public List getList(int parentId, int notInId)
    throws DaoException
  {
    CityDao dao = new CityDao();
    return dao.getList(parentId, notInId);
  }
  
  public List getList()
    throws DaoException
  {
    CityDao dao = new CityDao();
    return dao.getList();
  }
  
  public City get(int cityId)
    throws DaoException
  {
    CityDao dao = new CityDao();
    return dao.get(cityId);
  }
  
  public void insert(City instance)
    throws DaoException, RenderException, RepeatException
  {
    CityDao dao = new CityDao();
    if ((instance.getCode() == null) || ("".equals(instance.getCode()))) {
      instance.setCode(geCityCode(instance.getParentId().intValue()));
    }
    dao.insert(instance);
  }
  
  public void update(City instance)
    throws DaoException, RenderException, RepeatException
  {
    CityDao dao = new CityDao();
    dao.update(instance);
  }
  
  public void delete(String ids)
    throws DaoException
  {
    CityDao dao = new CityDao();
    dao.delete(ids);
  }
  
  private String geCityCode(int parentId)
  {
    City parentCity = null;
    String parentCode = "";
    if (parentId != 0) {
      try
      {
        parentCity = get(parentId);
        parentCode = parentCity.getCode();
      }
      catch (Exception e)
      {
        parentCode = "";
      }
    } else {
      parentCode = "";
    }
    try
    {
      String likeCode = parentCode;
      for (int i = 0; i < code_length; i++) {
        likeCode = likeCode + "_";
      }
      CityQ dq = new CityQ();
      dq.setCode(likeCode);
      dq.setOrderByKind("desc");
      dq.setOrderByProperty("code");
      List list = getList(dq);
      if ((list == null) || (list.size() == 0)) {
        return parentCode + ToolsUtils.appendZero(1L, code_length);
      }
      String bigCode = ((City)list.get(0)).getCode();
      String endCode = bigCode.substring(parentCode.length());
      return parentCode + ToolsUtils.appendZero(new Integer(endCode).intValue() + 1, code_length);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return parentCode + ToolsUtils.appendZero(1L, code_length);
  }
  
  public City getCityByName(String city)
    throws DaoException, RenderException
  {
    CityDao dao = new CityDao();
    return dao.getCityByName(city);
  }
  
  public City getCityByCode(String code)
    throws DaoException, RenderException
  {
    CityDao dao = new CityDao();
    return dao.getCityByCode(code);
  }
}
