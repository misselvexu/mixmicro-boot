package xyz.vopen.framework.logging.client.notice.support;

import xyz.vopen.framework.logging.client.LoggingFactoryBean;
import xyz.vopen.framework.logging.client.admin.report.LoggingAdminReport;
import xyz.vopen.framework.logging.client.cache.LoggingCache;
import xyz.vopen.framework.logging.client.notice.LoggingNotice;
import xyz.vopen.framework.logging.core.MixmicroLog;
import xyz.vopen.framework.logging.core.ReportAway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import xyz.vopen.framework.logging.client.admin.report.LoggingReportScheduled;

import java.util.Arrays;

/**
 * Logging admin notification
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 */
public class LoggingAdminNotice implements LoggingNotice {
  /** the bean name of {@link LoggingAdminNotice} */
  public static final String BEAN_NAME = "loggingAdminNotice";
  /** logger instance */
  static Logger logger = LoggerFactory.getLogger(LoggingAdminNotice.class);

  /**
   * logging factory bean {@link LoggingFactoryBean} {@link LoggingCache} {@link LoggingAdminReport}
   */
  @Nullable private LoggingFactoryBean factoryBean;

  /**
   * Injecting {@link LoggingFactoryBean} through constructor injection
   *
   * @param factoryBean {@link LoggingFactoryBean}
   */
  public LoggingAdminNotice(@Nullable LoggingFactoryBean factoryBean) {
    this.factoryBean = factoryBean;
  }

  /**
   * if just report away，execute report logs to admin if timing report away，cache logs to {@link
   * LoggingCache} support， wait for {@link
   * LoggingReportScheduled} execute report
   *
   * @param mixmicroLog Mixmicro Boot Log
   */
  @Override
  public void notice(MixmicroLog mixmicroLog) {
    ReportAway reportAway = factoryBean.getReportAway();
    switch (reportAway) {
      case just:
        LoggingAdminReport loggingAdminReport = factoryBean.getLoggingAdminReport();
        loggingAdminReport.report(Arrays.asList(mixmicroLog));
        break;
      case timing:
        factoryBean.getLoggingCache().cache(mixmicroLog);
        logger.debug("Cache Request Logging Complete.");
        break;
    }
  }

  @Override
  public int getOrder() {
    return HIGHEST_PRECEDENCE + 1;
  }
}
