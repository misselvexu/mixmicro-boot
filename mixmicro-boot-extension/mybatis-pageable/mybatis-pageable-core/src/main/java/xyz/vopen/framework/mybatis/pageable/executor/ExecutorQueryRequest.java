package xyz.vopen.framework.mybatis.pageable.executor;

import lombok.Builder;
import lombok.Getter;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

/**
 * mybatis内置执行器query方法所需的参数封装对象
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version} - 2020-04-11.
 */
@Getter
@Builder
public class ExecutorQueryRequest {
  private MappedStatement statement;
  private Object parameter;
  private RowBounds rowBounds;
  private ResultHandler resultHandler;
  private CacheKey cacheKey;
  private BoundSql boundSql;
}
