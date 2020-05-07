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

package xyz.vopen.framework.boot.autoconfigure.logging.admin.ui;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import static xyz.vopen.framework.boot.autoconfigure.logging.admin.ui.MixmicroBootLoggingAdminUiProperties.MIXMICRO_BOOT_LOGGING_ADMIN_UI_PREFIX;

/**
 * Mixmicro Boot Logging Admin Ui Properties
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 *     <p>DateTime：2019-07-31 17:09
 */
@Configuration
@ConfigurationProperties(prefix = MIXMICRO_BOOT_LOGGING_ADMIN_UI_PREFIX)
@Data
public class MixmicroBootLoggingAdminUiProperties {
  /** Mixmicro Boot logging properties config prefix */
  public static final String MIXMICRO_BOOT_LOGGING_ADMIN_UI_PREFIX = "mixmicro.logging.admin.ui";
  /** Mixmicro Boot Logging Admin Ui Resource Locations */
  private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
    "classpath:/META-INF/mixmicro-logging-admin-ui/"
  };
  /** Locations of Mixmicro Boot Logging Admin ui resources. */
  private String[] resourceLocations = CLASSPATH_RESOURCE_LOCATIONS;
  /** Locations of Mixmicro Boot Logging Admin ui template. */
  private String templateLocation = CLASSPATH_RESOURCE_LOCATIONS[0];
  /** Wether the thymeleaf templates should be cached. */
  private boolean cacheTemplates = true;
  /** Page Title */
  private String title = "Mixmicro Logging Admin";
  /** Mixmicro Boot Logo */
  private String brand = "<img src=\"assets/img/icon-white.png\">";
}
