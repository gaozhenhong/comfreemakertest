package com.wiwi.jsoil.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.wiwi.jsoil.db.KeyGenerator;
import com.wiwi.jsoil.sys.model.Resource;
import com.wiwi.jsoil.sys.model.User;
import com.wiwi.jsoil.sys.service.ResourceService;
import com.wiwi.jsoil.util.RequestUtil;
import com.wiwi.jsoil.util.ResourceUploadUtil;

@Controller
@RequestMapping({"/sys/file/"})
public class FileUploadController extends BaseController
{
  private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
  ResourceService service;

  public FileUploadController()
  {
    this.service = new ResourceService();
  }

  @RequestMapping({"imgUpload.do"})
  public String imgUpload(@RequestParam(value="imgId", required=false) Long imgId, @RequestParam(value="imgUrl", required=false) String imgUrl, @RequestParam(value="folder", required=false) String folder, @RequestParam(value="cropPresets", required=false) String cropPresets, Model model) throws Exception
  {
    model.addAttribute("imgId", imgId);
    model.addAttribute("cropPresets", cropPresets);
    model.addAttribute("folder", folder);
    String basePath = ResourceUploadUtil.getFileBasePath(getRequest());
    model.addAttribute("imgUrl", basePath + imgUrl);
    model.addAttribute("fileViewUrl", basePath);
    model.addAttribute("uploadPath", ResourceUploadUtil.getServerURI(getRequest()) + "/sys/file/uploadAction.do");
    logger.debug("图片上传路径为{}", "/sys/file/uploadAction.do");
    processOperationMessage(model);

    return "/sys/resource/imgUpload";
  }

  @RequestMapping({"uploadAction.do"})
  @ResponseBody
  public List<Resource> uploadAction(HttpServletRequest request, HttpServletResponse response) throws Exception
  {
    User user = getUser();

    List<Resource>resourceList = new ArrayList<Resource>();

    CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());

    if (multipartResolver.isMultipart(request))
    {
      MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
      String folder = multiRequest.getParameter("folder");

      Iterator iter = multiRequest.getFileNames();
      while (iter.hasNext())
      {
        MultipartFile file = multiRequest.getFile((String)iter.next());
        if (file != null)
        {
          String myFileName = file.getOriginalFilename();

          if (myFileName.trim() != "")
          {
            String resName = file.getOriginalFilename();
            ResourceService service = new ResourceService();
            Resource instance = null;
            Long imgId = Long.valueOf(RequestUtil.getLongParameter(multiRequest, "imgId"));
            if (imgId.longValue() == 0L) {
              instance = new Resource();
              long resId = KeyGenerator.getInstance().getNextKey("s_resource");
              instance.setId(Long.valueOf(resId));
            }
            else { instance = service.get(imgId.longValue());
            }

            instance.setCreateTime(new Date());
            instance.setResName(resName);
            instance.setOrgId(user.getOrg().getId());
            instance.setCreateUserId(user.getId());
            instance.setResType(folder);

            String allFolder = ResourceUploadUtil.getImageRelativePath(folder);
            String fileName = ResourceUploadUtil.generateFileName(instance);
            instance.setResPath(allFolder + "/" + fileName);

            File fileOnServer = new File(ResourceUploadUtil.getImageSavePath(request, allFolder), fileName);
            file.transferTo(fileOnServer);
            if (imgId.longValue() == 0L)
              service.insert(instance);
            else
              service.update(instance);

            logger.debug("文件[{}={}]上传成功", fileOnServer.getName(), fileOnServer.getAbsoluteFile());
            resourceList.add(instance);
          }
        }
      }
    }
    else
      System.err.println("不是一个有效的文件上传表单：the enctype must be multipart/form-data");

    return resourceList;
  }

  // ERROR //

  @RequestMapping({"/downloadAction"})
  public String download(long resourceId, HttpServletRequest request, HttpServletResponse response)
  {
	  
      try {
    	   ServletOutputStream out;  
    	   User user = getUser();
          //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
          response.setContentType("multipart/form-data");
          //2.设置文件头：最后一个参数是设置下载文件名(假如我们叫a.pdf)
    	   ResourceService service = new ResourceService();
           Resource instance = null;
           instance = service.get(resourceId);
           String respath = instance.getResPath();
    	   File file = new File(respath);
           FileInputStream inputStream = new FileInputStream(file);
           response.setHeader("Content-Disposition", "attachment;fileName="+instance.getResName());
          //3.通过response获取ServletOutputStream对象(out)
          out = response.getOutputStream();

          int b = 0;
          byte[] buffer = new byte[512];
          while (b != -1){
              b = inputStream.read(buffer);
              //4.写到输出流(out)中
              out.write(buffer,0,b);
          }
          inputStream.close();
          out.close();
          out.flush();
          service.addDownloadCount(resourceId,user);

      } catch (Exception e) {
          e.printStackTrace();
      }
      return "";
  }
}