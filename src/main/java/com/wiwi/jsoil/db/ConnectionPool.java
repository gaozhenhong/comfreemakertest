package com.wiwi.jsoil.db;

import com.wiwi.jsoil.exception.DaoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.jdbc.core.JdbcTemplate;

public class ConnectionPool
{
  private DataSource ds = null;
  private JdbcTemplate jdbcTemplate;
  private static ConnectionPool connectionPool;
  private static final Logger logger = LoggerFactory.getLogger(ConnectionPool.class);

  private ConnectionPool()
  {
    try
    {
      Properties properties = new Properties();
      Resource resource = new ClassPathResource("/datasource.properties");
      try {
        properties = PropertiesLoaderUtils.loadProperties(resource);
      } catch (IOException e) {
        logger.error("读取数据源配置文件时出错！" + e);
        e.printStackTrace();
      }

      this.ds = BasicDataSourceFactory.createDataSource(properties);
    }
    catch (Exception e)
    {
      logger.error("从数据库连接池创建数据源是出错", e);
      e.printStackTrace();
    }
  }

  public static ConnectionPool getInstance()
  {
    if (connectionPool == null)
      connectionPool = new ConnectionPool();

    return connectionPool;
  }

  public Connection getConnection(boolean autoCommit) throws DaoException {
    Connection con;
    try {
      con = this.ds.getConnection();
      con.setAutoCommit(autoCommit);
      return con;
    } catch (SQLException e) {
      logger.error("sql状态错误（SQLState）：" + e.getSQLState() + "获得数据库链接时出现错误: ", e);
      throw new DaoException("sql状态错误（SQLState）：" + e.getSQLState() + "获得数据库链接时出现错误: ", e);
    }
  }
}