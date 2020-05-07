package xyz.vopen.framework.boot.plugin.resource.load.annotation;

import xyz.vopen.framework.boot.plugin.resource.load.enums.ResourceStoreEvent;

import java.lang.annotation.*;

/**
 * 资源加载注解，该注解只能配置在方法上
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 *     <p>DateTime：2019-04-11 21:52
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResourceLoad {
  /**
   * 资源存储事件 默认查询资源
   *
   * @return ResourceStoreEvent
   */
  ResourceStoreEvent event() default ResourceStoreEvent.SELECT;
}
