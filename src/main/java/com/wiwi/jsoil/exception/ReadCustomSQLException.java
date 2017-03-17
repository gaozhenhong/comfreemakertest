package com.wiwi.jsoil.exception;

public class ReadCustomSQLException extends DaoException
{
  private static final long serialVersionUID = -684385476L;

  public ReadCustomSQLException(String msg)
  {
    super(msg);
  }

  public ReadCustomSQLException(Exception e) {
    super(e);
  }

  public ReadCustomSQLException(String msg, Throwable cause) {
    super(msg, cause);
  }
}