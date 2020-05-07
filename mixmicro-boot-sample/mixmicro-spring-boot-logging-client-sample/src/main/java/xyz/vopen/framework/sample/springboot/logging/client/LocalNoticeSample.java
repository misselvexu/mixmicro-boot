package xyz.vopen.framework.sample.springboot.logging.client;

import org.springframework.stereotype.Component;
import xyz.vopen.framework.logging.client.notice.LoggingNotice;
import xyz.vopen.framework.logging.core.MixmicroLog;

@Component
public class LocalNoticeSample implements LoggingNotice {
  /**
   * order 值越小执行越靠前
   *
   * @return
   */
  @Override
  public int getOrder() {
    return 0;
  }

  /**
   * 请求日志通知执行方法 ApiBootLog为一次请求日志对象基本信息
   *
   * @param mixmicroLog ApiBoot Log
   */
  @Override
  public void notice(MixmicroLog mixmicroLog) {
    System.out.println(mixmicroLog);
  }
}
