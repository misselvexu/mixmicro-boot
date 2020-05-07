package xyz.vopen.framework.boot.autoconfigure.datasource;

import lombok.Data;
import xyz.vopen.framework.boot.plugin.datasource.config.DataSourceDruidConfig;
import xyz.vopen.framework.boot.plugin.datasource.config.DataSourceHikariConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

import static xyz.vopen.framework.boot.autoconfigure.datasource.MixmicroBootDataSourceSwitchProperties.MIXMICRO_BOOT_DATASOURCE_SWITCH_PREFIX;

/**
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 *     <p>DateTime：2019-04-02 09:48
 */
@Data
@Configuration
@ConfigurationProperties(prefix = MIXMICRO_BOOT_DATASOURCE_SWITCH_PREFIX)
public class MixmicroBootDataSourceSwitchProperties {
  /** Mixmicro Boot DataSource Switch Config Prefix */
  public static final String MIXMICRO_BOOT_DATASOURCE_SWITCH_PREFIX = "mixmicro.boot.datasource";
  /** primary datasource pool name default is master */
  private String primary = "master";
  /** config druid type datasource list */
  public Map<String, DataSourceDruidConfig> druid = new HashMap();
  /** config hikari type datasource list */
  public Map<String, DataSourceHikariConfig> hikari = new HashMap();
}
