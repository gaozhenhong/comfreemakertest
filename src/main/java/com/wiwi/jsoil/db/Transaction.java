package com.wiwi.jsoil.db;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;

public class Transaction {
	private Connection conn11 = null;
	
	/**
	 * 创建事务
	 * @throws DaoException
	 */
	public Transaction()throws DaoException{
		conn11=ConnectionPool.getInstance().getConnection(false);
	}
	
	/**
	 * 提交事务
	 * @throws DaoException
	 */
	public void commit()
	throws DaoException {
		try{
			conn11.commit();
		} catch (SQLException e) {
			try{
				conn11.rollback();
				throw new DaoException("执行事务出错，但回滚成功",e);
			}catch(SQLException se){
				throw new DaoException("事务回滚出错" ,se);
			}
		} finally {
			DaoBase.closeConn(conn11);
		}
	}
	
	/**
	 * 获得事务的连接
	 * @return
	 */
	public Connection getConnection(){
		return conn11;
	}
	
/*	*//**
	 * 得到对象
	 * @param selectSql
	 * @param targetClass
	 * @return
	 * @throws DaoException
	 * @throws RenderException
	 */
	/*
	public Object get(String selectSql, Class targetClass)
	throws DaoException,RenderException {
		PreparedStatement ps = null;
		ResultSet rs=null;
		Object obj=null;
		try {
			ps=conn11.prepareStatement(selectSql);
			rs = ps.executeQuery();
			while (rs.next()) {
				obj=DbAdapter.getBeanFormRs(targetClass,rs);
			}
		} catch (SQLException e) {
			throw new DaoException("从数据库查询单条记录的时候出现SQL错误：",e);
		} finally{
			DaoBase.closeRs(rs);
			DaoBase.closeStmt(ps);
		}		
		return obj;
		
	}
*/
	/**
	 * @param ps
	 * @param targetClass
	 * @return 从数据库返回单个值对象
	 * @throws DaoException
	 * @throws RenderException
	 */

/*	public Object get(PreparedStatement ps, Class targetClass)
	throws DaoException,RenderException {
		return DbAdapter.get(ps, targetClass);
		
	}
	
	public List getList(String sql, Class targetClass)
	throws DaoException,RenderException {
		PreparedStatement ps = null;
		try {
			ps=conn11.prepareStatement(sql);
			return getList(ps,targetClass);
		} catch (SQLException e) {
			throw new DaoException("从数据库查询列表时(不分页)候出现SQL错误.sql："+sql,e);
		}finally{
			DaoBase.closeStmt(ps);
		}
		
	}
	
	public List getList(PreparedStatement ps, Class targetClass)
	throws DaoException,RenderException {
		return DbAdapter.getList(ps, targetClass);
		
	}
	*//**
	 * @param selectSql
	 * @param pageUtil
	 * @param targetClass
	 * @return 从数据库返回分页列表
	 * @throws DaoException
	 * @throws RenderException
	 *//*
	public List getList(String selectSql, PageUtil pageUtil,Class targetClass)
	throws DaoException,RenderException {
		return getList(selectSql,pageUtil,targetClass,"id");
	}
	*//**
	 * @param selectSql
	 * @param pageUtil
	 * @param targetClass
	 * @param idName 数据库主键字段名－－用户排序所用！
	 * @return 从数据库返回分页列表
	 * @throws DaoException
	 * @throws RenderException
	 *//*
	public List getList(String selectSql, PageUtil pageUtil,Class targetClass,String idName)
	throws DaoException,RenderException {
		List<Object> list = new ArrayList<Object>();

		PreparedStatement ps = null;
		ResultSet rs=null;
		try {
			rs = DbAdapter.getRsPerPage(conn11, ps, selectSql, pageUtil, idName);
			while (rs.next()) {
				list.add(DbAdapter.getBeanFormRs(targetClass,rs));
			}
		} catch (SQLException e) {
			throw new DaoException("从数据库查询分页列表时候出现SQL错误：",e);
		}finally{
			DaoBase.closeRs(rs);
			DaoBase.closeStmt(ps);
		}
		return list;
		
	}
*/	
	
	
	public void deleteFromSingleTable(String ids,String tableName)throws DaoException{
		deleteFromSingleTable(ids,"id",tableName);
	}

	public void deleteFromSingleTable(String ids,String idNames,String tableName)throws DaoException{
		PreparedStatement ps = null;
		String sql=null;
		try {
			sql = "DELETE FROM "+tableName+" WHERE "+idNames+" IN (" + ids + ") ";
			ps = conn11.prepareStatement(sql);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("调用公用方法删除指定Ids的对象时出错.ID=" + ids+",执行Sql为："+sql,e);
		} finally {
			DaoBase.closeStmt(ps);
		}
	}
	
	public void deleteFromTables(String mainIds,String mainTableName,String[] slaveIdName,String[] slaveTableName )
			throws DaoException{
		deleteFromTables(mainIds,"id",mainTableName,slaveIdName,slaveTableName );
	}
	
	public void deleteFromTables(String mainIds,String mainIdName,String mainTableName,String[] slaveIdName,String[] slaveTableName )
			throws DaoException{
		PreparedStatement ps = null;
		String sql=null;
		try {
			if(slaveTableName!=null){
				for(int i=0;i<slaveTableName.length;i++){
					sql = "DELETE FROM "+slaveTableName[i]+" WHERE "+slaveIdName[i]+" IN (" + mainIds + ") ";
					ps = conn11.prepareStatement(sql);
					ps.executeUpdate();
				}
			}
			
			sql = "DELETE FROM "+mainTableName+" WHERE "+mainIdName+" IN (" + mainIds + ") ";
			ps = conn11.prepareStatement(sql);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("调用公用方法删除指定Ids的对象时出错.ID=" + mainIds+",执行Sql为："+sql, e);
		} finally {
			DaoBase.closeStmt(ps);
		}
	}
	/**
	 * @param source
	 * @param sourceClass
	 * @param tableName
	 * @throws DaoException 
	 * @throws SQLException 
	 */
	public void insert2SingleTable(Object source,String tableName) throws DaoException,RenderException{
		KeyGenerator keygen = KeyGenerator.getInstance();
		String id = keygen.getUUID();
		
		String sql = null;
		PreparedStatement ps=null;

		String fieldStr="id";
		String preParedStr="?";
		List preparedValueList=new ArrayList();
		
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(source.getClass());
			PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();
			for (int i = 0; i < properties.length; i++) {
				String propertyName = properties[i].getName();
				if(propertyName.equals("class")) continue;
				if(propertyName.equals("id")) {
					properties[i].getWriteMethod().invoke(source,new String[]{id});
					continue;
				}
				if (properties[i].getReadMethod() != null) {
					DbAdapter.Field field=DbAdapter.getField(properties[i],source);
					if(field == null){
						continue;
					}
					fieldStr+=","+field.getName();
					preParedStr+=",?";
					preparedValueList.add(field.getValue());
				}
			}
			
			sql = "insert into "+tableName+" ("+fieldStr+") VALUES("+preParedStr+")";
			//log.info("insertsql:"+sql);
			//log.info("id:"+id);
			ps = conn11.prepareStatement(sql);
			ps.setString(1, id);
			
			for(int i=0;i<preparedValueList.size();i++){
				ps.setObject(i+2, preparedValueList.get(i));
			}
			ps.executeUpdate();
		} catch (IntrospectionException e) {
			try{
				conn11.rollback();
			}catch(SQLException re){
				throw new DaoException("调用共用方法向数据库插入数据时调用回滚出错,执行Sql为："+sql,e);
			}
			throw new RenderException("调用共用方法向数据库插入数据时出现内省期间异常：",e);
		} catch (IllegalAccessException e) {
			try{
				conn11.rollback();
			}catch(SQLException re){
				throw new DaoException("调用共用方法向数据库插入数据时调用回滚出错,执行Sql为："+sql,e);
			}
			throw new RenderException("调用共用方法向数据库插入数据时出现无法访问异常：",e);
		} catch (InvocationTargetException e) {
			try{
				conn11.rollback();
			}catch(SQLException re){
				throw new DaoException("调用共用方法向数据库插入数据时调用回滚出错,执行Sql为："+sql,e);
			}
			throw new RenderException("调用共用方法向数据库插入数据时出现调用目标异常：",e);
		} catch (SQLException e) {
			try{
				conn11.rollback();
			}catch(SQLException re){
				throw new DaoException("调用共用方法向数据库插入数据时调用回滚出错,执行Sql为："+sql,e);
			}
			throw new DaoException("调用共用方法向数据库插入数据时出现SQL异常：",e);
		}finally{
			DaoBase.closeStmt(ps);
		}		
	}

	public void update2SingleTable(Object source,String tableName) throws DaoException,RenderException{
		update2SingleTable(source,tableName,true);
	}
	public void update2SingleTable(Object source,String tableName,boolean ignoreNullProperty) throws DaoException,RenderException{
		Object oId=new Object();
		
		String sql = null;
		PreparedStatement ps=null;

		String setSqlStr="";
		String whereSqlStr="id=?";
		List preparedValueList=new ArrayList();
		
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(source.getClass());
			PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();
			for (int i = 0; i < properties.length; i++) {
				String propertyName = properties[i].getName();
				if(propertyName.equals("class")) continue;
				if(propertyName.equals("id")) {
					oId=DbAdapter.getField(properties[i],source).getValue();
					
					continue;
				}
				if (properties[i].getReadMethod() != null) {
					DbAdapter.Field field=DbAdapter.getField(properties[i],source);
					if(field == null){
						continue;
					}
					if(ignoreNullProperty&&field.getValue()==null){
						continue;
					}
					setSqlStr+=","+field.getName()+"=?";
					//preParedStr+=",?";
					preparedValueList.add(field.getValue());
				}
			}
			
			sql = "update "+tableName+" set "+setSqlStr.substring(1)+" where "+whereSqlStr;
			ps = conn11.prepareStatement(sql);
			
			int i;
			for(i=0;i<preparedValueList.size();i++){
				ps.setObject(i+1, preparedValueList.get(i));
			}
			ps.setObject(i+1, oId);
			ps.executeUpdate();
		} catch (IntrospectionException e) {
			try{
				conn11.rollback();
			}catch(SQLException re){
				throw new DaoException("调用共用方法向数据库修改数据时调用回滚出错,执行Sql为："+sql,e);
			}
			throw new RenderException("调用共用方法向数据库修改数据时出现内省期间异常：",e);
		} catch (IllegalAccessException e) {
			try{
				conn11.rollback();
			}catch(SQLException re){
				throw new DaoException("调用共用方法向数据库修改数据时调用回滚出错,执行Sql为："+sql,e);
			}
			throw new RenderException("调用共用方法向数据库修改数据时出现无法访问异常：",e);
		} catch (InvocationTargetException e) {
			try{
				conn11.rollback();
			}catch(SQLException re){
				throw new DaoException("调用共用方法向数据库修改数据时调用回滚出错,执行Sql为："+sql,e);
			}
			throw new RenderException("调用共用方法向数据库修改数据时出现调用目标异常：",e);
		} catch (SQLException e) {
			try{
				conn11.rollback();
			}catch(SQLException re){
				throw new DaoException("调用共用方法向数据库修改数据时调用回滚出错,执行Sql为："+sql,e);
			}
			throw new DaoException("调用共用方法向数据库修改数据时出现SQL异常：",e);
		}finally{
			DaoBase.closeStmt(ps);
		}		
	}

	public void executeUpdate(String sql)throws DaoException{
		PreparedStatement ps = null;
		try {
			ps = conn11.prepareStatement(sql);
			ps.executeUpdate();
		} catch (SQLException e) {
			try{
				conn11.rollback();
			}catch(SQLException re){
				throw new DaoException("调用公用方法执行sql更新时调用回滚出错,执行Sql为："+sql,e);
			}
			throw new DaoException("调用公用方法执行sql更新时出错,执行Sql为："+sql,e);
		} finally {
			DaoBase.closeStmt(ps);
		}
	}

/*	public ResultSet executeQuery(String sql)throws DaoException{
		PreparedStatement ps = null;
		try {
			ps = conn11.prepareStatement(sql);
			return ps.executeQuery();
		} catch (SQLException e) {
			throw new DaoException("调用公用方法执行sql查询时出错,执行Sql为："+sql,e);
		} finally {
			DaoBase.closeStmt(ps);
		}
	}

	public boolean havaRecord(String sql)throws DaoException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn11.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()){
				return true;
			}
			return false;
		} catch (SQLException e) {
			throw new DaoException("调用公用方法执行sql查询时出错,执行Sql为："+sql,e);
		} finally {
			DaoBase.closeRs(rs);
			DaoBase.closeStmt(ps);
		}
	}
*/

	public Connection getConn() {
		return conn11;
	}
}
