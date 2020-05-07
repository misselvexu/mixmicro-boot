package xyz.vopen.framework.mybatis.enhance.dsl.update;

import xyz.vopen.framework.mybatis.enhance.dsl.expression.ColumnExpression;
import xyz.vopen.framework.mybatis.enhance.dsl.expression.TableExpression;
import xyz.vopen.framework.mybatis.enhance.dsl.update.filter.SetFilter;
import xyz.vopen.framework.mybatis.enhance.dsl.where.Whereable;
import xyz.vopen.framework.mybatis.enhance.exception.EnhanceFrameworkException;

/**
 * 动态更新接口定义 可根据指定字段 单个、多个查询条件进行处理数据 更新目标字段仅支持单表 更新时条件也仅支持单表
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version}
 */
public interface Updateable extends Whereable {
  /**
   * 设置所需更新的数据表表达式
   *
   * @param tableExpression 表达式
   * @return 更新对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  Updateable update(TableExpression tableExpression) throws EnhanceFrameworkException;

  /**
   * 设置需要更新的字段
   *
   * @param setFilter 更新过滤对象
   * @return 更新对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  Updateable set(SetFilter setFilter) throws EnhanceFrameworkException;

  /**
   * 设置多个需要更新的字段，至少传递一个
   *
   * @param setFilter 更新过滤对象
   * @param filters 更新过滤对象
   * @return 更新对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  Updateable set(SetFilter setFilter, SetFilter... filters) throws EnhanceFrameworkException;

  /**
   * 查询条件and v1.0.3编写方式
   *
   * @param columnExpression 列表达式
   * @param columnExpressions 列表达式
   * @return 更新对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  Updateable where(ColumnExpression columnExpression, ColumnExpression... columnExpressions)
      throws EnhanceFrameworkException;

  /**
   * 添加一个and查询条件 v1.0.4编写方式
   *
   * @param columnExpression 列表达式
   * @param columnExpressions 列表达式
   * @return 更新对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  Updateable and(ColumnExpression columnExpression, ColumnExpression... columnExpressions)
      throws EnhanceFrameworkException;

  /**
   * 根据sql进行and拼接 该方法后期会被替换 暂时用于解决复杂SQL问题
   *
   * @param sql sql
   * @return 更新对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  Updateable and(String sql) throws EnhanceFrameworkException;

  /**
   * 添加or查询条件 v1.0.4编写方式
   *
   * @param columnExpression 列表达式
   * @param columnExpressions 列表达式
   * @return 更新对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  Updateable or(ColumnExpression columnExpression, ColumnExpression... columnExpressions)
      throws EnhanceFrameworkException;

  /**
   * 执行更新
   *
   * @throws EnhanceFrameworkException 框架异常
   */
  void execute() throws EnhanceFrameworkException;
}
