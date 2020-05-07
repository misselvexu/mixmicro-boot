package xyz.vopen.framework.boot.plugin.datasource.support;

import com.alibaba.druid.pool.DruidDataSource;
import xyz.vopen.framework.boot.common.exception.MixmicroBootException;
import xyz.vopen.framework.boot.plugin.datasource.MixmicroBootDataSource;
import xyz.vopen.framework.boot.plugin.datasource.config.DataSourceDruidConfig;

import javax.sql.DataSource;

/**
 * The Druid {@link DataSource} config
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 */
public class MixmicroBootDruidDataSource extends DruidDataSource implements MixmicroBootDataSource {

  public MixmicroBootDruidDataSource(DataSourceDruidConfig config) {
    try {
      this.setUrl(config.getUrl());
      this.setUsername(config.getUsername());
      this.setPassword(config.getPassword());
      this.setDriverClassName(config.getDriverClassName());
      this.setFilters(config.getFilters());
      this.setMaxActive(config.getMaxActive());
      this.setInitialSize(config.getInitialSize());
      this.setMaxWait(config.getMaxWait());
      this.setValidationQuery(config.getValidationQuery());
      this.setTestWhileIdle(config.isTestWhileIdle());
      this.setTestOnBorrow(config.isTestOnBorrow());
      this.setTestOnReturn(config.isTestOnReturn());
    } catch (Exception e) {
      throw new MixmicroBootException("Create new druid dataSource fail.", e);
    }
  }

  /**
   * create new druid dataSource instance
   *
   * @return {@link DataSource} this class instance
   * @throws MixmicroBootException Mixmicro Boot Exception
   */
  @Override
  public DataSource build() throws MixmicroBootException {
    return this;
  }
}
