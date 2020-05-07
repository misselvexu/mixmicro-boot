package xyz.vopen.framework.boot.plugin.datasource.support;

import com.zaxxer.hikari.HikariDataSource;
import xyz.vopen.framework.boot.common.exception.MixmicroBootException;
import xyz.vopen.framework.boot.plugin.datasource.MixmicroBootDataSource;
import xyz.vopen.framework.boot.plugin.datasource.config.DataSourceConfig;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.datasource.DelegatingDataSource;

import javax.sql.DataSource;

/**
 * The Basic {@link DataSource} config
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 */
public class MixmicroBootBasicDataSource extends DelegatingDataSource implements MixmicroBootDataSource {
  private DataSourceConfig config;

  public MixmicroBootBasicDataSource(DataSourceConfig config) {
    this.config = config;
    this.setTargetDataSource(build());
  }

  /**
   * create default basic data source
   *
   * @return {@link DataSource} instance
   * @throws MixmicroBootException Mixmicro Boot Exception
   */
  @Override
  public DataSource build() throws MixmicroBootException {
    try {
      DataSource dataSource =
          DataSourceBuilder.create()
              .url(config.getUrl())
              .username(config.getUsername())
              .password(config.getPassword())
              .driverClassName(config.getDriverClassName())
              // springboot 2.x default is HikariDataSource
              .type(HikariDataSource.class)
              .build();
      return dataSource;
    } catch (Exception e) {
      throw new MixmicroBootException("Create a default data source exception", e);
    }
  }
}
