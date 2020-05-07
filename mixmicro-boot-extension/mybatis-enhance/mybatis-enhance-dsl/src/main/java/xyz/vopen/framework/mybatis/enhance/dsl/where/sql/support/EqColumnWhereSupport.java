package xyz.vopen.framework.mybatis.enhance.dsl.where.sql.support;

import xyz.vopen.framework.mybatis.enhance.dsl.expression.ColumnExpression;
import xyz.vopen.framework.mybatis.enhance.dsl.where.sql.entity.ColumnWhereSQLEntity;
import xyz.vopen.framework.mybatis.enhance.exception.EnhanceFrameworkException;

/**
 * 查询条件 "="指定列sql生成
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 */
class EqColumnWhereSupport extends AbstractColumnWhereSupport {
  /**
   * 返回查询条件实体
   *
   * @param expression 表达式
   * @param index 索引位置
   * @return 查询条件实体
   * @throws EnhanceFrameworkException 框架异常
   */
  @Override
  public ColumnWhereSQLEntity getColumnWhere(ColumnExpression expression, int index)
      throws EnhanceFrameworkException {
    /** 查询条件的值 该值根据placeholer而定 */
    Object value = expression.getValues().get(index);
    /** 集合类型 */
    if (value instanceof Object[]) {
      collectionEqValue(expression, index, (Object[]) value);
    }
    // 单值
    else {
      singleEqValue(expression, index, value);
    }
    return whereSQLEntity;
  }
}
