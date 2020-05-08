package xyz.vopen.framework.boot.plugin.datasource.aop.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import xyz.vopen.framework.boot.plugin.datasource.annotation.DynamicDataSource;
import xyz.vopen.framework.boot.plugin.datasource.routing.DataSourceContextHolder;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;

/**
 * Aspects of the method provided by the Mixmicro Boot DataSource Switch First get the {@link
 * DynamicDataSource} annotation instance from the class. If it is null, use the annotation
 * configuration on the method.
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 */
public class MixmicroBootDataSourceSwitchAnnotationInterceptor implements MethodInterceptor {

  @Override
  public Object invoke(MethodInvocation invocation) throws Throwable {
    Object methodResult;
    try {
      Class<?> targetClass =
          (invocation.getThis() != null ? AopUtils.getTargetClass(invocation.getThis()) : null);
      Method specificMethod = ClassUtils.getMostSpecificMethod(invocation.getMethod(), targetClass);
      Method userDeclaredMethod = BridgeMethodResolver.findBridgedMethod(specificMethod);
      // get class declared DataSourceSwitch annotation
      DynamicDataSource dynamicDataSource = targetClass.getDeclaredAnnotation(DynamicDataSource.class);
      if (dynamicDataSource == null) {
        // get declared DataSourceSwitch annotation
        dynamicDataSource = userDeclaredMethod.getDeclaredAnnotation(DynamicDataSource.class);
      }
      if (dynamicDataSource != null) {
        // setting current thread use data source pool name
        DataSourceContextHolder.set(dynamicDataSource.value());
      }
      methodResult = invocation.proceed();
    } finally {
      // remove current thread use datasource pool name
      DataSourceContextHolder.remove();
    }
    return methodResult;
  }
}
