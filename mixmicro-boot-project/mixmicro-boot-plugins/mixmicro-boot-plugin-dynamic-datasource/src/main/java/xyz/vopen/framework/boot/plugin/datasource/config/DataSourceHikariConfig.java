package xyz.vopen.framework.boot.plugin.datasource.config;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Hikari data source configuration class
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 */
@Data
public class DataSourceHikariConfig extends DataSourceConfig {
  /**
   * Hikari dataSource Property Map like：cachePrepStmts、prepStmtCacheSize、prepStmtCacheSqlLimit..
   */
  private Map<String, String> property = new HashMap<>();
}
