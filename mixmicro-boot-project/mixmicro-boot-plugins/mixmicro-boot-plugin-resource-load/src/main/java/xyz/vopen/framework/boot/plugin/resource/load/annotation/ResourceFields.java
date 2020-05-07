package xyz.vopen.framework.boot.plugin.resource.load.annotation;

import java.lang.annotation.*;

/**
 * 资源业务字段集合
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 *     <p>DateTime：2019-04-11 22:12
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResourceFields {
  /**
   * 资源业务字段的集合
   *
   * @return
   */
  ResourceField[] value();
}
