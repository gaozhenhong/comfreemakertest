package com.wiwi.jsoil.codeGen.db;

import com.wiwi.jsoil.codeGen.Column;
import com.wiwi.jsoil.exception.DaoException;
import java.sql.Connection;
import java.util.List;

public abstract interface SQLGenerator
{
  public abstract List<Column> getTableColumn(Connection paramConnection, String paramString)
    throws DaoException;

  public abstract List<String> getAllTables(Connection paramConnection)
    throws DaoException;
}