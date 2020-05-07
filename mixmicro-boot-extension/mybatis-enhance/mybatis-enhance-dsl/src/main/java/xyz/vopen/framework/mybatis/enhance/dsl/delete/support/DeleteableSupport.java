package xyz.vopen.framework.mybatis.enhance.dsl.delete.support;

import xyz.vopen.framework.mybatis.enhance.dsl.delete.Deleteable;
import xyz.vopen.framework.mybatis.enhance.dsl.expression.ColumnExpression;
import xyz.vopen.framework.mybatis.enhance.dsl.expression.TableExpression;
import xyz.vopen.framework.mybatis.enhance.dsl.factory.EnhanceDsl;
import xyz.vopen.framework.mybatis.enhance.dsl.where.support.WhereableSupport;
import xyz.vopen.framework.mybatis.enhance.exception.EnhanceFrameworkException;
import org.springframework.util.ObjectUtils;

/**
 * 动态删除接口定义实现
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version}
 */
public class DeleteableSupport extends WhereableSupport implements Deleteable {
  /** 操作数据表动态实体 */
  protected TableExpression table;

  /** 动态查询接口实例 fetch/fetchOne调用 */
  private EnhanceDsl enhanceDsl;

  /**
   * 构造函数初始化SQL对象以及sanmiDsl动态删除接口实例
   *
   * @param enhanceDsl 动态实例
   */
  public DeleteableSupport(EnhanceDsl enhanceDsl) {
    this.enhanceDsl = enhanceDsl;
  }

  /**
   * 设置删除动态对象
   *
   * @param tableExpression 表达式
   * @return 本类对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  @Override
  public Deleteable delete(TableExpression tableExpression) throws EnhanceFrameworkException {
    this.table = tableExpression;
    sql.DELETE_FROM(table.getRoot());
    return this;
  }

  /**
   * and 查询条件
   *
   * @param sql 执行sql
   * @return 本类对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  @Override
  public Deleteable and(String sql) throws EnhanceFrameworkException {
    buildAnd(sql);
    return this;
  }

  /**
   * where 查询条件
   *
   * @param columnExpression 列表达式
   * @param columnExpressions 列表达式列表
   * @return 本类对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  @Override
  public Deleteable where(ColumnExpression columnExpression, ColumnExpression... columnExpressions)
      throws EnhanceFrameworkException {
    buildWhere(columnExpression, columnExpressions);
    return this;
  }

  /**
   * and 查询条件
   *
   * @param columnExpression 列表达式
   * @param columnExpressions 列表达式集合
   * @return 本类对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  @Override
  public Deleteable and(ColumnExpression columnExpression, ColumnExpression... columnExpressions)
      throws EnhanceFrameworkException {
    buildAnd(columnExpression, columnExpressions);
    return this;
  }

  /**
   * or 查询条件
   *
   * @param columnExpression 列表达式
   * @param columnExpressions 列表达式集合
   * @return 本类对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  @Override
  public Deleteable or(ColumnExpression columnExpression, ColumnExpression... columnExpressions)
      throws EnhanceFrameworkException {
    buildOr(columnExpression, columnExpressions);
    return this;
  }
  /**
   * 执行删除
   *
   * @throws EnhanceFrameworkException 框架异常
   */
  @Override
  public void execute() throws EnhanceFrameworkException {
    if (ObjectUtils.isEmpty(table)) {
      throw new EnhanceFrameworkException("请先调用delete方法传递删除表的动态实体.");
    }
    /** 执行删除 */
    enhanceDsl.delete(this);
  }
}
