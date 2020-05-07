/*
 * Copyright (c) 2018 VOPEN.XYZ
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package xyz.vopen.framework.boot.autoconfigure.security;

import xyz.vopen.framework.boot.plugin.security.MixmicroBootWebSecurityConfiguration;
import xyz.vopen.framework.boot.plugin.security.delegate.MixmicroBootDefaultStoreDelegate;
import xyz.vopen.framework.boot.plugin.security.delegate.MixmicroBootStoreDelegate;
import xyz.vopen.framework.boot.plugin.security.userdetails.MixmicroBootUserDetailsService;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.sql.DataSource;

/**
 * Mixmicro Boot SpringSecurity自动化封装配置Jdbc的实现
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 *     <p>DateTime：2019-03-14 15:58
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties(ApiBootSecurityProperties.class)
@ConditionalOnClass(MixmicroBootWebSecurityConfiguration.class)
@ConditionalOnBean(DataSource.class)
@ConditionalOnProperty(prefix = ApiBootSecurityProperties.API_BOOT_SECURITY_PREFIX, name = "away", havingValue = "jdbc")
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
public class MixmicroBootWebSecurityJdbcAutoConfiguration extends MixmicroBootWebSecurityAutoConfiguration {

  public MixmicroBootWebSecurityJdbcAutoConfiguration(
      ApiBootSecurityProperties apiBootSecurityProperties,
      ObjectProvider<AccessDeniedHandler> accessDeniedHandler,
      ObjectProvider<AuthenticationEntryPoint> authenticationEntryPoint) {
    super(
        apiBootSecurityProperties,
        accessDeniedHandler.getIfAvailable(),
        authenticationEntryPoint.getIfAvailable());
  }

  @Override
  @Bean
  protected UserDetailsService userDetailsService() {
    return new MixmicroBootUserDetailsService();
  }

  /**
   * 开启使用ApiBoot默认自带的用户信息表
   *
   * @param dataSource DataSource
   * @return ApiBootStoreDelegate
   */
  @Bean
  @ConditionalOnProperty(
      prefix = ApiBootSecurityProperties.API_BOOT_SECURITY_PREFIX,
      name = "enable-default-store-delegate",
      havingValue = "true",
      matchIfMissing = true)
  public MixmicroBootStoreDelegate apiBootStoreDelegate(DataSource dataSource) {
    return new MixmicroBootDefaultStoreDelegate(dataSource);
  }
}
