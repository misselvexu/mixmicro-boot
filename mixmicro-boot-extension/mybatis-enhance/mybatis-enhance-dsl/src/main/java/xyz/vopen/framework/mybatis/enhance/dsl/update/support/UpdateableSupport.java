package xyz.vopen.framework.mybatis.enhance.dsl.update.support;

import xyz.vopen.framework.mybatis.enhance.common.enums.PlaceholderEnum;
import xyz.vopen.framework.mybatis.enhance.dsl.expression.ColumnExpression;
import xyz.vopen.framework.mybatis.enhance.dsl.expression.TableExpression;
import xyz.vopen.framework.mybatis.enhance.dsl.factory.EnhanceDsl;
import xyz.vopen.framework.mybatis.enhance.dsl.update.Updateable;
import xyz.vopen.framework.mybatis.enhance.dsl.update.filter.SetFilter;
import xyz.vopen.framework.mybatis.enhance.dsl.where.support.WhereableSupport;
import xyz.vopen.framework.mybatis.enhance.exception.EnhanceFrameworkException;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 动态更新实现
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version}
 */
public class UpdateableSupport extends WhereableSupport implements Updateable {
  /** 操作数据表动态实体 */
  protected TableExpression table;

  /** 动态查询接口实例 fetch/fetchOne调用 */
  private EnhanceDsl enhanceDsl;

  /**
   * 构造函数初始化SQL对象以及sanmiDsl动态更新接口实例
   *
   * @param enhanceDsl 动态查询实例
   */
  public UpdateableSupport(EnhanceDsl enhanceDsl) {
    this.enhanceDsl = enhanceDsl;
  }

  /**
   * update 查询条件
   *
   * @param tableExpression 表达式
   * @return 更新对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  @Override
  public Updateable update(TableExpression tableExpression) throws EnhanceFrameworkException {
    this.table = tableExpression;
    this.sql.UPDATE(table.getRoot());
    return this;
  }

  /**
   * and 查询条件
   *
   * @param sql sql
   * @return 更新对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  @Override
  public Updateable and(String sql) throws EnhanceFrameworkException {
    buildAnd(sql);
    return this;
  }

  /**
   * where 查询条件
   *
   * @param columnExpression 列表达式
   * @param columnExpressions 列表达式
   * @return 更新对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  @Override
  public Updateable where(ColumnExpression columnExpression, ColumnExpression... columnExpressions)
      throws EnhanceFrameworkException {
    buildWhere(columnExpression, columnExpressions);
    return this;
  }

  /**
   * and查询条件
   *
   * @param columnExpression 列表达式
   * @param columnExpressions 列表达式
   * @return 更新对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  @Override
  public Updateable and(ColumnExpression columnExpression, ColumnExpression... columnExpressions)
      throws EnhanceFrameworkException {
    buildAnd(columnExpression, columnExpressions);
    return this;
  }

  /**
   * or 查询条件
   *
   * @param columnExpression 列表达式
   * @param columnExpressions 列表达式
   * @return 更新对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  @Override
  public Updateable or(ColumnExpression columnExpression, ColumnExpression... columnExpressions)
      throws EnhanceFrameworkException {
    buildOr(columnExpression, columnExpressions);
    return this;
  }

  /**
   * 设置指定字段更新
   *
   * @param setFilter 更新过滤对象
   * @return 更新对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  @Override
  public Updateable set(SetFilter setFilter) throws EnhanceFrameworkException {
    ColumnExpression columnExpression = setFilter.getColumnExpression();
    if (ObjectUtils.isEmpty(columnExpression)) {
      throw new EnhanceFrameworkException("请输入更新具体列表达式");
    }

    /** 列名 */
    String columnName = getColumnWithTableAlias(columnExpression);
    /** 参数名称 */
    String paramName = getParamName(columnExpression, PlaceholderEnum.SET.getValue().trim());
    /** 添加到sql对象内 */
    sql.SET(columnName + "\t=\t" + "#{" + paramName + "}\n");

    this.params.put(paramName, setFilter.getValue());

    return this;
  }

  /**
   * 设置多个更新字段
   *
   * @param setFilter 至少传递一个
   * @param filters 多个字段
   * @return 更新对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  @Override
  public Updateable set(SetFilter setFilter, SetFilter... filters)
      throws EnhanceFrameworkException {
    /** 更新字段集合 */
    List<SetFilter> setFilterList = new ArrayList();
    /** 将第一个参数传递到集合内 */
    setFilterList.add(setFilter);
    /** 如果存在多个更新字段时，全部添加到集合内 */
    if (!ObjectUtils.isEmpty(filters)) {
      setFilterList.addAll(Arrays.asList(filters));
    }
    /** 遍历设置更新字段 */
    for (SetFilter filter : setFilterList) {
      set(filter);
    }
    return this;
  }

  /**
   * 执行更新
   *
   * @throws EnhanceFrameworkException 框架异常
   */
  @Override
  public void execute() throws EnhanceFrameworkException {
    if (ObjectUtils.isEmpty(table)) {
      throw new EnhanceFrameworkException("请先调用update方法传递更新表的动态实体.");
    }
    enhanceDsl.update(this);
  }
}
