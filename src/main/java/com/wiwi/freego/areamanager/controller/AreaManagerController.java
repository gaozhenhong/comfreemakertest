package com.wiwi.freego.areamanager.controller;

import com.wiwi.freego.areamanager.model.AreaManager;
import com.wiwi.freego.areamanager.model.AreaManagerQ;
import com.wiwi.freego.areamanager.service.AreaManagerService;
import com.wiwi.freego.areamanager.service.AreaUserMappingService;
import com.wiwi.freego.hotel.model.Hotel;
import com.wiwi.freego.hotel.model.HotelQ;
import com.wiwi.freego.hotel.service.HotelService;
import com.wiwi.jsoil.base.BaseController;
import com.wiwi.jsoil.sys.model.Category;
import com.wiwi.jsoil.sys.model.CategoryQ;
import com.wiwi.jsoil.sys.model.UserQ;
import com.wiwi.jsoil.sys.service.CategoryService;
import com.wiwi.jsoil.sys.service.UserService;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/areamanager/"})
public class AreaManagerController extends BaseController
{
  private static final Logger logger = LoggerFactory.getLogger(AreaManagerController.class);
  AreaManagerService service;

  public AreaManagerController()
  {
    this.service = new AreaManagerService();
  }

  @RequestMapping({"list.do"})
  public String list(Model model, CategoryService categoryService)
    throws Exception
  {
    CategoryQ categoryQ = new CategoryQ();
    categoryQ.setParentCategoryCode("areaManager");
    model.addAttribute("categoryList", categoryService.getList(categoryQ));

    processOperationMessage(model);

    return "/areamanager/areaManagerList";
  }

  @RequestMapping({"add.do"})
  public String addView(Model model)
  {
    model.addAttribute("instance", new AreaManager());

    return "/areamanager/areaManagerAdd";
  }

  @RequestMapping({"addAction.do"})
  public String addAction(@ModelAttribute("instance") AreaManager instance, Model model)
    throws Exception
  {
    this.service.insert(instance);

    setOperationMessage("添加成功！");

    return "redirect:list.do";
  }

  @RequestMapping({"edit.do"})
  public String editView(@RequestParam long id, Model model)
    throws Exception
  {
    model.addAttribute("instance", this.service.get(id));

    return "/areamanager/areaManagerEdit";
  }

  @RequestMapping({"editAction.do"})
  public String editAction(@ModelAttribute("instance") AreaManager instance, Model model)
    throws Exception
  {
    this.service.update(instance);

    setOperationMessage("修改成功！");

    return "redirect:list.do";
  }

  @RequestMapping({"view.do"})
  public String view(@RequestParam long id, @RequestParam(required=false, value="isModal") Boolean isModal, Model model)
    throws Exception
  {
    model.addAttribute("instance", this.service.get(id));

    model.addAttribute("isModal", Boolean.valueOf(isModal.booleanValue()));

    return "/areamanager/areaManagerView";
  }

  @RequestMapping({"deleteAction.do"})
  public String deleteAction(@RequestParam(required=true) long id, Model model)
    throws Exception
  {
    this.service.delete(id);

    setOperationMessage("删除成功！");

    return "redirect:list.do";
  }

  @RequestMapping({"batchDeleteAction.do"})
  public String batchDeleteAction(@RequestParam(value="ids", required=true) String ids, Model model)
    throws Exception
  {
    this.service.batchDelete(ids);

    setOperationMessage("批量删除成功！");

    return "redirect:list.do";
  }

  @RequestMapping({"areaUserList.do"})
  public String areaUserList(@ModelAttribute("query") UserQ userQ, @RequestParam(required=true, value="areaId") Long areaId, Model model)
    throws Exception
  {
    Category category = new CategoryService().get(areaId.longValue());
    userQ.setStatus("1");
    String otherCondition = " where id in (select userId from fg_area_user_mapping where areaId=" + areaId + ")";
    userQ.setOtherCondition(otherCondition);
    model.addAttribute("userList", new UserService().getList(userQ));
    model.addAttribute("pager", userQ);
    model.addAttribute("category", category);
    processOperationMessage(model);

    return "/areamanager/areaUserList";
  }

  @RequestMapping({"notAreaUserList.do"})
  public String notAreaUserList(@ModelAttribute("query") UserQ userQ, @RequestParam(required=true, value="areaId") Long areaId, Model model)
    throws Exception
  {
    Category category = new CategoryService().get(areaId.longValue());
    userQ.setStatus("1");
    String otherCondition = " where id not in (select userId from fg_area_user_mapping where areaId=" + areaId + ")";
    userQ.setOtherCondition(otherCondition);
    model.addAttribute("userList", new UserService().getList(userQ));
    model.addAttribute("pager", userQ);
    model.addAttribute("category", category);
    processOperationMessage(model);

    return "/areamanager/notAreaUserList";
  }

  @RequestMapping({"addUserToAreaAction.do"})
  @ResponseBody
  public String addUserToAreaAction(@RequestParam(value="ids", required=true) String ids, @RequestParam(value="areaId", required=true) int areaId, Model model)
    throws Exception
  {
    if (ids == null)
      ids = "";
    if (ids.startsWith(","))
      ids = ids.substring(1);
    if (ids.endsWith(","))
      ids = ids.substring(0, ids.length() - 1);
    String[] useIdArray = ids.split(",");
    Long[] idLongs = new Long[useIdArray.length];
    for (int i = 0; i < useIdArray.length; ++i) {
      idLongs[i] = Long.valueOf(Long.parseLong(useIdArray[i]));
    }

    AreaUserMappingService mappService = new AreaUserMappingService();
    mappService.addUserToArea(areaId, idLongs);
    return "success";
  }

  @RequestMapping({"deleteUserFromAreaAction.do"})
  public String deleteUserFromAreaAction(@RequestParam(value="ids", required=true) String ids, @RequestParam(value="areaId", required=true) int areaId, Model model) throws Exception
  {
    new AreaUserMappingService().removeUserFromArea(areaId, ids);

    setOperationMessage("删除成功！");

    return "redirect:list.do";
  }

  @RequestMapping({"areaHotelList.do"})
  public String areaHotelList(@ModelAttribute("query") HotelQ hotelQ, @RequestParam(required=true, value="areaId") Long areaId, Model model)
    throws Exception
  {
    Category category = new CategoryService().get(areaId.longValue());
    hotelQ.setCategory(category);
    model.addAttribute("hotelList", new HotelService().getList(hotelQ));
    model.addAttribute("pager", hotelQ);
    model.addAttribute("category", category);
    processOperationMessage(model);

    return "/areamanager/areaHotelList";
  }

  @RequestMapping({"noAreaHotelList.do"})
  public String noAreaHotelList(@ModelAttribute("query") HotelQ hotelQ, @RequestParam(required=true, value="areaId") Long areaId, Model model)
    throws Exception
  {
    Category category = new CategoryService().get(areaId.longValue());
    hotelQ.setOtherCondition(" where categoryId is null ");
    model.addAttribute("hotelList", new HotelService().getList(hotelQ));
    model.addAttribute("pager", hotelQ);
    model.addAttribute("category", category);
    processOperationMessage(model);

    return "/areamanager/notAreaHotelList";
  }

  @RequestMapping({"addHotelToAreaAction.do"})
  @ResponseBody
  public String addHotelToAreaAction(@RequestParam(value="ids", required=true) String ids, @RequestParam(value="areaId", required=true) int areaId, Model model)
    throws Exception
  {
    Category category = new CategoryService().get(areaId);
    if (ids == null)
      ids = "";
    if (ids.startsWith(","))
      ids = ids.substring(1);
    if (ids.endsWith(","))
      ids = ids.substring(0, ids.length() - 1);
    String[] hotelIdArray = ids.split(",");
    HotelService hotelService = new HotelService();
    for (int i = 0; i < hotelIdArray.length; ++i) {
      Hotel hotel = hotelService.get(Long.parseLong(hotelIdArray[i]));
      if (hotel != null) {
        hotel.setCategory(category);
        hotelService.update(hotel);
      }
    }

    return "success";
  }

  @RequestMapping({"deleteHotelFromAreaAction.do"})
  public String deleteHotelFromAreaAction(@RequestParam(value="ids", required=true) String ids, @RequestParam(value="areaId", required=true) int areaId, Model model) throws Exception
  {
    if (ids == null)
      ids = "";
    if (ids.startsWith(","))
      ids = ids.substring(1);
    if (ids.endsWith(","))
      ids = ids.substring(0, ids.length() - 1);
    String[] hotelIdArray = ids.split(",");
    HotelService hotelService = new HotelService();
    for (int i = 0; i < hotelIdArray.length; ++i) {
      hotelService.deleteHotelCategory(Long.parseLong(hotelIdArray[i]));
    }

    setOperationMessage("删除成功！");

    return "redirect:list.do";
  }

  @RequestMapping({"addHotelLocation.do"})
  @ResponseBody
  public String addHotelLocation(Model model, @RequestParam(value="areaId", required=true) long areaId, @RequestParam(value="scene", required=true) String scene, @RequestParam(value="ath", required=true) float ath, @RequestParam(value="atv", required=true) float atv, @RequestParam(value="hotelId", required=true) long hotelId, HttpServletRequest request)
  {
    Category area;
    try
    {
      AreaManager areaManager;
      area = new CategoryService().get(areaId);
      Hotel hotel = new HotelService().get(hotelId);
      AreaManagerQ query = new AreaManagerQ();
      query.setHotelId(Long.valueOf(hotelId));
      List list = this.service.getList(query);

      if ((list != null) && (list.size() > 0)) {
        areaManager = (AreaManager)list.get(0);
        areaManager.setScene(scene);
        areaManager.setLatitude(Float.valueOf(ath));
        areaManager.setLongitude(Float.valueOf(atv));

        this.service.update(areaManager);
      } else {
        areaManager = new AreaManager();
        areaManager.setHotelId(Long.valueOf(hotelId));
        areaManager.setScene(scene);
        areaManager.setLatitude(Float.valueOf(ath));
        areaManager.setLongitude(Float.valueOf(atv));

        this.service.insert(areaManager);
      }

      write3DMapConfigXml(area, hotel, areaManager, request);
    } catch (Exception e) {
      return "failed";
    }

    return "success";
  }

  private synchronized void write3DMapConfigXml(Category area, Hotel hotel, AreaManager areaManager, HttpServletRequest request)
  {
    XMLWriter xmlWriter = null;
    try {
      String basePath = System.getProperty("webapp.root");
      basePath = basePath + "\\pano\\project\\" + area.getCode() + "\\config.xml";
      SAXReader reader = new SAXReader();
      Document dom = reader.read(new File(basePath));
      if (dom != null) {
        Element root = dom.getRootElement();
        Element e = (Element)root.selectObject("myspots");
        if (e != null) {
          Iterator iterator = e.elementIterator("hotspot");
          while (iterator.hasNext()) {
            Element oldEle = (Element)iterator.next();
            if (oldEle.attribute("id").getValue().equals(hotel.getId()))
              e.remove(oldEle);

          }

          Element newElement = e.addElement("hotspot");
          newElement.addAttribute("id", hotel.getId()+"");
          newElement.addAttribute("name", hotel.getName());
          newElement.addAttribute("scene", areaManager.getScene());
          newElement.addAttribute("title", hotel.getName());
          newElement.addAttribute("style", "hotspot_40");
          newElement.addAttribute("ath", areaManager.getLatitude()+"");
          newElement.addAttribute("atv", areaManager.getLongitude()+"");
          newElement.addAttribute("linkedscene", request.getContextPath() + "/" + hotel.webSiteUrl());
          newElement.addAttribute("hotspot_type", "1");
        }

        xmlWriter = new XMLWriter(
          new BufferedOutputStream(new FileOutputStream(new File(basePath))));
        xmlWriter.write(dom);
        xmlWriter.flush();
      }
    }
    catch (Exception basePath) {
      if (xmlWriter == null) return;  } finally {
      if (xmlWriter != null)
        try {
          xmlWriter.close();
        } catch (Exception localException2) {
        }

      if (xmlWriter == null) return; 
    }
    try {
      xmlWriter.close();
    }
    catch (Exception localException3)
    {
    }
  }

  @RequestMapping(value={"getHotel.do"}, produces={"application/x-www-form-urlencoded; charset=utf-8"})
  @ResponseBody
  public String getHotel(Model model, @RequestParam(value="areaId", required=true) long areaId) throws Exception
  {
    Category category = new CategoryService().get(areaId);
    HotelQ hotelQ = new HotelQ();
    hotelQ.setCategory(category);
    List list = new HotelService().getList(hotelQ);
    JSONArray result = new JSONArray();
    if ((list != null) && (list.size() > 0))
      for (Iterator localIterator = list.iterator(); localIterator.hasNext(); ) { Hotel hotel = (Hotel)localIterator.next();
        JSONObject obj = new JSONObject();
        obj.put("hotelId", hotel.getId());
        obj.put("hotelName", hotel.getName());

        AreaManagerQ query = new AreaManagerQ();
        query.setHotelId(hotel.getId());
        List areaList = this.service.getList(query);
        JSONObject locationObj = new JSONObject();
        if ((areaList != null) && (areaList.size() > 0)) {
          AreaManager am = (AreaManager)areaList.get(0);
          locationObj.put("scene", am.getScene());
          locationObj.put("latitude", am.getLatitude());
          locationObj.put("longitude", am.getLongitude());
        }

        obj.put("location", locationObj);

        result.put(obj);
      }


    return result.toString();
  }
}