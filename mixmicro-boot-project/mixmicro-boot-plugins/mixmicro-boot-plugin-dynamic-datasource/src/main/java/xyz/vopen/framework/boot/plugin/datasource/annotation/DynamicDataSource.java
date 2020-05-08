package xyz.vopen.framework.boot.plugin.datasource.annotation;

import java.lang.annotation.*;

/**
 * data source switch annotation
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface DynamicDataSource {
  /**
   * data source pool name
   *
   * @return pool name
   */
  String value();
}
