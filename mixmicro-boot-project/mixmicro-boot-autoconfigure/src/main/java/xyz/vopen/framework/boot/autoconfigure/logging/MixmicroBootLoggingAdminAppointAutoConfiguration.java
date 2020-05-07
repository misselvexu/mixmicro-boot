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
import xyz.vopen.framework.logging.client.admin.discovery.lb.LoadBalanceStrategy;
import xyz.vopen.framework.logging.client.admin.discovery.lb.support.RandomWeightedStrategy;
import xyz.vopen.framework.logging.client.admin.discovery.lb.support.SmoothWeightedRoundRobinStrategy;
import xyz.vopen.framework.logging.client.admin.discovery.support.LoggingAppointAdminDiscovery;
import xyz.vopen.framework.logging.client.admin.discovery.support.LoggingRegistryCenterAdminDiscovery;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static xyz.vopen.framework.boot.autoconfigure.logging.MixmicroBootLoggingProperties.MIXMICRO_BOOT_LOGGING_PREFIX;

/**
 * Mixmicro Boot Logging Admin Appoint Configuration
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 *     <p>DateTime：2019-07-26 15:19
 */
@Configuration
@EnableConfigurationProperties(MixmicroBootLoggingProperties.class)
@ConditionalOnMissingBean(LoggingRegistryCenterAdminDiscovery.class)
@ConditionalOnProperty(prefix = MIXMICRO_BOOT_LOGGING_PREFIX, name = "admin.server-address")
public class MixmicroBootLoggingAdminAppointAutoConfiguration {
  /** Mixmicro Boot Logging Properties */
  private MixmicroBootLoggingProperties mixmicroBootLoggingProperties;

  public MixmicroBootLoggingAdminAppointAutoConfiguration(
      MixmicroBootLoggingProperties mixmicroBootLoggingProperties) {
    this.mixmicroBootLoggingProperties = mixmicroBootLoggingProperties;
  }

  /**
   * Mixmicro Boot Logging Admin Config Discovery Multiple Use "," Separation
   *
   * @return LoggingAdminDiscovery
   */
  @Bean
  @ConditionalOnMissingBean
  public LoggingAdminDiscovery loggingConfigAdminDiscovery() {
    String[] serverAddressArray = mixmicroBootLoggingProperties.getAdmin().getServerAddress().split(",");
    LoggingAppointAdminDiscovery appointAdminDiscovery =
        new LoggingAppointAdminDiscovery(serverAddressArray);
    LoadBalanceStrategy loadBalanceStrategy = instantiationLoadBalanceStrategy();
    appointAdminDiscovery.setLoadBalanceStrategy(loadBalanceStrategy);
    return appointAdminDiscovery;
  }

  /**
   * Create {@link LoadBalanceStrategy} by {@link LoadBalanceStrategyAway} default is use {@link
   * RandomWeightedStrategy}
   *
   * @return {@link LoadBalanceStrategy} support class instance
   */
  private LoadBalanceStrategy instantiationLoadBalanceStrategy() {
    LoadBalanceStrategyAway strategyAway = mixmicroBootLoggingProperties.getLoadBalanceStrategy();
    LoadBalanceStrategy strategy;
    switch (strategyAway) {
      case POLL_WEIGHT:
        strategy = new SmoothWeightedRoundRobinStrategy();
        break;
      case RANDOM_WEIGHT:
      default:
        strategy = new RandomWeightedStrategy();
        break;
    }
    return strategy;
  }
}
