package xyz.vopen.framework.mybatis.enhance.dsl.where.sql.support;

import xyz.vopen.framework.mybatis.enhance.common.enums.PlaceholderEnum;
import xyz.vopen.framework.mybatis.enhance.dsl.expression.ColumnExpression;
import xyz.vopen.framework.mybatis.enhance.dsl.expression.TableExpression;
import xyz.vopen.framework.mybatis.enhance.dsl.where.sql.ColumnWhereSQL;
import xyz.vopen.framework.mybatis.enhance.dsl.where.sql.entity.ColumnWhereSQLEntity;
import xyz.vopen.framework.mybatis.enhance.exception.EnhanceFrameworkException;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 指定列的查询条件构建抽象类 该类提供一些处理查询条件时的常用方法 处理列别名、表别名、查询字段列表、函数字段列表等
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version}
 */
public abstract class AbstractColumnWhereSupport implements ColumnWhereSQL {
  /** 返回查询条件实体 */
  protected ColumnWhereSQLEntity whereSQLEntity = new ColumnWhereSQLEntity();

  /** 使用表别名集合 key=表名 value=别名 */
  protected Map<String, String> tableAlias = new HashMap();
  /** 参数索引 */
  protected int paramIndex;

  /**
   * 根据表的别名获取列
   *
   * @param columnExpression 列表达式
   * @return 格式化后的列名
   */
  protected String getColumnWithTableAlias(ColumnExpression columnExpression)
      throws EnhanceFrameworkException {
    /** 获取默认列名 */
    String columnName = columnExpression.getRoot();
    /** 格式化列名=表别名.列名 */
    String tableName = tableAlias.get(columnExpression.getTableName());
    /** 如果表别名不存在，则添加到别名集合内 */
    if (StringUtils.isEmpty(tableName)) {
      checkTableAlias(columnExpression.getTableExpression());
    }
    columnName = tableAlias.get(columnExpression.getTableName()) + "." + columnName;
    /** 返回格式化后的列名 */
    return columnName;
  }

  /**
   * 检查表别名
   *
   * @param tableExpression 表达式
   */
  protected void checkTableAlias(TableExpression tableExpression) throws EnhanceFrameworkException {
    String tableName = tableExpression.getRoot();
    if (!StringUtils.isEmpty(tableExpression.getAsName())) {
      /** 设置别名 */
      tableAlias.put(tableExpression.getRoot(), tableExpression.getAsName());
    } else {
      tableAlias.put(tableName, tableName);
    }
  }

  @Override
  public void setParamIndex(int paramIndex) throws EnhanceFrameworkException {
    this.paramIndex = paramIndex;
  }

  /**
   * 获取参数的名称
   *
   * @param columnExpression 查询列名
   * @return 参数名称
   */
  protected String getParamName(ColumnExpression columnExpression, int index) {
    // 格式化列名
    String columnName = getColumnWithTableAlias(columnExpression);
    // 查询值的映射名称
    return columnName.replace(".", "_") + "_" + paramIndex + "_" + index;
  }

  /**
   * 单值eq时处理
   *
   * @param expression 表达式
   * @param index 索引位置
   * @param value 索引对应的值
   */
  protected void singleEqValue(ColumnExpression expression, int index, Object value) {
    /** 查询占位符 */
    String placeholer = ((PlaceholderEnum) expression.getPlaceholers().get(index)).getValue();
    /** 格式化列名 */
    String columnName = getColumnWithTableAlias(expression);
    /** 生成参数名称 */
    String paramName = getParamName(expression, index);
    /** 设置返回信息 */
    whereSQLEntity.getParamNames().add(paramName);
    whereSQLEntity.getValues().add(value);
    whereSQLEntity
        .getSqls()
        .add(columnName + "\n" + placeholer + (value != null ? "\n#{" + paramName + "}" : ""));
  }

  /**
   * 单值ne处理
   *
   * @param expression 表达式
   * @param index 索引位置
   * @param value 索引位置对应的值
   */
  protected void singleNeValue(ColumnExpression expression, int index, Object value) {
    singleEqValue(expression, index, value);
  }

  /**
   * 查询eq集合时处理
   *
   * @param expression 表达式
   * @param index 索引位置
   * @param values 索引位置对应的值
   */
  protected void collectionEqValue(ColumnExpression expression, int index, Object[] values) {
    for (Object value : values) {
      singleEqValue(expression, index, value);
    }
  }

  /**
   * 查询ne集合时处理
   *
   * @param expression 表达式
   * @param index 索引位置
   * @param values 索引位置对应的值
   */
  protected void collectionNeValue(ColumnExpression expression, int index, Object[] values) {
    for (Object value : values) {
      singleNeValue(expression, index, value);
    }
  }
}
