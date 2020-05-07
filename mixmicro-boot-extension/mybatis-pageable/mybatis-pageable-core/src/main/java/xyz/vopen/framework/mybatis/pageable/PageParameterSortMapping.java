package xyz.vopen.framework.mybatis.pageable;

import xyz.vopen.framework.mybatis.pageable.dialect.AbstractDialect;
import lombok.Builder;
import lombok.Data;

/**
 * 分页参数排序映射实体
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version} - 2020-04-11.
 * @see AbstractDialect#addPageMapping(org.apache.ibatis.mapping.BoundSql,
 *     org.apache.ibatis.mapping.MappedStatement, Page)
 */
@Data
@Builder
public class PageParameterSortMapping {
  /** 参数名称 */
  private String parameterName;
  /** 参数类型 */
  private Class typeClass;
}
