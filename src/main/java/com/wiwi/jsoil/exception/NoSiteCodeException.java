package com.wiwi.jsoil.exception;

public class NoSiteCodeException extends Exception
{
  private static final long serialVersionUID = 1L;

  public NoSiteCodeException(String msg)
  {
    super(msg);
  }

  public NoSiteCodeException(Exception e) {
    super(e);
  }

  public NoSiteCodeException(String msg, Throwable cause) {
    super(msg, cause);
  }
}