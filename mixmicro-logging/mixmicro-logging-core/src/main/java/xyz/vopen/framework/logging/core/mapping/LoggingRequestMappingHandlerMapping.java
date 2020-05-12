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

package xyz.vopen.framework.logging.core.mapping;

import xyz.vopen.framework.logging.core.annotation.ApiEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.PostConstruct;

/**
 * Mixmicro Boot Logging RequestMapping Handler
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 */
public class LoggingRequestMappingHandlerMapping extends RequestMappingHandlerMapping {
  /** The bean name of {@link LoggingRequestMappingHandlerMapping} */
  public static final String BEAN_NAME = "loggingRequestMappingHandlerMapping";
  /** logger instance */
  static Logger logger = LoggerFactory.getLogger(LoggingRequestMappingHandlerMapping.class);

  @PostConstruct
  public void setOrder() {
    super.setOrder(0);
  }

  /**
   * Only Endpoint Class with @Endpoint annotation is processed
   *
   * @param beanType endpoint bean type class
   * @return is handler
   */
  @Override
  protected boolean isHandler(Class<?> beanType) {
    boolean isEndpoint = AnnotationUtils.findAnnotation(beanType, ApiEndpoint.class) != null;
    if (isEndpoint) {
      logger.info("Load Mixmicro Boot Logging Endpoint，BeanType：[{}]", beanType.getName());
    }
    return isEndpoint;
  }
}
