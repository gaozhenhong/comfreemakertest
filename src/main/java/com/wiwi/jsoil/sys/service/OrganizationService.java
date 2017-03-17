package com.wiwi.jsoil.sys.service;

import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.exception.RepeatException;
import com.wiwi.jsoil.sys.dao.OrganizationDao;
import com.wiwi.jsoil.sys.model.Organization;
import com.wiwi.jsoil.sys.model.OrganizationQ;
import com.wiwi.jsoil.util.ToolsUtils;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class OrganizationService
{
  public static int code_length = 4;
  OrganizationDao dao = new OrganizationDao();
  
  public void insert(Organization instance)
    throws RepeatException, DaoException, RenderException
  {
    if (isNameRepeat(instance.getName().trim(), instance.getParentOrg().getId().intValue(), 0)) {
      throw new RepeatException("机构名称重复！该父结点下已经存在该名称的机构！");
    }
    if ((instance.getCode() == null) || ("".equals(instance.getCode()))) {
      instance.setCode(genOrgCode(instance.getParentOrg().getId().intValue()));
    }
    this.dao.insert(instance);
  }
  
  public void update(Organization instance)
    throws RepeatException, DaoException, RenderException
  {
    if (isNameRepeat(instance.getName().trim(), instance.getParentOrg().getId().intValue(), instance.getId().intValue())) {
      throw new RepeatException("机构名称重复！该父结点下已经存在该名称的机构！");
    }
    this.dao.update(instance);
  }
  
  public void updateSubOrgCode(String oldCode, String newCode)
    throws DaoException, RenderException
  {
    this.dao.updateSubOrgCode(oldCode, newCode);
  }
  
  public void delete(int id)
    throws DaoException
  {
    this.dao.delete(id+"");
  }
  
  public void batchDelete(String ids)
    throws DaoException
  {
    if (ids.startsWith(",")) {
      ids = ids.substring(1);
    }
    this.dao.delete(ids);
  }
  
  public Organization get(int id)
    throws DaoException, RenderException
  {
    return this.dao.get(id);
  }
  
  public List<Organization> getList(PageUtil pageUtil)
    throws DaoException, RenderException
  {
    OrganizationDao dao = new OrganizationDao();
    return dao.getList(pageUtil);
  }
  
  public List<Organization> getList(OrganizationQ query)
    throws DaoException, RenderException
  {
    return this.dao.getList(query);
  }
  
  public boolean isNameRepeat(String name, int parentId, int selfId)
    throws NumberFormatException, DaoException
  {
    return this.dao.isNameRepeat(name, parentId, selfId);
  }
  
  public String genOrgCode(int parentId)
  {
    Organization parentDept = null;
    String parentCode = "";
    if (parentId != 0) {
      try
      {
        parentDept = get(parentId);
        parentCode = parentDept.getCode();
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
      OrganizationQ dq = new OrganizationQ();
      dq.setCode(likeCode);
      dq.setOrderByKind("desc");
      dq.setOrderByProperty("code");
      List<Organization> list = getList(dq);
      if ((list == null) || (list.size() == 0)) {
        return parentCode + ToolsUtils.appendZero(1L, code_length);
      }
      String bigCode = ((Organization)list.get(0)).getCode();
      String endCode = bigCode.substring(parentCode.length());
      return parentCode + ToolsUtils.appendZero(new Integer(endCode).intValue() + 1, code_length);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return parentCode + ToolsUtils.appendZero(1L, code_length);
  }
  
  public Organization getCompany(int orgId)
    throws DaoException, RenderException
  {
    return this.dao.getCompany(orgId);
  }
}
