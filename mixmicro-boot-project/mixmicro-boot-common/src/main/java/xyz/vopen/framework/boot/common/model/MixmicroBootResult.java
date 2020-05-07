package xyz.vopen.framework.boot.common.model;

import lombok.Builder;
import lombok.Data;

/**
 * ApiBoot提供的接口返回对象
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 *     <p>DateTime：2019-03-18 10:57
 */
@Data
@Builder
public class MixmicroBootResult {
  /** 返回数据内容 */
  private Object data;
  /** 遇到业务异常或者系统异常时的错误码 */
  private String errorCode;
  /** 遇到业务异常或者系统异常时的错误消息提示内容 */
  private String errorMessage;
}
