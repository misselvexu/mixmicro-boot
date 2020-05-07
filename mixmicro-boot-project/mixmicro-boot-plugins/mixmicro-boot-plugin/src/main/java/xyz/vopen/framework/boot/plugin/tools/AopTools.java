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

package xyz.vopen.framework.boot.plugin.tools;

import org.springframework.core.BridgeMethodResolver;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;

/**
 * Aop Tools
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 */
public class AopTools {
  /**
   * get method declared annotation
   *
   * @param targetClass class instance
   * @param method method instance
   * @param annotationClass annotation class
   * @param <T> annotation type
   * @return annotation instance
   */
  public static <T> T getMethodAnnotation(Class targetClass, Method method, Class annotationClass) {
    Method specificMethod = ClassUtils.getMostSpecificMethod(method, targetClass);
    // declared method object instance
    Method declaredMethod = BridgeMethodResolver.findBridgedMethod(specificMethod);
    return (T) declaredMethod.getDeclaredAnnotation(annotationClass);
  }
}
