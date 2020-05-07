package xyz.vopen.framework.mybatis.enhance.dsl.where.filter;

import xyz.vopen.framework.mybatis.enhance.common.enums.PlaceholderEnum;
import xyz.vopen.framework.mybatis.enhance.dsl.expression.ColumnExpression;
import lombok.Getter;

/**
 * 查询条件过滤类 添加查询条件列表
 *
 * @version ${project.version}
 */
@Getter
public final class WhereFilter {
  /** 查询列表达式 */
  private ColumnExpression columnExpression;
  /** 占位符 */
  private PlaceholderEnum placeholder;
  /** 查询内容 */
  private Object value;

  /**
   * 私有构造函数
   *
   * @param columnExpression 列表达式
   * @param placeholder 占位符
   * @param value 查询内容
   */
  private WhereFilter(
      ColumnExpression columnExpression, PlaceholderEnum placeholder, Object value) {
    this.columnExpression = columnExpression;
    this.placeholder = placeholder;
    this.value = value;
  }

  /**
   * 添加查询条件
   *
   * @param columnExpression 查询列表达式
   * @param placeholder 占位符
   * @param value 查询内容
   * @return 过滤查询条件
   */
  public static WhereFilter filter(
      ColumnExpression columnExpression, PlaceholderEnum placeholder, Object value) {
    return new WhereFilter(columnExpression, placeholder, value);
  }
}
