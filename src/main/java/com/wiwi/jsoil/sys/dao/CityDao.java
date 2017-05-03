package com.wiwi.jsoil.sys.dao;

import com.wiwi.jsoil.db.DaoBase;
import com.wiwi.jsoil.db.DbAdapter;
import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.db.Transaction;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.sys.model.City;
import com.wiwi.jsoil.sys.model.CityQ;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CityDao
  extends DaoBase
{
  private static Logger log =  LoggerFactory.getLogger(CityDao.class);
  private String sql = null;
  private Connection conn = null;
  private PreparedStatement ps = null;
  private ResultSet rs = null;
  
  /* Error */
  public void insert(City instance)
    throws DaoException, RenderException
  {
	  DbAdapter.insert2SingleTable(instance, "S_City"); 
  }
  
  public void update(City instance)
    throws DaoException, RenderException
  {
    DbAdapter.update2SingleTable(instance, "S_City");
  }
  
  public List getList(PageUtil query)
    throws DaoException, RenderException
  {
    this.sql = "select d.* from S_City d ";
    
    return DbAdapter.getList(this.sql, query, City.class);
  }
  
  public void delete(String ids)
    throws DaoException
  {
    CityQ query = new CityQ();
    query.setParentIds(ids);
    List list = null;
    try
    {
      list = getList(query);
    }
    catch (Exception e)
    {
      list = null;
    }
    if ((list == null) || (list.size() < 1))
    {
      Transaction tran = new Transaction();
      if (ids.startsWith(",")) {
        ids = ids.substring(1);
      }
      if (ids.endsWith(",")) {
        ids = ids.substring(0, ids.length() - 1);
      }
      if (ids.indexOf(",") != -1) {
        ids = ids.replaceAll(",", "','");
      }
      if (!ids.startsWith("'")) {
        ids = "'" + ids;
      }
      if (!ids.endsWith("'")) {
        ids = ids + "'";
      }
      this.sql = ("delete From S_City where id in(" + ids + ")");
      tran.executeUpdate(this.sql);
      tran.commit();
    }
    else
    {
      String tempIds = "";
      City temp = null;
      for (int i = 0; i < list.size(); i++)
      {
        temp = (City)list.get(i);
        tempIds = temp.getId() + ",";
      }
      if (tempIds.endsWith(",")) {
        tempIds = tempIds.substring(0, tempIds.length() - 1);
      }
      delete(tempIds);
    }
  }
  
  public List getList(int parentId)
    throws DaoException
  {
    List<City> list = new ArrayList();
    try
    {
      this.conn = getConn(true);
      this.sql = ("SELECT * FROM S_City where parentId ='" + parentId + "' ORDER BY abbreviation ASC ");
      this.ps = this.conn.prepareStatement(this.sql);
      this.rs = this.ps.executeQuery();
      while (this.rs.next())
      {
        City city = new City();
        city.setId(this.rs.getLong("id"));
        city.setCode(this.rs.getString("code"));
        city.setAbbreviation(this.rs.getString("abbreviation"));
        city.setName(this.rs.getString("name"));
        city.setDemo(this.rs.getString("demo"));
        city.setParentId(this.rs.getLong("parentId"));
        list.add(city);
      }
      return list;
    }
    catch (Exception e)
    {
      log.error("获取cityList对象列表时出错.", e);
      throw new DaoException(e);
    }
    finally
    {
      closeRs(this.rs);
      closeStmt(this.ps);
      closeConn(this.conn);
    }
  }
  
  public List getList(int parentId, int notInId)
    throws DaoException
  {
    List<City> list = new ArrayList();
    try
    {
      this.conn = getConn(true);
      this.sql = ("SELECT * FROM S_City where parentId ='" + parentId + "' and id<>" + notInId + " ORDER BY abbreviation ASC ");
      this.ps = this.conn.prepareStatement(this.sql);
      this.rs = this.ps.executeQuery();
      while (this.rs.next())
      {
        City city = new City();
        city.setId(this.rs.getLong("id"));
        city.setCode(this.rs.getString("code"));
        city.setAbbreviation(this.rs.getString("abbreviation"));
        city.setName(this.rs.getString("name"));
        city.setDemo(this.rs.getString("demo"));
        city.setParentId(this.rs.getLong("parentId"));
        list.add(city);
      }
      return list;
    }
    catch (Exception e)
    {
      log.error("获取cityList对象列表时出错.", e);
      throw new DaoException(e);
    }
    finally
    {
      closeRs(this.rs);
      closeStmt(this.ps);
      closeConn(this.conn);
    }
  }
  
  public List getList()
    throws DaoException
  {
    List<City> list = new ArrayList();
    try
    {
      this.conn = getConn(true);
      this.sql = "SELECT * FROM S_City ORDER BY code,parentId,abbreviation ";
      this.ps = this.conn.prepareStatement(this.sql);
      this.rs = this.ps.executeQuery();
      while (this.rs.next())
      {
        City city = new City();
        city.setId(this.rs.getLong("id"));
        city.setCode(this.rs.getString("code"));
        city.setAbbreviation(this.rs.getString("abbreviation"));
        city.setName(this.rs.getString("name"));
        city.setDemo(this.rs.getString("demo"));
        city.setParentId(this.rs.getLong("parentId"));
        list.add(city);
      }
      return list;
    }
    catch (Exception e)
    {
      log.error("获取cityList对象列表时出错.", e);
      throw new DaoException(e);
    }
    finally
    {
      closeRs(this.rs);
      closeStmt(this.ps);
      closeConn(this.conn);
    }
  }
  
  public City get(int cityId)
    throws DaoException
  {
    try
    {
      this.conn = getConn(true);
      City city = null;
      this.sql = "SELECT * FROM S_City where id=? ";
      this.ps = this.conn.prepareStatement(this.sql);
      this.ps.setInt(1, cityId);
      this.rs = this.ps.executeQuery();
      if (this.rs.next())
      {
        city = new City();
        city.setId(this.rs.getLong("id"));
        city.setCode(this.rs.getString("code"));
        city.setAbbreviation(this.rs.getString("abbreviation"));
        city.setName(this.rs.getString("name"));
        city.setDemo(this.rs.getString("demo"));
        city.setParentId(this.rs.getLong("parentId"));
      }
      return city;
    }
    catch (Exception e)
    {
      log.error("获取cityList对象列表时出错.", e);
      throw new DaoException(e);
    }
    finally
    {
      closeRs(this.rs);
      closeStmt(this.ps);
      closeConn(this.conn);
    }
  }
  
  public City getCityByName(String city)
    throws DaoException, RenderException
  {
    this.sql = ("SELECT * FROM S_City where name ='" + city + "'");
    return (City)DbAdapter.get(this.sql, City.class);
  }
  
  public City getCityByCode(String code)
    throws DaoException, RenderException
  {
    this.sql = ("SELECT * FROM S_City where code ='" + code + "'");
    return (City)DbAdapter.get(this.sql, City.class);
  }
}
