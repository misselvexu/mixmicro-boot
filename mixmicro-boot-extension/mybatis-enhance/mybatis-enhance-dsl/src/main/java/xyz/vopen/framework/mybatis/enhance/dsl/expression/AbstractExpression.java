package xyz.vopen.framework.mybatis.enhance.dsl.expression;

import xyz.vopen.framework.mybatis.enhance.dsl.expression.interfaces.WhereExpression;
import xyz.vopen.framework.mybatis.enhance.dsl.expression.support.WhereExpressionSupport;

/**
 * 抽象查询表达式实现类
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 *     <p>
 */
public abstract class AbstractExpression<T> extends WhereExpressionSupport<T>
    implements Expression<T>, WhereExpression<T> {
  /** 列名 或者 表名 */
  protected String root;
  /** 别名 */
  protected String asName;

  /**
   * 构造函数初始化根描述
   *
   * @param root 表名或者列名
   */
  public AbstractExpression(String root) {
    this.root = root;
  }

  /**
   * 获取根名称 表名或者列名 在不同的实现类内设置如：TableExpression、ColumnExpression
   *
   * @return 表名或者列表
   */
  @Override
  public String getRoot() {
    return this.root;
  }

  /**
   * 获取表别名 获取列别名
   *
   * @return 列或者表的表名
   */
  @Override
  public String getAsName() {
    return asName;
  }
}
