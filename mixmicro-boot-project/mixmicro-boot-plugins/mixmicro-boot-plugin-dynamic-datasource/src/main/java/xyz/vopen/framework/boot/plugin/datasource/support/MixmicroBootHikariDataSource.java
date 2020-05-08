package xyz.vopen.framework.boot.plugin.datasource.support;

import com.zaxxer.hikari.HikariDataSource;
import xyz.vopen.framework.boot.common.exception.MixmicroBootException;
import xyz.vopen.framework.boot.plugin.datasource.MixmicroBootDataSource;
import xyz.vopen.framework.boot.plugin.datasource.config.DataSourceHikariConfig;

import javax.sql.DataSource;

/**
 * The Hikari {@link DataSource} config
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 */
public class MixmicroBootHikariDataSource extends HikariDataSource implements MixmicroBootDataSource {

  public MixmicroBootHikariDataSource(DataSourceHikariConfig config) {
    try {
      this.setJdbcUrl(config.getUrl());
      this.setUsername(config.getUsername());
      this.setPassword(config.getPassword());
      config.getProperty().keySet().stream()
          .forEach(param -> this.addDataSourceProperty(param, config.getProperty().get(param)));
    } catch (Exception e) {
      throw new MixmicroBootException("Create new Hikari dataSource fail.", e);
    }
  }

  /**
   * create new Hikari dataSource instance
   *
   * @return {@link DataSource} this class instance
   * @throws MixmicroBootException Mixmicro Boot Exception
   */
  @Override
  public DataSource build() throws MixmicroBootException {
    return this;
  }
}
