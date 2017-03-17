package com.wiwi.jsoil.exception;

public class NoLoginException extends Exception
{
  private static final long serialVersionUID = -911653100L;

  public NoLoginException(String msg)
  {
    super(msg);
  }

  public NoLoginException(Exception e) {
    super(e);
  }

  public NoLoginException(String msg, Throwable cause) {
    super(msg, cause);
  }
}