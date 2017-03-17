package com.wiwi.jsoil.db;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.util.AppConstants;


public class DbAdapter {
	private static final Logger logger = LoggerFactory.getLogger(DbAdapter.class);

	public static JSONObject getJSONObject(String sql) throws DaoException, RenderException {
		return getJSONObject(sql, null);
	}

	public static JSONObject getJSONObject(String sql, Object[] params) throws DaoException, RenderException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		JSONObject obj = null;
		try {
			conn = ConnectionPool.getInstance().getConnection(true);
			ps = conn.prepareStatement(sql);
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					ps.setObject(i + 1, params[i]);
				}
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				obj = new JSONObject();
				int columCount = rs.getMetaData().getColumnCount();
				for (int i = 0; i < columCount; i++) {
					obj.put(rs.getMetaData().getColumnName(i + 1), rs.getObject(i + 1));
				}
			}
		} catch (SQLException e) {
			logger.error("从数据库查询单条记录的时候出现SQL错误,sql:" + sql, e);
			throw new DaoException(e);
		} finally {
			DaoBase.closeRs(rs);
			DaoBase.closeStmt(ps);
			DaoBase.closeConn(conn);
		}
		return obj;

	}

	public static JSONArray getJSONArray(String sql) throws DaoException, RenderException {
		return getJSONArray(sql, null);
	}

	public static JSONArray getJSONArray(String sql, Object[] params) throws DaoException, RenderException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		JSONArray jsonArray = new JSONArray();
		try {
			conn = ConnectionPool.getInstance().getConnection(true);
			ps = conn.prepareStatement(sql);
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					ps.setObject(i + 1, params[i]);
				}
			}
			logger.debug("sql:" + sql);
			rs = ps.executeQuery();
			int colLen = rs.getMetaData().getColumnCount();
			JSONObject jsonObj;

			while (rs.next()) {
				jsonObj = new JSONObject();
				for (int i = 1; i <= colLen; i++) {
					jsonObj.put(rs.getMetaData().getColumnName(i), rs.getObject(i) != null ? rs.getObject(i) : "");
				}
				jsonArray.put(jsonObj);
			}
			logger.debug("jsonArray:" + jsonArray.toString());
			return jsonArray;
		} catch (SQLException e) {
			throw new DaoException("从数据库获取getJSONArray的时候出现SQL错误!sql:" + sql, e);
		} finally {
			DaoBase.closeRs(rs);
			DaoBase.closeStmt(ps);
			DaoBase.closeConn(conn);
		}
	}

	public static Object get(String sql, Class targetClass) throws DaoException, RenderException {
		return get(sql, targetClass, null);

	}

	/**
	 * @param selectSql
	 * @param targetClass
	 * @return 从数据库返回单个值对象
	 * @throws DaoException
	 * @throws RenderException
	 */
	public static Object get(String sql, Class targetClass, Object[] params) throws DaoException, RenderException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Object obj = null;
		try {
			conn = ConnectionPool.getInstance().getConnection(true);
			ps = conn.prepareStatement(sql);
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					ps.setObject(i + 1, params[i]);
				}
			}
			logger.debug("sql:" + sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				obj = getBeanFormRs(targetClass, rs);
			}
		} catch (SQLException e) {
			throw new DaoException("从数据库查询单条记录的时候出现SQL错误,sql:" + sql, e);
		} finally {
			DaoBase.closeRs(rs);
			DaoBase.closeStmt(ps);
			DaoBase.closeConn(conn);
		}
		return obj;
	}

	/**
	 * @param ps
	 * @param targetClass
	 * @return 从数据库返回单个值对象
	 * @throws DaoException
	 * @throws RenderException
	 */
	public static Object get(PreparedStatement ps, Class targetClass) throws DaoException, RenderException {
		ResultSet rs = null;
		Object obj = null;
		try {
			rs = ps.executeQuery();
			while (rs.next()) {
				obj = getBeanFormRs(targetClass, rs);
			}
		} catch (SQLException e) {
			throw new DaoException("从数据库查询单条记录的时候出现SQL错误：", e);
		} finally {
			DaoBase.closeRs(rs);
		}
		return obj;
	}

	public static List getList(String sql, Class targetClass) throws DaoException, RenderException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = ConnectionPool.getInstance().getConnection(true);
			logger.info(sql);
			ps = conn.prepareStatement(sql);
			return getList(ps, targetClass);
		} catch (SQLException e) {
			throw new DaoException("从数据库查询列表时(不分页)候出现SQL错误：", e);
		} finally {
			DaoBase.closeStmt(ps);
			DaoBase.closeConn(conn);
		}
	}

	public static List getList(PreparedStatement ps, Class targetClass) throws DaoException, RenderException {
		List<Object> list = new ArrayList<Object>();
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(getBeanFormRs(targetClass, rs));
			}
		} catch (SQLException e) {
			throw new DaoException("从数据库查询列表时(不分页)候出现SQL错误：", e);
		} finally {
			DaoBase.closeRs(rs);
		}
		return list;

	}

	/**
	 * @param selectSql
	 * @param pageUtil
	 * @param targetClass
	 * @return 从数据库返回分页列表
	 * @throws DaoException
	 * @throws RenderException
	 */
	public static List getList(String selectSql, PageUtil pageUtil, Class targetClass) throws DaoException, RenderException {
		System.out.println("sql"+selectSql);
		return getList(selectSql, pageUtil, targetClass, "id");
	}

	/**
	 * @param selectSql
	 * @param pageUtil
	 * @param targetClass
	 * @param idName
	 *            数据库主键字段名－－用户排序所用！
	 * @return 从数据库返回分页列表
	 * @throws DaoException
	 * @throws RenderException
	 */
	public static List getList(String selectSql, PageUtil pageUtil, Class targetClass, String idName) throws DaoException, RenderException {
		List<Object> list = new ArrayList<Object>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectionPool.getInstance().getConnection(true);
			rs = getRsPerPage(conn, ps, selectSql, pageUtil, idName);
			if (rs != null) {
				while (rs.next()) {
					list.add(getBeanFormRs(targetClass, rs));
				}
			}
		} catch (SQLException e) {
			throw new DaoException("从数据库查询分页列表时候出现SQL错误：", e);
		} finally {
			DaoBase.closeRs(rs);
			DaoBase.closeStmt(ps);
			DaoBase.closeConn(conn);
		}
		return list;

	}

	public static ResultSet getRsPerPage(Connection conn, PreparedStatement ps, String selectSql, PageUtil pageUtil, String idName) throws DaoException {
		String sql = null;
		ResultSet rs = null;
		try {
			String orderByKind = pageUtil.getOrderByKind();
			String orderByKind1 = pageUtil.getOrderByKind1();
			String orderByKindTemp = "asc".equals(orderByKind) ? "desc" : "asc";
			String orderByKind1Temp = "asc".equals(orderByKind1) ? "desc" : "asc";

			String orderbySql = pageUtil.getOrderByProperty() + " " + orderByKind;
			if (pageUtil.getOrderByProperty1() != null && !pageUtil.getOrderByProperty1().equalsIgnoreCase(""))
				orderbySql += "," + pageUtil.getOrderByProperty1() + " " + orderByKind1;

			String orderbySqlTemp = pageUtil.getOrderByProperty() + " " + orderByKindTemp;
			if (pageUtil.getOrderByProperty1() != null && !pageUtil.getOrderByProperty1().equalsIgnoreCase(""))
				orderbySqlTemp += "," + pageUtil.getOrderByProperty1() + " " + orderByKind1Temp;

			if (pageUtil.getRecordPerPage() == 0) {
				if (idName.equalsIgnoreCase(pageUtil.getOrderByProperty())) {
					sql = "select * from (" + selectSql + ") m  " + pageUtil.toWhereString() + " order by " + orderbySql;
				} else {
					sql = "select * from (" + selectSql + ") m  " + pageUtil.toWhereString() + " order by " + orderbySql + "," + idName + " asc";
				}
				// logger.info(sql);
			} else {
				sql = "select count(*) from (" + selectSql + ") m  " + pageUtil.toWhereString();
				logger.info("分页SQL的统计总条数：{}",sql);
				ps = conn.prepareStatement(sql);
				initParameter(ps, pageUtil);

				rs = ps.executeQuery();
				int totalRecord = 0;
				if (rs.next()) {
					totalRecord = rs.getInt(1);
				}
				int totalPage = (totalRecord + pageUtil.getRecordPerPage() - 1) / pageUtil.getRecordPerPage();
				if (pageUtil.getCurrentPage() > totalPage) {
					return null;
				}
				pageUtil.render(totalRecord);

				// tableName=idName.indexOf(".")==-1?"m":idName.substring(0,idName.indexOf("."));
				sql = getPagerSql(selectSql, pageUtil, idName, orderByKind, orderbySql, orderbySqlTemp);
			}
			logger.info("生成的分页SQL：" + sql);
			ps = conn.prepareStatement(sql);
			initParameter(ps, pageUtil);

			rs = ps.executeQuery();
			return rs;
		} catch (SQLException e) {
			throw new DaoException("从数据库查询分页ResultSet时候出现SQL错误.sql：" + sql, e);
		}
	}

	private static void initParameter(PreparedStatement ps, PageUtil pageUtil) {
		if (ps == null || pageUtil == null)
			return;
		List<Object> parameterList = pageUtil.getParameterList();
		if (parameterList == null || parameterList.size() < 1) {
			return;
		}
		for (int i = 0; i < parameterList.size(); i++) {
			Object parameter = parameterList.get(i);
			if (parameter instanceof Integer) {
				try {
					ps.setInt(i + 1, (Integer) parameter);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else if (parameter instanceof Long) {
				try {
					ps.setLong(i + 1, (Long) parameter);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else if (parameter instanceof Short) {
				try {
					ps.setShort(i + 1, (Short) parameter);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else if (parameter instanceof Double) {
				try {
					ps.setDouble(i + 1, (Double) parameter);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else if (parameter instanceof Float) {
				try {
					ps.setFloat(i + 1, (Float) parameter);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else if (parameter instanceof Date) {
				// LCY_TODO 时间类型的处理
				try {
					ps.setString(i + 1, parameter.toString());
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else {
				try {
					ps.setString(i + 1, parameter.toString());
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * @param selectSql
	 * @param pageUtil
	 * @param idName
	 * @param orderByKind
	 * @param orderbySql
	 * @param orderbySqlTemp
	 * @param firstResult
	 * @param lastResult
	 * @return
	 */
	private static String getPagerSql(String selectSql, PageUtil pageUtil, String idName, String orderByKind, String orderbySql, String orderbySqlTemp) {
		String sql = "";
		int firstResult = 0;
		int lastResult = 0;
		switch (AppConstants.DB_TYPE) {
		case AppConstants.DB_TYPE_MYSQL:
			firstResult = (pageUtil.getCurrentPage() - 1) * pageUtil.getRecordPerPage();
			lastResult = pageUtil.getCurrentPage() * pageUtil.getRecordPerPage();
			sql = "select * from (" + selectSql + ") m " + pageUtil.toWhereString() + " order by " + orderbySql;
			if (idName.equalsIgnoreCase(pageUtil.getOrderByProperty()) || pageUtil.getOrderByProperty().indexOf("," + idName) != -1) {
				sql = sql + "," + idName + " desc";
			}
			sql = sql + " limit " + firstResult + "," + pageUtil.getRecordPerPage();
			break;
		case AppConstants.DB_TYPE_MSSQL:
			firstResult = pageUtil.getCurrentPage() * pageUtil.getRecordPerPage();
			lastResult = pageUtil.getTotalPage() == pageUtil.getCurrentPage() ? (pageUtil.getTotalRecord() - ((pageUtil.getTotalPage() - 1) * pageUtil.getRecordPerPage())) : pageUtil.getRecordPerPage();
			if (idName.equalsIgnoreCase(pageUtil.getOrderByProperty()) || pageUtil.getOrderByProperty().indexOf("," + idName) != -1) {
				sql = "select * from (select top " + lastResult + " * from (select top " + firstResult + " * from (" + selectSql + ") m " + pageUtil.toWhereString() + " order by " + orderbySql + ") m order by " + orderbySqlTemp + ") m order by "
						+ pageUtil.getOrderByProperty() + " " + orderByKind;
			} else {
				sql = "select * from (select top " + lastResult + " * from (select top " + firstResult + " * from (" + selectSql + ") m " + pageUtil.toWhereString() + " order by " + orderbySql + "," + idName + " desc) m order by " + orderbySqlTemp
						+ "," + idName + " asc) m order by " + orderbySql + "," + idName + " desc";
			}
			break;

		}
		logger.debug("数据库类型为：" + AppConstants.DB_TYPE + ";生成的分页SQL：" + sql);
		return sql;
	}

	public static void deleteFromSingleTable(String ids, String tableName) throws DaoException {
		deleteFromSingleTable(ids, "id", tableName);
	}

	public static void deleteFromSingleTable(String ids, String idNames, String tableName) throws DaoException {
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = null;
		try {
			conn = ConnectionPool.getInstance().getConnection(true);
			sql = "DELETE FROM " + tableName + " WHERE " + idNames + " IN (" + ids + ") ";
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("调用公用方法删除指定Ids的对象时出错.ID=" + ids + ",执行Sql为：" + sql, e);
			throw new DaoException(e);
		} finally {
			DaoBase.closeStmt(ps);
			DaoBase.closeConn(conn);
		}
	}

	public static void deleteFromTables(String mainIds, String mainTableName, String[] slaveIdName, String[] slaveTableName) throws DaoException {
		deleteFromTables(mainIds, "id", mainTableName, slaveIdName, slaveTableName);
	}

	public static void deleteFromTables(String mainIds, String mainIdName, String mainTableName, String[] slaveIdName, String[] slaveTableName) throws DaoException {
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = null;
		try {
			conn = ConnectionPool.getInstance().getConnection(false);
			if (slaveTableName != null) {
				for (int i = 0; i < slaveTableName.length; i++) {
					sql = "DELETE FROM " + slaveTableName[i] + " WHERE " + slaveIdName[i] + " IN (" + mainIds + ") ";
					ps = conn.prepareStatement(sql);
					ps.executeUpdate();
				}
			}

			sql = "DELETE FROM " + mainTableName + " WHERE " + mainIdName + " IN (" + mainIds + ") ";
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (Exception se) {
				throw new DaoException("调用公用方法删除指定对象回滚时出错:", se);
			}
			throw new DaoException("调用公用方法删除指定Ids的对象时出错.ID=" + mainIds + ",执行Sql为：" + sql, e);
		} finally {
			DaoBase.closeStmt(ps);
			DaoBase.closeConn(conn);
		}
	}

	/**
	 * @param source
	 * @param sourceClass
	 * @param tableName
	 * @throws DaoException
	 * @throws SQLException
	 */
	public static void insert2SingleTable(Object source, String tableName) throws DaoException, RenderException {
		insert2SingleTable(source, source.getClass(), tableName);
	}

	public static void insert2SingleTable(Object source, Class clazz, String tableName) throws DaoException, RenderException {
		KeyGenerator keygen = KeyGenerator.getInstance();
		Object id = null;// = keygen.getUUID();

		String fieldStr = "id";
		String preParedStr = "?";
		List preparedValueList = new ArrayList();

		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
			PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();
			for (int i = 0; i < properties.length; i++) {
				String propertyName = properties[i].getName();
				if (propertyName.equals("class"))
					continue;
				if (propertyName.equals("id")) {
					Field field = getField(properties[i], source);
					if (field == null || field.getValue() == null || field.getValue().toString().isEmpty()) {
						if (properties[i].getPropertyType() == String.class)
							id = keygen.getUUID();
						else if (properties[i].getPropertyType() == Long.class)
							id = keygen.getNextKey(tableName);
						else if (properties[i].getPropertyType() == Integer.class)
							id = (int) keygen.getNextKey(tableName);
						logger.debug("主键型值：" + source.getClass() + "--" + id);
						properties[i].getWriteMethod().invoke(source, new Object[] { id });
					} else {
						id = field.getValue();
					}
					continue;
				}
				if (properties[i].getReadMethod() != null) {
					Field field = getField(properties[i], source);
					if (field == null) {
						continue;
					}
					fieldStr += "," + field.getName();
					preParedStr += ",?";
					preparedValueList.add(field.getValue());
				}
			}
			String sql = "insert into " + tableName + " (" + fieldStr + ") VALUES(" + preParedStr + ")";
			excuteInsertSql(sql, id, preparedValueList);

		} catch (IntrospectionException e) {
			throw new RenderException("调用共用方法向数据库插入数据时出现内省期间异常：", e);
		} catch (IllegalAccessException e) {
			throw new RenderException("调用共用方法向数据库插入数据时出现无法访问异常：", e);
		} catch (InvocationTargetException e) {
			throw new RenderException("调用共用方法向数据库插入数据时出现调用目标异常：", e);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("调用共用方法向数据库插入数据时出现SQL异常：", e);
		}
	}

	private static int excuteInsertSql(String sql, Object id, List preparedValueList) throws DaoException, SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = ConnectionPool.getInstance().getConnection(true);

			logger.info("insertsql:{},{}" , sql,id);
			ps = conn.prepareStatement(sql);
			ps.setObject(1, id);

			for (int i = 0; i < preparedValueList.size(); i++) {
				ps.setObject(i + 2, preparedValueList.get(i));
			}
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("调用共用方法向数据库插入数据时出现SQL异常：", e);
		} finally {
			DaoBase.closeStmt(ps);
			DaoBase.closeConn(conn);
		}
	}

	public static void update2SingleTable(Object source, String tableName) throws DaoException, RenderException {
		update2SingleTable(source, source.getClass(), tableName, true);
	}

	public static void update2SingleTable(Object source, Class clazz, String tableName) throws DaoException, RenderException {
		update2SingleTable(source, clazz, tableName, true);
	}

	public static void update2SingleTable(Object source, Class clazz, String tableName, boolean ignoreNullProperty) throws DaoException, RenderException {
		Object oId = new Object();

		String setSqlStr = "";
		String whereSqlStr = "id=?";
		List preparedValueList = new ArrayList();

		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
			PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();
			for (int i = 0; i < properties.length; i++) {
				String propertyName = properties[i].getName();
				if (propertyName.equals("class"))
					continue;
				if (propertyName.equals("id")) {
					oId = getField(properties[i], source).getValue();

					continue;
				}
				if (properties[i].getReadMethod() != null) {
					Field field = getField(properties[i], source);
					if (field == null) {
						continue;
					}
					if (ignoreNullProperty && field.getValue() == null) {
						continue;
					}
					setSqlStr += "," + field.getName() + "=?";
					// preParedStr+=",?";
					preparedValueList.add(field.getValue());
				}
			}

			executeUpdateSql(tableName, oId, setSqlStr, whereSqlStr, preparedValueList);
		} catch (IntrospectionException e) {
			throw new RenderException("调用共用方法向数据库修改数据时出现内省期间异常：", e);
		} catch (IllegalAccessException e) {
			throw new RenderException("调用共用方法向数据库修改数据时出现无法访问异常：", e);
		} catch (InvocationTargetException e) {
			throw new RenderException("调用共用方法向数据库修改数据时出现调用目标异常：", e);
		} catch (SQLException e) {
			throw new DaoException("调用共用方法向数据库修改数据时出现SQL异常：", e);
		}
	}

	private static int executeUpdateSql(String tableName, Object oId, String setSqlStr, String whereSqlStr, List preparedValueList) throws DaoException, SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		String sql;
		try {
			conn = ConnectionPool.getInstance().getConnection(true);
			sql = "update " + tableName + " set " + setSqlStr.substring(1);
			if (whereSqlStr != null && !"".equals(whereSqlStr.trim())) {
				sql = sql + " where " + whereSqlStr;
			}
			logger.info("update:" + sql);
			// logger.info("id:"+oId);
			ps = conn.prepareStatement(sql);

			int i;
			for (i = 0; i < preparedValueList.size(); i++) {
				ps.setObject(i + 1, preparedValueList.get(i));
			}
			ps.setObject(i + 1, oId);
			return ps.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("调用共用方法向数据库修改数据时出现SQL异常：", e);
		} finally {
			DaoBase.closeStmt(ps);
			DaoBase.closeConn(conn);
		}
	}

	public static void executeUpdate(String sql) throws DaoException {
		executeUpdate(sql, null);
	}

	public static void executeUpdate(String sql, Object[] params) throws DaoException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = ConnectionPool.getInstance().getConnection(true);
			ps = conn.prepareStatement(sql);
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					ps.setObject(i + 1, params[i]);
				}
			}
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("调用公用方法执行sql更新时出错,执行Sql为：" + sql, e);
		} finally {
			DaoBase.closeStmt(ps);
			DaoBase.closeConn(conn);
		}
	}

	public static boolean havaRecord(String sql) throws DaoException {
		return havaRecord(sql, null);
	}

	public static boolean havaRecord(String sql, Object[] params) throws DaoException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectionPool.getInstance().getConnection(true);
			ps = conn.prepareStatement(sql);
			logger.debug("执行sql:" + sql);
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					ps.setObject(i + 1, params[i]);
					// logger.info((i+1)+"::::::::::::::::::"+params[i]);
				}
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			throw new DaoException("调用公用方法执行sql判断是否有记录时出错,执行Sql为：" + sql + "\n参数为:" + params, e);
		} finally {
			DaoBase.closeRs(rs);
			DaoBase.closeStmt(ps);
			DaoBase.closeConn(conn);
		}
	}

	public static long count(String sql) throws DaoException {
		return count(sql, null);
	}

	public static long count(String sql, Object[] params) throws DaoException {
		String value = getValue(sql, params);
		return Long.parseLong(value);
	}

	public static double getDoubleValue(String sql, Object[] params) throws DaoException {
		String value = getValue(sql, params);
		return Double.parseDouble(value);
	}

	public static String getValue(String sql) throws DaoException {
		return getValue(sql, null);
	}

	public static String getValue(String sql, Object[] params) throws DaoException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectionPool.getInstance().getConnection(true);
			ps = conn.prepareStatement(sql);
			logger.debug("执行统计sql:" + sql);
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					ps.setObject(i + 1, params[i]);
				}
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			}

		} catch (SQLException e) {
			throw new DaoException("调用公用方法执行sql获取值时出错,执行Sql为：" + sql + "\n参数为:" + params, e);
		} finally {
			DaoBase.closeRs(rs);
			DaoBase.closeStmt(ps);
			DaoBase.closeConn(conn);
		}
		return "";
	}

	public static Object getBeanFormRs(Class invokeClass, ResultSet rs) throws DaoException, RenderException {
		Object target = null;
		try {
			/** 用于存储从rs结果集中获得的缓存 */
			Map rsValueMap = new HashMap();

			putRs2Map(rsValueMap, rs);

			target = invokeClass.newInstance();
			BeanInfo beanInfo = Introspector.getBeanInfo(invokeClass);
			PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();
			for (int i = 0; i < properties.length; i++) {
				String propertyName = properties[i].getName();
				if (propertyName.equals("class"))
					continue;
				 logger.info(properties[i].getName()+":"+properties[i].getPropertyType());
				Object targetPropertyValue = getPropertyFormMap(properties[i].getPropertyType(), propertyName, rsValueMap, 1);
				// logger.info(targetPropertyValue);
				if (targetPropertyValue == null) {
					// 该转化会导致时间字段在值为null时被初始化为当前时间，所在针对这两个类型进行非初始化处理
					if (properties[i].getPropertyType() != Date.class) {
						try {
							targetPropertyValue = properties[i].getPropertyType().newInstance();
						} catch (InstantiationException e) {
							continue;
						}
					}
				}
				if (properties[i].getWriteMethod() != null && targetPropertyValue != null) {
					try {
						properties[i].getWriteMethod().invoke(target, new Object[] { targetPropertyValue });
					} catch (IllegalArgumentException e) {
						e.fillInStackTrace();
						logger.info("=================="+properties[i].getPropertyType()+":::::::"+properties[i].getWriteMethod().getName()+":::::::"+targetPropertyValue);
						logger.error("调用write方法写属性值时出现错误:", e);
					}
				}
			}
			rsValueMap = null;
		} catch (InstantiationException e) {
			throw new RenderException("调用共用方法从ResultSet得到Object，库实例化对象时出现InstantiationException：", e);
		} catch (IntrospectionException e) {
			throw new RenderException("调用共用方法从ResultSet得到Object时出现内省期间异常：", e);
		} catch (IllegalAccessException e) {
			throw new RenderException("调用共用方法从ResultSet得到Object时出现无法访问异常：", e);
		} catch (InvocationTargetException e) {
			throw new RenderException("调用共用方法从ResultSet得到Object时出现调用目标异常：", e);
		} catch (SQLException e) {
			throw new DaoException("调用共用方法从ResultSet得到Object时出现SQLException：", e);
		}
		return target;
	}

	private static Object getPropertyFormMap(Class type, String fieldName, Map map, int currentLevel) throws SQLException, InstantiationException, IllegalAccessException, IntrospectionException, InvocationTargetException {
		if (map != null && map.size() != 0) {
			fieldName = fieldName.toLowerCase();
			if (type == int.class || type == Integer.class) {
				Object obj = getValueFromMap(map, fieldName);
				if (obj == null)
					return 0;
				if (obj.getClass() == Long.class) {
					return new Integer((int) ((Long)obj).longValue()); 
				}
				return obj;
			} else if (type == long.class || type == Long.class) {
				Object obj = getValueFromMap(map, fieldName);
				return obj == null ? 0l : obj;
			} else if (type == short.class || type == Short.class) {
				Object obj = getValueFromMap(map, fieldName);
				return obj == null ? 0 : obj;
			} else if (type == byte.class || type == Byte.class) {
				Object obj = getValueFromMap(map, fieldName);
				return obj == null ? 0 : obj;
			} else if (type == float.class || type == Float.class) {
				Object obj = getValueFromMap(map, fieldName);
				return obj == null ? 0.0f : obj;
			} else if (type == double.class || type == Double.class) {
				Object obj = getValueFromMap(map, fieldName);
				return obj == null ? 0.0d : obj;
			} else if (type == boolean.class || type == Boolean.class) {
				Object obj = getValueFromMap(map, fieldName);
				String objStr = (String) obj;
				if (objStr != null && (objStr.equals("1") || objStr.toLowerCase().equalsIgnoreCase("true"))) {
					return new Boolean(true);
				}
				return new Boolean(false);
			} else if (type == char.class || type == Character.class) {
				Object obj = getValueFromMap(map, fieldName);
				return obj == null ? ' ' : obj;
			} else if (type == String.class) {
				Object obj = getValueFromMap(map, fieldName);
				return (String) obj;
			} else if (type == BigDecimal.class) {
				return (BigDecimal) getValueFromMap(map, fieldName);
			} else if (type == BigInteger.class) {
				return (BigInteger) getValueFromMap(map, fieldName);
			} else if (type == Date.class) {
				Object obj = getValueFromMap(map, fieldName);
				return obj;
			} else if (type == List.class) {
				return null;
			} else if (type == Set.class) {
				return null;
			} else if (type == ArrayList.class) {
				return null;
			} else if (type == HashSet.class) {
				return null;
			} else if (type.isEnum()) {
				Object [] enumConstants = type.getEnumConstants();
				Object obj = getValueFromMap(map, fieldName);
				for(Object o : enumConstants){
					if(o.toString().equals((String)obj)){
						return o;
					}
				}
				return null;
			} else {
				/* 为防止对象嵌套死循化，属性解析只解析两层。 */
				if (currentLevel > 1) {
					return null;
				}
				
				Object target = type.newInstance();
				BeanInfo beanInfo = Introspector.getBeanInfo(type);
				PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();
				for (int i = 0; i < properties.length; i++) {
					String propertyName = properties[i].getName();
					if (propertyName.equals("class"))
						continue;
					Object targetPropertyValue = getPropertyFormMap(properties[i].getPropertyType(), fieldName + propertyName, map, currentLevel + 1);
					if (targetPropertyValue == null) {
						if (properties[i].getPropertyType() != Date.class ) {
							try {
								targetPropertyValue = properties[i].getPropertyType().newInstance();
							} catch (InstantiationException e) {
								continue;
							}
						}
					}
					// logger.info("propertiName::::::::::"+fieldName+propertyName+"\npropertivalue:::::::::::"+targetPropertyValue+"\nWriteMethod:::::::::::"+properties[i].getWriteMethod().getName());
					if (properties[i].getWriteMethod() != null) {
						try {
							properties[i].getWriteMethod().invoke(target, new Object[] { targetPropertyValue });
						} catch (RuntimeException e) {
							logger.info("=================="+properties[i].getPropertyType()+":::::::"+properties[i].getWriteMethod().getName()+":::::::"+targetPropertyValue);
							logger.error("调用write方法写属性值时出现错误:", e);
						}
					}
				}
				// logger.info(target.getClass().getName()+"===>"+JSONObject.fromBean(target).toString());
				return target;
			}
		}
		return null;
	}

	private static Object getValueFromMap(Map map, String columnName) throws SQLException {
		columnName = columnName.toLowerCase();
		Object obj = null;
		if (map.containsKey(columnName)) {
			obj = map.get(columnName);
			map.remove(columnName);
		}

		return obj;
	}

	private static void putRs2Map(Map<String, Object> map, ResultSet rs) throws SQLException {
		int columnCount = rs.getMetaData().getColumnCount();
		for (int i = 0; i < columnCount; i++) {
			/**
			 * 这里不能使用rs.getMetaData().getColumnName()，mysql里如果两个表关联，
			 * 字段相同后rs.getMetaData().getColumnName()得到的还是相同的
			 * 只有用rs.getMetaData().getColumnLabel()才会得到别名
			 */
			map.put(rs.getMetaData().getColumnLabel(i + 1).toLowerCase(), rs.getObject(i + 1));
		}
	}

	public static Field getField(PropertyDescriptor property, Object source) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, IntrospectionException {

		Class type = property.getPropertyType();
		if (type == int.class || type == Integer.class) {
			Object obj = property.getReadMethod().invoke(source, new Object[] {});
			return new Field(property.getName(), obj);
		} else if (type == long.class || type == Long.class) {
			Object obj = property.getReadMethod().invoke(source, new Object[] {});
			return new DbAdapter.Field(property.getName(), obj);
		} else if (type == short.class || type == Short.class) {
			Object obj = property.getReadMethod().invoke(source, new Object[] {});
			return new Field(property.getName(), obj);
		} else if (type == byte.class || type == Byte.class) {
			Object obj = property.getReadMethod().invoke(source, new Object[] {});
			return new Field(property.getName(), obj);
		} else if (type == float.class || type == Float.class) {
			Object obj = property.getReadMethod().invoke(source, new Object[] {});
			return new Field(property.getName(), obj);
		} else if (type == double.class || type == Double.class) {
			Object obj = property.getReadMethod().invoke(source, new Object[] {});
			return new Field(property.getName(), obj);
		} else if (type == boolean.class || type == Boolean.class) {
			Object obj = property.getReadMethod().invoke(source, new Object[] {});
			return new Field(property.getName(), obj);
		} else if (type == char.class || type == Character.class) {
			Object obj = property.getReadMethod().invoke(source, new Object[] {});
			return new Field(property.getName(), obj);
		} else if (type == String.class) {
			Object obj = property.getReadMethod().invoke(source, new Object[] {});
			return new Field(property.getName(), obj);
		} else if (type == BigDecimal.class) {
			Object obj = property.getReadMethod().invoke(source, new Object[] {});
			return new Field(property.getName(), obj);
		} else if (type == BigInteger.class) {
			Object obj = property.getReadMethod().invoke(source, new Object[] {});
			return new Field(property.getName(), obj);
		} else if (type == Date.class) {
			Object obj = property.getReadMethod().invoke(source, new Object[] {});
			return new Field(property.getName(), obj);
		} else if (type.isEnum()){
			Object obj = property.getReadMethod().invoke(source, new Object[] {});
			return new Field(property.getName(), obj.toString());
		}else if (type == List.class) {
			return null;
		} else if (type == Set.class) {
			return null;
		} else if (type == ArrayList.class) {
			return null;
		} else if (type == HashSet.class) {
			return null;
		} else {
			Object obj = property.getReadMethod().invoke(source, new Object[] {});
			if(obj == null) return null;
			BeanInfo beanInfo = Introspector.getBeanInfo(type);
			PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();
			String subPropertyName = "";
			Object subPropertyValue = null;
			for (int i = 0; i < properties.length; i++) {
				subPropertyName = properties[i].getName();
				if (subPropertyName.equals("id")) {
					try {
						subPropertyValue = properties[i].getReadMethod().invoke(obj, new Object[] {});
					} catch (RuntimeException e) {
						logger.info("读取" + property.getName() + "对象的" + subPropertyName + "属性的ReadMethod方法出错！", e);
					}
					break;
				}
			}
			return new Field(property.getName() + subPropertyName, subPropertyValue);
		}
	}

	public static class Field {
		private String name;
		private Object value;

		public Field(String name, Object value) {
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Object getValue() {
			return value;
		}

		public void setValue(Object value) {
			this.value = value;
		}
	}
}
