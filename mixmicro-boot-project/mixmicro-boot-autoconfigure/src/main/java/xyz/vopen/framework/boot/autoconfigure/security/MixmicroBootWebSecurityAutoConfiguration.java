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
import xyz.vopen.framework.boot.plugin.security.handler.MixmicroBootDefaultAccessDeniedHandler;
import xyz.vopen.framework.boot.plugin.security.point.MixmicroBootDefaultAuthenticationEntryPoint;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Mixmicro Boot SpringSecurity自动化封装配置
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 *     <p>DateTime：2019-03-14 15:24
 */
public class MixmicroBootWebSecurityAutoConfiguration extends MixmicroBootWebSecurityConfiguration {
  /** 注入ApiBoot安全属性 */
  protected MixmicroBootSecurityProperties mixmicroBootSecurityProperties;
  /** 异常处理 */
  private AccessDeniedHandler accessDeniedHandler;
  /** 端点处理 */
  private AuthenticationEntryPoint authenticationEntryPoint;

  public MixmicroBootWebSecurityAutoConfiguration(
      MixmicroBootSecurityProperties mixmicroBootSecurityProperties,
      AccessDeniedHandler accessDeniedHandler,
      AuthenticationEntryPoint authenticationEntryPoint) {
    this.mixmicroBootSecurityProperties = mixmicroBootSecurityProperties;
    this.accessDeniedHandler = accessDeniedHandler;
    this.authenticationEntryPoint = authenticationEntryPoint;
  }

  /**
   * 配置排除的路径列表
   *
   * @return 将要排除的路径
   */
  @Override
  protected List<String> configureIgnoreUrls() {
    List<String> ignoringUrls = new ArrayList<>();
    // 默认排除路径
    ignoringUrls.addAll(Arrays.asList(MixmicroBootSecurityProperties.DEFAULT_IGNORE_URLS));
    // 自定义排除的路径
    if (!ObjectUtils.isEmpty(mixmicroBootSecurityProperties.getIgnoringUrls())) {
      ignoringUrls.addAll(Arrays.asList(mixmicroBootSecurityProperties.getIgnoringUrls()));
    }
    return ignoringUrls;
  }

  /**
   * 返回项目内定义的AccessDeniedHandler实现类实例 给Mixmicro Boot Security进行配置
   *
   * @return AccessDeniedHandler
   */
  @Override
  protected AccessDeniedHandler getAccessDeniedHandler() {
    return ObjectUtils.isEmpty(this.accessDeniedHandler)
        ? new MixmicroBootDefaultAccessDeniedHandler()
        : this.accessDeniedHandler;
  }

  @Override
  protected AuthenticationEntryPoint getAuthenticationEntryPoint() {
    return ObjectUtils.isEmpty(this.authenticationEntryPoint)
        ? new MixmicroBootDefaultAuthenticationEntryPoint()
        : this.authenticationEntryPoint;
  }

  /**
   * 配置禁用http basic
   *
   * @return true：禁用，false：不禁用
   */
  @Override
  protected boolean disableHttpBasic() {
    return mixmicroBootSecurityProperties.isDisableHttpBasic();
  }

  /**
   * 配置csrf
   *
   * @return true：禁用，false：不禁用
   */
  @Override
  protected boolean disableCsrf() {
    return mixmicroBootSecurityProperties.isDisableCsrf();
  }
}
