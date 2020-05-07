package xyz.vopen.framework.boot.plugin.sms.response;

import lombok.Builder;
import lombok.Data;

/**
 * Mixmicro Boot responds to entity when sending SMS
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 */
@Data
@Builder
public class MixmicroBootSmsResponse {
  /** Whether the SMS was sent successfully */
  private boolean success;
}
