package xyz.vopen.framework.boot.plugin.datasource;

/**
 * {@link javax.sql.DataSource} Types name definition
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 */
public interface DataSourceTypeNames {
  /**
   * The Druid Class Name
   *
   * @see com.alibaba.druid.pool.DruidDataSource
   */
  String DRUID = "com.alibaba.druid.pool.DruidDataSource";
  /**
   * The Hikari Class Name
   *
   * @see com.zaxxer.hikari.HikariDataSource
   */
  String HIKARI = "com.zaxxer.hikari.HikariDataSource";
}
