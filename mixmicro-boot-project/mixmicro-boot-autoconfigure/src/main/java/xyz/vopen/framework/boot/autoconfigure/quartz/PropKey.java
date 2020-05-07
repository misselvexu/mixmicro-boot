package xyz.vopen.framework.boot.autoconfigure.quartz;

import java.lang.annotation.*;

/**
 * Prop Key
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 *     <p>DateTime：2019-03-30 17:17
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PropKey {
  String value();
}
