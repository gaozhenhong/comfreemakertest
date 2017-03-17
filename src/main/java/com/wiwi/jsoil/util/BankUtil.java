package com.wiwi.jsoil.util;

public class BankUtil
{
  public static enum BANK
  {
    ICBC("中国工商银行"), ABC("中国农业银行"), CCB("中国建设银行"), CMB("招商银行"), 
    BOC("中国银行"), PSBC("中国邮政储蓄银行"), COMM("交通银行"), CITIC("中信银行"), 
    CMBC("中国民生银行"), CEB("中国光大银行"), CIB("兴业银行"), SPDB("浦发银行"), 

    GDB("广发银行"), SPABANK("平安银行"), HXBANK("华夏银行"), CCD("城市商业银行"), 
    RCB("农村商业银行"), RCC("农村信用社");

    String label = "";

    private BANK(String label) {
      this.label = label;
    }

    public String getLabel() {
      return this.label;
    }

    public void setLabel(String label) {
      this.label = label;
    }
  }
}