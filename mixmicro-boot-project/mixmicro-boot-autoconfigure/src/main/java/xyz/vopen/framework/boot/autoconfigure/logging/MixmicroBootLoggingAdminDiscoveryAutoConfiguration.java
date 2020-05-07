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

import xyz.vopen.framework.logging.client.admin.discovery.LoggingAdminDiscovery;
import xyz.vopen.framework.logging.client.admin.discovery.support.LoggingRegistryCenterAdminDiscovery;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;

import static xyz.vopen.framework.boot.autoconfigure.logging.MixmicroBootLoggingProperties.MIXMICRO_BOOT_LOGGING_PREFIX;

/**
 * Mixmicro Boot Logging Admin Discovery Configuration
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 *     <p>DateTime：2019-07-26 15:18
 */
@Configuration
@ConditionalOnClass(LoadBalancerClient.class)
@EnableConfigurationProperties(MixmicroBootLoggingProperties.class)
@ConditionalOnProperty(prefix = MIXMICRO_BOOT_LOGGING_PREFIX, name = "discovery.service-id")
public class MixmicroBootLoggingAdminDiscoveryAutoConfiguration {
  /** Mixmicro Boot Logging Properties */
  private MixmicroBootLoggingProperties mixmicroBootLoggingProperties;

  public MixmicroBootLoggingAdminDiscoveryAutoConfiguration(
      MixmicroBootLoggingProperties mixmicroBootLoggingProperties) {
    this.mixmicroBootLoggingProperties = mixmicroBootLoggingProperties;
  }

  /**
   * Mixmicro Boot Logging Admin Registry Center Discovery setting basic auth username if not empty {@link
   * LoggingRegistryCenterAdminDiscovery#setUsername(String)} setting basic auth password if not
   * empty {@link LoggingRegistryCenterAdminDiscovery#setPassword(String)}
   *
   * @param loadBalancerClient LoadBalance Client
   * @return LoggingRegistryCenterAdminDiscovery
   */
  @Bean
  @ConditionalOnMissingBean
  public LoggingAdminDiscovery loggingRegistryCenterAdminDiscovery(
      LoadBalancerClient loadBalancerClient) {
    LoggingRegistryCenterAdminDiscovery registryCenterAdminDiscovery =
        new LoggingRegistryCenterAdminDiscovery(
            mixmicroBootLoggingProperties.getDiscovery().getServiceId(), loadBalancerClient);
    String basicAuthUserName = mixmicroBootLoggingProperties.getDiscovery().getUsername();
    if (!ObjectUtils.isEmpty(basicAuthUserName)) {
      registryCenterAdminDiscovery.setUsername(basicAuthUserName);
    }
    String basicAuthPassword = mixmicroBootLoggingProperties.getDiscovery().getPassword();
    if (!ObjectUtils.isEmpty(basicAuthPassword)) {
      registryCenterAdminDiscovery.setPassword(basicAuthPassword);
    }
    return registryCenterAdminDiscovery;
  }
}
