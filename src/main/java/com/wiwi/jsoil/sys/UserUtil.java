package com.wiwi.jsoil.sys;

import com.wiwi.jsoil.sys.model.User;

public class UserUtil
{
  public static boolean isSystemAdmin(User user)
  {
    if (user == null) return false;
    return user.getLoginName().equals("sysadmin");
  }

  public static boolean isSystemAdmin(long userId)
  {
    return (userId == 1L);
  }

  public static boolean isSystemAdminByLoginName(String loginName)
  {
    return "sysadmin".equals(loginName);
  }
  
  public static boolean isAdminByLoginName(String loginName){
	  return "sysadmin".equals(loginName);
  }
}