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

package xyz.vopen.framework.boot.plugin.rate.limiter.support;

import xyz.vopen.framework.boot.plugin.rate.limiter.MixmicroBootRateLimiter;
import xyz.vopen.framework.boot.plugin.rate.limiter.centre.RateLimiterConfigCentre;
import org.springframework.util.ObjectUtils;
import xyz.vopen.framework.boot.plugin.rate.limiter.centre.support.AbstractRateLimiterConfigCentre;

/**
 * Mixmicro Boot RateLimiter Abstract Support
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 */
public abstract class AbstractRateLimiter implements MixmicroBootRateLimiter {
  /** global QPS */
  private Long globalQPS;
  /** Mixmicro Boot RateLimiter Config Centre */
  private RateLimiterConfigCentre rateLimiterConfigCentre;

  public AbstractRateLimiter(Long globalQPS, RateLimiterConfigCentre rateLimiterConfigCentre) {
    this.globalQPS = globalQPS;
    this.rateLimiterConfigCentre = rateLimiterConfigCentre;
  }

  /**
   * Priority to get QPS order first：Distributed configuration center（Nacos、Apollo）
   * second：Annotation @RateLimiter QPS value third：GlobalQPS from
   * application.yml（api.boot.rate-limiter.global-qps）
   *
   * @param configKey config key
   * @param annotationQPS RateLimiter(QPS=xx)
   * @return QPS value
   */
  protected Long getPriorityQPS(String configKey, Double annotationQPS) {
    // first：config centre value
    Long centreConfigValue = rateLimiterConfigCentre.getQps(configKey);
    if (!AbstractRateLimiterConfigCentre.DEFAULT_QPS.equals(centreConfigValue)) {
      return centreConfigValue;
    }
    // If the configuration center does not have the qps of the key
    // set qps to config centre
    else {
      rateLimiterConfigCentre.setQps(configKey, annotationQPS.longValue());
    }
    // second：@RateLimiter value
    if (!ObjectUtils.isEmpty(annotationQPS) && annotationQPS > 0) {
      return annotationQPS.longValue();
    }
    // third：global value
    return globalQPS;
  }
}
