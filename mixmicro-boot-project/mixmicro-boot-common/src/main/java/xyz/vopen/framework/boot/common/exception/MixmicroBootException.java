package xyz.vopen.framework.boot.common.exception;

import lombok.NoArgsConstructor;

/**
 * ApiBoot自定义异常
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 *     <p>DateTime：2019-03-15 14:11
 */
@NoArgsConstructor
public class MixmicroBootException extends RuntimeException {
  /**
   * 构造函数初始化异常对象
   *
   * @param message 异常信息
   */
  public MixmicroBootException(String message) {
    super(message);
  }

  /**
   * 构造函数初始化异常对象
   *
   * @param message 异常消息
   * @param cause 异常堆栈信息
   */
  public MixmicroBootException(String message, Throwable cause) {
    super(message, cause);
  }
}
