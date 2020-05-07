package xyz.vopen.framework.mybatis.enhance.dsl.expression.support;

import xyz.vopen.framework.mybatis.enhance.dsl.exception.ColumnException;
import xyz.vopen.framework.mybatis.enhance.dsl.expression.ColumnExpression;
import xyz.vopen.framework.mybatis.enhance.dsl.expression.interfaces.FunctionExpression;

/**
 * 函数表达式实现类
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version}
 */
public abstract class FunctionExpressionSupport<T> extends DateExpressionSupport<T>
    implements FunctionExpression<T> {
  /**
   * 查询某个字段的总数量
   *
   * @return 表达式
   * @throws ColumnException 列异常
   */
  public ColumnExpression<T> count() throws ColumnException {
    ColumnExpression<T> columnExpression = getSlfe();
    columnExpression.setCount(true);
    return columnExpression;
  }

  /**
   * 查询某个字段的最大值
   *
   * @return 表达式
   * @throws ColumnException 列异常
   */
  public ColumnExpression<T> max() throws ColumnException {
    ColumnExpression<T> columnExpression = getSlfe();
    columnExpression.setMax(true);
    return columnExpression;
  }

  /**
   * 查询某个字段的最小值
   *
   * @return 表达式
   * @throws ColumnException 列异常
   */
  public ColumnExpression<T> min() throws ColumnException {
    ColumnExpression<T> columnExpression = getSlfe();
    columnExpression.setMin(true);
    return columnExpression;
  }

  /**
   * 查询某个字段的平均值
   *
   * @return 表达式
   * @throws ColumnException 列异常
   */
  public ColumnExpression<T> avg() throws ColumnException {
    ColumnExpression<T> columnExpression = getSlfe();
    columnExpression.setAvg(true);
    return columnExpression;
  }

  /**
   * 查询某个字段的总值
   *
   * @return 表达式
   * @throws ColumnException 列异常
   */
  public ColumnExpression<T> sum() throws ColumnException {
    ColumnExpression<T> columnExpression = getSlfe();
    columnExpression.setSum(true);
    return columnExpression;
  }
}
