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

import xyz.vopen.framework.logging.client.LoggingFactoryBean;
import xyz.vopen.framework.logging.client.interceptor.LoggingInterceptor;
import xyz.vopen.framework.logging.client.interceptor.web.LoggingWebInterceptor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Mixmicro Boot logging web auto configuration registry MinBox logging request interceptor {@link
 * LoggingInterceptor}
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 */
@Configuration
@ConditionalOnClass(LoggingFactoryBean.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@EnableConfigurationProperties(MixmicroBootLoggingProperties.class)
@AutoConfigureAfter(MixmicroBootLoggingAutoConfiguration.class)
public class MixmicroBootLoggingWebAutoConfiguration implements WebMvcConfigurer {
  private LoggingWebInterceptor loggingWebInterceptor;
  private MixmicroBootLoggingProperties loggingProperties;

  public MixmicroBootLoggingWebAutoConfiguration(
      LoggingWebInterceptor loggingWebInterceptor, MixmicroBootLoggingProperties loggingProperties) {
    this.loggingWebInterceptor = loggingWebInterceptor;
    this.loggingProperties = loggingProperties;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry
        .addInterceptor(loggingWebInterceptor)
        .addPathPatterns(loggingProperties.getLoggingPathPrefix());
  }
}
