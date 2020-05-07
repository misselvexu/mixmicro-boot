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

import xyz.vopen.framework.boot.plugin.oauth.MixmicroBootAuthorizationServerConfiguration;
import xyz.vopen.framework.boot.plugin.oauth.grant.MixmicroBootOauthTokenGranter;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.List;

import static xyz.vopen.framework.boot.autoconfigure.oauth.MixmicroBootOauthProperties.MIXMICRO_BOOT_OAUTH_PREFIX;

/**
 * ApiBoot授权服务器配置
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 *     <p>DateTime：2019-03-14 16:51
 */
public class MixmicroBootAuthorizationServerAutoConfiguration
    extends MixmicroBootAuthorizationServerConfiguration {
  /** 注入属性配置 */
  protected MixmicroBootOauthProperties mixmicroBootOauthProperties;

  public MixmicroBootAuthorizationServerAutoConfiguration(
      ObjectProvider<List<MixmicroBootOauthTokenGranter>> objectProvider,
      MixmicroBootOauthProperties mixmicroBootOauthProperties) {
    super(objectProvider);
    this.mixmicroBootOauthProperties = mixmicroBootOauthProperties;
  }

  /**
   * 配置jwt生成token的转换 使用自定义Sign Key 进行加密
   *
   * @return Jwt Access Token转换实例
   */
  @Bean
  @ConditionalOnProperty(prefix = MIXMICRO_BOOT_OAUTH_PREFIX, name = "jwt.enable", havingValue = "true")
  public AccessTokenConverter jwtAccessTokenConverter() {
    JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
    converter.setSigningKey(mixmicroBootOauthProperties.getJwt().getSignKey());
    return converter;
  }

  /**
   * 默认token转换 不配置jwt转换时
   *
   * @return AccessTokenConverter
   */
  @Bean
  @ConditionalOnProperty(
      prefix = MIXMICRO_BOOT_OAUTH_PREFIX,
      name = "jwt.enable",
      havingValue = "false",
      matchIfMissing = true)
  public AccessTokenConverter defaultAccessTokenConverter() {
    return new DefaultAccessTokenConverter();
  }
}
