package com.wiwi.jsoil.util;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.security.MessageDigest;

public class MD5Util
{
  public static void main(String[] args)
  {
    if (Array.getLength(args) == 0)
    {
      System.out.println("MD5Util Test suite:");
      String s = new String("adminsys");
      System.out.println("MD5Util(\"adminsys\"):" + string2MD5(s));

      System.out.println("MD5Util(\"\"):" + string2MD5(""));
      System.out.println("MD5Util(\"adminsys\"):" + string2MD5("adminsys"));
      System.out.println("MD5Util(\"a\"):" + string2MD5("a"));
      System.out.println("MD5Util(\"abc\"):" + string2MD5("abc"));
      System.out.println("MD5Util(\"message digest\"):" + string2MD5("message digest"));
      System.out.println("MD5Util(\"abcdefghijklmnopqrstuvwxyz\"):" + string2MD5("abcdefghijklmnopqrstuvwxyz"));
      System.out.println("MD5Util(\"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789\"):" + string2MD5("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"));
      System.out.println("MD5Util(\"这是测试密码\"):" + string2MD5("这是测试密码"));
      System.out.println("MD5Util(\"中文真的可以吗？～！@#￥%……&*（）——+~~!@#$%^&*((())________+-\"):" + string2MD5("中文真的可以吗？"));
    }
    else System.out.println("MD5Util(" + args[0] + ")=" + string2MD5(args[0]));
  }

  public static String string2MD5(String inStr)
  {
    MessageDigest md5 = null;
    try {
      md5 = MessageDigest.getInstance("MD5");
    } catch (Exception e) {
      System.err.println("获取MD5加密算法失败！！！！");
      e.printStackTrace();
      return "";
    }
    char[] charArray = inStr.toCharArray();
    try {
      byte[] byteArray = inStr.getBytes("utf-8");

      for (int i = 0; i < charArray.length; ++i)
        byteArray[i] = (byte)charArray[i];

      byte[] md5Bytes = md5.digest(byteArray);
      StringBuffer hexValue = new StringBuffer();
      for (int i = 0; i < md5Bytes.length; ++i) {
        int val = md5Bytes[i] & 0xFF;
        if (val < 16)
          hexValue.append("0");

        hexValue.append(Integer.toHexString(val));
      }
      return hexValue.toString();
    } catch (UnsupportedEncodingException e) {
      System.err.println("MD5加密失败！！！！");
      e.printStackTrace();

      return "";
    }
  }
}