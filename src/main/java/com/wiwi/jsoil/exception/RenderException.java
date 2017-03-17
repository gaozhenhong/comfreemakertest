package com.wiwi.jsoil.exception;

public class RenderException extends Exception
{
  private static final long serialVersionUID = 1L;

  public RenderException(String msg)
  {
    super(msg);
  }

  public RenderException(Exception e) {
    super(e);
  }

  public RenderException(String msg, Throwable cause) {
    super(msg, cause);
  }
}