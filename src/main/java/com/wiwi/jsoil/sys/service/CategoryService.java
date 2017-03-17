package com.wiwi.jsoil.sys.service;

import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.sys.dao.CategoryDao;
import com.wiwi.jsoil.sys.model.Category;
import com.wiwi.jsoil.sys.model.CategoryQ;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryService
{
  public void insert(Category instance)
    throws DaoException, RenderException
  {
    CategoryDao dao = new CategoryDao();
    dao.insert(instance);
  }
  
  public void update(Category instance)
    throws DaoException, RenderException
  {
    CategoryDao dao = new CategoryDao();
    dao.update(instance);
  }
  
  public void delete(long id)
    throws DaoException
  {
    batchDelete(id+"");
  }
  
  public void batchDelete(String ids)
    throws DaoException
  {
    CategoryDao dao = new CategoryDao();
    dao.delete(ids);
  }
  
  public Category get(long id)
    throws DaoException, RenderException
  {
    CategoryDao dao = new CategoryDao();
    return dao.get(id);
  }
  
  public Category getByCode(String code)
    throws DaoException, RenderException
  {
    CategoryDao dao = new CategoryDao();
    return dao.getByCode(code);
  }
  
  public List<Category> getList(PageUtil pageUtil)
    throws DaoException, RenderException
  {
    CategoryDao dao = new CategoryDao();
    return dao.getList(pageUtil);
  }
  
  public List<Category> getListByCode(String code)
    throws DaoException, RenderException
  {
    CategoryDao dao = new CategoryDao();
    CategoryQ query = new CategoryQ();
    query.setTopCode(code);
    query.setOrderByKind("categoryIndex");
    query.setOrderByKind("ASC");
    query.setOrderByKind1("code");
    query.setOrderByKind1("DESC");
    return dao.getList(query);
  }
  
  public List<Category> getCategoryTreeList(String topCode)
    throws DaoException, RenderException
  {
    CategoryService serverice = new CategoryService();
    List<Category> topCategoryList = new ArrayList();
    Map<Long, List<Category>> subCategoryMap = new HashMap();
    List<Category> allCategoryList = serverice.getListByCode(topCode);
    for (Category category : allCategoryList)
    {
      subCategoryMap.put(category.getId(), new ArrayList());
      if (category.getParentCategory().getCode().equals(topCode))
      {
        topCategoryList.add(category);
      }
      else
      {
        if (subCategoryMap.get(category.getParentCategory().getId()) == null) {
          subCategoryMap.put(category.getParentCategory().getId(), new ArrayList());
        }
        ((List)subCategoryMap.get(category.getParentCategory().getId())).add(category);
      }
    }
    for (Category module : allCategoryList) {
      module.setSubCategoryList((List)subCategoryMap.get(module.getId()));
    }
    return topCategoryList;
  }
}
