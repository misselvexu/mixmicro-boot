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

package xyz.vopen.framework.boot.autoconfigure.logging.admin.ui;

import xyz.vopen.framework.boot.autoconfigure.logging.admin.MixmicroBootLoggingAdminAutoConfiguration;
import xyz.vopen.framework.logging.admin.LoggingAdminFactoryBean;
import xyz.vopen.framework.logging.admin.ui.HomepageForwardingFilter;
import xyz.vopen.framework.logging.admin.ui.LoggingAdminUiEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * Mixmicro Boot Logging Admin Ui Configuration
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 *     <p>DateTime：2019-07-26 15:08
 */
@Configuration
@ConditionalOnClass(LoggingAdminUiEndpoint.class)
@EnableConfigurationProperties(MixmicroBootLoggingAdminUiProperties.class)
@AutoConfigureAfter(MixmicroBootLoggingAdminAutoConfiguration.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class MixmicroBootLoggingAdminUiAutoConfiguration
    implements WebMvcConfigurer, InitializingBean, ApplicationContextAware {
  /** logger instance */
  static Logger logger = LoggerFactory.getLogger(MixmicroBootLoggingAdminUiAutoConfiguration.class);
  /** Mixmicro Boot Logging Admin Ui Resource Handler Prefix */
  private static final String RESOURCE_PREFIX = "/**";
  /** Mixmicro Boot Logging Admin Template Html Suffix */
  private static final String TEMPLATE_SUFFIX = ".html";
  /** Default Ui Router List */
  private static final List<String> DEFAULT_UI_ROUTES =
      asList("/about/**", "/services/**", "/logs/**", "/wallboard/**");
  /** Application Context */
  private ApplicationContext applicationContext;
  /** Mixmicro Boot Logging Admin Ui Properties */
  private MixmicroBootLoggingAdminUiProperties adminUiProperties;
  /** LoggingAdmin FactoryBean {@link LoggingAdminFactoryBean} */
  private LoggingAdminFactoryBean loggingAdminFactoryBean;

  public MixmicroBootLoggingAdminUiAutoConfiguration(
      MixmicroBootLoggingAdminUiProperties adminUiProperties,
      LoggingAdminFactoryBean loggingAdminFactoryBean) {
    this.adminUiProperties = adminUiProperties;
    this.loggingAdminFactoryBean = loggingAdminFactoryBean;
  }

  /**
   * Configuration Resource Handler add "api-boot-logging-admin-ui" resource path handler
   *
   * @param registry ResourceHandlerRegistry
   */
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry
        .addResourceHandler(RESOURCE_PREFIX)
        .addResourceLocations(this.adminUiProperties.getResourceLocations());
  }

  /**
   * Config thymeleaf Template Resolver
   *
   * @return SpringResourceTemplateResolver
   */
  @Bean
  public SpringResourceTemplateResolver adminTemplateResolver() {
    SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
    resolver.setApplicationContext(this.applicationContext);
    resolver.setPrefix(this.adminUiProperties.getTemplateLocation());
    resolver.setSuffix(TEMPLATE_SUFFIX);
    resolver.setTemplateMode(TemplateMode.HTML);
    resolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
    resolver.setCacheable(this.adminUiProperties.isCacheTemplates());
    resolver.setOrder(10);
    resolver.setCheckExistence(true);
    return resolver;
  }

  /**
   * Home Page Forwarding Filter
   *
   * @return HomepageForwardingFilter
   * @throws IOException Io Exception
   */
  @Bean
  @ConditionalOnMissingBean
  public HomepageForwardingFilter homepageForwardFilter() throws IOException {
    return new HomepageForwardingFilter("/", DEFAULT_UI_ROUTES);
  }

  /**
   * setting admin ui configs application brand {@link
   * LoggingAdminFactoryBean.AdminUiSetting#getBrand()} application title {@link
   * LoggingAdminFactoryBean.AdminUiSetting#getTitle()} page routes {@link
   * LoggingAdminFactoryBean.AdminUiSetting#getRoutes()}
   *
   * @throws Exception
   * @see LoggingAdminFactoryBean.AdminUiSetting
   */
  @Override
  public void afterPropertiesSet() throws Exception {
    loggingAdminFactoryBean.getAdminUiSetting().setBrand(adminUiProperties.getBrand());
    loggingAdminFactoryBean.getAdminUiSetting().setTitle(adminUiProperties.getTitle());
    loggingAdminFactoryBean.getAdminUiSetting().setRoutes(DEFAULT_UI_ROUTES);
    logger.info("LoggingAdmin UiSetting set successfully.");
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
    logger.debug("ApplicationContext set successfully.");
  }
}
