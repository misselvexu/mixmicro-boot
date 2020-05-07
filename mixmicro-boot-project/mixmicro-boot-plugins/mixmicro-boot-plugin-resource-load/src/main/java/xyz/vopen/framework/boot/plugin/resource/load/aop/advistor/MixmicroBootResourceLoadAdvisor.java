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
package xyz.vopen.framework.boot.plugin.resource.load.aop.advistor;

import org.aopalliance.aop.Advice;
import xyz.vopen.framework.boot.plugin.resource.load.annotation.ResourceLoad;
import xyz.vopen.framework.boot.plugin.resource.load.aop.interceptor.MixmicroBootResourceLoadMethodInterceptor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.util.Assert;

/**
 * Mixmicro Boot Resource Load Pointcut Advisor
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 *     <p>DateTime：2019-04-01 16:29
 */
public class MixmicroBootResourceLoadAdvisor extends AbstractPointcutAdvisor
    implements BeanFactoryAware {

  private Advice advice;
  private Pointcut pointcut;
  private BeanFactory beanFactory;

  public MixmicroBootResourceLoadAdvisor(
      MixmicroBootResourceLoadMethodInterceptor mixmicroBootResourceLoadMethodInterceptor) {
    // build pointcut instance
    this.pointcut = buildPointcut();
    // build advice instance
    this.advice = mixmicroBootResourceLoadMethodInterceptor;
    if (this.advice instanceof BeanFactoryAware) {
      ((BeanFactoryAware) this.advice).setBeanFactory(beanFactory);
    }
  }

  @Override
  public Pointcut getPointcut() {
    Assert.notNull(this.pointcut, "pointcut is required.");
    return this.pointcut;
  }

  @Override
  public Advice getAdvice() {
    Assert.notNull(this.advice, "advice is required.");
    return this.advice;
  }

  @Override
  public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
    this.beanFactory = beanFactory;
  }

  /** build pointcut instance */
  private Pointcut buildPointcut() {
    // method
    Pointcut mpc = AnnotationMatchingPointcut.forMethodAnnotation(ResourceLoad.class);
    return new ComposablePointcut(mpc);
  }
}
