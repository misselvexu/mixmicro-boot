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

import xyz.vopen.framework.logging.client.global.MixmicroLogging;
import xyz.vopen.framework.logging.client.global.support.MixmicroLoggingMemoryStorage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * the "minbox-logging" global log repository configuration
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 */
@Configuration
@ConditionalOnClass(MixmicroLogging.class)
public class MixmicroBootLoggingGlobalLogStorageAutoConfiguration {
  /**
   * Instance global log memory mode repository
   *
   * @return {@link MixmicroLoggingMemoryStorage}
   */
  @Bean
  @ConditionalOnProperty(
      prefix = MixmicroBootLoggingProperties.MIXMICRO_BOOT_LOGGING_PREFIX,
      name = "global-logging-repository-away",
      havingValue = "memory",
      matchIfMissing = true)
  public MixmicroLogging globalLogging() {
    return new MixmicroLoggingMemoryStorage();
  }
}
