package com.wiwi.edb.product.dao;

import com.wiwi.edb.product.model.ProductSnapshot;
import com.wiwi.jsoil.db.DaoBase;
import com.wiwi.jsoil.db.DbAdapter;
import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import java.util.List;

public class ProductSnapshotDao extends DaoBase
{
  private String sql;

  public ProductSnapshotDao()
  {
    this.sql = null; }

  public void insert(ProductSnapshot instance) throws DaoException, RenderException {
    DbAdapter.insert2SingleTable(instance, "edb_product_snapshot");
  }

  public void update(ProductSnapshot instance) throws DaoException, RenderException {
    DbAdapter.update2SingleTable(instance, "edb_product_snapshot");
  }

  public void delete(String ids) throws DaoException {
    if (ids.startsWith(","))
      ids = ids.substring(1);

    if (ids.indexOf(",") != -1)
      ids = ids.replaceAll(",", "','");

    if (!(ids.startsWith("'")))
      ids = "'" + ids;

    if (!(ids.endsWith("'")))
      ids = ids + "'";

    this.sql = "DELETE FROM edb_product_snapshot WHERE id in (" + ids + ") ";
    DbAdapter.executeUpdate(this.sql);
  }

  public ProductSnapshot get(long id) throws DaoException, RenderException {
    this.sql = "select * FROM edb_product_snapshot WHERE id ='" + id + "'";
    return ((ProductSnapshot)DbAdapter.get(this.sql, ProductSnapshot.class));
  }

  public List<ProductSnapshot> getList(PageUtil pageUtil) throws DaoException, RenderException
  {
    this.sql = "select * FROM edb_product_snapshot";
    return DbAdapter.getList(this.sql, pageUtil, ProductSnapshot.class);
  }
}