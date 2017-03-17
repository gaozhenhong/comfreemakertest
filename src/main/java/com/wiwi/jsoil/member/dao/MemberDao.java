package com.wiwi.jsoil.member.dao;

import com.wiwi.jsoil.db.DaoBase;
import com.wiwi.jsoil.db.DbAdapter;
import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.member.model.Member;
import java.util.List;

public class MemberDao extends DaoBase
{
  private String sql;

  public MemberDao()
  {
    this.sql = null; }

  public void insert(Member instance) throws DaoException, RenderException {
    DbAdapter.insert2SingleTable(instance, "s_member");
  }

  public void update(Member instance) throws DaoException, RenderException {
    DbAdapter.update2SingleTable(instance, "s_member");
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

    this.sql = "DELETE FROM s_member WHERE id in (" + ids + ") ";
    DbAdapter.executeUpdate(this.sql);
  }

  public Member get(Long id) throws DaoException, RenderException {
    this.sql = "select * FROM s_member WHERE id ='" + id + "'";
    return ((Member)DbAdapter.get(this.sql, Member.class));
  }

  public List<Member> getList(PageUtil pageUtil) throws DaoException, RenderException
  {
    this.sql = "select * FROM s_member";
    return DbAdapter.getList(this.sql, pageUtil, Member.class);
  }

  public Member checkLogin(String loginName, String loginPswd) throws DaoException, RenderException {
    this.sql = "select * from s_member where status>=0 and telphone=? and password=? ";
    Member member = (Member)DbAdapter.get(this.sql, Member.class, new String[] { loginName.trim(), loginPswd });
    return member;
  }

  public boolean checkPswd(Long memberId, String loginPswd) throws DaoException {
    this.sql = "select * from s_member where id='" + memberId + "' and password=?";
    return DbAdapter.havaRecord(this.sql, new String[] { loginPswd });
  }

  public void changePswd(Long memberId, String newPswd) throws DaoException {
    this.sql = "update s_member set password='" + newPswd + "' where id='" + memberId + "'";
    DbAdapter.executeUpdate(this.sql);
  }
}