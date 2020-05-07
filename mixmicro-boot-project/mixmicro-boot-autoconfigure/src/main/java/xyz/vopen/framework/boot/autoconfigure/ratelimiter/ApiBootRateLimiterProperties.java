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

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import static xyz.vopen.framework.boot.autoconfigure.ratelimiter.ApiBootRateLimiterProperties.API_BOOT_RATE_LIMITER_PREFIX;

/**
 * Mixmicro Boot Rate Limiter Properties
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 *     <p>DateTime：2019-04-26 11:27
 */
@Data
@Configuration
@ConfigurationProperties(prefix = API_BOOT_RATE_LIMITER_PREFIX)
public class ApiBootRateLimiterProperties {
  /** rate limiter prefix */
  public static final String API_BOOT_RATE_LIMITER_PREFIX = "mixmicro.boot.rate-limiter";
  /** 限流请求地址前缀 */
  @Deprecated private String[] interceptorUrl = {"/**"};
  /** 全局QPS配置 默认每秒限流10次请求 */
  @Deprecated private Long globalQps = 10L;
  /** 开启全局QPS配置 */
  @Deprecated private boolean enableGlobalQps = false;
}
