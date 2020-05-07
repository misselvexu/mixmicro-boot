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

package xyz.vopen.framework.boot.plugin.rate.limiter.aop.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import xyz.vopen.framework.boot.plugin.rate.limiter.MixmicroBootRateLimiter;
import xyz.vopen.framework.boot.plugin.rate.limiter.annotation.RateLimiter;
import xyz.vopen.framework.boot.plugin.rate.limiter.result.RateLimiterOverFlowResponse;
import xyz.vopen.framework.boot.plugin.tools.AopTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;

/**
 * Mixmicro Boot RateLimiter MethodInterceptor Current Limitation Request
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 */
public class MixmicroBootRateLimiterMethodInterceptor implements MethodInterceptor {
  /** logger instance */
  static Logger logger = LoggerFactory.getLogger(MixmicroBootRateLimiterMethodInterceptor.class);
  /** Mixmicro Boot RateLimiter */
  private MixmicroBootRateLimiter mixmicroBootRateLimiter;
  /** Response results after flow exceeding */
  private RateLimiterOverFlowResponse overFlowRequest;

  public MixmicroBootRateLimiterMethodInterceptor(
      MixmicroBootRateLimiter mixmicroBootRateLimiter, RateLimiterOverFlowResponse overFlowRequest) {
    this.mixmicroBootRateLimiter = mixmicroBootRateLimiter;
    this.overFlowRequest = overFlowRequest;
    Assert.notNull(mixmicroBootRateLimiter, "No ApiBootRateLimiter implementation class instance.");
    logger.info("ApiBootDefaultRateLimiterInterceptorHandler load complete.");
  }

  /**
   * Processing Current Limited Business Logic
   *
   * @param invocation method invocation
   * @return method result
   * @throws Throwable error instance
   */
  @Override
  public Object invoke(MethodInvocation invocation) throws Throwable {
    try {
      Class<?> targetClass =
          (invocation.getThis() != null ? AopUtils.getTargetClass(invocation.getThis()) : null);
      Method executeMethod = invocation.getMethod();

      RateLimiter rateLimiter =
          AopTools.getMethodAnnotation(targetClass, executeMethod, RateLimiter.class);

      // request key
      String requestKey = formatRequestKey(targetClass, executeMethod);
      logger.debug("RateLimiter Request Key：{}", requestKey);
      boolean acquire = mixmicroBootRateLimiter.tryAcquire(rateLimiter.QPS(), requestKey);
      if (acquire) {
        return invocation.proceed();
      }
    } catch (Exception e) {
      logger.error("Current Limiting Request Encountered Exception.", e);
      throw e;
    }
    // If an instance is created
    if (!ObjectUtils.isEmpty(overFlowRequest)) {
      return overFlowRequest.overflow(invocation.getArguments());
    }
    return null;
  }

  /**
   * format request key
   *
   * @param targetClass target class
   * @param executeMethod execute method
   * @return request key
   */
  private String formatRequestKey(Class<?> targetClass, Method executeMethod) {
    return String.format("%s#%s", targetClass.getName(), executeMethod.getName());
  }
}
