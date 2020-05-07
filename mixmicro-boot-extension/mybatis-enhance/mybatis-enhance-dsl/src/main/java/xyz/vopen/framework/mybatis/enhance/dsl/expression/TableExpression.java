package xyz.vopen.framework.mybatis.enhance.dsl.expression;

import xyz.vopen.framework.mybatis.enhance.dsl.exception.ColumnException;

/**
 * 定义了查询表的表达式类型 实现表达式接口  xyz.vopen.framework.mybatis.enhance.dsl.serach.expression.Expression全部方法
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 */
public abstract class TableExpression<T> extends AbstractExpression<T> {

  /**
   * 构造函数初始化根描述
   *
   * @param root 表名 或者 列名
   */
  public TableExpression(String root) {
    super(root);
  }

  /**
   * 获取定义的所有列表达式
   *
   * @return 列表达式数组
   */
  public abstract ColumnExpression[] getColumns();

  /**
   * @return 列表达式数组
   * @throws ColumnException 列异常
   */
  @Override
  public ColumnExpression<T> getSlfe() throws ColumnException {
    return null;
  }

  /**
   * 设置表的别名
   *
   * @param asName 别名内容
   * @return 表达式
   */
  @Override
  public TableExpression<T> as(String asName) {
    this.asName = asName;
    return this;
  }
}
