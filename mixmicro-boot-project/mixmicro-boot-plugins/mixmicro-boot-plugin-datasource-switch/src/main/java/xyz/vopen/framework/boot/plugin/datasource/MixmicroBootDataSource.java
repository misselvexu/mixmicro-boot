package xyz.vopen.framework.boot.plugin.datasource;

import xyz.vopen.framework.boot.common.exception.MixmicroBootException;

import javax.sql.DataSource;

/**
 * Mixmicro Boot Extends {@link DataSource}
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 */
public interface MixmicroBootDataSource extends DataSource {
  /**
   * Create new data source Instance
   *
   * @return {@link DataSource}
   * @throws MixmicroBootException Mixmicro Boot Exception
   */
  DataSource build() throws MixmicroBootException;
}
