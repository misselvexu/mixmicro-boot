package xyz.vopen.framework.mybatis.enhance.dsl.delete;

import xyz.vopen.framework.mybatis.enhance.dsl.expression.ColumnExpression;
import xyz.vopen.framework.mybatis.enhance.dsl.expression.TableExpression;
import xyz.vopen.framework.mybatis.enhance.dsl.where.Whereable;
import xyz.vopen.framework.mybatis.enhance.exception.EnhanceFrameworkException;

/**
 * 动态删除接口定义
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 *
 *    @version ${project.version}
 */
public interface Deleteable extends Whereable {
  /**
   * 设置所需删除的数据表表达式
   *
   * @param tableExpression 表达式
   * @return 本接口对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  Deleteable delete(TableExpression tableExpression) throws EnhanceFrameworkException;

  /**
   * 查询条件and v1.0.3编写方式
   *
   * @param columnExpression 列表达式
   * @param columnExpressions 列表达式列表
   * @return 本接口对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  Deleteable where(ColumnExpression columnExpression, ColumnExpression... columnExpressions)
      throws EnhanceFrameworkException;

  /**
   * 添加一个and查询条件 v1.0.4编写方式
   *
   * @param columnExpression 列表达式
   * @param columnExpressions 列表达式集合
   * @return 本接口对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  Deleteable and(ColumnExpression columnExpression, ColumnExpression... columnExpressions)
      throws EnhanceFrameworkException;

  /**
   * 根据sql进行and拼接 该方法后期会被替换 暂时用于解决复杂SQL问题
   *
   * @param sql 执行sql
   * @return 本接口对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  Deleteable and(String sql) throws EnhanceFrameworkException;
  /**
   * 添加or查询条件 v1.0.4编写方式
   *
   * @param columnExpression 列表达式
   * @param columnExpressions 列表达式集合
   * @return 本接口对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  Deleteable or(ColumnExpression columnExpression, ColumnExpression... columnExpressions)
      throws EnhanceFrameworkException;

  /**
   * 执行删除
   *
   * @throws EnhanceFrameworkException 框架异常
   */
  void execute() throws EnhanceFrameworkException;
}
