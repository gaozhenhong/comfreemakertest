package com.wiwi.jsoil.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

public class QRCodeUtil
{
  private static final Logger logger = LoggerFactory.getLogger(QRCodeUtil.class);
  
  public static void genQRCode(String content, String filePath, String fileName, int width, int height)
  {
    String format = "png";
    Map<EncodeHintType, Object> hints = new HashMap();
    hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
    try
    {
      BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
      
      Path path = FileSystems.getDefault().getPath(filePath, new String[] { fileName });
      
      MatrixToImageWriter.writeToFile(bitMatrix, format, new File(filePath+"/"+fileName));
    }
    catch (WriterException e)
    {
      logger.error("生成二维码矩阵失败，异常信息：{}", e.getMessage());
      e.printStackTrace();
    }
    catch (IOException e)
    {
      logger.error("生成二维码文件失败，异常信息：{}", e.getMessage());
      e.printStackTrace();
    }
  }
  
  public static void genQRCode(String content, String filePath, String fileName)
  {
    genQRCode(content, filePath, fileName, 200, 200);
  }
  
  public static String getQRCodeContent(String qrCodeFilePath)
  {
    if ((qrCodeFilePath == null) || (qrCodeFilePath.trim().isEmpty())) {
      return null;
    }
    return getQRCodeContent(new File(qrCodeFilePath));
  }
  
  public static String getQRCodeContent(File qrCodeFile)
  {
    if (qrCodeFile == null) {
      return null;
    }
    try
    {
      BufferedImage image = ImageIO.read(qrCodeFile);
      LuminanceSource source = new BufferedImageLuminanceSource(image);
      Binarizer binarizer = new HybridBinarizer(source);
      BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
      Map<DecodeHintType, Object> hints = new HashMap();
      hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
      Result result = new MultiFormatReader().decode(binaryBitmap, hints);
      return result.getText();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    catch (NotFoundException e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  public static void main(String[] args)
  {
    String filePath = "/Users/lichunyu/";
    String fileName = "zxing.png";
    String content = "http://www.liulangg.com";
    genQRCode(content, filePath, fileName);
    String getContent = getQRCodeContent(filePath + fileName);
    System.out.println("二维码中的内容：" + getContent);
  }
}
