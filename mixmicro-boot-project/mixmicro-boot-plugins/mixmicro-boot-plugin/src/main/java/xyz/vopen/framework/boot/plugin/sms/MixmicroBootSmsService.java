package xyz.vopen.framework.boot.plugin.sms;

import xyz.vopen.framework.boot.common.exception.MixmicroBootException;
import xyz.vopen.framework.boot.plugin.sms.request.MixmicroBootSmsRequest;
import xyz.vopen.framework.boot.plugin.sms.response.MixmicroBootSmsResponse;

/**
 * Mixmicro Boot integrated SMS service interface definition
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 */
public interface MixmicroBootSmsService {
  /**
   * send sms
   *
   * @param request {@link MixmicroBootSmsRequest}
   * @return {@link MixmicroBootSmsResponse}
   * @throws MixmicroBootException Mixmicro Boot Exception
   */
  MixmicroBootSmsResponse send(MixmicroBootSmsRequest request) throws MixmicroBootException;
}
