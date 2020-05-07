package xyz.vopen.framework.mybatis.enhance.dsl.where.sql;

import xyz.vopen.framework.mybatis.enhance.dsl.expression.ColumnExpression;
import xyz.vopen.framework.mybatis.enhance.dsl.where.sql.entity.ColumnWhereSQLEntity;
import xyz.vopen.framework.mybatis.enhance.exception.EnhanceFrameworkException;

/**
 * 查询参数SQL生成接口 如：
 *
 * <p>xx = value xx in (value1,value2) xx not in (value1,value2)
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 *     <p>
 * @version ${project.version}
 */
public interface ColumnWhereSQL {
  /**
   * 获取指定列的查询条件
   *
   * @param columnExpression 列表达式
   * @param valueIndex 表达式内值的索引位置
   * @return 查询条件实体
   * @throws EnhanceFrameworkException 框架异常
   */
  ColumnWhereSQLEntity getColumnWhere(ColumnExpression columnExpression, int valueIndex)
      throws EnhanceFrameworkException;

  /**
   * 设置参数索引
   *
   * @param paramIndex 参数索引
   * @throws EnhanceFrameworkException 框架异常
   */
  void setParamIndex(int paramIndex) throws EnhanceFrameworkException;
}
