package xyz.vopen.framework.boot.autoconfigure.swagger.annotation;

import xyz.vopen.framework.boot.autoconfigure.swagger.ApiBootSwaggerAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 使用该注解开启ApiBoot整合Swagger2文档
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 *     <p>DateTime：2019-03-17 08:58
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import(ApiBootSwaggerAutoConfiguration.class)
public @interface EnableApiBootSwagger {}
