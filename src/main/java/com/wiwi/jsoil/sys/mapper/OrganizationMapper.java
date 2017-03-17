package com.wiwi.jsoil.sys.mapper;

import java.util.List;

import com.wiwi.jsoil.sys.model.Organization;
import com.wiwi.jsoil.sys.model.OrganizationQ;
import org.apache.ibatis.annotations.Param;

public abstract interface OrganizationMapper
{
  public abstract void insert(Organization paramOrganization);

  public abstract void update(Organization paramOrganization);

  public abstract void updateSubOrgCode(@Param("oldCode") String paramString1, @Param("newCode") String paramString2);

  public abstract void delete(@Param("id") int paramInt);

  public abstract void batchDelete(@Param("ids") String paramString);

  public abstract Organization get(@Param("id") int paramInt);

  public abstract List<Organization> getList(@Param("query") OrganizationQ paramOrganizationQ);

  public abstract Organization isNameRepeat(@Param("name") String paramString, @Param("parentId") int paramInt1, @Param("selfId") int paramInt2);

  public abstract Organization getCompany(@Param("orgId") int paramInt);
}