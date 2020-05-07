package xyz.vopen.framework.logging.client.global.support;

import xyz.vopen.framework.logging.client.global.AbstractGlobalLogging;
import xyz.vopen.framework.logging.client.global.GlobalLoggingThreadLocal;
import xyz.vopen.framework.logging.core.GlobalLog;
import xyz.vopen.framework.logging.core.GlobalLogLevel;
import org.springframework.util.ObjectUtils;
import xyz.vopen.framework.util.StackTraceUtil;

/**
 * Global log memory mode storage implementation
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 */
public class GlobalLoggingMemoryStorage extends AbstractGlobalLogging {
  /**
   * collection debug level log
   *
   * @param msg log content
   */
  @Override
  public void debug(String msg) {
    GlobalLog globalLog = buildGlobalLog(GlobalLogLevel.debug, msg);
    GlobalLoggingThreadLocal.addGlobalLogs(globalLog);
  }

  /**
   * collection debug level log for example: this is test log,value is {}
   *
   * @param format Unformatted log content
   * @param arguments List of parameters corresponding to log content
   */
  @Override
  public void debug(String format, Object... arguments) {
    String log = replacePlaceholder(format, arguments);
    GlobalLog globalLog = buildGlobalLog(GlobalLogLevel.debug, log);
    GlobalLoggingThreadLocal.addGlobalLogs(globalLog);
  }

  @Override
  public void info(String msg) {
    GlobalLog globalLog = buildGlobalLog(GlobalLogLevel.info, msg);
    GlobalLoggingThreadLocal.addGlobalLogs(globalLog);
  }

  @Override
  public void info(String format, Object... arguments) {
    String log = replacePlaceholder(format, arguments);
    GlobalLog globalLog = buildGlobalLog(GlobalLogLevel.info, log);
    GlobalLoggingThreadLocal.addGlobalLogs(globalLog);
  }

  @Override
  public void error(String msg) {
    this.error(msg, java.util.Optional.ofNullable(null));
  }

  @Override
  public void error(String msg, Throwable throwable) {
    GlobalLog globalLog = buildGlobalLog(GlobalLogLevel.error, msg);
    if (!ObjectUtils.isEmpty(throwable)) {
      String exceptionStack = StackTraceUtil.getStackTrace(throwable);
      globalLog.setExceptionStack(exceptionStack);
    }
    GlobalLoggingThreadLocal.addGlobalLogs(globalLog);
  }

  @Override
  public void error(String format, Object... arguments) {
    String log = replacePlaceholder(format, arguments);
    GlobalLog globalLog = buildGlobalLog(GlobalLogLevel.error, log);
    GlobalLoggingThreadLocal.addGlobalLogs(globalLog);
  }
}
