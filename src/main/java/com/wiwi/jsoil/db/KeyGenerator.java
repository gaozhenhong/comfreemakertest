package com.wiwi.jsoil.db;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class KeyGenerator
{
  private HashMap<String, KeyInfo> keyMap = new HashMap(10);
  private KeyInfo keyInfo;
  private static KeyGenerator keyGenerator = null;

  public static KeyGenerator getInstance()
  {
    if (keyGenerator == null)
      keyGenerator = new KeyGenerator();

    return keyGenerator;
  }

  public synchronized String getUUID() {
    UUID uuid = UUID.randomUUID();
    return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + uuid.toString();
  }

  public synchronized long getNextKey(String keyName) {
    if (this.keyMap.containsKey(keyName))
      this.keyInfo = ((KeyInfo)this.keyMap.get(keyName));
    else {
      this.keyInfo = new KeyInfo(keyName);
      this.keyMap.put(keyName, this.keyInfo);
    }
    return this.keyInfo.getNextKey();
  }

  public static void main(String[] a) {
    System.out.println(getInstance().getUUID());
    System.out.println(getInstance().getUUID().length());
    System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
  }
}