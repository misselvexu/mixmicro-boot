package xyz.vopen.framework.mybatis.enhance.dsl.expression;

/**
 * 查询表达式接口
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version}
 */
public interface Expression<T> {
  /**
   * 根描述 类型=  xyz.vopen.framework.mybatis.enhance.dsl.serach.expression.ColumnExpression代表字段名称
   * 类型=  xyz.vopen.framework.mybatis.enhance.dsl.serach.expression.TableExpression代表表名
   *
   * @return
   */
  String getRoot();

  /**
   * 添加别名，为字段 或者表
   *
   * @param asName 别名
   * @return 表达式
   */
  Expression<T> as(String asName);

  /**
   * 获取别名
   *
   * @return 别名
   */
  String getAsName();
}
