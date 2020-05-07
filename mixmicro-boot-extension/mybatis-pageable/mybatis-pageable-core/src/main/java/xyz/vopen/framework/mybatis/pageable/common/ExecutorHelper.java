package xyz.vopen.framework.mybatis.pageable.common;

import xyz.vopen.framework.mybatis.pageable.executor.ExecutorQueryRequest;
import org.apache.ibatis.executor.Executor;

import java.sql.SQLException;
import java.util.List;

/**
 * mybatis执行器工具类
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version} - 2020-04-11.
 */
public final class ExecutorHelper {
  /**
   * 执行查询
   *
   * @param executor 执行器
   * @param request 执行查询请求对象
   * @param <E> 泛型类型
   * @return 查询后的数据列表
   * @throws SQLException 数据库异常
   */
  public static <E> List<E> query(Executor executor, ExecutorQueryRequest request)
      throws SQLException {
    return executor.query(
        request.getStatement(),
        request.getParameter(),
        request.getRowBounds(),
        request.getResultHandler(),
        request.getCacheKey(),
        request.getBoundSql());
  }
}
