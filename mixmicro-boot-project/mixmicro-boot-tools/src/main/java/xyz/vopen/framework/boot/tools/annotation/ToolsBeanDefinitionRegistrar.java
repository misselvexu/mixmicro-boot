package xyz.vopen.framework.boot.tools.annotation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import xyz.vopen.framework.boot.tools.ApplicationContextTools;
import xyz.vopen.framework.boot.tools.BeanFactoryTools;
import xyz.vopen.framework.util.BeanUtils;

/**
 * Register tool classes through {@link BeanDefinitionRegistry}
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 */
public class ToolsBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
  /** logger instance */
  static Logger logger = LoggerFactory.getLogger(ToolsBeanDefinitionRegistrar.class);

  @Override
  public void registerBeanDefinitions(@Nullable AnnotationMetadata importingClassMetadata, @NonNull BeanDefinitionRegistry registry) {
    this.registerApplicationContextTools(registry);
    this.registerBeanFactoryTools(registry);
  }

  /**
   * Register {@link ApplicationContextTools}
   *
   * @param registry The {@link BeanDefinitionRegistry} instance
   */
  private void registerApplicationContextTools(BeanDefinitionRegistry registry) {
    BeanUtils.registerInfrastructureBeanIfAbsent(registry, ApplicationContextTools.BEAN_NAME, ApplicationContextTools.class);
    logger.info("Register ApplicationContextTools successfully.");
  }

  /**
   * Register {@link BeanFactoryTools}
   *
   * @param registry The {@link BeanDefinitionRegistry} instance
   */
  private void registerBeanFactoryTools(BeanDefinitionRegistry registry) {
    BeanUtils.registerInfrastructureBeanIfAbsent(registry, BeanFactoryTools.BEAN_NAME, BeanFactoryTools.class);
    logger.info("Register BeanFactoryTools successfully.");
  }
}
