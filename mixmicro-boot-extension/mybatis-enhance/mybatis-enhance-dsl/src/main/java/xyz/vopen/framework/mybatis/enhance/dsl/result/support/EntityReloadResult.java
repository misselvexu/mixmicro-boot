package xyz.vopen.framework.mybatis.enhance.dsl.result.support;

import xyz.vopen.framework.mybatis.enhance.common.helper.RandomHelper;
import xyz.vopen.framework.mybatis.enhance.common.helper.TableHelper;
import xyz.vopen.framework.mybatis.enhance.common.struct.ColumnStruct;
import xyz.vopen.framework.mybatis.enhance.dsl.result.AbstractReloadResult;
import xyz.vopen.framework.mybatis.enhance.dsl.result.ResultReloadUtils;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 实体数据类型重载查询数据返回
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version}
 */
public class EntityReloadResult extends AbstractReloadResult {
  /**
   * 构造函数初始化返回类型
   *
   * @param parameterResultType 请求参数携带的返回类型
   */
  public EntityReloadResult(Class<?> parameterResultType) {
    super(parameterResultType);
  }

  /**
   * 重新处理实体数据类型返回数据
   *
   * @param statement MappedStatement对象实例
   */
  @Override
  public void reload(MappedStatement statement) {
    // 返回映射集合
    List<ResultMapping> resultMappings = new ArrayList();
    // 转换表内列结构
    List<ColumnStruct> columnStruct = TableHelper.getColumnStruct(parameterResultType);
    for (ColumnStruct column : columnStruct) {

      // 排除非基本数据类型
      // 目前不添加自动映射实体返回
      if (!ResultReloadUtils.isBasicResultType(column.getJavaType())) {
        continue;
      }
      // 将列与实体类字段映射写入映射集合
      ResultMapping.Builder builder =
          new ResultMapping.Builder(
              statement.getConfiguration(),
              column.getFieldName(),
              column.getColumnName(),
              column.getJavaType());
      resultMappings.add(builder.build());
    }
    /*
     * 重设返回集合到MappedStatement
     */
    ResultMap.Builder builder =
        new ResultMap.Builder(
            statement.getConfiguration(),
            statement.getId() + RandomHelper.generateLowerString(5),
            parameterResultType,
            resultMappings,
            true);
    ResultMap resultMap = builder.build();
    MetaObject metaObject = SystemMetaObject.forObject(statement);
    metaObject.setValue("resultMaps", Collections.unmodifiableList(Arrays.asList(resultMap)));
  }
}
