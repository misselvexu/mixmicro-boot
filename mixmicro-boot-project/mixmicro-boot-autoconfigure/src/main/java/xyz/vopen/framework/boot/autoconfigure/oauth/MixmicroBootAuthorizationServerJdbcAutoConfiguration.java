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
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;
import java.util.List;

import static xyz.vopen.framework.boot.autoconfigure.oauth.MixmicroBootOauthProperties.MIXMICRO_BOOT_OAUTH_PREFIX;

/**
 * Mixmicro Boot 授权服务器Jdbc方式实现
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 *     <p>DateTime：2019-03-14 16:55
 */
@Configuration
@EnableConfigurationProperties(MixmicroBootOauthProperties.class)
@EnableAuthorizationServer
@ConditionalOnBean(DataSource.class)
@ConditionalOnClass(MixmicroBootAuthorizationServerConfiguration.class)
@ConditionalOnProperty(prefix = MIXMICRO_BOOT_OAUTH_PREFIX, name = "away", havingValue = "jdbc")
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
public class MixmicroBootAuthorizationServerJdbcAutoConfiguration
    extends MixmicroBootAuthorizationServerAutoConfiguration {
  private DataSource dataSource;

  public MixmicroBootAuthorizationServerJdbcAutoConfiguration(
      ObjectProvider<List<MixmicroBootOauthTokenGranter>> objectProvider,
      MixmicroBootOauthProperties mixmicroBootOauthProperties,
      DataSource dataSource) {
    super(objectProvider, mixmicroBootOauthProperties);
    this.dataSource = dataSource;
  }

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients.jdbc(dataSource);
  }

  /**
   * 配置内存方式令牌存储
   *
   * @return TokenStore
   */
  @Bean
  public TokenStore jdbcTokenStore() {
    return new JdbcTokenStore(dataSource);
  }
}
