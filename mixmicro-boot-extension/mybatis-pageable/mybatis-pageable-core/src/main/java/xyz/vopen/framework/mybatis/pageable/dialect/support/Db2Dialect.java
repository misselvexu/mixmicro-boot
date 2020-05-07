package xyz.vopen.framework.mybatis.pageable.dialect.support;

import xyz.vopen.framework.mybatis.pageable.Page;
import xyz.vopen.framework.mybatis.pageable.PageParameterSortMapping;
import xyz.vopen.framework.mybatis.pageable.dialect.AbstractDialect;
import org.apache.ibatis.mapping.BoundSql;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * db2数据库方言
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version} - 2020-04-11.
 */
public class Db2Dialect extends AbstractDialect {
  /**
   * 设置db2数据库排序后的分页参数
   * 对应  xyz.vopen.framework.mybatis.pageable.dialect.support.Db2Dialect#getPageSql(org.apache.ibatis.mapping.BoundSql,
   * Page) 获取的分页sql的占位符索引
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
   * 设置开始位置为1
   *
   * <p>注意：db2数据库分页开始位置是"1"而不是"0"
   *
   * @param parameter 分页查询时请求参数集合
   * @param page 分页响应对象实例
   * @return 获取分页sql
   */
  @Override
  public Object getPageParameter(Object parameter, Page page) {
    // 转换参数集合
    Map pageParameter = (Map) super.getPageParameter(parameter, page);
    // 设置开始位置+1
    pageParameter.put(PARAM_PAGE_OFFSET, page.getOffset() + 1);
    return pageParameter;
  }

  /**
   * 获取db2数据库分页sql
   *
   * @param boundSql boundSql 对象
   * @param page 分页响应对象实例
   * @return 分页sql
   */
  @Override
  public String getPageSql(BoundSql boundSql, Page page) {
    StringBuilder sql = new StringBuilder();
    sql.append("SELECT * FROM (SELECT PAGEABLE_.*,ROWNUMBER() OVER() AS ROW_ID FROM ( ");
    sql.append(boundSql.getSql());
    sql.append(" ) AS PAGEABLE_) PAGEABLE_ WHERE ROW_ID BETWEEN ? AND ?");
    return sql.toString();
  }
}
