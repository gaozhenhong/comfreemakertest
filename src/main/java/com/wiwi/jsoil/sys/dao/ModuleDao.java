package com.wiwi.jsoil.sys.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wiwi.jsoil.db.DaoBase;
import com.wiwi.jsoil.db.DbAdapter;
import com.wiwi.jsoil.db.KeyGenerator;
import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.db.Transaction;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.sys.controller.CategoryController;
import com.wiwi.jsoil.sys.model.Module;

public class ModuleDao extends DaoBase {
	  private static final Logger log = LoggerFactory.getLogger(ModuleDao.class);

	private String sql = null;

	private Connection conn = null;

	private PreparedStatement ps = null;

	private ResultSet rs = null;

	
	
	
	public long save(Module instance) throws DaoException {
		try {
			KeyGenerator keygen = null; // 序列生成器类

			String tableName = "S_Module"; // 日志表在种子表的的键名

			keygen = KeyGenerator.getInstance();
			long id = (long)keygen.getNextKey(tableName);

			instance.setId(id);

			conn = getConn(true);
			sql = "insert into S_Module (id,code,name,forwardUrl,demo,icon,parentId) values(?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(sql);

			ps.setLong(1, instance.getId());
			
			if (instance.getCode() != null) {
				ps.setString(2, instance.getCode());
			} else {
				ps.setString(2, null);
			}
			
			if (instance.getName() != null) {
				ps.setString(3, instance.getName());
			} else {
				ps.setString(3, null);
			}
			
			if (instance.getForwardUrl() != null) {
				ps.setString(4, instance.getForwardUrl());
			} else {
				ps.setString(4, null);
			}
			
			if (instance.getDemo() != null) {
				ps.setString(5, instance.getDemo());
			} else {
				ps.setString(5, null);
			}

			
			if (instance.getIcon() != null) {
				ps.setString(6, instance.getIcon());
			} else {
				ps.setString(6, null);
			}
			
			ps.setLong(7, instance.getParentId());
			ps.executeUpdate();
			return id;
		} catch (SQLException e) {
			log.error("保存功能模块信息时出错！",e);
			throw new DaoException(e);
		} finally {
			closeStmt(ps);
			closeConn(conn);
		}
	}

	
	public void update(Module instance) throws DaoException {
		try {
			conn = getConn(true);
			sql = "update S_Module set code=?,name=?,forwardUrl=?,demo=?,icon=?,parentId=? "
					+ " where id=?";

			ps = conn.prepareStatement(sql);

			if (instance.getCode() != null) {
				ps.setString(1, instance.getCode());
			} else {
				ps.setString(1, null);
			}
			
			if (instance.getName() != null) {
				ps.setString(2, instance.getName());
			} else {
				ps.setString(2, null);
			}
			
			if (instance.getForwardUrl() != null) {
				ps.setString(3, instance.getForwardUrl());
			} else {
				ps.setString(3, null);
			}
			
			if (instance.getDemo() != null) {
				ps.setString(4, instance.getDemo());
			} else {
				ps.setString(4, null);
			}

			if (instance.getIcon() != null) {
				ps.setString(5, instance.getIcon());
			} else {
				ps.setString(5, null);
			}
			
			ps.setLong(6, instance.getParentId());
			ps.setLong(7, instance.getId());
			
			ps.executeUpdate();
		} catch (SQLException e) {
			log.error("修改功能模块信息错误！",e);
			throw new DaoException(e);
		} finally {
			closeStmt(ps);
			closeConn(conn);
		}
	}

	public void delete(String ids) throws DaoException {
		if(ids.endsWith("'")) {
			ids = ids.substring(0,ids.length()-1);
		}
		if(ids.startsWith(",")) {
			ids = ids.substring(1);
		}
		String strInSql = ids.replaceAll(",", "','");
		strInSql = "'" + strInSql + "'";

		Transaction tran=new Transaction();
		sql = "delete from S_GroupUser where GroupId in (" + ids + ") ";
		tran.executeUpdate(sql);

		sql = "delete from S_GroupModule where moduleId in (" + ids + ") ";
		tran.executeUpdate(sql);

		sql = "delete from S_Module where id in (" + ids + ")";
		tran.executeUpdate(sql);
		tran.commit();
	}

	
	public Module get(long id) throws DaoException {
		Module instance = new Module();
		try {
			conn = getConn(true);
			sql = "select * from S_Module WHERE id=" + id;

			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				instance = new Module();
				instance.setId(id);
				instance.setCode(rs.getString("code"));
				instance.setName(rs.getString("name"));
				instance.setForwardUrl(rs.getString("forwardUrl"));
				instance.setIcon(rs.getString("icon"));
				instance.setDemo(rs.getString("demo"));
				instance.setParentId(rs.getInt("parentId"));
			}
			return instance;
		} catch (Exception e) {
			log.error("获取功能模块信息id="+id+"出错！",e);
			throw new DaoException(e);
		} finally {
			closeRs(rs);
			closeStmt(ps);
			closeConn(conn);
		}
	}

	@SuppressWarnings("rawtypes")
	public List getList(PageUtil pageUtil) throws DaoException {
		ArrayList<Module> returnList = new ArrayList<Module>();
		try {
			conn = getConn(true);
            sql="select * from S_Module ";
            rs=DbAdapter.getRsPerPage(conn, ps,sql, pageUtil,"id");
            Module instance;
            while (rs!=null && rs.next()) { 
				instance = new Module();
				instance.setId(rs.getLong("id"));
				instance.setCode(rs.getString("code"));
				instance.setName(rs.getString("name"));
				instance.setForwardUrl(rs.getString("forwardUrl"));
				instance.setIcon(rs.getString("icon"));
				instance.setDemo(rs.getString("demo"));
				instance.setParentId(rs.getInt("parentId"));

				returnList.add(instance);
			}
			return returnList;
		} catch (Exception e) {
			log.error("获取功能模块列表信息出错！",e);
			throw new DaoException(e);
		} finally {
			closeRs(rs);
			closeStmt(ps);
			closeConn(conn);
		}
	}
	
	
	/**
	 * 得到该用户在该模块下所有有权限的功能清单
	 * @param moduleCode
	 * @param userId
	 * @return
	 * @throws DaoException
	 */
	@SuppressWarnings("rawtypes")
	public List getMenuList(String moduleCode, long userId)
			throws DaoException {
		List<Module> menuList = new ArrayList<Module>();
		try {
			conn = getConn(true);

			String whereStr = "WHERE id IN (select moduleId from S_GroupModule WHERE groupId IN (SELECT groupId FROM S_GroupUser WHERE userId='"
					+ userId + "'))";
			Module instance;
			sql = " select *  from S_Module " + whereStr;
			if(moduleCode != null && moduleCode.trim().length() > 0){
				sql = sql + " AND code like '" + moduleCode + "%' AND  code<>'" +
						moduleCode + "' ";
			}
			sql = sql	+ " ORDER BY code";
			log.debug(sql);
			System.out.println(sql);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			String code;
			String forwardUrl;
			while (rs.next()) {
				instance = new Module();
				instance.setId(rs.getLong("id"));
				code = rs.getString("code");
				if (!code.substring(0, code.length() - 3).equalsIgnoreCase(
						moduleCode))
					instance.setParentId(rs.getInt("parentId"));
				else
					instance.setParentId(-1);
				instance.setName(rs.getString("name"));
				instance.setDemo(rs.getString("demo"));
				instance.setCode(rs.getString("code"));
				instance.setIcon(rs.getString("icon"));
				forwardUrl = rs.getString("forwardUrl");
				if (forwardUrl == null)
					forwardUrl = "";

				instance.setForwardUrl(forwardUrl);
				menuList.add(instance);
			}
			return menuList;
		} catch (SQLException e) {
			throw new DaoException("获取菜单信息出错（DAO）：" + e);
		} finally {
			closeRs(rs);
			closeStmt(ps);
			closeConn(conn);
		}
	}
	
	
	/**
	 * 得到该用户组所有有权限的功能清单
	 * @param moduleCode
	 * @param userId
	 * @return
	 * @throws DaoException
	 */
	@SuppressWarnings("rawtypes")
	public List getMenuListByGroupId( int groupId)
			throws DaoException {
		List<Module> menuList = new ArrayList<Module>();
		try {
			conn = getConn(true);

			String whereStr = "WHERE id IN (select moduleId from S_GroupModule WHERE groupId='"
					+ groupId + "')";
			Module instance;
			sql = " select *  from S_Module " + whereStr;
			sql = sql	+ " ORDER BY code";
			log.info(sql);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			String forwardUrl;
			while (rs.next()) {
				instance = new Module();
				instance.setId(rs.getLong("id"));
				instance.setParentId(rs.getInt("parentId"));
				instance.setName(rs.getString("name"));
				instance.setDemo(rs.getString("demo"));
				instance.setCode(rs.getString("code"));
				instance.setIcon(rs.getString("icon"));
				forwardUrl = rs.getString("forwardUrl");
				if (forwardUrl == null)
					forwardUrl = "";

				instance.setForwardUrl(forwardUrl);
				menuList.add(instance);
			}
			return menuList;
		} catch (SQLException e) {
			throw new DaoException("获取用户组功能模块清单信息出错（DAO）：" + e);
		} finally {
			closeRs(rs);
			closeStmt(ps);
			closeConn(conn);
		}
	}
	
}
