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

package xyz.vopen.framework.boot.autoconfigure.push;

import cn.jpush.api.JPushClient;
import xyz.vopen.framework.boot.plugin.message.push.MixmicroBootMessagePushService;
import xyz.vopen.framework.boot.plugin.message.push.aop.advistor.MixmicroBootMessagePushClientSwitchAdvisor;
import xyz.vopen.framework.boot.plugin.message.push.aop.interceptor.MixmicroBootMessagePushSwitchAnnotationInterceptor;
import xyz.vopen.framework.boot.plugin.message.push.model.PushClientConfig;
import xyz.vopen.framework.boot.plugin.message.push.support.MixmicroBootMessagePushJiGuangServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Mixmicro Boot Message Push Auto Configuration
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 *     <p>DateTime：2019-04-20 14:27
 */
@Configuration
@ConditionalOnClass({MixmicroBootMessagePushService.class, JPushClient.class})
@EnableConfigurationProperties(MixmicroBootMessagePushProperties.class)
public class MixmicroBootMessagePushAutoConfiguration {
  /** Mixmicro Boot Message Push Properties */
  private MixmicroBootMessagePushProperties mixmicroBootMessagePushProperties;
  /** default client name */
  private static final String DEFAULT_CLIENT_NAME = "default";

  public MixmicroBootMessagePushAutoConfiguration(
      MixmicroBootMessagePushProperties mixmicroBootMessagePushProperties) {
    this.mixmicroBootMessagePushProperties = mixmicroBootMessagePushProperties;
  }

  /**
   * instantiation message push service
   *
   * @return ApiBootMessagePushService
   */
  @Bean
  @ConditionalOnMissingBean
  MixmicroBootMessagePushService apiBootMessagePushService() {
    Map<String, PushClientConfig> configs = getClientConfig();
    return new MixmicroBootMessagePushJiGuangServiceImpl(
        configs, mixmicroBootMessagePushProperties.isProduction());
  }

  /**
   * Message Push Aop Interceptor
   *
   * @return ApiBootMessagePushSwitchAnnotationInterceptor
   */
  @Bean
  @ConditionalOnMissingBean
  MixmicroBootMessagePushSwitchAnnotationInterceptor apiBootMessagePushSwitchAnnotationInterceptor() {
    return new MixmicroBootMessagePushSwitchAnnotationInterceptor();
  }

  /**
   * Message Push Aop Advisor
   *
   * @return ApiBootMessagePushClientSwitchAdvisor
   */
  @Bean
  @ConditionalOnMissingBean
  MixmicroBootMessagePushClientSwitchAdvisor apiBootMessagePushClientSwitchAdvisor() {
    return new MixmicroBootMessagePushClientSwitchAdvisor(
        apiBootMessagePushSwitchAnnotationInterceptor());
  }

  /**
   * get client config
   *
   * @return
   */
  private Map<String, PushClientConfig> getClientConfig() {

    Map<String, PushClientConfig> configs = new HashMap(1);

    // default client config
    configs.put(DEFAULT_CLIENT_NAME, mixmicroBootMessagePushProperties.getClient());

    // multiple
    if (!ObjectUtils.isEmpty(mixmicroBootMessagePushProperties.getMultiple())) {
      configs.putAll(mixmicroBootMessagePushProperties.getMultiple());
    }
    return configs;
  }
}
