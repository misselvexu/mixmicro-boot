package xyz.vopen.framework.mybatis.enhance.dsl.expression.interfaces;

import xyz.vopen.framework.mybatis.enhance.dsl.exception.ColumnException;
import xyz.vopen.framework.mybatis.enhance.dsl.expression.ColumnExpression;

/**
 * 数据库函数表达式 v1.0.0：count(_d_good.gPrice) v1.0.3：_d_good.gPrice.count()
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 */
public interface FunctionExpression<T> extends DateExpression<T> {

  /**
   * 设置返回的字段表达式
   *
   * @return 表达式
   * @throws ColumnException 列异常
   */
  ColumnExpression<T> getSlfe() throws ColumnException;

  /**
   * 查询某个字段的总数量
   *
   * @return 表达式
   * @throws ColumnException 列异常
   */
  ColumnExpression<T> count() throws ColumnException;

  /**
   * 查询某个字段的最大值
   *
   * @return 表达式
   * @throws ColumnException 列异常
   */
  ColumnExpression<T> max() throws ColumnException;

  /**
   * 查询某个字段的最小值
   *
   * @return 表达式
   * @throws ColumnException 列异常
   */
  ColumnExpression<T> min() throws ColumnException;

  /**
   * 查询某个字段的平均值
   *
   * @return 表达式
   * @throws ColumnException 列异常
   */
  ColumnExpression<T> avg() throws ColumnException;

  /**
   * 查询某个字段的总值
   *
   * @return 表达式
   * @throws ColumnException 列异常
   */
  ColumnExpression<T> sum() throws ColumnException;
}
