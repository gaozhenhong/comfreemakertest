package com.wiwi.jsoil.exception;

public class DaoException extends Exception
{
  private static final long serialVersionUID = 1576845789L;

  public DaoException(String msg)
  {
    super(msg);
  }

  public DaoException(Exception e) {
    super(e);
  }

  public DaoException(String msg, Throwable cause) {
    super(msg, cause);
  }
}