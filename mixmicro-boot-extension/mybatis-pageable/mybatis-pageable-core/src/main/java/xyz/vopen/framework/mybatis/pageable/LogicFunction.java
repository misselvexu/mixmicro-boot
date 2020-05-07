package xyz.vopen.framework.mybatis.pageable;

/**
 * 业务逻辑执行方法接口定义 提供给PageRequest作为参数使用
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version} - 2020-04-11.
 */
public interface LogicFunction {
  /** 查询逻辑执行方法 */
  void invoke();
}
