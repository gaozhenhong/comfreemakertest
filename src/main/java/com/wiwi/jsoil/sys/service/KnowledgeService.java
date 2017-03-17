package com.wiwi.jsoil.sys.service;

import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.sys.dao.KnowledgeDao;
import com.wiwi.jsoil.sys.model.Knowledge;
import java.util.List;

public class KnowledgeService
{
  public void insert(Knowledge instance)
    throws DaoException, RenderException
  {
    KnowledgeDao dao = new KnowledgeDao();
    dao.insert(instance);
  }
  
  public void update(Knowledge instance)
    throws DaoException, RenderException
  {
    KnowledgeDao dao = new KnowledgeDao();
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
    KnowledgeDao dao = new KnowledgeDao();
    dao.delete(ids);
  }
  
  public Knowledge get(long id)
    throws DaoException, RenderException
  {
    KnowledgeDao dao = new KnowledgeDao();
    return dao.get(id);
  }
  
  public List<Knowledge> getList(PageUtil pageUtil)
    throws DaoException, RenderException
  {
    KnowledgeDao dao = new KnowledgeDao();
    return dao.getList(pageUtil);
  }
  
  public void addDownloadCount(long id)
    throws DaoException, RenderException
  {
    KnowledgeDao dao = new KnowledgeDao();
    dao.addDownloadCount(id);
  }
  
  public void addBrowseCount(long id)
    throws DaoException, RenderException
  {
    KnowledgeDao dao = new KnowledgeDao();
    dao.addBrowseCount(id);
  }
}
