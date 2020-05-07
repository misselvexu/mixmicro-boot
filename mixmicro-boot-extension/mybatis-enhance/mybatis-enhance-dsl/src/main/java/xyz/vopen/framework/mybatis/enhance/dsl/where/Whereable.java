package xyz.vopen.framework.mybatis.enhance.dsl.where;

import xyz.vopen.framework.mybatis.enhance.dsl.expression.ColumnExpression;
import xyz.vopen.framework.mybatis.enhance.dsl.where.filter.WhereFilter;
import xyz.vopen.framework.mybatis.enhance.exception.EnhanceFrameworkException;

import java.io.Serializable;
import java.util.Map;

/**
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version}
 */
public interface Whereable extends Serializable {
  /**
   * 查询条件处理
   *
   * @param whereFilter 第一个占位查询条件
   * @param whereFilters 查询条件集合
   * @throws EnhanceFrameworkException 框架异常
   */
  void buildWhere(WhereFilter whereFilter, WhereFilter... whereFilters)
      throws EnhanceFrameworkException;

  /**
   * 查询条件处理
   *
   * @param columnExpression 第一个占位字段表达式
   * @param columnExpressions 字段表达式集合
   * @throws EnhanceFrameworkException 框架异常
   */
  void buildWhere(ColumnExpression columnExpression, ColumnExpression... columnExpressions)
      throws EnhanceFrameworkException;

  /**
   * 构建and条件
   *
   * @param whereFilter 查询条件过滤
   * @param filters 查询条件过滤
   * @throws EnhanceFrameworkException 框架异常
   */
  void buildAnd(WhereFilter whereFilter, WhereFilter... filters) throws EnhanceFrameworkException;

  /**
   * 构建and条件
   *
   * @param columnExpression 列表达式
   * @param columnExpressions 列表达式
   * @throws EnhanceFrameworkException 框架异常
   */
  void buildAnd(ColumnExpression columnExpression, ColumnExpression... columnExpressions)
      throws EnhanceFrameworkException;

  /**
   * 构建sql and条件
   *
   * @param sql sql
   * @throws EnhanceFrameworkException 框架异常
   */
  void buildAnd(String sql) throws EnhanceFrameworkException;

  /**
   * 构建or条件
   *
   * @param firstFilter 查询过滤条件
   * @param filters 查询过滤条件
   * @throws EnhanceFrameworkException 框架异常
   */
  void buildOr(WhereFilter firstFilter, WhereFilter... filters) throws EnhanceFrameworkException;

  /**
   * 构建or条件
   *
   * @param columnExpression 列表达式
   * @param columnExpressions 列表达式
   * @throws EnhanceFrameworkException 框架异常
   */
  void buildOr(ColumnExpression columnExpression, ColumnExpression... columnExpressions)
      throws EnhanceFrameworkException;

  /**
   * 获取动态查询条件集合
   *
   * @return 参数集合
   * @throws EnhanceFrameworkException 框架异常
   */
  Map getParams() throws EnhanceFrameworkException;

  /**
   * 获取格式化后的SQL
   *
   * @return sql
   * @throws EnhanceFrameworkException 框架异常
   */
  String getSql() throws EnhanceFrameworkException;
}
