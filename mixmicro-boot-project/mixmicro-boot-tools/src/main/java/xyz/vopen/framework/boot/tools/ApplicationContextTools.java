package xyz.vopen.framework.boot.tools;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;

/**
 * The {@link ApplicationContext} tools
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 */
public class ApplicationContextTools implements ApplicationContextAware {
  /**
   * The bean name of {@link ApplicationContextTools}
   */
  public static final String BEAN_NAME = "ApiBootApplicationContext";

  private static ApplicationContext context;

  public static ApplicationContext getContext() {
    return context;
  }

  /**
   * Publish a {@link ApplicationEvent}
   *
   * @param event The {@link ApplicationEvent} instance
   */
  public static void publishEvent(ApplicationEvent event) {
    context.publishEvent(event);
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    context = applicationContext;
  }
}
