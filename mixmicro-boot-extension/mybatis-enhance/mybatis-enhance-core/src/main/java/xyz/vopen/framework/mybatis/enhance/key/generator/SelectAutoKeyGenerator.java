package xyz.vopen.framework.mybatis.enhance.key.generator;

import xyz.vopen.framework.mybatis.enhance.common.ConfigConstants;
import xyz.vopen.framework.mybatis.enhance.common.helper.StatementHelper;
import xyz.vopen.framework.mybatis.enhance.exception.EnhanceFrameworkException;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.RowBounds;
import org.springframework.util.ObjectUtils;

import java.sql.Statement;
import java.util.*;

/**
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 *     <p>DateTime：2019-05-26 11:52
 */
public class SelectAutoKeyGenerator implements KeyGenerator {
  @Override
  public void processBefore(
      Executor executor, MappedStatement ms, Statement stmt, Object parameter) {
    // ignore
  }

  @Override
  public void processAfter(
      Executor executor, MappedStatement ms, Statement stmt, Object parameter) {
    try {
      String selectKeySql = "select last_insert_id()";
      final Configuration configuration = ms.getConfiguration();
      Executor keyExecutor =
          configuration.newExecutor(executor.getTransaction(), ExecutorType.SIMPLE);
      MappedStatement statement =
          StatementHelper.createSelectKeyStatement(ms.getConfiguration(), ms.getId(), selectKeySql);

      ResultMap.Builder builder =
          new ResultMap.Builder(
              statement.getConfiguration(),
              statement.getId() + "-Inline",
              Integer.class,
              new ArrayList(),
              true);
      ResultMap resultMap = builder.build();
      MetaObject metaObject = SystemMetaObject.forObject(statement);
      metaObject.setValue("resultMaps", Collections.unmodifiableList(Arrays.asList(resultMap)));

      List<Object> values =
          keyExecutor.query(statement, parameter, RowBounds.DEFAULT, Executor.NO_RESULT_HANDLER);

      // 如果参数存在多个，为每一个参数实体的主键都设置
      batchProcessAfter(ms, getParameters(parameter), values.get(0));

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 批量设置主键的值
   *
   * @param ms MappedStatement对象实例
   * @param parameters Mapepr方法的参数集合
   */
  private void batchProcessAfter(
      MappedStatement ms, Collection<Object> parameters, Object autoValue) {
    // 获取主键的FieldName
    String[] keyProperties = ms.getKeyProperties();
    if (ObjectUtils.isEmpty(keyProperties)) {
      throw new EnhanceFrameworkException("并未设置主键字段，无法根据主键策略生成对应的主键值.");
    }
    Configuration configuration = ms.getConfiguration();
    // 遍历所有的参数，分别设置主键的值
    for (Object parameter : parameters) {
      // 设置主键对应field的策略值
      MetaObject metaParam = configuration.newMetaObject(parameter);
      setValue(metaParam, keyProperties[0], autoValue);
    }
  }

  /**
   * 设置值到指定属性
   *
   * @param metaParam 参数集合
   * @param property 属性名城管
   * @param value 值
   */
  private void setValue(MetaObject metaParam, String property, Object value) {
    if (metaParam.hasSetter(property)) {
      metaParam.setValue(property, value);
    } else {
      throw new ExecutorException(
          "No setter found for the keyProperty '"
              + property
              + "' in "
              + metaParam.getOriginalObject().getClass().getName()
              + ".");
    }
  }

  /**
   * 沿用Jdbc3KeyGenerator内解析参数的方法实现
   *
   * @param parameter 请求对象
   * @return 参数集合
   * @see org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator
   */
  private Collection<Object> getParameters(Object parameter) {
    Collection<Object> parameters = null;
    if (parameter instanceof Collection) {
      parameters = (Collection) parameter;
    } else if (parameter instanceof Map) {
      Map parameterMap = (Map) parameter;
      // 如果参数map内存在,key=collection的@Param时
      if (parameterMap.containsKey(ConfigConstants.COLLECTION_PARAMETER_NAME)) {
        Object collection = parameterMap.get(ConfigConstants.COLLECTION_PARAMETER_NAME);
        if (collection instanceof Collection) {
          parameters = (Collection) collection;
        } else if (collection instanceof Object[]) {
          parameters = Arrays.asList((Object[]) collection);
        }
      }
      // 如果参数map内存在,key=array的@Param时
      else if (parameterMap.containsKey(ConfigConstants.LIST_PARAMETER_NAME)) {
        parameters = (List) parameterMap.get(ConfigConstants.LIST_PARAMETER_NAME);
      }
      // 如果参数map内存在,key=array的@Param时
      else if (parameterMap.containsKey(ConfigConstants.ARRAY_PARAMETER_NAME)) {
        parameters =
            Arrays.asList((Object[]) parameterMap.get(ConfigConstants.ARRAY_PARAMETER_NAME));
      }
      // 如果参数map内存在,key=bean的@Param时
      else if (parameterMap.containsKey(ConfigConstants.BEAN_PARAMETER_NAME)) {
        parameters = Arrays.asList((Object) parameterMap.get(ConfigConstants.BEAN_PARAMETER_NAME));
      }
    }

    if (parameters == null) {
      parameters = new ArrayList();
      parameters.add(parameter);
    }

    return parameters;
  }
}
