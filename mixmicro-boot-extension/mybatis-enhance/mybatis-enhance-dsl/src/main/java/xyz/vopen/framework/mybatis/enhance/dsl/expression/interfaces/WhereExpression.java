package xyz.vopen.framework.mybatis.enhance.dsl.expression.interfaces;

import xyz.vopen.framework.mybatis.enhance.dsl.exception.ColumnException;
import xyz.vopen.framework.mybatis.enhance.dsl.expression.ColumnExpression;

import java.util.List;

/**
 * 查询条件表达式 将代替v1.0.0版本的PlaceholderEnum枚举 v1.0.0：WhereFilter.filter(_d_good.gId,
 * PlaceholderEnum.EQ,52) v1.0.3：_d_good.gId.eq(52)，甩掉一切臃肿的包袱，更有艺术的编码
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version}
 */
public interface WhereExpression<T> extends FunctionExpression<T> {
  /**
   * 查询表达式：等于
   *
   * @param value 表达式所需值
   * @return 表达式
   * @throws ColumnException 列异常
   */
  ColumnExpression<T> eq(Object value) throws ColumnException;

  /**
   * 查询表达式：等于集合内的数据 采用and条件链接
   *
   * @param eqs 表达式所需值列表
   * @return 表达式
   * @throws ColumnException 列异常
   */
  ColumnExpression<T> eqAll(Object... eqs) throws ColumnException;

  /**
   * 查询表达式：不等于
   *
   * @param value 表达式所需值
   * @return 表达式
   * @throws ColumnException 列异常
   */
  ColumnExpression<T> ne(Object value) throws ColumnException;

  /**
   * 查询表达式：全部不等于数组 采用and条件链接
   *
   * @param nes 表达式所需值列表
   * @return 表达式
   * @throws ColumnException 列异常
   */
  ColumnExpression<T> neAll(Object... nes) throws ColumnException;

  /**
   * 查询表达式：全部不等于集合 采用and条件链接
   *
   * @param nes 表达式所需值列表
   * @return 表达式
   * @throws ColumnException 列异常
   */
  ColumnExpression<T> neAll(List nes) throws ColumnException;

  /**
   * 查询表达式：大于
   *
   * @param value 表达式所需值
   * @return 表达式
   * @throws ColumnException 列异常
   */
  ColumnExpression<T> gt(Object value) throws ColumnException;

  /**
   * 查询表达式：大于等于
   *
   * @param value 表达式所需值
   * @return 表达式
   * @throws ColumnException 列异常
   */
  ColumnExpression<T> gte(Object value) throws ColumnException;

  /**
   * 查询表达式：小于
   *
   * @param value 表达式所需值
   * @return 表达式
   * @throws ColumnException 列异常
   */
  ColumnExpression<T> lt(Object value) throws ColumnException;

  /**
   * 查询表达式：小于等于
   *
   * @param value 表达式所需值
   * @return 表达式
   * @throws ColumnException 列异常
   */
  ColumnExpression<T> lte(Object value) throws ColumnException;

  /**
   * 查询表达式：模糊查询
   *
   * @param preffix 前缀，如：%，null
   * @param value 值
   * @param suffix 后缀，如：%，null
   * @return 表达式
   * @throws ColumnException 列异常
   */
  ColumnExpression<T> like(String preffix, Object value, String suffix) throws ColumnException;

  /**
   * 查询表达式：包含集合查询
   *
   * @param ins 表达式所需值
   * @return 表达式
   * @throws ColumnException 列异常
   */
  ColumnExpression<T> in(List ins) throws ColumnException;

  /**
   * 查询表达式：包含数组
   *
   * @param ins 表达式所需值
   * @return 表达式
   * @throws ColumnException 列异常
   */
  ColumnExpression<T> in(Object... ins) throws ColumnException;

  /**
   * 查询表达式：为空
   *
   * @return 表达式
   * @throws ColumnException 列异常
   */
  ColumnExpression<T> isNull() throws ColumnException;

  /**
   * 查询表达式为空字符串
   *
   * @return 表达式
   * @throws ColumnException 列异常
   */
  ColumnExpression<T> isEmpty() throws ColumnException;

  /**
   * 查询表达式为非空字符串
   *
   * @return 表达式
   * @throws ColumnException 列异常
   */
  ColumnExpression<T> isNotEmpty() throws ColumnException;

  /**
   * 查询表达式：不为空
   *
   * @return 表达式
   * @throws ColumnException 列异常
   */
  ColumnExpression<T> isNotNull() throws ColumnException;
}
