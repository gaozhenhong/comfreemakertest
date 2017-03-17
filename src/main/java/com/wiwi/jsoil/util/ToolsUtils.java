package com.wiwi.jsoil.util;

public class ToolsUtils
{
  static String[] HanDigiStr = { "零", "壹", "贰", "叁", "肆", "伍", 
    "陆", "柒", "捌", "玖" };
  static String[] HanDiviStr = { "", "拾", "佰", "仟", "万", "拾", 
    "佰", "仟", "亿", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", 
    "仟", "万", "拾", "佰", "仟" };
  
  public static String appendZero(long sourceNumber, int theLength)
  {
    String returnStr = String.valueOf(sourceNumber);
    int sourceLength = returnStr.length();
    for (int i = 0; i < theLength - sourceLength; i++) {
      returnStr = "0" + returnStr;
    }
    return returnStr;
  }
  
  public static String prependChar(String sourceString, int theLength, char c)
  {
    String returnStr = sourceString;
    int sourceLength = returnStr.length();
    for (int i = 0; i < theLength - sourceLength; i++) {
      returnStr = c + returnStr;
    }
    return returnStr;
  }
  
  public static double roundNumber(double sourceNumber, int decimalLength)
  {
    long theValue = Math.round(sourceNumber * Math.pow(10.0D, decimalLength));
    return theValue / Math.pow(10.0D, decimalLength);
  }
  
  public static String numberToChinese(long num)
  {
    String str = "";
    if (num < 0L) {
      str = "负";
    }
    String numStr = Math.abs(num)+"";
    boolean lastzero = false;
    boolean hasvalue = false;
    
    int len = numStr.length();
    if (len > 15) {
      return null;
    }
    for (int i = len - 1; i >= 0; i--) {
      if (numStr.charAt(len - i - 1) != ' ')
      {
        int n = numStr.charAt(len - i - 1) - '0';
        if ((n < 0) || (n > 9)) {
          return null;
        }
        if (n != 0)
        {
          if (lastzero) {
            str = str + HanDigiStr[0];
          }
          if ((n != 1) || (i % 4 != 1) || (i != len - 1)) {
            str = str + HanDigiStr[n];
          }
          str = str + HanDiviStr[i];
          hasvalue = true;
        }
        else if ((i % 8 == 0) || ((i % 8 == 4) && (hasvalue)))
        {
          str = str + HanDiviStr[i];
        }
        if (i % 8 == 0) {
          hasvalue = false;
        }
        lastzero = (n == 0) && (i % 4 != 0);
      }
    }
    if (str.length() == 0) {
      return HanDigiStr[0];
    }
    return str;
  }
  
  public static String numberStrToChinese(String numStr)
  {
    String str = "";
    if (numStr.length() == 0) {
      return "";
    }
    int len = numStr.length();
    for (int i = len - 1; i >= 0; i--)
    {
      if (numStr.charAt(len - i - 1) == '.') {
        str = str + "点";
      }
      if (numStr.charAt(len - i - 1) != ' ')
      {
        int n = numStr.charAt(len - i - 1) - '0';
        if ((n < 0) || (n > 9)) {
          return null;
        }
        str = str + HanDigiStr[n];
      }
    }
    return str;
  }
}
