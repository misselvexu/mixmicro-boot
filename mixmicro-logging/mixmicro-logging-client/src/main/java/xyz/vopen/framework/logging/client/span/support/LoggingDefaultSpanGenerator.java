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

package xyz.vopen.framework.logging.client.span.support;

import xyz.vopen.framework.logging.client.MinBoxLoggingException;
import xyz.vopen.framework.logging.client.span.LoggingSpanGenerator;
import xyz.vopen.framework.logging.core.MixmicroLog;

import java.util.UUID;

/**
 * Mixmicro Boot Logging Default Span Use By Create New SpanId
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 */
public class LoggingDefaultSpanGenerator implements LoggingSpanGenerator {
  /**
   * Create New SpanId Use random uuid as default spanId
   *
   * @return {@link MixmicroLog#getSpanId()}
   * @throws MinBoxLoggingException Exception
   */
  @Override
  public String createSpanId() throws MinBoxLoggingException {
    return UUID.randomUUID().toString();
  }
}
