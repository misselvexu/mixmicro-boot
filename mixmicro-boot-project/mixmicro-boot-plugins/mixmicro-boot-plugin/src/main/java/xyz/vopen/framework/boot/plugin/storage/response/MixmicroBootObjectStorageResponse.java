package xyz.vopen.framework.boot.plugin.storage.response;

import lombok.Builder;
import lombok.Data;

/**
 * Mixmicro Boot object storage response entity
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 */
@Data
@Builder
public class MixmicroBootObjectStorageResponse {
  /** file name */
  private String objectName;
  /** file request url */
  private String objectUrl;
}
