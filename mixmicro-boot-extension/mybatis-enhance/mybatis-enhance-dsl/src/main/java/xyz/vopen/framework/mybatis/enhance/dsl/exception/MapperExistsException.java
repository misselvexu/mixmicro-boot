package xyz.vopen.framework.mybatis.enhance.dsl.exception;

import xyz.vopen.framework.mybatis.enhance.exception.EnhanceFrameworkException;

/**
 * mapper已经存在异常
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version}
 */
public class MapperExistsException extends EnhanceFrameworkException {

  public MapperExistsException(String message) {
    super(message);
  }
}
