package xyz.vopen.framework.mybatis.enhance.dsl.result.support;

import xyz.vopen.framework.mybatis.enhance.common.helper.RandomHelper;
import xyz.vopen.framework.mybatis.enhance.dsl.result.AbstractReloadResult;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import xyz.vopen.framework.mybatis.enhance.dsl.result.ResultReloadUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * 基本数据类型重载查询数据返回
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version}
 */
public class BasicReloadResult extends AbstractReloadResult {
  /**
   * 构造函数初始化返回类型
   *
   * @param parameterResultType 请求参数携带的返回类型
   */
  public BasicReloadResult(Class<?> parameterResultType) {
    super(parameterResultType);
  }

  /**
   * 重新处理基本数据类型返回数据
   *
   * @param statement MappedStatement对象实例
   * @see ResultReloadUtils BASIC_CLASS_TYPE
   */
  @Override
  public void reload(MappedStatement statement) {
    /*
     * 重设返回集合到MappedStatement
     */
    ResultMap.Builder builder =
        new ResultMap.Builder(
            statement.getConfiguration(),
            statement.getId() + RandomHelper.generateLowerString(5),
            parameterResultType,
            new ArrayList(),
            true);
    ResultMap resultMap = builder.build();
    MetaObject metaObject = SystemMetaObject.forObject(statement);
    metaObject.setValue("resultMaps", Collections.unmodifiableList(Arrays.asList(resultMap)));
  }
}
