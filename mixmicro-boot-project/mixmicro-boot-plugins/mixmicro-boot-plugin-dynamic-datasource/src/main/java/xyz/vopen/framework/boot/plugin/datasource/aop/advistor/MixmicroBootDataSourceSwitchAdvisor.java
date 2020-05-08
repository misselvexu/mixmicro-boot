package xyz.vopen.framework.boot.plugin.datasource.aop.advistor;

import org.aopalliance.aop.Advice;
import xyz.vopen.framework.boot.plugin.datasource.annotation.DynamicDataSource;
import xyz.vopen.framework.boot.plugin.datasource.aop.interceptor.MixmicroBootDataSourceSwitchAnnotationInterceptor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.util.Assert;

/**
 * Mixmicro Boot DataSource Switch {@link org.springframework.aop.PointcutAdvisor}
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 */
public class MixmicroBootDataSourceSwitchAdvisor extends AbstractPointcutAdvisor
    implements BeanFactoryAware {
  /**
   * Method Aspect Implementation
   *
   * @see MixmicroBootDataSourceSwitchAnnotationInterceptor
   */
  private Advice advice;
  /** use {@link DynamicDataSource} annotations as entry points */
  private Pointcut pointcut;
  /** the spring {@link BeanFactory} */
  private BeanFactory beanFactory;

  /**
   * Initialize global variables using constructor
   *
   * @param mixmicroBootDataSourceSwitchAnnotationInterceptor {@link
   *     MixmicroBootDataSourceSwitchAnnotationInterceptor}
   */
  public MixmicroBootDataSourceSwitchAdvisor(
      MixmicroBootDataSourceSwitchAnnotationInterceptor mixmicroBootDataSourceSwitchAnnotationInterceptor) {
    // build pointcut instance
    this.pointcut = buildPointcut();
    // build advice instance
    this.advice = mixmicroBootDataSourceSwitchAnnotationInterceptor;
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

  /**
   * build pointcut instance use {@link DynamicDataSource} as a {@link Pointcut} scope： 1. {@link
   * DynamicDataSource} on the class 2. {@link DynamicDataSource} on the method
   */
  private Pointcut buildPointcut() {
    // class
    Pointcut cpc = new AnnotationMatchingPointcut(DynamicDataSource.class, true);
    // method
    Pointcut mpc = AnnotationMatchingPointcut.forMethodAnnotation(DynamicDataSource.class);

    // union
    ComposablePointcut pointcut = new ComposablePointcut(cpc);
    return pointcut.union(mpc);
  }

  /**
   * Highest priority
   *
   * @return {@link org.springframework.aop.PointcutAdvisor} Priority value
   */
  @Override
  public int getOrder() {
    return HIGHEST_PRECEDENCE;
  }
}
