package xyz.vopen.framework.mybatis.enhance.dsl.where.sql.support;

import xyz.vopen.framework.mybatis.enhance.common.enums.PlaceholderEnum;
import xyz.vopen.framework.mybatis.enhance.dsl.expression.ColumnExpression;
import xyz.vopen.framework.mybatis.enhance.dsl.where.sql.entity.ColumnWhereSQLEntity;
import xyz.vopen.framework.mybatis.enhance.exception.EnhanceFrameworkException;

import java.util.Arrays;
import java.util.List;

/**
 * 查询条件 "in"指定列sql生成
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version}
 */
public class InColumnWhereSupport extends AbstractColumnWhereSupport {
  /**
   * 获取查询条件实体
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
    /** array类型 */
    if (value instanceof Object[]) {
      putArrayIns(expression, index, (Object[]) value);
    }
    /** list类型 */
    else if (value instanceof List) {
      putListIns(expression, index, (List<Object>) value);
    }
    return whereSQLEntity;
  }

  /**
   * array形式追加in条件列表
   *
   * @param expression
   * @param index
   * @param values
   */
  void putArrayIns(ColumnExpression expression, int index, Object[] values) {
    putListIns(expression, index, Arrays.asList(values));
  }

  /**
   * list形式追加in条件列表
   *
   * @param expression
   * @param index
   * @param values
   */
  void putListIns(ColumnExpression expression, int index, List<Object> values) {
    /** 查询占位符 */
    String placeholer = ((PlaceholderEnum) expression.getPlaceholers().get(index)).getValue();
    /** 格式化列名 */
    String columnName = getColumnWithTableAlias(expression);
    /** Stringbuffer sql值 */
    StringBuffer sql = new StringBuffer();
    /** 追加前缀 */
    putInPreffix(placeholer, columnName, sql);
    for (int i = 0; i < values.size(); i++) {
      /** 追加参数值 */
      putIns(expression, values.get(i), sql, i);
      /** 追加分隔符 */
      sql.append(values.size() - 1 == i ? "" : ",");
    }
    /** 追加后缀 */
    putInSuffix(sql);
    /** 设置返回格式化后的sql */
    whereSQLEntity.getSqls().add(sql.toString());
  }

  /**
   * 拼接in前缀
   *
   * @param placeholer 占位符
   * @param columnName 列名
   * @param sql sql临时对象
   */
  void putInPreffix(String placeholer, String columnName, StringBuffer sql) {
    sql.append(columnName + placeholer + "\n(");
  }

  /**
   * 拼接in后缀
   *
   * @param sql sql临时对象
   */
  void putInSuffix(StringBuffer sql) {
    sql.append(")");
  }

  /**
   * 拼接in参数列表
   *
   * @param expression 列表达式
   * @param value 参数值
   * @param sql sql临时对象
   */
  void putIns(ColumnExpression expression, Object value, StringBuffer sql, int index) {

    /** 生成参数名称 */
    String paramName = getParamName(expression, index);
    /** 设置返回信息 */
    whereSQLEntity.getParamNames().add(paramName);
    whereSQLEntity.getValues().add(value);
    sql.append("#{" + paramName + "}");
  }
}
