package xyz.vopen.framework.boot.plugin.sms.request;

import lombok.Builder;
import lombok.Data;

/**
 * Mixmicro Boot request entity when sending SMS
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 */
@Data
@Builder
public class MixmicroBootSmsRequest {
  /** Target phone number */
  private String phone;
  /** sms template code get from alibaba cloud console */
  private String templateCode;
  /** parameters {@link MixmicroBootSmsRequestParam} */
  private MixmicroBootSmsRequestParam param;
}
