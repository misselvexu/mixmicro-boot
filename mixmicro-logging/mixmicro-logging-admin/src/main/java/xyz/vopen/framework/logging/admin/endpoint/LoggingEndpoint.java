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

package xyz.vopen.framework.logging.admin.endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.vopen.framework.logging.admin.event.ReportLogEvent;
import xyz.vopen.framework.logging.core.LoggingClientNotice;
import xyz.vopen.framework.logging.core.annotation.ApiEndpoint;
import xyz.vopen.framework.logging.core.response.ReportResponse;

/**
 * Mixmicro Boot Logging Endpoint Controller Receive Log report Provide log analysis
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 */
@ApiEndpoint
public class LoggingEndpoint implements ApplicationContextAware {
  /** The bean name of {@link LoggingEndpoint} */
  public static final String BEAN_NAME = "loggingEndpoint";
  /** logger instance */
  static Logger logger = LoggerFactory.getLogger(LoggingEndpoint.class);

  /** Application Context */
  private ApplicationContext applicationContext;

  /**
   * Receive Mixmicro Boot Logging Client Report Request Log
   *
   * @param notice Mixmicro Boot Logging Client Notice Log Instance
   * @return report status
   */
  @PostMapping(value = "/logging/report")
  @ResponseBody
  public ResponseEntity<ReportResponse> report(@RequestBody LoggingClientNotice notice) {
    // is report success
    boolean reportSuccess = true;
    try {
      // publish ReportLogEvent
      // Persistence logs are handed over to listeners
      applicationContext.publishEvent(new ReportLogEvent(this, notice));
    } catch (Exception e) {
      reportSuccess = false;
      logger.error(e.getMessage(), e);
    }
    ReportResponse response = new ReportResponse();
    response.setStatus(reportSuccess ? ReportResponse.SUCCESS : ReportResponse.ERROR);

    return ResponseEntity.ok(response);
  }

  @Override
  public void setApplicationContext(@NonNull ApplicationContext applicationContext)
      throws BeansException {
    this.applicationContext = applicationContext;
  }
}
