package xyz.vopen.framework.mybatis.enhance.dsl.update.filter;

import xyz.vopen.framework.mybatis.enhance.dsl.expression.ColumnExpression;
import xyz.vopen.framework.mybatis.enhance.exception.EnhanceFrameworkException;
import lombok.Data;

/**
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version}
 */
@Data
public class SetFilter {
  /** 更新列 */
  private ColumnExpression columnExpression;
  /** 更新值 */
  private Object value;

  /**
   * 构造函数初始化
   *
   * @param columnExpression 更新列
   * @param value 更新值
   */
  public SetFilter(ColumnExpression columnExpression, Object value) {
    this.columnExpression = columnExpression;
    this.value = value;
  }

  /**
   * 创建一个setFilter对象实例
   *
   * @param columnExpression 更新列
   * @param value 更新值
   * @return 更新过滤对象
   * @throws EnhanceFrameworkException 框架异常
   */
  public static SetFilter set(ColumnExpression columnExpression, Object value)
      throws EnhanceFrameworkException {
    return new SetFilter(columnExpression, value);
  }
}
