package com.wiwi.edb.product.service;

import com.wiwi.edb.product.dao.ProductSnapshotDao;
import com.wiwi.edb.product.model.ProductSnapshot;
import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import java.util.List;

public class ProductSnapshotService
{
  public void insert(ProductSnapshot instance)
    throws DaoException, RenderException
  {
    ProductSnapshotDao dao = new ProductSnapshotDao();
    dao.insert(instance);
  }

  public void update(ProductSnapshot instance) throws DaoException, RenderException {
    ProductSnapshotDao dao = new ProductSnapshotDao();
    dao.update(instance);
  }

  public void delete(long id) throws DaoException {
    batchDelete(id+"");
  }

  public void batchDelete(String ids) throws DaoException {
    ProductSnapshotDao dao = new ProductSnapshotDao();
    dao.delete(ids);
  }

  public ProductSnapshot get(long id) throws DaoException, RenderException {
    ProductSnapshotDao dao = new ProductSnapshotDao();
    return dao.get(id);
  }

  public List<ProductSnapshot> getList(PageUtil pageUtil) throws DaoException, RenderException {
    ProductSnapshotDao dao = new ProductSnapshotDao();
    return dao.getList(pageUtil);
  }
}