package com.wiwi.jsoil.sys.dao;

import com.wiwi.jsoil.db.DaoBase;
import com.wiwi.jsoil.db.DbAdapter;
import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.sys.model.Organization;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class OrganizationDao
  extends DaoBase
{
  private String sql = null;
  
  public void insert(Organization instance)
    throws DaoException, RenderException
  {
    DbAdapter.insert2SingleTable(instance, "s_organization");
  }
  
  public void update(Organization instance)
    throws DaoException, RenderException
  {
    DbAdapter.update2SingleTable(instance, "s_organization");
  }
  
  public void updateSubOrgCode(String oldCode, String newCode)
    throws DaoException, RenderException
  {
    this.sql = 
      (" update s_organization set code = concat('" + newCode + "',substring(code,length('" + oldCode + "')+1) )" + " where length(code) > length('" + oldCode + "')  and code like '" + oldCode + "%' ");
    DbAdapter.executeUpdate(this.sql);
  }
  
  public void delete(String ids)
    throws DaoException
  {
    if (ids.startsWith(",")) {
      ids = ids.substring(1);
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
    this.sql = ("DELETE FROM s_organization WHERE id in (" + ids + ") ");
    DbAdapter.executeUpdate(this.sql);
  }
  
  public boolean isNameRepeat(String name, int parentId, int selfId)
    throws DaoException
  {
    this.sql = " SELECT id FROM s_organization WHERE name=? AND parentOrgId =  ? AND id<>?";
    return DbAdapter.havaRecord(this.sql, new Object[] { name, Integer.valueOf(parentId), Integer.valueOf(selfId) });
  }
  
  public Organization get(int id)
    throws DaoException, RenderException
  {
    this.sql = 
    
      ("select m.*,c.name as typeName,p.name as parentOrgName,p.code as parentOrgCode   FROM s_organization m,s_category c ,s_organization p WHERE m.typeId = c.id and m.parentOrgId = p.id and m.id ='" + id + "'");
    return (Organization)DbAdapter.get(this.sql, Organization.class);
  }
  
  public Organization getCompany(int orgId)
    throws DaoException, RenderException
  {
    this.sql = 
    
      ("select m.*,c.name as typeName,subOrg.name as parentOrgName,subOrg.code as parentOrgCode   FROM s_organization m,s_category c,s_organization subOrg WHERE m.typeId = c.id and m.code  =substring(subOrg.code,1,4) and subOrg.id='" + orgId + "'");
    return (Organization)DbAdapter.get(this.sql, Organization.class);
  }
  
  public List<Organization> getList(PageUtil pageUtil)
    throws DaoException, RenderException
  {
    this.sql = "select m.*,c.name as typeName,p.name as parentOrgName,p.code as parentOrgCode   FROM s_organization m,s_category c ,s_organization p WHERE m.typeId = c.id and m.parentOrgId = p.id ";
    
    return DbAdapter.getList(this.sql, pageUtil, Organization.class);
  }
}
