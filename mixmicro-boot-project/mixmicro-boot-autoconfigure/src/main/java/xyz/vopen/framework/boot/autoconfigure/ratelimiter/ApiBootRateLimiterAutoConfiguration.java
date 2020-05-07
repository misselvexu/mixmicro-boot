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
import xyz.vopen.framework.boot.plugin.rate.limiter.aop.advisor.MixmicroBootRateLimiterAdvisor;
import xyz.vopen.framework.boot.plugin.rate.limiter.aop.interceptor.MixmicroBootRateLimiterMethodInterceptor;
import xyz.vopen.framework.boot.plugin.rate.limiter.centre.RateLimiterConfigCentre;
import xyz.vopen.framework.boot.plugin.rate.limiter.centre.support.DefaultRateLimiterConfigCentre;
import xyz.vopen.framework.boot.plugin.rate.limiter.result.RateLimiterOverFlowResponse;
import xyz.vopen.framework.boot.plugin.rate.limiter.support.GoogleGuavaRateLimiter;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Mixmicro Boot RateLimiter Auto Configuration
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 *     <p>DateTime：2019-04-26 09:36
 */
@Configuration
@ConditionalOnClass(MixmicroBootRateLimiter.class)
@EnableConfigurationProperties(ApiBootRateLimiterProperties.class)
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
@Import({
  ApiBootRateLimiterRedisAutoConfiguration.class,
  ApiBootRateLimiterNacosConfigConfiguration.class
})
public class ApiBootRateLimiterAutoConfiguration {
  /** Mixmicro Boot Rate Limiter Properties */
  private ApiBootRateLimiterProperties apiBootRateLimiterProperties;
  /** RateLimiter OverFlow Request */
  private RateLimiterOverFlowResponse rateLimiterOverFlowResponse;

  public ApiBootRateLimiterAutoConfiguration(
      ApiBootRateLimiterProperties apiBootRateLimiterProperties,
      ObjectProvider<RateLimiterOverFlowResponse> rateLimiterOverFlowRequestObjectProvider) {
    this.apiBootRateLimiterProperties = apiBootRateLimiterProperties;
    this.rateLimiterOverFlowResponse = rateLimiterOverFlowRequestObjectProvider.getIfAvailable();
  }

  /**
   * google guava rate limiter
   *
   * @param rateLimiterConfigCentre RateLimiter Config Centre
   * @return ApiBootRateLimiter
   */
  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnMissingClass("org.springframework.data.redis.core.RedisTemplate")
  public MixmicroBootRateLimiter googleGuavaRateLimiter(
      RateLimiterConfigCentre rateLimiterConfigCentre) {
    return new GoogleGuavaRateLimiter(
        apiBootRateLimiterProperties.isEnableGlobalQps()
            ? apiBootRateLimiterProperties.getGlobalQps()
            : 0L,
        rateLimiterConfigCentre);
  }

  /**
   * default config centre
   *
   * @return RateLimiterConfigCentre
   */
  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnMissingClass({"com.alibaba.boot.nacos.config.properties.NacosConfigProperties"})
  public RateLimiterConfigCentre defaultRateLimiterConfigCentre() {
    return new DefaultRateLimiterConfigCentre();
  }

  /**
   * Mixmicro Boot RateLimiter Pointcut Advisor
   *
   * @param mixmicroBootRateLimiterMethodInterceptor ResourceLoad Annotation Method Interceptor
   * @return ApiBootRateLimiterAdvisor
   */
  @Bean
  @ConditionalOnMissingBean
  MixmicroBootRateLimiterAdvisor rateLimiterAdvisor(
      MixmicroBootRateLimiterMethodInterceptor mixmicroBootRateLimiterMethodInterceptor) {
    return new MixmicroBootRateLimiterAdvisor(mixmicroBootRateLimiterMethodInterceptor);
  }

  /**
   * ResourceLoad Annotation Method Interceptor Implementing major business logic
   *
   * @param mixmicroBootRateLimiter apiBootRateLimiter
   * @return ApiBootRateLimiterMethodInterceptor
   */
  @Bean
  @ConditionalOnMissingBean
  MixmicroBootRateLimiterMethodInterceptor rateLimiterMethodInterceptor(
      MixmicroBootRateLimiter mixmicroBootRateLimiter) {
    return new MixmicroBootRateLimiterMethodInterceptor(mixmicroBootRateLimiter, rateLimiterOverFlowResponse);
  }
}
