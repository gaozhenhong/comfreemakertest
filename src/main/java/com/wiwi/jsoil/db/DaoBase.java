package com.wiwi.jsoil.db;

import com.wiwi.jsoil.exception.DaoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DaoBase
{
  private static ConnectionPool pool = ConnectionPool.getInstance();

  public static Connection getConn(boolean autoCommit)
    throws DaoException
  {
    return pool.getConnection(autoCommit);
  }

  public static void closeConn(Connection conn) throws DaoException {
    try {
      if (conn != null)
        conn.close();
    }
    catch (SQLException e) {
      throw new DaoException(e);
    }
  }

  public static void closeStmt(PreparedStatement stmt) throws DaoException {
    try {
      if (stmt != null)
        stmt.close();
    }
    catch (SQLException e) {
      throw new DaoException(e);
    }
  }

  public static void closeStmt(Statement stmt) throws DaoException {
    try {
      if (stmt != null)
        stmt.close();
    }
    catch (SQLException e) {
      throw new DaoException(e);
    }
  }

  public static void closeRs(ResultSet rs) throws DaoException {
    try {
      if (rs != null)
        rs.close();
    }
    catch (SQLException e) {
      throw new DaoException(e);
    }
  }
}