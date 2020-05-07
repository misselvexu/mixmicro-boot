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
package xyz.vopen.framework.boot.plugin.resource.load.aop.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import xyz.vopen.framework.boot.plugin.resource.load.annotation.ResourceLoad;
import xyz.vopen.framework.boot.plugin.resource.load.enums.ResourceStoreEvent;
import xyz.vopen.framework.boot.plugin.resource.load.pusher.MixmicroBootResourcePusher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;

/**
 * Mixmicro Boot DataSource Advice use spring aop
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 *     <p>DateTime：2019-04-01 16:44
 */
public class MixmicroBootResourceLoadMethodInterceptor implements MethodInterceptor {
  /** logger instance */
  static Logger logger = LoggerFactory.getLogger(MixmicroBootResourceLoadMethodInterceptor.class);

  /** Mixmicro Boot Resource Pusher Use to query resource url */
  private MixmicroBootResourcePusher mixmicroBootResourcePusher;

  public MixmicroBootResourceLoadMethodInterceptor(MixmicroBootResourcePusher mixmicroBootResourcePusher) {
    this.mixmicroBootResourcePusher = mixmicroBootResourcePusher;
  }

  /**
   * Execute method All event is after method execution query resource url
   *
   * @param invocation MethodInvocation
   * @return Execute Result
   * @throws Throwable method declared exception
   * @see ResourceStoreEvent
   */
  @Override
  public Object invoke(MethodInvocation invocation) throws Throwable {
    Class<?> targetClass =
        (invocation.getThis() != null ? AopUtils.getTargetClass(invocation.getThis()) : null);
    Method specificMethod = ClassUtils.getMostSpecificMethod(invocation.getMethod(), targetClass);

    // declared method object instance
    Method declaredMethod = BridgeMethodResolver.findBridgedMethod(specificMethod);

    // method param array
    Object[] params = invocation.getArguments();

    // execute method logic
    Object result = invocation.proceed();
    // get resource Load
    ResourceLoad resourceLoad = declaredMethod.getDeclaredAnnotation(ResourceLoad.class);
    if (!ObjectUtils.isEmpty(resourceLoad)) {
      switch (resourceLoad.event()) {
        case SELECT:
          logger.debug("Execute select resource.");
          if (!ObjectUtils.isEmpty(result)) {
            // resource push
            mixmicroBootResourcePusher.loadResource(declaredMethod, result);
          }
          break;
        case INSERT:
          logger.debug("Execute insert resource.");
          // pull resource form param
          mixmicroBootResourcePusher.insertResource(declaredMethod, params);
          break;
        case DELETE:
          logger.debug("Execute delete resource.");
          mixmicroBootResourcePusher.deleteResource(declaredMethod, params);
          break;
        case UPDATE:
          logger.debug("Execute update resource.");
          mixmicroBootResourcePusher.updateResource(declaredMethod, params);
          break;
        default:
          break;
      }
    }
    return result;
  }
}
