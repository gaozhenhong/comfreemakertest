package com.wiwi.jsoil.util;

import com.wiwi.jsoil.db.KeyGenerator;
import com.wiwi.jsoil.sys.model.Organization;
import com.wiwi.jsoil.sys.model.Resource;
import com.wiwi.jsoil.sys.model.User;
import com.wiwi.jsoil.sys.service.ResourceService;
import java.io.File;
import java.io.PrintStream;
import java.util.Date;
import java.util.Iterator;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

public class ResourceUploadUtil
{
  private static final Logger logger = LoggerFactory.getLogger(ResourceUploadUtil.class);

  public static String generateFileName(Resource instance)
  {
    String resName = instance.getResName();
    String fileName = "";
    if (resName.lastIndexOf(".") > -1)
      fileName = instance.getId() + fileName + resName.substring(resName.lastIndexOf("."));
    else
      fileName = resName;

    return fileName;
  }

  public static String getImageSavePath(HttpServletRequest request, String folder)
  {
    String contentPath = getFileRealRootPath(request) + folder;
    File file = new File(contentPath);
    if (!(file.isDirectory()))
      file.mkdirs();

    return contentPath;
  }

  public static String getImageRelativePath(String folder)
  {
    return "/uploadFile/" + folder;
  }

  public static String getFileRealRootPath(HttpServletRequest request)
  {
    return request.getSession().getServletContext().getRealPath("/");
  }

  public static String getImageUrl(HttpServletRequest request, Resource resource)
  {
    if (resource == null)
      return "";

    String fileUploadUrl = getFileBasePath(request);
    return fileUploadUrl + resource.getResPath();
  }

  public static String getFileBasePath(HttpServletRequest request)
  {
    String requestUrl = request.getRequestURL().toString();
    String contentPath = request.getContextPath();
    String fileUploadUrl = requestUrl.substring(0, requestUrl.indexOf(contentPath) + contentPath.length());
    return fileUploadUrl;
  }

  public static String getServerURI(HttpServletRequest request)
  {
    String requestUrl = request.getRequestURL().toString();
    String contentPath = request.getContextPath();
    String fileUploadUrl = requestUrl.substring(0, requestUrl.indexOf(contentPath) + contentPath.length());
    return fileUploadUrl;
  }

  public static Resource uploadFile(HttpServletRequest request, User user, String defaultFolder) throws Exception
  {
    MultipartHttpServletRequest multiRequest = null;
    Iterator iter = null;
    Resource resourceInstance = null;
    CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());

    if (multipartResolver.isMultipart(request))
    {
      multiRequest = (MultipartHttpServletRequest)request;
      if (defaultFolder == null) {
        defaultFolder = "";
      }

      iter = multiRequest.getFileNames(); }
    try {
      while (iter.hasNext())
      {
        MultipartFile file = multiRequest.getFile((String)iter.next());
        if (file != null)
        {
          String myFileName = file.getOriginalFilename();
          String fileType = "";

          if (myFileName.trim() != "")
          {
            String resName = file.getOriginalFilename();
            fileType = resName.substring(resName.lastIndexOf("."));
            ResourceService service = new ResourceService();
            Long resourceId = Long.valueOf(RequestUtil.getLongParameter(multiRequest, "resourceId"));
            if (resourceId.longValue() == 0L) {
              resourceInstance = new Resource();
              long resId = KeyGenerator.getInstance().getNextKey("s_resource");
              resourceInstance.setId(Long.valueOf(resId));
            }
            else { resourceInstance = service.get(resourceId.longValue());
            }

            resourceInstance.setCreateTime(new Date());
            resourceInstance.setResName(resName);
            if (user != null) {
              resourceInstance.setOrgId(user.getOrg().getId());
              resourceInstance.setCreateUserId(user.getId());
            }
            resourceInstance.setResType(defaultFolder);
            resourceInstance.setFileType(fileType);
            String allFolder = getImageRelativePath(defaultFolder);
            String fileName = generateFileName(resourceInstance);
            resourceInstance.setResPath(allFolder + "/" + fileName);

            File fileOnServer = new File(getImageSavePath(request, allFolder), fileName);
            file.transferTo(fileOnServer);
            if (resourceId.longValue() == 0L)
              service.insert(resourceInstance);
            else
              service.update(resourceInstance);
          }
        }
      }
    }
    catch (Exception e) {
      logger.error("文件上传失败，请检查！{}", e);
      e.printStackTrace();
      System.err.println("不是一个有效的文件上传表单：the enctype must be multipart/form-data");
    }
    
    return resourceInstance;
  }
}