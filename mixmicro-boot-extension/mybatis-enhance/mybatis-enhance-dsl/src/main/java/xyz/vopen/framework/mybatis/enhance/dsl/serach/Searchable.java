package xyz.vopen.framework.mybatis.enhance.dsl.serach;

import xyz.vopen.framework.mybatis.enhance.dsl.expression.ColumnExpression;
import xyz.vopen.framework.mybatis.enhance.dsl.expression.TableExpression;
import xyz.vopen.framework.mybatis.enhance.dsl.where.Whereable;
import xyz.vopen.framework.mybatis.enhance.exception.EnhanceFrameworkException;
import xyz.vopen.framework.mybatis.enhance.sort.SortEnum;

import java.util.List;

/**
 * 动态查询接口类型 定义常用查询接口方法
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version}
 */
public interface Searchable extends Whereable {
  /**
   * 查询方法 可传递单表、多表内的单个或者多个字段
   *
   * @param columnExpressions 列表达式
   * @return 查询对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  Searchable select(ColumnExpression... columnExpressions) throws EnhanceFrameworkException;

  /**
   * 查询sql列 根据自定义sql查询作为列 该方法仅用于复杂查询 后期会被替换
   *
   * @param sql sql
   * @param asName 别名
   * @return 查询对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  Searchable SQLColumn(String sql, String asName) throws EnhanceFrameworkException;

  /**
   * 查询表内的全部字段
   *
   * @param tableExpression 表达式
   * @return 查询对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  Searchable select(TableExpression tableExpression) throws EnhanceFrameworkException;

  /**
   * 查询主表信息 仅第一个参数是查询主表信息 从第一个往后可以为表设置别名 如tableExpression.as(别名内容)
   *
   * @param tableExpressions 表达式
   * @return 查询对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  Searchable from(TableExpression... tableExpressions) throws EnhanceFrameworkException;

  /**
   * 查询参数表内的所有列并将参数作为主表
   *
   * @param tableExpression 表达式
   * @return 查询对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  Searchable selectFrom(TableExpression tableExpression) throws EnhanceFrameworkException;

  /**
   * 查询条件and v1.0.4编写方式
   *
   * @param columnExpression 单个列表达式
   * @param columnExpressions 多个列表达式
   * @return 查询对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  Searchable where(ColumnExpression columnExpression, ColumnExpression... columnExpressions)
      throws EnhanceFrameworkException;

  /**
   * 添加一个and查询条件 v1.0.4编写方式
   *
   * @param columnExpression 列查询条件
   * @param columnExpressions 列查询条件
   * @return 查询对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  Searchable and(ColumnExpression columnExpression, ColumnExpression... columnExpressions)
      throws EnhanceFrameworkException;

  /**
   * 根据sql进行and拼接 该方法后期会被替换 暂时用于解决复杂SQL问题
   *
   * @param sql sql
   * @return 查询对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  Searchable and(String sql) throws EnhanceFrameworkException;

  /**
   * 添加or查询条件 v1.0.4编写方式
   *
   * @param columnExpression 列表达式
   * @param columnExpressions 列表达式
   * @return 查询对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  Searchable or(ColumnExpression columnExpression, ColumnExpression... columnExpressions)
      throws EnhanceFrameworkException;

  /**
   * 根据指定列进行count操作
   *
   * @param columnExpressions 列表达式
   * @return 查询对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  Searchable count(ColumnExpression... columnExpressions) throws EnhanceFrameworkException;

  /**
   * 根据指定列进行avg操作
   *
   * @param columnExpressions 列表达式
   * @return 查询对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  Searchable avg(ColumnExpression... columnExpressions) throws EnhanceFrameworkException;

  /**
   * 根据指定列进行sum操作
   *
   * @param columnExpressions 列表达式
   * @return 查询对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  Searchable sum(ColumnExpression... columnExpressions) throws EnhanceFrameworkException;

  /**
   * 查询指定字段最小值
   *
   * @param columnExpressions 列表达式
   * @return 查询对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  Searchable min(ColumnExpression... columnExpressions) throws EnhanceFrameworkException;

  /**
   * 查询指定字段最大值
   *
   * @param columnExpressions 列表达式
   * @return 查询对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  Searchable max(ColumnExpression... columnExpressions) throws EnhanceFrameworkException;

  /**
   * left join 查询关联
   *
   * @param onColumn 关联表列表达式，注意：必须为主表内的关联字段
   * @param joinColumn 被关联表达式
   * @return 查询对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  Searchable leftJoin(ColumnExpression onColumn, ColumnExpression joinColumn)
      throws EnhanceFrameworkException;

  /**
   * join 关联查询 添加日期：2018-7-6 备注：测试效率要比left join提高百倍
   *
   * @param onColumn 关联表列表达式，注意：必须为主表内的关联字段
   * @param joinColumn 被关联表达式
   * @return 查询对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  Searchable join(ColumnExpression onColumn, ColumnExpression joinColumn)
      throws EnhanceFrameworkException;

  /**
   * inner join 查询关联
   *
   * @param onColumn 关联表达式，注意：必须为主表内的关联字段
   * @param joinColumn 被关联表达式
   * @return 查询对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  Searchable innerJoin(ColumnExpression onColumn, ColumnExpression joinColumn)
      throws EnhanceFrameworkException;

  /**
   * right join 查询关联
   *
   * @param onColumn 关联列表达式，注意：必须为主表内的关联字段
   * @param joinColumn 被关联列表达式
   * @return 查询对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  Searchable rightJoin(ColumnExpression onColumn, ColumnExpression joinColumn)
      throws EnhanceFrameworkException;

  /**
   * 根据字段排序 可以被重复调用添加多个排序条件
   *
   * @param columnExpression 列表达式
   * @param sortEnum 排序枚举
   * @return 查询对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  Searchable orderBy(ColumnExpression columnExpression, SortEnum sortEnum)
      throws EnhanceFrameworkException;

  /**
   * 根据自定义的sql排序
   *
   * @param sql sql
   * @param sortEnum 排序枚举
   * @return 查询对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  Searchable orderBy(String sql, SortEnum sortEnum) throws EnhanceFrameworkException;

  /**
   * 根据字段列表分组
   *
   * @param columnExpressions 字段列表
   * @return 查询对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  Searchable groupBy(ColumnExpression... columnExpressions) throws EnhanceFrameworkException;

  /**
   * 单独存在时，代表查询条数 与limit同时存在时代表查询开始位置
   *
   * @param offset 分页开始位置
   * @return 查询对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  Searchable offset(int offset) throws EnhanceFrameworkException;

  /**
   * 查询条数 offset大于0时代表查询区间（分页）
   *
   * @param limit 分页限制条数
   * @return 查询对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  Searchable limit(int limit) throws EnhanceFrameworkException;

  /**
   * 是否去掉重复
   *
   * @param distinct 是否排除重复
   * @return 查询对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  Searchable distinct(boolean distinct) throws EnhanceFrameworkException;

  /**
   * 设置返回实体的映射类型
   *
   * @param resultTypeClass 结果实体类型
   * @return 查询对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  Searchable resultType(Class<?> resultTypeClass) throws EnhanceFrameworkException;

  /**
   * 获取结果集
   *
   * @return 结果实体类型
   * @throws EnhanceFrameworkException 框架异常
   */
  Class<?> getResultType() throws EnhanceFrameworkException;

  /**
   * 调用该方法后执行查询集合列表
   *
   * @param <T> 对象类型
   * @return 对象结果列表
   * @throws EnhanceFrameworkException 框架异常
   */
  <T> List<T> fetch() throws EnhanceFrameworkException;

  /**
   * 调用该方法后执行查询单条数据
   *
   * @param <T> 对象类型
   * @return 单个对象结果
   * @throws EnhanceFrameworkException 框架异常
   */
  <T> T fetchOne() throws EnhanceFrameworkException;
}
