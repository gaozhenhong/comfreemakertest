package com.wiwi.jsoil.util;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class SqlUtil
{
  public static String getInSqlStr(List<String> list)
  {
    if (list == null) return "";
    String inSqlStr = "";
    for (Iterator localIterator = list.iterator(); localIterator.hasNext(); ) { String value = (String)localIterator.next();
      inSqlStr = inSqlStr + ",'" + value + "'";
    }

    if (inSqlStr.startsWith(",'"))
      inSqlStr = inSqlStr.substring(1);

    return inSqlStr;
  }

  public static String getInSqlStr(Set<Long> list)
  {
    if (list == null) return "";
    String inSqlStr = "";
    for (Iterator localIterator = list.iterator(); localIterator.hasNext(); ) { Long value = (Long)localIterator.next();
      inSqlStr = inSqlStr + ",'" + value + "'";
    }

    if (inSqlStr.startsWith(",'"))
      inSqlStr = inSqlStr.substring(1);

    return inSqlStr;
  }

  public static String getInSqlStr(String ids)
  {
    if (ids == null) return "";
    if (ids.startsWith(","))
      ids = ids.substring(1);

    if (ids.endsWith(","))
      ids = ids.substring(0, ids.length() - 1);

    String inSqlStr = "'" + ids.replace(",", "','") + "'";
    return inSqlStr;
  }

  public static void main(String[] args)
  {
    List idsList = new ArrayList();
    idsList.add(Long.valueOf(3L));
    idsList.add(Long.valueOf(5L));
    idsList.add(Long.valueOf(6L));
    idsList.add(Long.valueOf(9L));
    idsList.add(Long.valueOf(33L));
    idsList.add(Long.valueOf(3L));
    Set set = new HashSet(idsList);
    System.out.println(set);

    idsList = new ArrayList(set);
    System.out.println(idsList);
  }
}