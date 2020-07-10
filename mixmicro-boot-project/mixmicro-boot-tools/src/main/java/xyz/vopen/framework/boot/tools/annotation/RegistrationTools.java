package xyz.vopen.framework.boot.tools.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Registration all tools
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import(ToolsBeanDefinitionRegistrar.class)
public @interface RegistrationTools {}
