package com.wiwi.jsoil.sys.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionListener
  implements HttpSessionListener
{
  private static Logger logger = LoggerFactory.getLogger(SessionListener.class);
  private static int online = 0;

  public void sessionCreated(HttpSessionEvent se)
  {
    try
    {
      online = online + 1;
      HttpSession session = se.getSession();
      String sessionId = session.getId();
      logger.info("[会话][id={}] 已经创建，共有 {} 人在线！", sessionId, Integer.valueOf(online));
    } catch (Throwable throwable) {
      logger.error("异常: ", throwable);
    }
  }

  public void sessionDestroyed(HttpSessionEvent se) {
    try {
      if (online > 0)
        online = online - 1;

      HttpSession session = se.getSession();
      session.removeAttribute("LoGiNsEsSiOnKeY");
      String sessionId = session.getId();
      logger.info("[会话][id={}] 已经失效，共有 {} 人在线！", sessionId, Integer.valueOf(online));
    } catch (Throwable throwable) {
      logger.error("异常: ", throwable);
    }
  }

  public static long getOnline() {
    return ((online > 0) ? online : 1);
  }
}