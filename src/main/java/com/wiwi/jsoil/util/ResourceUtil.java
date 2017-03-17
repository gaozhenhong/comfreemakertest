package com.wiwi.jsoil.util;

import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.sys.model.Resource;
import com.wiwi.jsoil.sys.model.ResourceQ;
import com.wiwi.jsoil.sys.service.ResourceService;
import java.util.ArrayList;
import java.util.List;

public class ResourceUtil
{
  public static String getPath(long resourceId, String defaultPath)
  {
    if (resourceId == 0L) {
      return defaultPath;
    }
    String path = null;
    try
    {
      Resource resource = new ResourceService().get(resourceId);
      if (resource != null) {
        path = resource.getResPath();
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return (path == null) || (path.trim().length() < 1) ? defaultPath : path;
  }
  
  public static String getPath(Resource resource, String defaultPath)
  {
    String path = null;
    try
    {
      if (resource != null) {
        path = resource.getResPath();
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return (path == null) || (path.trim().length() < 1) ? defaultPath : path;
  }
  
  public static List<Resource> getResources(String className, String primaryKey, int count)
  {
    List<Resource> resources = new ArrayList();
    ResourceService rService = new ResourceService();
    ResourceQ rQuery = new ResourceQ();
    rQuery.setClassName(className);
    rQuery.setRecordPerPage(count);
    rQuery.setPrimaryKey(primaryKey);
    rQuery.setOrderByKind("desc");
    rQuery.setOrderByProperty("orderNo");
    rQuery.setNotResType("logo");
    try
    {
      resources = rService.getList(rQuery);
    }
    catch (DaoException|RenderException e)
    {
      e.printStackTrace();
    }
    return resources;
  }
}
