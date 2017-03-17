package com.wiwi.jsoil.codeGen.service;

import com.wiwi.jsoil.codeGen.Column;
import com.wiwi.jsoil.codeGen.Function;
import com.wiwi.jsoil.codeGen.db.DBProcessor;
import com.wiwi.jsoil.db.ConnectionPool;
import com.wiwi.jsoil.exception.DaoException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CodeGenFromDbService
{
  static Log log = LogFactory.getLog(CodeGenFromDbService.class);
  private static Connection conn = null;

  private static Connection getConnection()
  {
    if (conn == null)
      try {
        conn = ConnectionPool.getInstance().getConnection(true);
      } catch (DaoException e) {
        e.printStackTrace();
      }

    return conn;
  }

  public static List<Column> getTableColumns(String tableName) {
    try {
      return DBProcessor.getTableColumn(getConnection(), tableName);
    } catch (DaoException e) {
      e.printStackTrace();

      return new ArrayList();
    }
  }

  public static List<String> getAllTables() {
    try {
      return DBProcessor.getAllTables(getConnection());
    } catch (DaoException e) {
      e.printStackTrace();

      return new ArrayList(); }
  }

  public static void genFile(Function function, String projectRealPath, String javaSrcPath, String viewPageSrcPath) {
    JavaFileGenerator.genModel(function, javaSrcPath);
    JavaFileGenerator.genQueryModel(function, javaSrcPath);
    JavaFileGenerator.genDao(function, javaSrcPath);
    JavaFileGenerator.genService(function, javaSrcPath);
    ControllerJavaFileGenerator.genController(function, javaSrcPath);
    if (function.getGenViewPage() != 2)
      ViewFileGenerator.genJspFile(function, projectRealPath, viewPageSrcPath);
  }
}