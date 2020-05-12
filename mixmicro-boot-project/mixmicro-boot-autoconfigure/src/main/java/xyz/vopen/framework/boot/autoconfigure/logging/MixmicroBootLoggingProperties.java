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

import lombok.Data;
import xyz.vopen.framework.logging.core.ReportAway;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import xyz.vopen.framework.logging.client.admin.discovery.lb.LoadBalanceStrategy;
import xyz.vopen.framework.logging.client.admin.discovery.lb.support.RandomWeightedStrategy;
import xyz.vopen.framework.logging.client.admin.discovery.lb.support.SmoothWeightedRoundRobinStrategy;

import static xyz.vopen.framework.boot.autoconfigure.logging.MixmicroBootLoggingProperties.MIXMICRO_BOOT_LOGGING_PREFIX;

/**
 * Mixmicro Boot Logging Properties
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 *     <p>DateTime：2019-07-15 22:29
 */
@Configuration
@ConfigurationProperties(prefix = MIXMICRO_BOOT_LOGGING_PREFIX)
@Data
public class MixmicroBootLoggingProperties {
  /** Mixmicro Boot logging properties config prefix */
  public static final String MIXMICRO_BOOT_LOGGING_PREFIX = "mixmicro.boot.logging";

  /** Interception log path prefix */
  private String[] loggingPathPrefix = new String[] {"/**"};
  /** Ignore path array */
  private String[] ignorePaths;
  /** Format console log JSON */
  private boolean formatConsoleLogJson = false;
  /** show console log */
  private boolean showConsoleLog = false;
  /** Report Request Log To Admin Away */
  private ReportAway reportAway = ReportAway.just;
  /** Number of request logs reported once */
  private int reportNumberOfRequestLog = 10;
  /** report to admin initial delay second */
  private int reportInitialDelaySecond = 5;
  /** report to admin interval second */
  private int reportIntervalSecond = 5;
  /** logging cache away */
  private LoggingCacheAway loggingCacheAway = LoggingCacheAway.memory;
  /** global logging repository away */
  private MixmicroLoggingStorageAway mixmicroLoggingStorageAway = MixmicroLoggingStorageAway.memory;
  /** Mixmicro Boot Logging Admin Instance */
  private AdminInstance admin = new AdminInstance();
  /** Mixmicro Boot Logging Discovery Instance support eureka */
  private DiscoveryInstance discovery;
  /**
   * Choose load balancing strategy for admin report log {@link
   * LoadBalanceStrategy}
   *
   * @see RandomWeightedStrategy
   * @see
   *     SmoothWeightedRoundRobinStrategy
   */
  private LoadBalanceStrategyAway loadBalanceStrategy = LoadBalanceStrategyAway.RANDOM_WEIGHT;

  /** Config Mixmicro Boot Logging Admin Server report every request log to api-boot-logging-admin */
  @Data
  public static class AdminInstance {
    /** Mixmicro Boot Logging Admin Server Address */
    private String serverAddress;
  }

  /**
   * Config Mixmicro Boot Logging Discovery Instance Draw the list of Mixmicro Boot Logging Admin addresses from
   * the registry and report the request log through load balancing
   */
  @Data
  public static class DiscoveryInstance {
    /** Mixmicro Boot Logging Admin Spring Security Username */
    private String username;
    /** Mixmicro Boot Logging Admin Spring Security User Password */
    private String password;
    /** Mixmicro Boot Logging Admin Service ID */
    private String serviceId;
  }
}
