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
import xyz.vopen.framework.boot.plugin.resource.load.aop.advistor.MixmicroBootResourceLoadAdvisor;
import xyz.vopen.framework.boot.plugin.resource.load.aop.interceptor.MixmicroBootResourceLoadMethodInterceptor;
import xyz.vopen.framework.boot.plugin.resource.load.pusher.MixmicroBootResourcePusher;
import xyz.vopen.framework.boot.plugin.resource.load.pusher.support.MixmicroBootMemoryResourcePusher;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Mixmicro Boot Resource Load Auto Config
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 *     <p>DateTime：2019-04-12 13:30
 */
@Configuration
@ConditionalOnClass(MixmicroBootResourceStoreDelegate.class)
@Import(ApiBootResourceRedisLoadAutoConfiguration.class)
public class ApiBootResourceLoadAutoConfiguration {
  /** Mixmicro Boot Resource Load Store Delegate */
  private MixmicroBootResourceStoreDelegate resourceStoreDelegate;

  public ApiBootResourceLoadAutoConfiguration(
      ObjectProvider<MixmicroBootResourceStoreDelegate> resourceStoreDelegateObjectProvider) {
    this.resourceStoreDelegate = resourceStoreDelegateObjectProvider.getIfAvailable();
  }

  /**
   * Mixmicro Boot Resource Load Pointcut Advisor
   *
   * @param resourceLoadMethodInterceptor ResourceLoad Annotation Method Interceptor
   * @return ApiBootResourceLoadAdvisor
   */
  @Bean
  @ConditionalOnMissingBean
  MixmicroBootResourceLoadAdvisor resourceLoadAdvisor(
      MixmicroBootResourceLoadMethodInterceptor resourceLoadMethodInterceptor) {
    return new MixmicroBootResourceLoadAdvisor(resourceLoadMethodInterceptor);
  }

  /**
   * ResourceLoad Annotation Method Interceptor Implementing major business logic
   *
   * @return ApiBootResourceLoadMethodInterceptor
   */
  @Bean
  @ConditionalOnMissingBean
  MixmicroBootResourceLoadMethodInterceptor resourceLoadMethodInterceptor(
      MixmicroBootResourcePusher mixmicroBootResourcePusher) {
    return new MixmicroBootResourceLoadMethodInterceptor(mixmicroBootResourcePusher);
  }

  /**
   * Mixmicro Boot Jdbc Resource Pusher extends from ApiBootJdbcResourcePusher
   *
   * @return ApiBootJdbcResourcePusher
   */
  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnMissingClass("org.springframework.data.redis.core.RedisTemplate")
  MixmicroBootMemoryResourcePusher apiBootMemoryResourcePusher() {
    return new MixmicroBootMemoryResourcePusher(resourceStoreDelegate);
  }
}
