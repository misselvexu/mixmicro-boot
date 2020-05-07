package xyz.vopen.framework.mybatis.enhance.dsl.serach.support;

import xyz.vopen.framework.mybatis.enhance.common.enums.PlaceholderEnum;
import xyz.vopen.framework.mybatis.enhance.dsl.expression.ColumnExpression;
import xyz.vopen.framework.mybatis.enhance.dsl.expression.TableExpression;
import xyz.vopen.framework.mybatis.enhance.dsl.factory.EnhanceDsl;
import xyz.vopen.framework.mybatis.enhance.dsl.serach.Searchable;
import xyz.vopen.framework.mybatis.enhance.dsl.where.support.WhereableSupport;
import xyz.vopen.framework.mybatis.enhance.exception.EnhanceFrameworkException;
import xyz.vopen.framework.mybatis.enhance.sort.SortEnum;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 动态查询实现类 实现所有Searchable接口内的方法
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version}
 */
public class SearchableSupport extends WhereableSupport implements Searchable {
  /** 返回类型 */
  private Class<?> resultType;
  /** 查询开始位置 */
  private int offset;
  /** 限制查询数量 */
  private int limit;
  /** 默认不去重复查询 */
  private boolean distinct = false;

  /** 动态查询接口实例 fetch/fetchOne调用 */
  private EnhanceDsl enhanceDsl;

  /** 查询列集合 */
  private List<ColumnExpression> selectColumns = new ArrayList();
  /** 查询自定义sql列集合 如：TIMESTAMPDIFF(YEAR,nanny.BIRTHDAY,CURDATE()) key = 别名 value = 自定义sql */
  private Map<String, String> selectSQLColumns = new HashMap();
  /** 聚合列集合 */
  private List<ColumnExpression> functionColumns = new ArrayList();

  /**
   * 构造函数初始化SQL对象以及sanmiDsl动态查询接口实例
   *
   * @param enhanceDsl 动态查询接口
   */
  public SearchableSupport(EnhanceDsl enhanceDsl) {
    this.enhanceDsl = enhanceDsl;
  }

  /**
   * 查询方法 可传递单表、多表内的单个或者多个字段
   *
   * @param columnExpressions 列表达式
   * @return 查询对象实例
   */
  @Override
  public Searchable select(ColumnExpression... columnExpressions) {
    /** 处理每个字段是否存在函数使用 */
    for (ColumnExpression columnExpression : columnExpressions) {
      // 存在函数添加函数表达式
      if (checkHaveFunction(columnExpression)) {
        functionColumns.add(columnExpression);
      }
      // 不存在函数，添加查询表达式
      else {
        selectColumns.add(columnExpression);
      }
    }
    return this;
  }

  /**
   * @param sql sql
   * @return 查询对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  @Override
  public Searchable SQLColumn(String sql, String asName) throws EnhanceFrameworkException {
    selectSQLColumns.put(asName, sql);
    return this;
  }

  /**
   * 查询表内的全部字段
   *
   * @param tableExpression 表达式
   * @return 查询对象实例
   */
  @Override
  public Searchable select(TableExpression tableExpression) {
    select(tableExpression.getColumns());
    return this;
  }

  /**
   * 查询参数表内的所有列并将参数作为主表
   *
   * @param tableExpression 表达式
   * @return 查询对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  @Override
  public Searchable selectFrom(TableExpression tableExpression) throws EnhanceFrameworkException {
    this.select(tableExpression);
    this.from(tableExpression);
    return this;
  }

  /**
   * 查询主表信息 仅第一个参数是查询主表信息 从第一个往后可以为表设置别名 如tableExpression.as(别名内容)
   *
   * @param tableExpressions 表达式
   * @return 查询对象实例
   */
  @Override
  public Searchable from(TableExpression... tableExpressions) {
    TableExpression tableExpression = tableExpressions[0];
    checkTableAlias(tableExpression);
    sql.FROM(
        tableExpression.getRoot()
            + "\n"
            + (!StringUtils.isEmpty(tableExpression.getAsName())
                ? tableAlias.get(tableExpression.getRoot())
                : ""));
    return this;
  }

  /**
   * 查询条件and
   *
   * @param columnExpression 列表达式
   * @param columnExpressions 列表达式
   * @return 查询对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  @Override
  public Searchable where(ColumnExpression columnExpression, ColumnExpression... columnExpressions)
      throws EnhanceFrameworkException {
    super.buildWhere(columnExpression, columnExpressions);
    return this;
  }

  /**
   * 根据字段表达式查询
   *
   * @param columnExpression 列表达式
   * @param columnExpressions 列表达式
   * @return 查询对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  @Override
  public Searchable and(ColumnExpression columnExpression, ColumnExpression... columnExpressions)
      throws EnhanceFrameworkException {
    buildAnd(columnExpression, columnExpressions);
    return this;
  }

  /**
   * @param sql
   * @return 查询对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  @Override
  public Searchable and(String sql) throws EnhanceFrameworkException {
    buildAnd(sql);
    return this;
  }
  /**
   * 根据字段表达式查询
   *
   * @param columnExpression 列表达式
   * @param columnExpressions 列表达式
   * @return 查询对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  @Override
  public Searchable or(ColumnExpression columnExpression, ColumnExpression... columnExpressions)
      throws EnhanceFrameworkException {
    buildOr(columnExpression, columnExpressions);
    return this;
  }

  /**
   * 根据指定字段求总数 可根据多个字段进行count(xxx)
   *
   * @param columnExpressions 列表达式
   * @return 查询对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  @Override
  public Searchable count(ColumnExpression... columnExpressions) throws EnhanceFrameworkException {
    setFuntionType(FunctionTypeEnum.COUNT, columnExpressions);
    return this;
  }

  /**
   * 根据指定列求平均值
   *
   * @param columnExpressions 列表达式
   * @return 查询对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  @Override
  public Searchable avg(ColumnExpression... columnExpressions) throws EnhanceFrameworkException {
    setFuntionType(FunctionTypeEnum.AVG, columnExpressions);
    return this;
  }

  /**
   * 根据指定字段求合
   *
   * @param columnExpressions 列表达式
   * @return 查询对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  @Override
  public Searchable sum(ColumnExpression... columnExpressions) throws EnhanceFrameworkException {
    setFuntionType(FunctionTypeEnum.SUM, columnExpressions);
    return this;
  }

  /**
   * 根据指定字段查询最小值
   *
   * @param columnExpressions 列表达式
   * @return 查询对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  @Override
  public Searchable min(ColumnExpression... columnExpressions) throws EnhanceFrameworkException {
    setFuntionType(FunctionTypeEnum.MIN, columnExpressions);
    return this;
  }

  /**
   * 根据指定字段查询最大值
   *
   * @param columnExpressions 列表达式
   * @return 查询对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  @Override
  public Searchable max(ColumnExpression... columnExpressions) throws EnhanceFrameworkException {
    setFuntionType(FunctionTypeEnum.MAX, columnExpressions);
    return this;
  }

  /**
   * join 关联查询
   *
   * @param onColumn 关联表列表达式，注意：必须为主表内的关联字段
   * @param joinColumn 被关联表达式
   * @return 查询对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  @Override
  public Searchable join(ColumnExpression onColumn, ColumnExpression joinColumn)
      throws EnhanceFrameworkException {
    sql.JOIN(formatterJoinSql(onColumn, joinColumn));
    return this;
  }

  /**
   * left join 查询关联
   *
   * @param onColumn 关联表列表达式，注意：必须为主表内的关联字段
   * @param joinColumn 被关联表达式
   * @return 查询对象
   */
  @Override
  public Searchable leftJoin(ColumnExpression onColumn, ColumnExpression joinColumn) {
    sql.LEFT_OUTER_JOIN(formatterJoinSql(onColumn, joinColumn));
    return this;
  }

  /**
   * inner join 查询关联
   *
   * @param onColumn 关联表达式，注意：必须为主表内的关联字段
   * @param joinColumn 被关联表达式
   * @return 查询对象
   */
  @Override
  public Searchable innerJoin(ColumnExpression onColumn, ColumnExpression joinColumn) {
    sql.INNER_JOIN(formatterJoinSql(onColumn, joinColumn));
    return this;
  }

  /**
   * right join 查询关联
   *
   * @param onColumn 关联列表达式，注意：必须为主表内的关联字段
   * @param joinColumn 被关联列表达式
   * @return 查询对象
   */
  @Override
  public Searchable rightJoin(ColumnExpression onColumn, ColumnExpression joinColumn) {
    sql.RIGHT_OUTER_JOIN(formatterJoinSql(onColumn, joinColumn));
    return this;
  }

  /**
   * 根据字段排序 可以被重复调用添加多个排序条件
   *
   * @param columnExpression 列表达式
   * @param sortEnum 排序枚举
   * @return 查询对象实例
   */
  @Override
  public Searchable orderBy(ColumnExpression columnExpression, SortEnum sortEnum) {
    sql.ORDER_BY(
        "\n" + getColumnWithTableAlias(columnExpression) + "\n" + sortEnum.toString() + "\n");
    return this;
  }

  /**
   * 排序
   *
   * @param sql sql
   * @param sortEnum 排序枚举
   * @return 查询对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  @Override
  public Searchable orderBy(String sql, SortEnum sortEnum) throws EnhanceFrameworkException {
    this.sql.ORDER_BY(sql + "\n" + sortEnum.toString() + "\n");
    return this;
  }

  /**
   * 根据字段列表分组
   *
   * @param columnExpressions 字段列表
   * @return 查询对象
   */
  @Override
  public Searchable groupBy(ColumnExpression... columnExpressions) {
    for (ColumnExpression columnExpression : columnExpressions) {
      sql.GROUP_BY(getColumnWithTableAlias(columnExpression));
    }
    return this;
  }

  /**
   * 单独存在时，代表查询条数 与limit同时存在时代表查询开始位置
   *
   * @param offset 查询开始位置
   * @return 查询对象
   */
  @Override
  public Searchable offset(int offset) {
    this.offset = offset;
    return this;
  }

  /**
   * 查询条数 offset大于0时代表查询区间（分页）
   *
   * @param limit 每页限制条数
   * @return 查询对象实例
   */
  @Override
  public Searchable limit(int limit) {
    this.limit = limit;
    return this;
  }

  /**
   * 去掉重复
   *
   * @param distinct true：去重
   * @return 查询对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  @Override
  public Searchable distinct(boolean distinct) throws EnhanceFrameworkException {
    this.distinct = distinct;
    return this;
  }

  /**
   * 设置返回实体的映射类型
   *
   * @param resultTypeClass 返回实体类类型
   * @return 查询对象实例
   */
  @Override
  public Searchable resultType(Class<?> resultTypeClass) {
    this.resultType = resultTypeClass;
    return this;
  }

  /**
   * 获取拼接的SQL字符串
   *
   * @return 格式化后的sql
   * @throws EnhanceFrameworkException 框架异常
   */
  @Override
  public String getSql() throws EnhanceFrameworkException {

    // 获取sql之前处理部分逻辑
    getSQLPre();
    // 调用父类业务
    String _sql = super.getSql();

    params.put("limit", limit);
    params.put("offset", offset);
    if (limit > 0) {
      _sql += "\nlimit\n#{offset},#{limit}";
    }
    return _sql;
  }

  /**
   * 获取结果集单个对象类型
   *
   * @return 查询结果类型
   * @throws EnhanceFrameworkException 框架异常
   */
  @Override
  public Class<?> getResultType() throws EnhanceFrameworkException {
    return this.resultType;
  }

  /**
   * 获取动态查询条件集合
   *
   * @return 参数集合
   * @throws EnhanceFrameworkException 框架异常
   */
  @Override
  public Map getParams() throws EnhanceFrameworkException {
    return this.params;
  }

  /**
   * 调用该方法后执行查询集合列表
   *
   * @param <T> 返回实体类型
   * @return 结果列表
   * @throws EnhanceFrameworkException 框架异常
   */
  @Override
  public <T> List<T> fetch() throws EnhanceFrameworkException {
    return enhanceDsl.select(this);
  }

  /**
   * 调用该方法后执行查询单条数据
   *
   * @param <T> 返回实体类型
   * @return 查询单个结果
   * @throws EnhanceFrameworkException 框架异常
   */
  @Override
  public <T> T fetchOne() throws EnhanceFrameworkException {
    return enhanceDsl.selectOne(this);
  }

  /** 调用fetch前执行该方法 */
  private void getSQLPre() throws EnhanceFrameworkException {
    /** 查询列集合 */
    putSelectColumns();
    /** 查询聚合函数列集合 */
    putFunctionColumns();
    /** 查询自定义sql列 */
    putSelectSQLColumns();
  }

  /** 自定义sql列 */
  private void putSelectSQLColumns() {
    Iterator<String> iterator = selectSQLColumns.keySet().iterator();
    while (iterator.hasNext()) {
      String asName = iterator.next();
      String sql = selectSQLColumns.get(asName);
      this.sql.SELECT(sql + "\n" + PlaceholderEnum.AS + "\n" + asName);
    }
  }

  /** 查询查询字段到集合内 格式化查询字段 包含普通查询表内字段、count聚合字段、sum聚合字段、avg聚合字段、max聚合字段、min聚合字段 */
  private void putSelectColumns() {
    for (ColumnExpression columnExpression : selectColumns) {
      // 表名，格式化别名后
      String columnName = getColumnWithTableAlias(columnExpression);
      /** 是否存在别名 */
      boolean isHaveAsName = !StringUtils.isEmpty(columnExpression.getAsName());

      /** 存在别名 */
      if (isHaveAsName) {
        columnName += "\n" + PlaceholderEnum.AS + "\n" + columnExpression.getAsName();
      }

      if (!distinct) {
        sql.SELECT(columnName);
      } else {
        sql.SELECT_DISTINCT(columnName);
      }
    }
  }

  /** 添加聚合函数列 */
  private void putFunctionColumns() {
    for (ColumnExpression columnExpression : functionColumns) {
      // 表名，格式化别名后
      String columnName = getColumnWithTableAlias(columnExpression);
      /** 是否存在别名 */
      boolean isHaveAsName = !StringUtils.isEmpty(columnExpression.getAsName());
      /** 检查是否存在聚合函数 */
      String functionColumnForrmatter =
          checkFunctionColumn(columnExpression, columnName, isHaveAsName);
      if (!StringUtils.isEmpty(functionColumnForrmatter)) {
        columnName = functionColumnForrmatter;
      }

      if (!distinct) {
        sql.SELECT(columnName);
      } else {
        sql.SELECT_DISTINCT(columnName);
      }
    }
  }

  /**
   * 检查是否存在聚合函数
   *
   * @param columnExpression 列表达式
   * @param columnName 字段名
   * @param isHaveAsName 是否存在别名
   * @return 追加函数sql
   */
  private String checkFunctionColumn(
      ColumnExpression columnExpression, String columnName, boolean isHaveAsName) {
    String funColumnName = null;
    /** 存在count聚合函数 */
    if (columnExpression.isCount()) {
      funColumnName =
          "\ncount("
              + columnName
              + ")\n"
              + (isHaveAsName ? columnExpression.getAsName() : columnExpression.getRoot());
    }
    /** 存在sum聚合函数 */
    else if (columnExpression.isSum()) {
      funColumnName =
          "\nsum("
              + columnName
              + ")\n"
              + (isHaveAsName ? columnExpression.getAsName() : columnExpression.getRoot());
    }
    /** 存在avg聚合函数 */
    else if (columnExpression.isAvg()) {
      funColumnName =
          "\navg("
              + columnName
              + ")\n"
              + (isHaveAsName ? columnExpression.getAsName() : columnExpression.getRoot());
    }
    /** 存在max聚合函数 */
    else if (columnExpression.isMax()) {
      funColumnName =
          "\nmax("
              + columnName
              + ")\n"
              + (isHaveAsName ? columnExpression.getAsName() : columnExpression.getRoot());
    }
    /** 存在min聚合函数 */
    else if (columnExpression.isMin()) {
      funColumnName =
          "\nmin("
              + columnName
              + ")\n"
              + (isHaveAsName ? columnExpression.getAsName() : columnExpression.getRoot());
    }
    return funColumnName;
  }

  /**
   * 格式化join sql，可用于left join 、inner join
   *
   * @param onColumn 关联列表达式
   * @param joinColumn 被关联列表达式
   * @return 格式化join语句
   */
  private String formatterJoinSql(ColumnExpression onColumn, ColumnExpression joinColumn)
      throws EnhanceFrameworkException {
    StringBuffer joinSql = new StringBuffer();

    // 被关联表表达式
    TableExpression joinTable = joinColumn.getTableExpression();
    // 被关联表别名
    String joinTableAsName = joinTable.getAsName();
    // 被关联表是否存在别名
    boolean joinTableIsHaveAs = !StringUtils.isEmpty(joinTableAsName);
    // 被关联表名称
    String joinTableName = joinTable.getRoot();

    // 关联表表达式
    TableExpression onTable = onColumn.getTableExpression();
    // 关联表名
    String onTableName = onTable.getRoot();

    /** 检查别名是否存在与集合内 */
    checkTableAlias(joinTable);
    checkTableAlias(onTable);

    joinSql.append("\n" + joinTable.getRoot() + "\n");
    joinSql.append(joinTableIsHaveAs ? tableAlias.get(joinTableName) : "");
    joinSql.append("\non\n");
    joinSql.append(tableAlias.get(joinTableName));
    joinSql.append("." + joinColumn.getRoot());
    joinSql.append("\n=\n");
    joinSql.append(tableAlias.get(onTableName));
    joinSql.append("." + onColumn.getRoot() + "\n");

    return joinSql.toString();
  }

  /**
   * 设置根据聚合函数查询时的列表达式类型 如：count/avg/sum/max/min
   *
   * @param function 聚合函数
   * @param columnExpressions 列表达式集合
   */
  void setFuntionType(FunctionTypeEnum function, ColumnExpression... columnExpressions) {
    for (ColumnExpression ce : columnExpressions) {
      // jdk1.7之后加入enum类型支持
      switch (function) {
        case COUNT:
          ce.setCount(true);
          break;
        case AVG:
          ce.setAvg(true);
          break;
        case SUM:
          ce.setSum(true);
          break;
        case MAX:
          ce.setMax(true);
          break;
        case MIN:
          ce.setMin(true);
          break;
      }
      functionColumns.add(ce);
    }
  }

  /**
   * 验证列表达式是否存在函数 如：xxx.count()
   *
   * @param columnExpression 列表达式
   * @return true：存在函数表达式
   */
  boolean checkHaveFunction(ColumnExpression columnExpression) {
    return columnExpression.isCount()
        || columnExpression.isAvg()
        || columnExpression.isMax()
        || columnExpression.isMin()
        || columnExpression.isSum();
  }

  /** 聚合函数类型枚举 */
  enum FunctionTypeEnum {
    COUNT,
    AVG,
    SUM,
    MAX,
    MIN
  }
}
