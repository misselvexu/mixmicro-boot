package xyz.vopen.framework.sample.logging.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.stereotype.Component;
import xyz.vopen.framework.logging.admin.event.ReportLogEvent;
import xyz.vopen.framework.logging.core.LoggingClientNotice;
import xyz.vopen.framework.logging.core.MixmicroLog;

import java.util.List;

@Component
public class CustomerReportEventListener implements SmartApplicationListener {

  /** logger instance */
  static Logger logger = LoggerFactory.getLogger(CustomerReportEventListener.class);

  public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
    return ReportLogEvent.class == eventType;
  }

  public void onApplicationEvent(ApplicationEvent event) {

    ReportLogEvent reportLogEvent = (ReportLogEvent) event;
    LoggingClientNotice loggingClientNotice = reportLogEvent.getLogClientNotice();

    // MinBoxLog 日志列表
    List<MixmicroLog> logs = loggingClientNotice.getLoggers();

    logger.debug(
        "上报日志服务：{}，IP地址：{}，端口号：{}，日志列表：{}",
        loggingClientNotice.getClientServiceId(),
        loggingClientNotice.getClientServiceIp(),
        loggingClientNotice.getClientServicePort(),
        logs);
  }
}
