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

package xyz.vopen.framework.boot.autoconfigure.oauth;

import xyz.vopen.framework.boot.autoconfigure.security.ApiBootSecurityProperties;
import xyz.vopen.framework.boot.plugin.security.SecurityUser;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * Mixmicro Boot 接口资源服务器配置
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 *     <p>DateTime：2019-03-14 16:49
 */
@Configuration
@ConditionalOnClass({ResourceServerConfigurerAdapter.class, SecurityUser.class})
@EnableConfigurationProperties({ApiBootSecurityProperties.class, ApiBootOauthProperties.class})
@EnableResourceServer
public class ApiBootResourceServerAutoConfiguration extends ResourceServerConfigurerAdapter {
  /** Spring Security Properties */
  private ApiBootSecurityProperties apiBootSecurityProperties;
  /** Oauth2 Properties */
  private ApiBootOauthProperties apiBootOauthProperties;

  public ApiBootResourceServerAutoConfiguration(
      ApiBootSecurityProperties apiBootSecurityProperties,
      ApiBootOauthProperties apiBootOauthProperties) {
    this.apiBootSecurityProperties = apiBootSecurityProperties;
    this.apiBootOauthProperties = apiBootOauthProperties;
  }

  /**
   * 配置开启对指定前缀路径的认证
   *
   * @param http http安全构建对象
   * @throws Exception 异常信息
   */
  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .anyRequest()
        .authenticated()
        .and()
        .requestMatchers()
        .antMatchers(apiBootSecurityProperties.getAuthPrefix());
  }

  @Override
  public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
    resources.resourceId(apiBootOauthProperties.getResourceId());
  }
}