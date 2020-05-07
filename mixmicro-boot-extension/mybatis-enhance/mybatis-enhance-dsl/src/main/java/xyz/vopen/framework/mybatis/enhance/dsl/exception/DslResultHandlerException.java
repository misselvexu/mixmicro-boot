package xyz.vopen.framework.mybatis.enhance.dsl.exception;

import xyz.vopen.framework.mybatis.enhance.exception.EnhanceFrameworkException;

/**
 * 动态查询结果映射异常
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version}
 */
public class DslResultHandlerException extends EnhanceFrameworkException {
  public DslResultHandlerException(String message) {
    super(message);
  }
}
