package com.wiwi.freego.hotel;

import com.wiwi.freego.hotel.model.Hotel;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.sys.model.City;
import com.wiwi.jsoil.sys.service.CityService;
import com.wiwi.jsoil.util.QRCodeUtil;
import com.wiwi.jsoil.util.ResourceUploadUtil;
import com.wiwi.jsoil.util.ToolsUtils;
import java.io.File;
import javax.servlet.http.HttpServletRequest;

public class HotelUtil
{
  public static final int CODE_LENGHT = 8;
  public static String hotelQrCodeImageUrl = "/uploadFile/hotel/qrCode/";
  public static String hotelQrCodeImageType = ".png";

  public static String genHotelCode(Hotel hotel)
  {
    String code = "";
    if (hotel == null)
      code = ToolsUtils.appendZero(0L, 8);
    else {
      String cityCode = "";
      try {
        City city = new CityService().get(hotel.getCity().getId().intValue());
        cityCode = (city == null) ? "" : city.getAbbreviation();
      } catch (DaoException e) {
        e.printStackTrace();
      }

      code = "CN" + cityCode + ToolsUtils.appendZero(Math.round(Math.random() * 1000.0D), 4);
    }
    return code;
  }

  public static String getHotelQrCodeUrl(HttpServletRequest request, Hotel hotel)
  {
    if (!(haveOrCodeImage(request, hotel)))
      genOrCodeImage(request, hotel);

    return hotelQrCodeImageUrl + hotel.getId() + hotelQrCodeImageType;
  }

  public static boolean haveOrCodeImage(HttpServletRequest request, Hotel hotel)
  {
    String rootPath = hotelQrCodeImageUrl;
    String filePath = rootPath + hotel.getId() + hotelQrCodeImageType;
    File hotelQrCodeImage = new File(filePath);

    return (hotelQrCodeImage.exists());
  }

  public static void genOrCodeImage(HttpServletRequest request, Hotel hotel)
  {
    String rootPath = ResourceUploadUtil.getImageSavePath(request, hotelQrCodeImageUrl);
    rootPath = rootPath.replace("//", "/");
    String fileName = hotel.getId() + hotelQrCodeImageType;
    String basePath = ResourceUploadUtil.getServerURI(request);
    String webUrl = basePath + String.format("/site/mobile/web/%S/index", new Object[] { hotel.getCode() });
    QRCodeUtil.genQRCode(webUrl, rootPath, fileName);
  }
}