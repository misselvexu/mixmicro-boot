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

package xyz.vopen.framework.boot.autoconfigure.oauth;

import xyz.vopen.framework.boot.plugin.oauth.MixmicroBootAuthorizationServerConfiguration;
import xyz.vopen.framework.boot.plugin.oauth.grant.MixmicroBootOauthTokenGranter;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.util.List;

import static xyz.vopen.framework.boot.autoconfigure.oauth.MixmicroBootOauthProperties.MIXMICRO_BOOT_OAUTH_PREFIX;

/**
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 *     <p>DateTime：2019-07-13 09:35
 */
@Configuration
@EnableConfigurationProperties(MixmicroBootOauthProperties.class)
@EnableAuthorizationServer
@ConditionalOnBean(RedisConnectionFactory.class)
@ConditionalOnClass({MixmicroBootAuthorizationServerConfiguration.class})
@ConditionalOnProperty(prefix = MIXMICRO_BOOT_OAUTH_PREFIX, name = "away", havingValue = "redis")
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class MixmicroBootAuthorizationServerRedisAutoConfiguration
    extends MixmicroBootAuthorizationServerAutoConfiguration {
  /** redis connection factory */
  private RedisConnectionFactory redisConnectionFactory;

  /**
   * constructor instance redis connection factory
   *
   * @param objectProvider Mixmicro Boot Token Granter
   * @param mixmicroBootOauthProperties Mixmicro Boot Oauth Properties
   * @param redisConnectionFactory Redis Connection Factory
   */
  public MixmicroBootAuthorizationServerRedisAutoConfiguration(
      ObjectProvider<List<MixmicroBootOauthTokenGranter>> objectProvider,
      MixmicroBootOauthProperties mixmicroBootOauthProperties,
      RedisConnectionFactory redisConnectionFactory) {
    super(objectProvider, mixmicroBootOauthProperties);
    this.redisConnectionFactory = redisConnectionFactory;
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
    mixmicroBootOauthProperties.getClients().stream()
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
   * Redis Token Store
   *
   * @return TokenStore
   */
  @Bean
  public TokenStore redisTokenStore() {
    return new RedisTokenStore(redisConnectionFactory);
  }
}
