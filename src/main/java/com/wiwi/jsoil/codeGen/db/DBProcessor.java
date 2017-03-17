package com.wiwi.jsoil.codeGen.db;

import java.sql.Connection;
import java.util.List;

import com.wiwi.jsoil.codeGen.Column;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.util.AppConstants;

public class DBProcessor
{
  private static SQLGenerator sqlGenerator = null;

  public static List<Column> getTableColumn(Connection conn, String tableName)
    throws DaoException
  {
    return getSQLGenerator().getTableColumn(conn, tableName);
  }

  public static List<String> getAllTables(Connection conn) throws DaoException
  {
    return getSQLGenerator().getAllTables(conn);
  }

  private static SQLGenerator getSQLGenerator()
    throws DaoException
  {
    if (sqlGenerator == null) {
      String str = AppConstants.DB_TYPE; 
      
		switch (str) {
	    	case AppConstants.DB_TYPE_MYSQL:
			sqlGenerator = new MySqlSQLGenerator();
			break;
			
	    	case AppConstants.DB_TYPE_ORALCE:
				sqlGenerator = new OracleSQLGenerator();
			break;
		}

      if (sqlGenerator == null)
        label87: throw new DaoException("鏍规嵁鏁版嵁搴撶被鍨嬭幏鍙朣QL澶勭悊鍣ㄦ椂鍑洪敊寮傚父锛氭棤娉曡幏鍙栧埌SQL澶勭悊鍣�!鏁版嵁搴撶被鍨�:" + AppConstants.DB_TYPE);
    }

    return sqlGenerator;
  }
}