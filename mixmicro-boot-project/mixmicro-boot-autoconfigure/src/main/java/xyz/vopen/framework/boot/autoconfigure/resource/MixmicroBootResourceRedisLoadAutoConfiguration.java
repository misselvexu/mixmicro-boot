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

package xyz.vopen.framework.boot.autoconfigure.resource;

import xyz.vopen.framework.boot.plugin.resource.load.MixmicroBootResourceStoreDelegate;
import xyz.vopen.framework.boot.plugin.resource.load.pusher.support.MixmicroBootRedisResourcePusher;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 *     <p>DateTime：2019-04-19 10:46
 */
@Configuration
@ConditionalOnClass(RedisTemplate.class)
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class MixmicroBootResourceRedisLoadAutoConfiguration {
  /** Mixmicro Boot Resource Load Store Delegate */
  private MixmicroBootResourceStoreDelegate resourceStoreDelegate;

  public MixmicroBootResourceRedisLoadAutoConfiguration(
      ObjectProvider<MixmicroBootResourceStoreDelegate> resourceStoreDelegateObjectProvider) {
    this.resourceStoreDelegate = resourceStoreDelegateObjectProvider.getIfAvailable();
  }

  /**
   * Mixmicro Boot Redis Resource Pusher
   *
   * @param redisTemplate Redis Template
   * @return ApiBootRedisResourcePusher
   */
  @Bean
  @ConditionalOnMissingBean
  MixmicroBootRedisResourcePusher apiBootRedisResourcePusher(RedisTemplate redisTemplate) {
    return new MixmicroBootRedisResourcePusher(resourceStoreDelegate, redisTemplate);
  }
}
