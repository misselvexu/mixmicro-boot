package xyz.vopen.framework.boot.plugin.datasource.routing;

import xyz.vopen.framework.boot.plugin.datasource.MixmicroBootDataSourceFactoryBean;
import xyz.vopen.framework.boot.plugin.datasource.config.DataSourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import xyz.vopen.framework.boot.plugin.datasource.config.DataSourceDruidConfig;
import xyz.vopen.framework.boot.plugin.datasource.config.DataSourceHikariConfig;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Mixmicro Boot DataSource Routing
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 */
public class MixmicroBootRoutingDataSource extends AbstractRoutingDataSource
    implements InitializingBean, DisposableBean {
  /** logger instance */
  static Logger logger = LoggerFactory.getLogger(MixmicroBootRoutingDataSource.class);

  /** datasource factory bean */
  private MixmicroBootDataSourceFactoryBean factoryBean;
  /** all datasource config */
  private List<DataSourceConfig> configs;
  /** primary pool name */
  private String primaryPoolName;
  /** cache all data source key is dataSource pool name value is dataSource instance */
  private Map<String, DataSource> dataSourceMap = new LinkedHashMap<>();

  public MixmicroBootRoutingDataSource(
      MixmicroBootDataSourceFactoryBean factoryBean,
      String primaryPoolName,
      List<DataSourceConfig> configs) {
    this.factoryBean = factoryBean;
    this.primaryPoolName = primaryPoolName;
    this.configs = configs;
  }

  /**
   * get current datasource instance
   *
   * @return DataSource
   */
  @Override
  protected Object determineCurrentLookupKey() {
    String poolName = DataSourceContextHolder.get();
    return StringUtils.isEmpty(poolName) ? primaryPoolName : poolName;
  }

  /**
   * get primary data source instance
   *
   * @return dataSource
   */
  private DataSource getPrimaryDataSource() {
    return dataSourceMap.get(primaryPoolName);
  }

  /**
   * init all datasource by DataSourceConfig
   *
   * @see DataSourceConfig
   * @see DataSourceDruidConfig
   * @see DataSourceHikariConfig
   */
  @Override
  public void afterPropertiesSet() {
    // config is required.
    Assert.notNull(configs, "DataSource config is required.");

    Map<Object, Object> targetDataSources = new HashMap(1);

    // Instantiate all data source configurations
    configs.stream()
        .forEach(
            config -> {
              // new datasource instance
              DataSource dataSource = factoryBean.newDataSource(config);
              // cache datasource to map
              dataSourceMap.put(config.getPoolName(), dataSource);

              // cache ti set target datasource
              targetDataSources.put(config.getPoolName(), dataSource);
              logger.info(
                  "【Mixmicro Boot DataSource Switch】Execute create datasource [{}] instance.",
                  config.getPoolName());
            });

    // set target datasource's
    this.setTargetDataSources(targetDataSources);
    // set default data source
    this.setDefaultTargetDataSource(getPrimaryDataSource());

    // call parent afterProperties method
    super.afterPropertiesSet();
  }

  /**
   * service shutdown all datasource execute destroy
   *
   * @throws Exception
   */
  @Override
  public void destroy() throws Exception {
    dataSourceMap.keySet().stream()
        .forEach(
            poolName -> {
              try {
                DataSource dataSource = dataSourceMap.get(poolName);
                Class<? extends DataSource> clazz = dataSource.getClass();
                // get close method
                // druid or Hikari dataSource have "close" method
                Method closeMethod = clazz.getDeclaredMethod("close");
                if (closeMethod != null) {
                  closeMethod.invoke(dataSource);
                  logger.info("【Mixmicro Boot DataSource Switch】Execute close datasource [{}]", poolName);
                }
              } catch (Exception e) {
                // ignore
              }
            });
  }
}
