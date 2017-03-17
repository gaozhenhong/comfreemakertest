package com.wiwi.jsoil.codeGen.db;

import com.wiwi.jsoil.codeGen.Column;
import com.wiwi.jsoil.db.ConnectionPool;
import com.wiwi.jsoil.db.DaoBase;
import com.wiwi.jsoil.exception.DaoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MySqlSQLGenerator extends GeneralSQLGenerator
{

  public List<com.wiwi.jsoil.codeGen.Column> getTableColumn(Connection conn, String tableName)
    throws DaoException
  {
	  List<Column> fieldList = new ArrayList<Column>(); 
		PreparedStatement ps = null;
		ResultSet rs=null;
		String sql = "";
		String id = "";
		try {
			Map<String,String> columnTypeMap = getColumnType(conn,tableName);
			sql = "show full fields from "+tableName;
			ps=conn.prepareStatement(sql);
			rs = ps.executeQuery();
			Column column = null;
			String name = "";
			while(rs.next()){
				column = new Column();
				id = rs.getString("field");
				column.setId(id);
				name = rs.getString("comment");
				if(name == null || name.isEmpty()){
					name = id;
				}
				column.setName(name);
				column.setNotNull(!rs.getBoolean("null"));
				column.setType(columnTypeMap.get(id));
				fieldList.add(column);
			}
			return fieldList;
		} catch (Exception e) {
			throw new DaoException("在读取mysql 表字段注释时代码出现错误!sql:"+sql,e);
		} finally{
			DaoBase.closeRs(rs);
			DaoBase.closeStmt(ps);
		}	
  }

  public List<String> getAllTables(Connection conn)
    throws DaoException
  {
    List tables = new ArrayList();
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = "";
    try {
      conn = ConnectionPool.getInstance().getConnection(true);

      sql = "show tables";

      ps = conn.prepareStatement(sql);
      rs = ps.executeQuery();
      while (rs.next())
        tables.add(rs.getString(1));
    }
    catch (Exception e) {
    }
    finally {
      DaoBase.closeRs(rs);
      DaoBase.closeStmt(ps);
      }

    return tables;
  }
}