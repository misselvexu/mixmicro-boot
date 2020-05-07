package xyz.vopen.framework.mybatis.enhance.dsl.expression.support;

import xyz.vopen.framework.mybatis.enhance.common.enums.PlaceholderEnum;
import xyz.vopen.framework.mybatis.enhance.dsl.exception.ColumnException;
import xyz.vopen.framework.mybatis.enhance.dsl.expression.ColumnExpression;
import xyz.vopen.framework.mybatis.enhance.dsl.expression.interfaces.WhereExpression;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询条件表达式实现类
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version}
 */
@Getter
public abstract class WhereExpressionSupport<T> extends FunctionExpressionSupport<T>
    implements WhereExpression<T> {
  /** 占位符列表 预防一个字段被使用多次，如：name.eq，name.like */
  private List<PlaceholderEnum> placeholers = new ArrayList<PlaceholderEnum>();
  /** 对应占位符值列表 */
  private List<Object> values = new ArrayList();

  /**
   * 查询表达式：等于
   *
   * @param value 表达式相关值
   * @return 表达式
   * @throws ColumnException 列异常
   */
  @Override
  public ColumnExpression<T> eq(Object value) throws ColumnException {
    this.values.add(value);
    this.placeholers.add(PlaceholderEnum.EQ);
    return getSlfe();
  }

  /**
   * 查询表达式：等于集合
   *
   * @param eqs 表达式相关值列表
   * @return 表达式
   * @throws ColumnException 列异常
   */
  @Override
  public ColumnExpression<T> eqAll(Object... eqs) throws ColumnException {
    this.values.add(eqs);
    this.placeholers.add(PlaceholderEnum.EQ);
    return getSlfe();
  }

  /**
   * 查询表达式：不等于
   *
   * @param value 表达式相关值
   * @return 表达式
   * @throws ColumnException 列异常
   */
  @Override
  public ColumnExpression<T> ne(Object value) throws ColumnException {
    this.values.add(value);
    this.placeholers.add(PlaceholderEnum.NEQ);
    return getSlfe();
  }

  /**
   * 查询表达式：全部不等于数组
   *
   * @param nes 表达式相关值列表
   * @return 表达式
   * @throws ColumnException 列异常
   */
  @Override
  public ColumnExpression<T> neAll(List nes) throws ColumnException {
    this.values.add(nes);
    this.placeholers.add(PlaceholderEnum.NEQ);
    return getSlfe();
  }

  /**
   * 查询表达式：全部不等于集合
   *
   * @param nes 表达式所需值列表
   * @return 表达式
   * @throws ColumnException 列异常
   */
  @Override
  public ColumnExpression<T> neAll(Object... nes) throws ColumnException {
    this.values.add(nes);
    this.placeholers.add(PlaceholderEnum.NEQ);
    return getSlfe();
  }

  /**
   * 查询表达式：大于
   *
   * @param value 表达式所需值
   * @return 表达式
   * @throws ColumnException 列异常
   */
  @Override
  public ColumnExpression<T> gt(Object value) throws ColumnException {
    this.values.add(value);
    this.placeholers.add(PlaceholderEnum.GT);
    return getSlfe();
  }

  /**
   * 查询表达式：大于等于
   *
   * @param value 表达式所需值
   * @return 表达式
   * @throws ColumnException 列异常
   */
  @Override
  public ColumnExpression<T> gte(Object value) throws ColumnException {
    this.values.add(value);
    this.placeholers.add(PlaceholderEnum.GET);
    return getSlfe();
  }

  /**
   * 查询表达式：小于
   *
   * @param value 表达式所需值
   * @return 表达式
   * @throws ColumnException 列异常
   */
  @Override
  public ColumnExpression<T> lt(Object value) throws ColumnException {
    this.values.add(value);
    this.placeholers.add(PlaceholderEnum.LT);
    return getSlfe();
  }

  /**
   * 查询表达式：小于等于
   *
   * @param value 表达式所需值
   * @return 表达式
   * @throws ColumnException 列异常
   */
  @Override
  public ColumnExpression<T> lte(Object value) throws ColumnException {
    this.values.add(value);
    this.placeholers.add(PlaceholderEnum.LET);
    return getSlfe();
  }

  /**
   * 查询表达式：模糊查询
   *
   * @param preffix 前缀，如：%，null
   * @param value 值
   * @param suffix 后缀，如：%，null
   * @return 表达式
   * @throws ColumnException 列异常
   */
  @Override
  public ColumnExpression<T> like(String preffix, Object value, String suffix)
      throws ColumnException {
    /** 格式化like的值 */
    String formatterLikeValue = !StringUtils.isEmpty(preffix) ? "%" : "";
    formatterLikeValue += value;
    formatterLikeValue += !StringUtils.isEmpty(suffix) ? "%" : "";

    this.values.add(formatterLikeValue);
    this.placeholers.add(PlaceholderEnum.LIKE);
    return getSlfe();
  }

  /**
   * 查询表达式：包含集合查询
   *
   * @param ins 表达式所需值
   * @return 表达式
   * @throws ColumnException 列异常
   */
  @Override
  public ColumnExpression<T> in(List ins) throws ColumnException {
    this.values.add(ins);
    this.placeholers.add(PlaceholderEnum.IN);
    return getSlfe();
  }

  /**
   * 查询表达式：包含数组
   *
   * @param ins 表达式所需值
   * @return 表达式
   * @throws ColumnException 列异常
   */
  @Override
  public ColumnExpression<T> in(Object... ins) throws ColumnException {
    this.values.add(ins);
    this.placeholers.add(PlaceholderEnum.IN);
    return getSlfe();
  }

  /**
   * 查询表达式：为空
   *
   * @return 表达式
   * @throws ColumnException 列异常
   */
  @Override
  public ColumnExpression<T> isNull() throws ColumnException {
    this.values.add(null);
    this.placeholers.add(PlaceholderEnum.IS_NULL);
    return getSlfe();
  }

  /**
   * 查询表达式：不为空
   *
   * @return 表达式
   * @throws ColumnException 列异常
   */
  @Override
  public ColumnExpression<T> isNotNull() throws ColumnException {
    this.values.add(null);
    this.placeholers.add(PlaceholderEnum.IS_NOT_NULL);
    return getSlfe();
  }

  /**
   * 查询空字符串
   *
   * @return 表达式
   * @throws ColumnException 列异常
   */
  @Override
  public ColumnExpression<T> isEmpty() throws ColumnException {
    this.values.add(null);
    this.placeholers.add(PlaceholderEnum.IS_EMPTY);
    return getSlfe();
  }

  /**
   * 不为空
   *
   * @return 表达式
   * @throws ColumnException 列异常
   */
  @Override
  public ColumnExpression<T> isNotEmpty() throws ColumnException {
    this.values.add(null);
    this.placeholers.add(PlaceholderEnum.IS_NOT_EMPTY);
    return getSlfe();
  }
}
