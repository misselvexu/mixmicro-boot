package xyz.vopen.framework.mybatis.enhance.dsl.result;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 动态查询结果重载工具类
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version}
 */
public class ResultReloadUtils {
  /** 结果类型参数名称 对应@Param("resultType) */
  private static final String RESULT_TYPE_PARAM = "resultType";
  /** 动态查询单个返回值支持的基本数据类型 */
  private static final List<Class> BASIC_CLASS_TYPE =
      new ArrayList() {
        {
          add(Long.class);
          add(long.class);
          add(BigDecimal.class);
          add(Double.class);
          add(double.class);
          add(Float.class);
          add(float.class);
          add(String.class);
          add(Integer.class);
          add(int.class);
          add(Timestamp.class);
          add(Date.class);
        }
      };

  /**
   * 获取内部约定的返回结果类型
   *
   * @param parameter Executor.query执行的参数对象
   * @return 返回结果类型
   */
  public static Class<?> getResultType(Object parameter) {
    if (parameter == null || !(parameter instanceof Map)) {
      return null;
    }
    // 转换请求参数为Map集合对象
    Map parameterMap = (Map) parameter;
    // 获取结果实体的类型
    Object resultType = parameterMap.get(RESULT_TYPE_PARAM);
    // 转换返回类型
    return resultType != null ? (Class<?>) resultType : null;
  }

  /**
   * 验证返回值是否为基本数据类型
   *
   * @param resultType 结果数据类型
   * @return true：基本数据类型，false：其他数据类型
   */
  public static boolean isBasicResultType(Class<?> resultType) {
    return BASIC_CLASS_TYPE.contains(resultType);
  }
}
