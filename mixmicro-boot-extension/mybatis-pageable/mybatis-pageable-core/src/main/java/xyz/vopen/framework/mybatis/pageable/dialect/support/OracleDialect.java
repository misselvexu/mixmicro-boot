package xyz.vopen.framework.mybatis.pageable.dialect.support;

import xyz.vopen.framework.mybatis.pageable.Page;
import xyz.vopen.framework.mybatis.pageable.PageParameterSortMapping;
import xyz.vopen.framework.mybatis.pageable.dialect.AbstractDialect;
import org.apache.ibatis.mapping.BoundSql;

import java.util.ArrayList;
import java.util.List;

/**
 * Oracle数据库方言
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version} - 2020-04-11.
 */
public class OracleDialect extends AbstractDialect {

  /**
   * 设置mysql数据库排序后的分页参数
   * 对应  xyz.vopen.framework.mybatis.pageable.dialect.support.MySqlDialect#getPageSql(org.apache.ibatis.mapping.BoundSql,
   * Page) 获取的分页sql的占位符索引
   *
   * @return 获取排序后的分页参数映射列表
   */
  public List<PageParameterSortMapping> getSortParameterMapping() {
    return new ArrayList() {
      {
        add(
            PageParameterSortMapping.builder()
                .parameterName(PARAM_PAGE_END)
                .typeClass(Long.class)
                .build());
        add(
            PageParameterSortMapping.builder()
                .parameterName(PARAM_PAGE_OFFSET)
                .typeClass(Long.class)
                .build());
      }
    };
  }

  /**
   * 获取oracle数据库分页sql
   *
   * @param boundSql boundSql 对象
   * @param page 分页响应对象实例
   * @return 获取分页sql
   */
  public String getPageSql(BoundSql boundSql, Page page) {
    StringBuilder sql = new StringBuilder();
    sql.append("SELECT * FROM ( ");
    sql.append("SELECT PAGEABLE_.*,ROWNUM ROW_ID FROM ( ");
    sql.append(boundSql.getSql());
    sql.append(" ) PAGEABLE_ WHERE ROWNUM <= ? ");
    sql.append(" ) WHERE ROW_ID > ? ");
    return sql.toString();
  }
}
