package xyz.vopen.framework.boot.plugin.storage.exception;

import lombok.NoArgsConstructor;

/**
 * Object storage exception extend from {@link RuntimeException}
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 */
@NoArgsConstructor
public class MixmicroBootObjectStorageException extends RuntimeException {
  public MixmicroBootObjectStorageException(String message, Throwable cause) {
    super(message, cause);
  }
}
