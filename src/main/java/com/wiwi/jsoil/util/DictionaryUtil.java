package com.wiwi.jsoil.util;

import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.exception.RenderException;
import com.wiwi.jsoil.sys.model.DictionaryOption;
import com.wiwi.jsoil.sys.service.DictionaryOptionService;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DictionaryUtil
{
  private static final Logger logger = LoggerFactory.getLogger(DictionaryUtil.class);
  
  public static List<DictionaryOption> getDictionaryOption(String dictionaryCode)
  {
    try
    {
      return new DictionaryOptionService().getListByDictionaryCode(dictionaryCode);
    }
    catch (DaoException|RenderException e)
    {
      logger.error("读取数据字典项列表错误，字典代码[{}]", dictionaryCode, e);
      e.printStackTrace();
    }
    return new ArrayList();
  }
  
  public static DictionaryOption getOptionName(String fullDicationaryCodes)
  {
    if ((fullDicationaryCodes == null) || (fullDicationaryCodes.split("#").length < 2)) {
      return new DictionaryOption("未知类型");
    }
    try
    {
      String dictionaryCode = fullDicationaryCodes.split("#")[0];
      String dictionaryOptionCode = fullDicationaryCodes.split("#")[1];
      
      return new DictionaryOptionService().getByCode(dictionaryCode, dictionaryOptionCode);
    }
    catch (DaoException|RenderException e)
    {
      logger.error("读取数据字典项错误，字典代码[{}]", fullDicationaryCodes, e);
      e.printStackTrace();
    }
    return new DictionaryOption("未知类型");
  }
}
