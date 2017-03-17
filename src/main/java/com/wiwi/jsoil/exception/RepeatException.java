package com.wiwi.jsoil.exception;

public class RepeatException extends Exception
{
  private static final long serialVersionUID = 391491962L;

  public RepeatException(String msg)
  {
    super(msg);
  }

  public RepeatException(Exception e) {
    super(e);
  }

  public RepeatException(String msg, Throwable cause) {
    super(msg, cause);
  }
}