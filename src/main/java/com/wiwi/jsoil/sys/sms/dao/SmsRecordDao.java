package com.wiwi.jsoil.sys.sms.dao;

import com.wiwi.jsoil.db.DaoBase;
import com.wiwi.jsoil.db.DbAdapter;
import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.sys.sms.model.SmsRecord;
import java.util.List;

public class SmsRecordDao extends DaoBase
{
  private String sql;

  public SmsRecordDao()
  {
    this.sql = null; }

  public void insert(SmsRecord instance) throws DaoException, RenderException {
    DbAdapter.insert2SingleTable(instance, "s_sms_record");
  }

  public void update(SmsRecord instance) throws DaoException, RenderException {
    DbAdapter.update2SingleTable(instance, "s_sms_record");
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

    this.sql = "DELETE FROM s_sms_record WHERE id in (" + ids + ") ";
    DbAdapter.executeUpdate(this.sql);
  }

  public SmsRecord get(long id) throws DaoException, RenderException {
    this.sql = "select * FROM s_sms_record WHERE id ='" + id + "'";
    return ((SmsRecord)DbAdapter.get(this.sql, SmsRecord.class));
  }

  public List<SmsRecord> getList(PageUtil pageUtil) throws DaoException, RenderException
  {
    this.sql = "select * FROM s_sms_record";
    return DbAdapter.getList(this.sql, pageUtil, SmsRecord.class);
  }
}