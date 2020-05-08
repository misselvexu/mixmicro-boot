package xyz.vopen.framework.boot.plugin.datasource;

import xyz.vopen.framework.boot.plugin.datasource.config.DataSourceConfig;
import xyz.vopen.framework.boot.plugin.datasource.config.DataSourceDruidConfig;
import xyz.vopen.framework.boot.plugin.datasource.config.DataSourceHikariConfig;
import xyz.vopen.framework.boot.plugin.datasource.support.MixmicroBootBasicDataSource;
import xyz.vopen.framework.boot.plugin.datasource.support.MixmicroBootDruidDataSource;
import xyz.vopen.framework.boot.plugin.datasource.support.MixmicroBootHikariDataSource;

import javax.sql.DataSource;

/**
 * {@link DataSource} Factory Class
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 */
public class MixmicroBootDataSourceFactoryBean {
  /**
   * create new dataSource instance
   *
   * @param config {@link DataSourceConfig}
   * @return {@link DataSource} the new instance
   */
  public DataSource newDataSource(DataSourceConfig config) {
    DataSource dataSource = null;
    // if not setting data source type class name
    if (config.getDataSourceType() == null) {
      // use druid data source
      if (checkUseAppointDataSource(DataSourceTypeNames.DRUID)) {
        dataSource = new MixmicroBootDruidDataSource((DataSourceDruidConfig) config);
      }
      // use Hikari data source
      else if (checkUseAppointDataSource(DataSourceTypeNames.HIKARI)) {
        dataSource = new MixmicroBootHikariDataSource((DataSourceHikariConfig) config);
      }
    }
    // if setting data source type class name
    else {
      // druid data source
      if (DataSourceTypeNames.DRUID.equals(config.getDataSourceType().getName())) {
        dataSource = new MixmicroBootDruidDataSource((DataSourceDruidConfig) config);
      }
      // Hikari data source
      else if (DataSourceTypeNames.HIKARI.equals(config.getDataSourceType().getName())) {
        dataSource = new MixmicroBootHikariDataSource((DataSourceHikariConfig) config);
      }
    }
    // use default basic data source
    if (dataSource == null) {
      dataSource = new MixmicroBootBasicDataSource(config);
    }
    return dataSource;
  }

  /**
   * check project is use appoint data source
   *
   * @return true/false
   */
  private boolean checkUseAppointDataSource(String dataSourceTypeName) {
    boolean isUseCheck = true;
    try {
      Class.forName(dataSourceTypeName);
    } catch (ClassNotFoundException e) {
      isUseCheck = false;
    }
    return isUseCheck;
  }
}
