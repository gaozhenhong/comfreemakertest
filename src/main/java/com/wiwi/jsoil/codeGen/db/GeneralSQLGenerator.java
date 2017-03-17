package com.wiwi.jsoil.codeGen.db;

import com.wiwi.jsoil.db.DaoBase;
import com.wiwi.jsoil.exception.DaoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashMap;
import java.util.Map;

public abstract class GeneralSQLGenerator
  implements SQLGenerator
{
  protected Map<String, String> getColumnType(Connection conn, String tableName)
    throws DaoException
  {
    PreparedStatement ps = null;
    ResultSet rs = null;

    Map fieldClassMap = new HashMap();
    try {
      String sql = "select * from " + tableName + " where 1=2";

      ps = conn.prepareStatement(sql);
      rs = ps.executeQuery();
      int colLen = rs.getMetaData().getColumnCount();
      for (int i = 1; i <= colLen; ++i) {
        String fieldName = rs.getMetaData().getColumnName(i);
        String fieldClassName = rs.getMetaData().getColumnClassName(i);
        if (fieldClassName.equalsIgnoreCase("java.sql.Timestamp"))
          fieldClassName = "java.util.Date";

        fieldClassMap.put(fieldName, fieldClassName);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      DaoBase.closeRs(rs);
      DaoBase.closeStmt(ps);
     }

    return fieldClassMap;
  }
}