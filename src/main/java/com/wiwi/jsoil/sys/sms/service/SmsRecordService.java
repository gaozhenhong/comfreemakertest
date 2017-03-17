package com.wiwi.jsoil.sys.sms.service;

import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.sys.sms.dao.SmsRecordDao;
import com.wiwi.jsoil.sys.sms.model.SmsRecord;
import java.util.List;

public class SmsRecordService
{
  public void insert(SmsRecord instance)
    throws DaoException, RenderException
  {
    SmsRecordDao dao = new SmsRecordDao();
    dao.insert(instance);
  }

  public void update(SmsRecord instance) throws DaoException, RenderException {
    SmsRecordDao dao = new SmsRecordDao();
    dao.update(instance);
  }

  public void delete(long id) throws DaoException {
    batchDelete(id+"");
  }

  public void batchDelete(String ids) throws DaoException {
    SmsRecordDao dao = new SmsRecordDao();
    dao.delete(ids);
  }

  public SmsRecord get(long id) throws DaoException, RenderException {
    SmsRecordDao dao = new SmsRecordDao();
    return dao.get(id);
  }

  public List<SmsRecord> getList(PageUtil pageUtil) throws DaoException, RenderException {
    SmsRecordDao dao = new SmsRecordDao();
    return dao.getList(pageUtil);
  }
}