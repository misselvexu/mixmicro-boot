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

package xyz.vopen.framework.boot.autoconfigure.logging;

import feign.RequestInterceptor;
import xyz.vopen.framework.logging.client.http.openfeign.LoggingOpenFeignInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Mixmicro Boot Logging Openfeign Http Away Configuration
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 *     <p>DateTime：2019-07-16 16:05
 */
@Configuration
@ConditionalOnClass({RequestInterceptor.class, LoggingOpenFeignInterceptor.class})
@EnableConfigurationProperties(MixmicroBootLoggingProperties.class)
public class MixmicroBootLoggingOpenfeignAutoConfiguration {

  /**
   * Mixmicro Boot Logging Openfeign Interceptor
   *
   * @return ApiBootLogOpenFeignInterceptor
   */
  @Bean
  @ConditionalOnMissingBean
  public LoggingOpenFeignInterceptor apiBootLogOpenFeignInterceptor() {
    return new LoggingOpenFeignInterceptor();
  }
}
