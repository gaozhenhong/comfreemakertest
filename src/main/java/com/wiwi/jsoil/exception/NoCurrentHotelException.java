package com.wiwi.jsoil.exception;

public class NoCurrentHotelException extends Exception
{
  private static final long serialVersionUID = 1L;

  public NoCurrentHotelException(String msg)
  {
    super(msg);
  }

  public NoCurrentHotelException(Exception e) {
    super(e);
  }

  public NoCurrentHotelException(String msg, Throwable cause) {
    super(msg, cause);
  }
}