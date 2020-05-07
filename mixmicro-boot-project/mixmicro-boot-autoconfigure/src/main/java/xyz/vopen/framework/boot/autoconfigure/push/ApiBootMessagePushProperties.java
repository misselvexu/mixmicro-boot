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

import lombok.Data;
import xyz.vopen.framework.boot.plugin.message.push.model.PushClientConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static xyz.vopen.framework.boot.autoconfigure.push.ApiBootMessagePushProperties.API_BOOT_PUSH_PREFIX;

/**
 * Mixmicro Boot Message Push Properties
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 *     <p>DateTime：2019-04-20 14:52
 */
@Data
@Configuration
@ConfigurationProperties(prefix = API_BOOT_PUSH_PREFIX)
public class ApiBootMessagePushProperties {
  /** push 配置前缀 */
  public static final String API_BOOT_PUSH_PREFIX = "mixmicro.boot.push";
  /** 极光推送客户端配置 */
  private PushClientConfig client;
  /** 多个推送客户端配置集合 */
  private Map<String, PushClientConfig> multiple;
  /** 配置是否生产环境推送，适用IOS平台 */
  private boolean production = false;
}
