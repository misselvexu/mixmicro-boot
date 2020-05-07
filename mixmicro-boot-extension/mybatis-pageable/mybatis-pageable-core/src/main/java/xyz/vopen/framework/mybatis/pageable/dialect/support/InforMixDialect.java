package xyz.vopen.framework.mybatis.pageable.dialect.support;

import xyz.vopen.framework.mybatis.pageable.Page;
import xyz.vopen.framework.mybatis.pageable.PageParameterSortMapping;
import xyz.vopen.framework.mybatis.pageable.dialect.AbstractDialect;
import org.apache.ibatis.mapping.BoundSql;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * InforMix数据库方言
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version} - 2020-04-11.
 */
public class InforMixDialect extends AbstractDialect {
  /** 分页关键字：skip */
  private static final String PAGE_KEYWORD_SKIP = " SKIP ";
  /** 分页关键字：first */
  private static final String PAGE_KEYWORD_FIRST = " FIRST ";

  /**
   * 获取InforMix数据库分页排序后的参数
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
   * 获取InforMix数据库分页sql
   *
   * @param boundSql boundSql对象实例
   * @param page 分页响应对象
   * @return 分页sql
   */
  public String getPageSql(BoundSql boundSql, Page page) {
    StringBuilder sql = new StringBuilder(PAGE_KEYWORD_SELECT);
    sql.append(PAGE_KEYWORD_SKIP);
    sql.append(PRE_PLACEHOLDER);
    sql.append(PAGE_KEYWORD_FIRST);
    sql.append(PRE_PLACEHOLDER);

    /*
     * 忽略大小写进行替换
     */
    Pattern pattern = Pattern.compile(PAGE_KEYWORD_SELECT, Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(boundSql.getSql());
    return matcher.replaceFirst(sql.toString());
  }
}
