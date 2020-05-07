package xyz.vopen.framework.mybatis.enhance.dsl.result;

import org.apache.ibatis.mapping.MappedStatement;

/**
 * 重载查询结果定义接口
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version}
 */
public interface ReloadResult {
  /**
   * 重载方法
   *
   * @param statement MappedStatement对象实例
   */
  void reload(MappedStatement statement);
}
