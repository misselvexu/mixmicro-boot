package xyz.vopen.framework.mybatis.enhance.dsl.exception;

import xyz.vopen.framework.mybatis.enhance.exception.EnhanceFrameworkException;

/**
 * 未检索到TableEntityStruct异常对象
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version}
 */
public class NotFoundTableEntityStructException extends EnhanceFrameworkException {
  public NotFoundTableEntityStructException(String message) {
    super(message);
  }
}
