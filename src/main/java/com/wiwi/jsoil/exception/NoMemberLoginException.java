package com.wiwi.jsoil.exception;

public class NoMemberLoginException extends Exception
{
  private static final long serialVersionUID = 2131223431L;

  public NoMemberLoginException(String msg)
  {
    super(msg);
  }

  public NoMemberLoginException(Exception e) {
    super(e);
  }

  public NoMemberLoginException(String msg, Throwable cause) {
    super(msg, cause);
  }
}