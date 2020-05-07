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

package xyz.vopen.framework.logging.admin.event;

import lombok.Getter;
import xyz.vopen.framework.logging.core.LoggingClientNotice;
import org.springframework.context.ApplicationEvent;
import xyz.vopen.framework.logging.admin.endpoint.LoggingEndpoint;

/**
 * The time of publication after receiving the reported log information
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @see LoggingEndpoint
 */
@Getter
public class ReportLogEvent extends ApplicationEvent {
  /** Mixmicro Boot Log Client Report Notice Object */
  private LoggingClientNotice logClientNotice;

  public ReportLogEvent(Object source, LoggingClientNotice logClientNotice) {
    super(source);
    this.logClientNotice = logClientNotice;
  }
}
