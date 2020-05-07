package xyz.vopen.framework.sample.logging.client;

import org.springframework.stereotype.Component;
import xyz.vopen.framework.logging.client.notice.LoggingNotice;
import xyz.vopen.framework.logging.core.MixmicroLog;

@Component
public class CustomerLoggingNotice implements LoggingNotice {
  /**
   * 通知方法
   *
   * @param mixmicroLog ApiBoot Log
   */
  @Override
  public void notice(MixmicroLog mixmicroLog) {
    System.out.println(mixmicroLog.getTraceId());
  }

  /**
   * 通知执行优先级 {@link #getOrder()}方法返回值值越小优先级越高
   *
   * @return
   */
  @Override
  public int getOrder() {
    return 1;
  }
}
