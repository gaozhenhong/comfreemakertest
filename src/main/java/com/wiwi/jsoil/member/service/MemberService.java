package com.wiwi.jsoil.member.service;

import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.member.dao.MemberDao;
import com.wiwi.jsoil.member.model.Member;
import com.wiwi.jsoil.util.MD5Util;
import java.util.List;

public class MemberService
{
  public void insert(Member instance, String password)
    throws DaoException, RenderException
  {
    instance.setStatus(Integer.valueOf(1));
    MemberDao dao = new MemberDao();
    dao.insert(instance);
    changePswd(instance.getId().longValue(), MD5Util.string2MD5(password));
  }

  public void update(Member instance) throws DaoException, RenderException {
    MemberDao dao = new MemberDao();
    dao.update(instance);
  }

  public void delete(Long id) throws DaoException {
    batchDelete(id+"");
  }

  public void batchDelete(String ids) throws DaoException {
    MemberDao dao = new MemberDao();
    dao.delete(ids);
  }

  public Member get(Long id) throws DaoException, RenderException {
    MemberDao dao = new MemberDao();
    return dao.get(id);
  }

  public List<Member> getList(PageUtil pageUtil) throws DaoException, RenderException {
    MemberDao dao = new MemberDao();
    return dao.getList(pageUtil);
  }

  public Member checkMemberLogin(String loginName, String password) throws DaoException, RenderException {
    MemberDao dao = new MemberDao();
    return dao.checkLogin(loginName, MD5Util.string2MD5(password));
  }

  public boolean changePswd(long memberId, String oldPswd, String newPswd) throws DaoException
  {
    MemberDao dao = new MemberDao();
    boolean isOldPswdOk = dao.checkPswd(Long.valueOf(memberId), oldPswd);
    if (isOldPswdOk)
      changePswd(memberId, newPswd);

    return isOldPswdOk;
  }

  public void changePswd(long memberId, String newPswd) throws DaoException {
    MemberDao dao = new MemberDao();
    dao.changePswd(Long.valueOf(memberId), newPswd);
  }

  public boolean checkPswd(long memberId, String oldPswd)
  {
    MemberDao dao = new MemberDao();
    try {
      return dao.checkPswd(Long.valueOf(memberId), oldPswd);
    } catch (DaoException e) {
      e.printStackTrace();

      return false;
    }
  }
}