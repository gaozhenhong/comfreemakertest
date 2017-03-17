package com.wiwi.jsoil.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wiwi.jsoil.exception.DaoException;

public class KeyInfo {

	private static Logger log = LoggerFactory.getLogger(KeyInfo.class);

	public static String TABLE_KEYINFO = "S_keyInfo";

	public static String FIELD_TABLENAME = "tableName";

	public static String FIELD_KEYVALUE = "keyValue";

	public static String FIELD_MODIFYTIME = "lastModifyTime";

	private static int POOL_SIZE=20;

	public long maxKey;

	public long minKey;

	public long nextKey;

	private String keyName;

	public KeyInfo(String keyName) {
		this.keyName = keyName;
		retrieveFromDB();
	}

	public long getNextKey() {
		if (nextKey > maxKey) {
			retrieveFromDB();
		}
		return nextKey++;
	}

	private void retrieveFromDB() {
		ConnectionPool pool = ConnectionPool.getInstance();
		String sql = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			conn = pool.getConnection(true);
			sql="select " + FIELD_KEYVALUE + " from " + TABLE_KEYINFO
					+ " where " + FIELD_TABLENAME + "='" + keyName + "'";
			log.debug("查询新主键值sql="+sql);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				minKey = rs.getLong(1);
				update(conn);
			}else{
				try{
					sql="select max(id) from " + keyName;
					ps = conn.prepareStatement(sql);
			        rs = ps.executeQuery();
			        if(rs.next()){
			        	minKey=rs.getLong(1);
			        }else{
			        	minKey=0;
			        }
				}catch(SQLException e){
					log.error("得到原始表的最大主键是出错，可能原因是该表不存在，或者该表的id字段不存在！[table="+keyName+"]");
					log.error(sql);
					minKey=0;
				}
		        insert(conn);
			}
			maxKey = minKey + POOL_SIZE;
			nextKey = minKey+1;
		} catch (SQLException e) {
			log.error("从数据库获得最大键值时出错:",e);
		} catch (DaoException e) {
			log.error("从数据库获得最大键值时出错:",e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				log.error("从数据库获得最大键值时关闭连接出错:"+e);
			}
		}
	}

	private void insert(Connection conn) throws SQLException {
		String sql = null;
        PreparedStatement ps = null;
		try {
			sql = "insert into " + TABLE_KEYINFO + " ("
					+ FIELD_TABLENAME + "," + FIELD_KEYVALUE + ","+FIELD_MODIFYTIME+") values(?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, keyName);
			ps.setLong(2, minKey+POOL_SIZE);
			ps.setString(3, new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
			ps.executeUpdate();
		} catch (SQLException e) {
			log.error("插入"+FIELD_TABLENAME+"的最大键值("+(minKey+POOL_SIZE)+")时出错:"+e);
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				log.error("插入"+FIELD_TABLENAME+"的最大键值时关闭连接出错:"+e);
			}
		}
	}
	private void update(Connection conn) throws SQLException {
		String sql = null;
        PreparedStatement ps = null;
		try {
			sql = "update " + TABLE_KEYINFO + " set " + FIELD_KEYVALUE + "=?,"+FIELD_MODIFYTIME+"=? where "
				+ FIELD_TABLENAME + "='" + keyName + "'";
			ps = conn.prepareStatement(sql);
			ps.setLong(1, minKey+POOL_SIZE);
			ps.setString(2, new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
			ps.executeUpdate();
		} catch (SQLException e) {
			log.error("修改"+FIELD_TABLENAME+"的最大键值("+(minKey+POOL_SIZE)+")时出错:"+e);
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				log.error("修改"+FIELD_TABLENAME+"的最大键值时关闭连接出错:"+e);
			}
		}
	}
}
