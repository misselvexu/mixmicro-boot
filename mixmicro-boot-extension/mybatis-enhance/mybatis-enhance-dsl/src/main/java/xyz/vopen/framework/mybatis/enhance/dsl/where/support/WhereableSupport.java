package xyz.vopen.framework.mybatis.enhance.dsl.where.support;

import xyz.vopen.framework.mybatis.enhance.common.enums.PlaceholderEnum;
import xyz.vopen.framework.mybatis.enhance.dsl.expression.ColumnExpression;
import xyz.vopen.framework.mybatis.enhance.dsl.expression.TableExpression;
import xyz.vopen.framework.mybatis.enhance.dsl.where.Whereable;
import xyz.vopen.framework.mybatis.enhance.dsl.where.filter.WhereFilter;
import xyz.vopen.framework.mybatis.enhance.dsl.where.sql.ColumnWhereSQL;
import xyz.vopen.framework.mybatis.enhance.dsl.where.sql.entity.ColumnWhereSQLEntity;
import xyz.vopen.framework.mybatis.enhance.dsl.where.sql.support.ColumnWhereFactory;
import xyz.vopen.framework.mybatis.enhance.exception.EnhanceFrameworkException;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 条件实现类 select/update/delete执行sql时的条件
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 *     <p>
 * @version ${project.version}
 */
public class WhereableSupport implements Whereable {

  /** 使用表别名集合 key=表名 value=别名 */
  protected Map<String, String> tableAlias = new HashMap();
  /** 参数列表 */
  protected HashMap<String, Object> params = new HashMap();
  /** 动态SQL对象 */
  protected SQL sql = new SQL();
  /** 当前参数索引位置 */
  protected int paramIndex;

  /**
   * 查询条件 and
   *
   * @param filters 多个查询条件
   * @param firstFilter 查询条件
   */
  @Override
  @Deprecated
  public void buildWhere(WhereFilter firstFilter, WhereFilter... filters)
      throws EnhanceFrameworkException {
    // 查询条件集合
    List<WhereFilter> filterList = new ArrayList();
    // 添加第一个查询条件
    filterList.add(firstFilter);
    // 当存在多参数查询条件时
    if (!ObjectUtils.isEmpty(filters)) {
      filterList.addAll(Arrays.asList(filters));
    }

    for (WhereFilter filter : filterList) {
      // 格式化列名
      String columnName = getColumnWithTableAlias(filter.getColumnExpression());
      // 查询值的映射名称
      String paramName =
          getParamName(filter.getColumnExpression(), PlaceholderEnum.AND.getValue().trim());

      sql.WHERE(columnName + "\n" + filter.getPlaceholder().getValue() + "\n#{" + paramName + "}");

      this.params.put(paramName, filter.getValue());
    }
  }

  /**
   * 根据字段表达式 构建查询条件
   *
   * @param columnExpression 第一个占位字段表达式
   * @param columnExpressions 字段表达式集合
   * @throws EnhanceFrameworkException 框架异常
   */
  @Override
  public void buildWhere(ColumnExpression columnExpression, ColumnExpression... columnExpressions)
      throws EnhanceFrameworkException {
    // 查询条件集合
    List<ColumnExpression> expressions = new ArrayList();
    // 添加第一个查询条件
    expressions.add(columnExpression);
    // 当存在多参数查询条件时
    if (!ObjectUtils.isEmpty(columnExpressions)) {
      expressions.addAll(Arrays.asList(columnExpressions));
    }
    /** 遍历处理表达式 */
    for (ColumnExpression expression : expressions) {
      paramIndex++;
      /** 处理遍历到的查询字段的全部条件 */
      for (int i = 0; i < expression.getValues().size(); i++) {
        /** 实例化ColumnWhereSQL接口实现类 */
        ColumnWhereSQL columnWhereSQL =
            ColumnWhereFactory.newInstance(
                (PlaceholderEnum) expression.getPlaceholers().get(i), paramIndex);
        /** 获取该列的查询条件实体 */
        ColumnWhereSQLEntity whereSQLEntity = columnWhereSQL.getColumnWhere(expression, i);
        /** 设置查询sql */
        for (int k = 0; k < whereSQLEntity.getSqls().size(); k++) {
          sql.WHERE(whereSQLEntity.getSqls().get(k));
        }
        /** 将该条sql所需要的参数添加到集合内 */
        for (int j = 0; j < whereSQLEntity.getParamNames().size(); j++) {
          this.params.put(whereSQLEntity.getParamNames().get(j), whereSQLEntity.getValues().get(j));
        }
      }
      /** 清空集合内的数据 */
      expression.getValues().clear();
      expression.getPlaceholers().clear();
    }
  }

  /**
   * 添加一个and查询条件
   *
   * @param whereFilter 查询条件过滤对象
   * @param filters 多个过滤条件
   * @throws EnhanceFrameworkException 框架异常
   */
  @Deprecated
  public void buildAnd(WhereFilter whereFilter, WhereFilter... filters)
      throws EnhanceFrameworkException {
    buildWhere(whereFilter, filters);
  }

  /**
   * 根据sql进行and拼接 该方法后期会被替换 暂时用于解决复杂SQL问题
   *
   * @param sql sql
   * @throws EnhanceFrameworkException 框架异常
   */
  @Override
  public void buildAnd(String sql) throws EnhanceFrameworkException {
    this.sql.WHERE(sql);
  }

  /**
   * 构建and查询条件
   *
   * @param columnExpression 列表达式
   * @param columnExpressions 列表达式
   * @throws EnhanceFrameworkException 框架异常
   */
  @Override
  public void buildAnd(ColumnExpression columnExpression, ColumnExpression... columnExpressions)
      throws EnhanceFrameworkException {
    buildWhere(columnExpression, columnExpressions);
  }

  /**
   * 构建or查询条件
   *
   * @param columnExpression 列表达式
   * @param columnExpressions 列表达式
   * @throws EnhanceFrameworkException 框架异常
   */
  @Override
  public void buildOr(ColumnExpression columnExpression, ColumnExpression... columnExpressions)
      throws EnhanceFrameworkException {
    sql.OR();
    buildWhere(columnExpression, columnExpressions);
  }

  /**
   * 查询条件 or
   *
   * @param filters 多个查询条件
   */
  @Deprecated
  @Override
  public void buildOr(WhereFilter firstFilter, WhereFilter... filters)
      throws EnhanceFrameworkException {
    sql.OR();
    // 查询条件集合
    List<WhereFilter> filterList = new ArrayList();
    // 添加第一个查询条件
    filterList.add(firstFilter);
    // 当存在多参数查询条件时
    if (!ObjectUtils.isEmpty(filters)) {
      filterList.addAll(Arrays.asList(filters));
    }
    for (WhereFilter filter : filterList) {
      // 格式化列名
      String columnName = getColumnWithTableAlias(filter.getColumnExpression());
      // 查询值的映射名称
      String paramName =
          getParamName(filter.getColumnExpression(), PlaceholderEnum.OR.getValue().trim());

      sql.WHERE(columnName + "\n" + filter.getPlaceholder().getValue() + "\n#{" + paramName + "}");

      this.params.put(paramName, filter.getValue());
    }
  }

  /**
   * 根据表的别名获取列
   *
   * @param columnExpression 列表达式
   * @return 列别名
   * @throws EnhanceFrameworkException 框架异常
   */
  protected String getColumnWithTableAlias(ColumnExpression columnExpression)
      throws EnhanceFrameworkException {
    /** 获取默认列名 */
    String columnName = columnExpression.getRoot();
    /** 格式化列名=>表别名.列名 */
    String tableName = tableAlias.get(columnExpression.getTableName());
    /** 如果表别名不存在，则添加到别名集合内 */
    if (StringUtils.isEmpty(tableName)) {
      checkTableAlias(columnExpression.getTableExpression());
    }
    columnName = tableAlias.get(columnExpression.getTableName()) + "." + columnName;
    /** 返回格式化后的列名 */
    return columnName;
  }

  /**
   * 检查表别名
   *
   * @param tableExpression 表达式
   */
  protected void checkTableAlias(TableExpression tableExpression) throws EnhanceFrameworkException {
    String tableName = tableExpression.getRoot();
    if (!StringUtils.isEmpty(tableExpression.getAsName())) {
      /** 设置别名 */
      tableAlias.put(tableExpression.getRoot(), tableExpression.getAsName());
    } else {
      tableAlias.put(tableName, tableName);
    }
  }

  /**
   * 获取参数的名称
   *
   * @param columnExpression 查询列名
   * @return 参数名称
   */
  protected String getParamName(ColumnExpression columnExpression, String prefix) {
    // 格式化列名
    String columnName = getColumnWithTableAlias(columnExpression);
    // 查询值的映射名称
    return columnName.replace(".", "_") + "_" + prefix + "_" + paramIndex;
  }

  /**
   * 获取参数列表集合
   *
   * @return 参数集合
   * @throws EnhanceFrameworkException 框架异常
   */
  @Override
  public Map getParams() throws EnhanceFrameworkException {
    return this.params;
  }

  /**
   * 获取格式化后的sql
   *
   * @return sql语句
   * @throws EnhanceFrameworkException 框架异常
   */
  @Override
  public String getSql() throws EnhanceFrameworkException {
    return this.sql.toString();
  }
}
