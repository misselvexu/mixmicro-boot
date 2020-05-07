package xyz.vopen.framework.mybatis.enhance.dsl.expression;

import xyz.vopen.framework.mybatis.enhance.dsl.exception.ColumnException;
import lombok.Getter;
import lombok.Setter;

/**
 * 定义了查询列的表达式类型 实现表达式接口  xyz.vopen.framework.mybatis.enhance.dsl.serach.expression.Expression全部方法
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 */
@Getter
@Setter
public class ColumnExpression<T> extends AbstractExpression<T> {
  private TableExpression tableExpression;
  /** 是否为count聚合函数列表达式 */
  private boolean count;
  /** 是否为avg聚合函数列表达式 */
  private boolean avg;
  /** 是否为sum聚合函数列表达式 */
  private boolean sum;
  /** 是否为max聚合函数列表达式 */
  private boolean max;
  /** 是否为min聚合函数列表达式 */
  private boolean min;

  /**
   * 返回表达式字段实例 提供给WhereExpression使用
   *
   * @return 列表达式
   * @throws ColumnException 列异常
   */
  @Override
  public ColumnExpression<T> getSlfe() throws ColumnException {
    return this;
  }

  /**
   * 构造函数初始化根描述
   *
   * @param root 表名
   * @param tableExpression 表达式
   */
  public ColumnExpression(String root, TableExpression tableExpression) {
    super(root);
    this.tableExpression = tableExpression;
  }

  /**
   * 设置列的别名
   *
   * @param asName 别名内容
   * @return 列表达式
   */
  @Override
  public ColumnExpression<T> as(String asName) {
    this.asName = asName;
    return this;
  }

  /**
   * 获取表名
   *
   * @return 表名
   */
  public String getTableName() {
    return tableExpression.getRoot();
  }

  /**
   * 获取table表达式
   *
   * @return 表达式
   */
  public TableExpression getTableExpression() {
    return tableExpression;
  }
}
