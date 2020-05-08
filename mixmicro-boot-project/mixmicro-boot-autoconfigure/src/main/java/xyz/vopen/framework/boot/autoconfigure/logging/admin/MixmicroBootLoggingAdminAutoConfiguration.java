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

package xyz.vopen.framework.boot.autoconfigure.logging.admin;

import xyz.vopen.framework.boot.autoconfigure.enhance.MixmicroBootMyBatisEnhanceAutoConfiguration;
import xyz.vopen.framework.boot.autoconfigure.logging.admin.ui.MixmicroBootLoggingAdminUiAutoConfiguration;
import xyz.vopen.framework.logging.admin.LoggingAdminFactoryBean;
import xyz.vopen.framework.logging.admin.storage.LoggingDataSourceStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import xyz.vopen.framework.logging.admin.storage.LoggingStorage;

import javax.sql.DataSource;

/**
 * Mixmicro Boot Logging Admin Configuration
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 *     <p>DateTime：2019-07-19 10:38
 */
@Configuration
@ConditionalOnClass(LoggingAdminFactoryBean.class)
@ConditionalOnBean(DataSource.class)
@EnableConfigurationProperties(MixmicroBootLoggingAdminProperties.class)
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
@Import({MixmicroBootLoggingAdminUiAutoConfiguration.class})
@EnableAsync
public class MixmicroBootLoggingAdminAutoConfiguration {
  /** logger instance */
  static Logger logger = LoggerFactory.getLogger(MixmicroBootLoggingAdminAutoConfiguration.class);
  /** {@link MixmicroBootLoggingAdminProperties} */
  private MixmicroBootLoggingAdminProperties mixmicroBootLoggingAdminProperties;

  public MixmicroBootLoggingAdminAutoConfiguration(
      MixmicroBootLoggingAdminProperties mixmicroBootLoggingAdminProperties) {
    this.mixmicroBootLoggingAdminProperties = mixmicroBootLoggingAdminProperties;
  }

  /**
   * {@link LoggingStorage} database
   *
   * @param dataSource {@link DataSource}
   * @return {@link LoggingDataSourceStorage}
   */
  @Bean
  @ConditionalOnMissingBean
  public LoggingDataSourceStorage loggingDataSourceStorage(DataSource dataSource) {
    return new LoggingDataSourceStorage(dataSource);
  }

  /**
   * instantiation {@link LoggingAdminFactoryBean}
   *
   * @param loggingDataSourceStorage {@link LoggingDataSourceStorage}
   * @return LoggingAdminFactoryBean
   */
  @Bean
  @ConditionalOnMissingBean
  public LoggingAdminFactoryBean loggingAdminFactoryBean(
      LoggingDataSourceStorage loggingDataSourceStorage) {
    LoggingAdminFactoryBean factoryBean = new LoggingAdminFactoryBean();
    factoryBean.setLoggingStorage(loggingDataSourceStorage);
    factoryBean.setShowConsoleReportLog(mixmicroBootLoggingAdminProperties.isShowConsoleReportLog());
    factoryBean.setFormatConsoleLogJson(mixmicroBootLoggingAdminProperties.isFormatConsoleLogJson());
    logger.info("【LoggingAdminFactoryBean】init successfully.");
    return factoryBean;
  }

  /**
   * Verify that the {@link DataSource} exists and perform an exception alert when it does not exist
   *
   * @see MixmicroBootMyBatisEnhanceAutoConfiguration
   */
  @Configuration
  @ConditionalOnMissingBean(DataSource.class)
  public static class DataSourceNotFoundConfiguration implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
      throw new BeanCreationException("No " + DataSource.class.getName() + " Found.");
    }
  }
}
