package xyz.vopen.framework.mybatis.pageable.dialect.support;

import xyz.vopen.framework.mybatis.pageable.PageParameterSortMapping;
import xyz.vopen.framework.mybatis.pageable.dialect.AbstractDialect;

import java.util.ArrayList;
import java.util.List;

/**
 * MySQL数据库方言实现
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version} - 2020-04-11.
 */
public class MySqlDialect extends AbstractDialect {

  /**
   * 设置mysql数据库排序后的分页参数
   * 对应  xyz.vopen.framework.mybatis.pageable.dialect.support.MySqlDialect#getPageSql(org.apache.ibatis.mapping.BoundSql,
   * Page) 获取的分页sql的占位符索引
   *
   * @return 获取排序后的参数映射列表
   */
  public List<PageParameterSortMapping> getSortParameterMapping() {
    return new ArrayList() {
      {
        add(
            PageParameterSortMapping.builder()
                .parameterName(PARAM_PAGE_SIZE)
                .typeClass(Integer.class)
                .build());
        add(
            PageParameterSortMapping.builder()
                .parameterName(PARAM_PAGE_OFFSET)
                .typeClass(Long.class)
                .build());
      }
    };
  }
}
