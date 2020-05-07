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

package xyz.vopen.framework.boot.autoconfigure.logging.admin;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import static xyz.vopen.framework.boot.autoconfigure.logging.admin.MixmicroBootLoggingAdminProperties.MIXMICRO_BOOT_LOGGING_ADMIN_PREFIX;

/**
 * Mixmicro Boot Logging Admin Properties
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 *     <p>DateTime：2019-07-19 12:56
 */
@Configuration
@ConfigurationProperties(prefix = MIXMICRO_BOOT_LOGGING_ADMIN_PREFIX)
@Data
public class MixmicroBootLoggingAdminProperties {
  /** Mixmicro Boot logging properties config prefix */
  public static final String MIXMICRO_BOOT_LOGGING_ADMIN_PREFIX = "mixmicro.boot.logging.admin";
  /** Whether to print the logs reported on the console */
  private boolean showConsoleReportLog = false;
  /** Format console log JSON */
  private boolean formatConsoleLogJson = false;
}
