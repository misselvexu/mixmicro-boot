/**
 * The MIT License (MIT)
 *
 * <p>Copyright (c) 2018 VOPEN.XYZ
 *
 * <p>Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * <p>The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * <p>THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package xyz.vopen.framework.mybatis.enhance.key.generator;

import xyz.vopen.framework.mybatis.enhance.common.ConfigConstants;
import xyz.vopen.framework.mybatis.enhance.exception.EnhanceFrameworkException;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.springframework.util.ObjectUtils;

import java.sql.Statement;
import java.util.*;

/**
 * 自定义UUID主键生成策略
 *
 */
public class UUIDKeyGenerator implements KeyGenerator {

  /**
   * 在执行insert之前设置对应主键的值
   *
   * @param executor 执行对象
   * @param ms MappedStatement对象实例
   * @param statement Statement数据对象实例
   * @param parameter 参数对象
   */
  @Override
  public void processBefore(
      Executor executor, MappedStatement ms, Statement statement, Object parameter) {
    // 如果参数存在多个，为每一个参数实体的主键都设置
    batchProcessBefore(ms, getParameters(parameter));
  }

  /**
   * 批量设置主键的值
   *
   * @param ms MappedStatement对象实例
   * @param parameters Mapepr方法的参数集合
   */
  private void batchProcessBefore(MappedStatement ms, Collection<Object> parameters) {
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
      setValue(metaParam, keyProperties[0], UUID.randomUUID().toString());
    }
  }

  /**
   * 执行后的方法
   *
   * @param executor 执行对象
   * @param ms MappedStatement对象实例
   * @param statement Statement数据对象实例
   * @param parameter 参数对象
   */
  @Override
  public void processAfter(
      Executor executor, MappedStatement ms, Statement statement, Object parameter) {}

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
