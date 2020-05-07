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

package xyz.vopen.framework.boot.autoconfigure.ratelimiter;

import xyz.vopen.framework.boot.plugin.rate.limiter.MixmicroBootRateLimiter;
import xyz.vopen.framework.boot.plugin.rate.limiter.centre.RateLimiterConfigCentre;
import xyz.vopen.framework.boot.plugin.rate.limiter.support.RedisLuaRateLimiter;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * RateLimiter Automation Configuration in redis Mode
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 *     <p>DateTime：2019-05-05 17:50
 */
@Configuration
@EnableConfigurationProperties(MixmicroBootRateLimiterProperties.class)
@ConditionalOnClass(name = "org.springframework.data.redis.core.RedisTemplate")
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class MixmicroBootRateLimiterRedisAutoConfiguration {
  /** Mixmicro Boot Rate Limiter Properties */
  private MixmicroBootRateLimiterProperties mixmicroBootRateLimiterProperties;

  public MixmicroBootRateLimiterRedisAutoConfiguration(
      MixmicroBootRateLimiterProperties mixmicroBootRateLimiterProperties) {
    this.mixmicroBootRateLimiterProperties = mixmicroBootRateLimiterProperties;
  }

  /**
   * redis lua rate limiter
   *
   * @param redisTemplate redis template
   * @param rateLimiterConfigCentre RateLimiter Config Centre
   * @return ApiBootRateLimiter
   */
  @Bean
  @ConditionalOnMissingBean
  public MixmicroBootRateLimiter redisLuaRateLimiter(
      RedisTemplate redisTemplate, RateLimiterConfigCentre rateLimiterConfigCentre) {
    return new RedisLuaRateLimiter(
        mixmicroBootRateLimiterProperties.isEnableGlobalQps()
            ? mixmicroBootRateLimiterProperties.getGlobalQps()
            : 0L,
        rateLimiterConfigCentre,
        redisTemplate);
  }
}
