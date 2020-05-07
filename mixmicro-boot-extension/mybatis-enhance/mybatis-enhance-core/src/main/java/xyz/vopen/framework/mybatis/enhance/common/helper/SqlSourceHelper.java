package xyz.vopen.framework.mybatis.enhance.common.helper;

import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;

import java.util.Map;

/**
 * SqlSource工具类
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version}
 */
public class SqlSourceHelper {
  /** MyBaits内部Xml节点解析的语言驱动 */
  private static final XMLLanguageDriver languageDriver = new XMLLanguageDriver();

  /**
   * 通过xml解析语言驱动创建SqlSource
   *
   * @param configuration 配置对象
   * @param sql sqlSource内存放的sql语句
   * @param parameterType 参数类型
   * @return SqlSource对象
   */
  public static SqlSource createSqlSource(
      Configuration configuration, String sql, Class<?> parameterType) {
    return languageDriver.createSqlSource(configuration, sql, parameterType);
  }

  /**
   * 创建Map参数类型的SqlSource对象
   *
   * @param configuration 配置对象
   * @param sql sqlSource内存放的sql语句
   * @return SqlSource对象
   */
  public static SqlSource createMapSqlSource(Configuration configuration, String sql) {
    return createSqlSource(configuration, sql, Map.class);
  }
}
