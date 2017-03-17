package com.wiwi.jsoil.cms.util;

import java.util.ArrayList;
import java.util.List;

public class CmsConstants
{
  public static final int STATUS_DRAFT = 0;
  public static final int STATUS_APPROVE = 1;
  public static final int STATUS_NOT_APPROVED = 2;
  public static final int STATUS_PUBLISH = 3;
  public static final int STATUS_DELETE = 4;
  public static final String STATUS_DRAFT_ZH = "草稿";
  public static final String STATUS_APPROVE_ZH = "待审核";
  public static final String STATUS_NOT_APPROVED_ZH = "审核不通过";
  public static final String STATUS_PUBLISH_ZH = "已发布";
  public static final String STATUS_DELETE_ZH = "已删除";
  public static final String MEDTOD_APPROVE = "approve";
  public static final String MEDTOD_ADD = "add";

  public static String getStatusZh(int status)
  {
    switch (status)
    {
    case 0:
      return "草稿";
    case 1:
      return "待审核";
    case 2:
      return "审核不通过";
    case 3:
      return "已发布";
    case 4:
      return "已删除";
    }

    return "";
  }

  public List<Integer> getStatusValues()
  {
    List list = new ArrayList();
    list.add(Integer.valueOf(0));
    list.add(Integer.valueOf(1));
    list.add(Integer.valueOf(2));
    list.add(Integer.valueOf(3));
    list.add(Integer.valueOf(4));

    return list;
  }
}