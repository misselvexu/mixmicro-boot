package xyz.vopen.framework.mybatis.pageable.dialect.support;

import xyz.vopen.framework.mybatis.pageable.Page;
import xyz.vopen.framework.mybatis.pageable.PageParameterSortMapping;
import xyz.vopen.framework.mybatis.pageable.dialect.AbstractDialect;
import org.apache.ibatis.mapping.BoundSql;

import java.util.ArrayList;
import java.util.List;

/**
 * SqlServer数据库方言 sqlServer 2000以上版本 2005、2008、2012版本方式
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version} - 2020-04-11.
 */
public class SqlServerDialect extends AbstractDialect {
  /**
   * 获取sqlserver数据库分页查询的排序后的参数列表
   *
   * @return 获取排序后的分页参数映射列表
   */
  public List<PageParameterSortMapping> getSortParameterMapping() {
    return new ArrayList() {
      {
        add(
            PageParameterSortMapping.builder()
                .parameterName(PARAM_PAGE_OFFSET)
                .typeClass(Long.class)
                .build());
        add(
            PageParameterSortMapping.builder()
                .parameterName(PARAM_PAGE_SIZE)
                .typeClass(Integer.class)
                .build());
      }
    };
  }

  /**
   * 获取SqlServer数据库分页sql
   *
   * @param boundSql boundSql 对象
   * @param page 分页响应对象
   * @return 分页sql
   */
  @Override
  public String getPageSql(BoundSql boundSql, Page page) {
    StringBuilder sql = new StringBuilder();
    sql.append(boundSql.getSql());
    sql.append(" OFFSET ");
    sql.append(PRE_PLACEHOLDER);
    sql.append(" ROWS FETCH NEXT ");
    sql.append(PRE_PLACEHOLDER);
    sql.append(" ROWS ONLY");
    return sql.toString();
  }
}
