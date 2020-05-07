package xyz.vopen.framework.logging.admin.storage;

import xyz.vopen.framework.logging.core.GlobalLog;
import xyz.vopen.framework.logging.core.MixmicroLog;
import xyz.vopen.framework.logging.core.response.LoggingResponse;
import xyz.vopen.framework.logging.core.response.ServiceResponse;

import java.sql.SQLException;
import java.util.List;

/**
 * The {@link LoggingStorage} default support
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 */
public class LoggingDefaultStorage implements LoggingStorage {
  @Override
  public String insertGlobalLog(String requestLogId, GlobalLog log) throws SQLException {
    return null;
  }

  @Override
  public String insertLog(String serviceDetailId, MixmicroLog log) throws SQLException {
    return null;
  }

  @Override
  public String insertServiceDetail(String serviceId, String serviceIp, int servicePort)
      throws SQLException {
    return null;
  }

  @Override
  public String selectServiceDetailId(String serviceId, String serviceIp, int servicePort)
      throws SQLException {
    return null;
  }

  @Override
  public List<ServiceResponse> findAllService() throws SQLException {
    return null;
  }

  @Override
  public List<LoggingResponse> findTopList(int topCount) throws SQLException {
    return null;
  }

  @Override
  public void updateLastReportTime(String serviceDetailId) throws SQLException {}
}
