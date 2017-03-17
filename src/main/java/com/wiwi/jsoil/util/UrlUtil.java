package com.wiwi.jsoil.util;

public class UrlUtil
{
  public static boolean isInternal(String url)
  {
    if (url == null) return true;
    String newUrl = url.toLowerCase();

    return ((!(newUrl.startsWith("http:"))) && (!(newUrl.startsWith("www."))) && (!(newUrl.startsWith("https:"))));
  }
}