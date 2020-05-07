/*
 * Copyright (c) 2018 VOPEN.XYZ
 *
 *      Licensed under the Apache License, Version 2.0 (the "License");
 *      you may not use this file except in compliance with the License.
 *      You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *      Unless required by applicable law or agreed to in writing, software
 *      distributed under the License is distributed on an "AS IS" BASIS,
 *      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *      See the License for the specific language governing permissions and
 *      limitations under the License.
 *
 */

package xyz.vopen.framework.boot.autoconfigure.logging;

import xyz.vopen.framework.logging.client.LoggingFactoryBean;
import xyz.vopen.framework.logging.client.admin.discovery.LoggingAdminDiscovery;
import xyz.vopen.framework.logging.client.admin.report.LoggingReportScheduled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.util.ObjectUtils;

import java.util.List;

import static xyz.vopen.framework.boot.autoconfigure.logging.ApiBootLoggingProperties.API_BOOT_LOGGING_PREFIX;

/**
 * Mixmicro Boot Logging Auto Configuration
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 *     <p>DateTime：2019-07-15 18:33
 */
@Configuration
@ConditionalOnClass(LoggingFactoryBean.class)
@EnableConfigurationProperties(ApiBootLoggingProperties.class)
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
@ConditionalOnWebApplication
@EnableAsync
@Import({
  ApiBootLoggingAdminDiscoveryAutoConfiguration.class,
  ApiBootLoggingAdminAppointAutoConfiguration.class,
  ApiBootLoggingOpenfeignAutoConfiguration.class,
  ApiBootLoggingRestTemplateAutoConfiguration.class,
  ApiBootLoggingWebAutoConfiguration.class,
  ApiBootLoggingGlobalLogStorageAutoConfiguration.class
})
public class ApiBootLoggingAutoConfiguration {
  /** logger instance */
  static Logger logger = LoggerFactory.getLogger(ApiBootLoggingAutoConfiguration.class);
  /** Mixmicro Boot Logging Properties */
  private ApiBootLoggingProperties apiBootLoggingProperties;

  public ApiBootLoggingAutoConfiguration(ApiBootLoggingProperties apiBootLoggingProperties) {
    this.apiBootLoggingProperties = apiBootLoggingProperties;
  }

  /**
   * logging factory bean {@link LoggingFactoryBean}
   *
   * @param loggingAdminDiscoveryObjectProvider Logging Admin Discovery Instance Provider
   * @return LoggingFactoryBean
   */
  @Bean
  @ConditionalOnMissingBean
  public LoggingFactoryBean loggingFactoryBean(
      ObjectProvider<LoggingAdminDiscovery> loggingAdminDiscoveryObjectProvider,
      ObjectProvider<List<LoggingFactoryBeanCustomizer>> customizerObjectProvider) {
    LoggingFactoryBean factoryBean = new LoggingFactoryBean();
    factoryBean.setIgnorePaths(apiBootLoggingProperties.getIgnorePaths());
    factoryBean.setReportAway(apiBootLoggingProperties.getReportAway());
    factoryBean.setNumberOfRequestLog(apiBootLoggingProperties.getReportNumberOfRequestLog());
    factoryBean.setReportInitialDelaySecond(apiBootLoggingProperties.getReportInitialDelaySecond());
    factoryBean.setReportIntervalSecond(apiBootLoggingProperties.getReportIntervalSecond());
    factoryBean.setLoggingAdminDiscovery(loggingAdminDiscoveryObjectProvider.getIfAvailable());
    factoryBean.setShowConsoleLog(apiBootLoggingProperties.isShowConsoleLog());
    factoryBean.setFormatConsoleLog(apiBootLoggingProperties.isFormatConsoleLogJson());

    List<LoggingFactoryBeanCustomizer> customizers = customizerObjectProvider.getIfAvailable();
    if (!ObjectUtils.isEmpty(customizers)) {
      customizers.stream().forEach(customizer -> customizer.customize(factoryBean));
    }
    logger.info("【LoggingFactoryBean】init successfully.");
    return factoryBean;
  }

  /**
   * Logging Report Scheduled Task Job When the configuration parameter
   * "mixmicro.boot.logging.report-away=timing" is configured, the creation timing task is performed
   * to report log information to admin node {@link ApiBootLoggingProperties} {@link
   * LoggingReportScheduled}
   *
   * @param factoryBean logging factory bean
   * @return LoggingReportScheduled
   */
  @Bean
  @ConditionalOnProperty(
      prefix = API_BOOT_LOGGING_PREFIX,
      name = "report-away",
      havingValue = "timing")
  @ConditionalOnMissingBean
  public LoggingReportScheduled loggingReportScheduled(LoggingFactoryBean factoryBean) {
    return new LoggingReportScheduled(factoryBean);
  }
}
