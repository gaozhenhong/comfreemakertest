package com.wiwi.jsoil.sys.service;

import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.sys.dao.CollectDao;
import com.wiwi.jsoil.sys.model.Collect;
import java.util.List;

public class CollectService
{
  public void insert(Collect instance)
    throws DaoException, RenderException
  {
    CollectDao dao = new CollectDao();
    dao.insert(instance);
  }
  
  public void update(Collect instance)
    throws DaoException, RenderException
  {
    CollectDao dao = new CollectDao();
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
    CollectDao dao = new CollectDao();
    dao.delete(ids);
  }
  
  public Collect get(long id)
    throws DaoException, RenderException
  {
    CollectDao dao = new CollectDao();
    return dao.get(id);
  }
  
  public List<Collect> getList(PageUtil pageUtil)
    throws DaoException, RenderException
  {
    CollectDao dao = new CollectDao();
    return dao.getList(pageUtil);
  }
  
  public Collect getCollect(String collectBeanId, String collectModule)
    throws DaoException, RenderException
  {
    CollectDao dao = new CollectDao();
    return dao.getCollect(collectBeanId, collectModule);
  }
  
  public boolean isCollected(String collectBeanId, String collectModule)
    throws DaoException, RenderException
  {
    boolean isCollected = false;
    Collect instance = getCollect(collectBeanId, collectModule);
    if (instance != null) {
      isCollected = true;
    }
    return isCollected;
  }
}
