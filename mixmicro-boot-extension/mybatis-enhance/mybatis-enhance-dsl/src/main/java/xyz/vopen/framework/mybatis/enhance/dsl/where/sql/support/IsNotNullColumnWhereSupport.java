package xyz.vopen.framework.mybatis.enhance.dsl.where.sql.support;

import xyz.vopen.framework.mybatis.enhance.dsl.expression.ColumnExpression;
import xyz.vopen.framework.mybatis.enhance.dsl.where.sql.entity.ColumnWhereSQLEntity;
import xyz.vopen.framework.mybatis.enhance.exception.EnhanceFrameworkException;

/**
 * 查询条件 "is not null"指定列sql生成
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 */
public class IsNotNullColumnWhereSupport extends AbstractColumnWhereSupport {
  /**
   * 返回自定义查询条件实体
   *
   * @param columnExpression 表达式
   * @param index 索引位置
   * @return 查询条件实体
   * @throws EnhanceFrameworkException 框架异常
   */
  @Override
  public ColumnWhereSQLEntity getColumnWhere(ColumnExpression columnExpression, int index)
      throws EnhanceFrameworkException {
    /** 调用单值条件 */
    singleEqValue(columnExpression, index, columnExpression.getValues().get(index));
    return whereSQLEntity;
  }
}
