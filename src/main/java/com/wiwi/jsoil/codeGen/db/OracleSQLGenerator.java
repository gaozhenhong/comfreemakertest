package com.wiwi.jsoil.codeGen.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.wiwi.jsoil.codeGen.Column;
import com.wiwi.jsoil.db.ConnectionPool;
import com.wiwi.jsoil.db.DaoBase;
import com.wiwi.jsoil.exception.DaoException;

public class OracleSQLGenerator extends GeneralSQLGenerator
{
  // ERROR //
  public List<com.wiwi.jsoil.codeGen.Column> getTableColumn(Connection conn, String tableName)
    throws DaoException
  {
		PreparedStatement ps;
		Exception exception;
		List fieldList = new ArrayList();
		ps = null;
		ResultSet rs = null;
		String sql = "";
		String id = "";
		List list;
		try
		{
			Map columnTypeMap = getColumnType(conn, tableName);
			sql = (new StringBuilder("select *  from user_tab_columns  where Table_Name='")).append(tableName).append("'  order by column_name").toString();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			Column column = null;
			for (; rs.next(); fieldList.add(column))
			{
				column = new Column();
				id = rs.getString("column_name");
				column.setId(id);
				column.setName(id);
				column.setNotNull(!rs.getBoolean("nullable"));
				column.setType((String)columnTypeMap.get(id));
			}

			list = fieldList;
		}
		catch (Exception e)
		{
			throw new DaoException((new StringBuilder("在读取Oracle 表字段注释时代码出现错误!sql:")).append(sql).toString(), e);
		}
		finally
		{
			DaoBase.closeRs(rs);
			DaoBase.closeStmt(ps);
		}
	
		return list;
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

      sql = "select * from user_tables;";

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