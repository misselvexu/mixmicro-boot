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
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import java.util.List;

/**
 * Mixmicro Boot OAuth Memory Away Support
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 *     <p>DateTime：2019-03-14 16:53
 */
@Configuration
@ConditionalOnClass(MixmicroBootAuthorizationServerConfiguration.class)
@EnableConfigurationProperties(ApiBootOauthProperties.class)
@EnableAuthorizationServer
@ConditionalOnProperty(
    prefix = ApiBootOauthProperties.API_BOOT_OAUTH_PREFIX,
    name = "away",
    havingValue = "memory",
    matchIfMissing = true)
public class MixmicroBootAuthorizationMemoryServerAutoConfiguration
    extends MixmicroBootAuthorizationServerAutoConfiguration {
  public MixmicroBootAuthorizationMemoryServerAutoConfiguration(
      ObjectProvider<List<MixmicroBootOauthTokenGranter>> objectProvider,
      ApiBootOauthProperties apiBootOauthProperties) {
    super(objectProvider, apiBootOauthProperties);
  }

  /**
   * configuration clients
   *
   * @param clients client details service configuration
   * @throws Exception exception
   */
  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    InMemoryClientDetailsServiceBuilder inMemoryClientDetailsServiceBuilder = clients.inMemory();
    apiBootOauthProperties.getClients().stream()
        .forEach(
            client ->
                inMemoryClientDetailsServiceBuilder
                    .withClient(client.getClientId())
                    .secret(passwordEncoder().encode(client.getClientSecret()))
                    .authorizedGrantTypes(client.getGrantTypes())
                    .scopes(client.getScopes())
                    .resourceIds(client.getResourceId())
                    .accessTokenValiditySeconds(client.getAccessTokenValiditySeconds()));
  }

  /**
   * memory away token store
   *
   * @return TokenStore
   */
  @Bean
  public TokenStore memoryTokenStore() {
    return new InMemoryTokenStore();
  }
}
