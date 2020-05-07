package xyz.vopen.framework.boot.autoconfigure.datasource;

import xyz.vopen.framework.boot.plugin.datasource.MixmicroBootDataSource;
import xyz.vopen.framework.boot.plugin.datasource.MixmicroBootDataSourceFactoryBean;
import xyz.vopen.framework.boot.plugin.datasource.aop.advistor.MixmicroBootDataSourceSwitchAdvisor;
import xyz.vopen.framework.boot.plugin.datasource.aop.interceptor.MixmicroBootDataSourceSwitchAnnotationInterceptor;
import xyz.vopen.framework.boot.plugin.datasource.config.DataSourceConfig;
import xyz.vopen.framework.boot.plugin.datasource.config.DataSourceDruidConfig;
import xyz.vopen.framework.boot.plugin.datasource.routing.MixmicroBootRoutingDataSource;
import xyz.vopen.framework.boot.plugin.datasource.support.MixmicroBootDruidDataSource;
import xyz.vopen.framework.boot.plugin.datasource.support.MixmicroBootHikariDataSource;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Mixmicro Boot DataSource Switch AutoConfiguration
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 *     <p>DateTime：2019-04-01 22:06
 */
@Configuration
@ConditionalOnClass(MixmicroBootDataSource.class)
@EnableConfigurationProperties(MixmicroBootDataSourceSwitchProperties.class)
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
public class MixmicroBootDataSourceSwitchAutoConfiguration {
  /** Mixmicro Boot DataSource Switch Properties */
  private MixmicroBootDataSourceSwitchProperties mixmicroBootDataSourceSwitchProperties;

  public MixmicroBootDataSourceSwitchAutoConfiguration(
      MixmicroBootDataSourceSwitchProperties mixmicroBootDataSourceSwitchProperties) {
    this.mixmicroBootDataSourceSwitchProperties = mixmicroBootDataSourceSwitchProperties;
  }

  /**
   * Mixmicro Boot DataSource FactoryBean Used to create datasource
   *
   * @return ApiBootDataSourceFactoryBean
   */
  @Bean
  @ConditionalOnMissingBean
  public MixmicroBootDataSourceFactoryBean apiBootDataSourceFactoryBean() {
    return new MixmicroBootDataSourceFactoryBean();
  }

  /**
   * Mixmicro Boot Routing DataSource switch use datasource {@link DataSource}
   *
   * @param mixmicroBootDataSourceFactoryBean Mixmicro Boot DataSource FactoryBean
   * @return DataSource
   */
  @Bean
  @ConditionalOnMissingBean
  public DataSource dataSource(MixmicroBootDataSourceFactoryBean mixmicroBootDataSourceFactoryBean) {
    List<DataSourceConfig> dataSourceConfigList = new LinkedList();
    Map<String, DataSourceConfig> dataSourceConfigMap = new HashMap(1);

    // put druid datasource config to map
    dataSourceConfigMap.putAll(mixmicroBootDataSourceSwitchProperties.getDruid());
    // put hikari datasource config to map
    dataSourceConfigMap.putAll(mixmicroBootDataSourceSwitchProperties.getHikari());

    // convert all datasource config
    dataSourceConfigMap.keySet().stream()
        .forEach(
            poolName -> {
              DataSourceConfig dataSourceConfig = dataSourceConfigMap.get(poolName);
              // set data source pool name
              dataSourceConfig.setPoolName(poolName);
              // datasource type
              dataSourceConfig.setDataSourceType(
                  dataSourceConfig instanceof DataSourceDruidConfig
                      ? MixmicroBootDruidDataSource.class
                      : MixmicroBootHikariDataSource.class);

              // after convert add to data source list
              dataSourceConfigList.add(dataSourceConfig);
            });

    return new MixmicroBootRoutingDataSource(
        mixmicroBootDataSourceFactoryBean,
        mixmicroBootDataSourceSwitchProperties.getPrimary(),
        dataSourceConfigList);
  }

  /**
   * Mixmicro Boot DataSource Switch Advice Interceptor
   *
   * @return ApiBootDataSourceSwitchAnnotationInterceptor
   */
  @Bean
  @ConditionalOnMissingBean
  public MixmicroBootDataSourceSwitchAnnotationInterceptor
      apiBootDataSourceSwitchAnnotationInterceptor() {
    return new MixmicroBootDataSourceSwitchAnnotationInterceptor();
  }

  /**
   * Mixmicro Boot DataSource Switch Advisor Used to get @DataSourceSwitch annotation define
   *
   * @param mixmicroBootDataSourceSwitchAnnotationInterceptor Mixmicro Boot DataSource Annotation Interceptor
   * @return ApiBootDataSourceSwitchAdvisor
   */
  @Bean
  @ConditionalOnMissingBean
  public MixmicroBootDataSourceSwitchAdvisor apiBootDataSourceSwitchAdvisor(
      MixmicroBootDataSourceSwitchAnnotationInterceptor mixmicroBootDataSourceSwitchAnnotationInterceptor) {
    return new MixmicroBootDataSourceSwitchAdvisor(mixmicroBootDataSourceSwitchAnnotationInterceptor);
  }
}
