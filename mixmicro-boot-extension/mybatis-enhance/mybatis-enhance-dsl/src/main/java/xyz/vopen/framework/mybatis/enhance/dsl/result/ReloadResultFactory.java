package xyz.vopen.framework.mybatis.enhance.dsl.result;

import xyz.vopen.framework.mybatis.enhance.dsl.result.support.BasicReloadResult;
import xyz.vopen.framework.mybatis.enhance.dsl.result.support.EntityReloadResult;

/**
 * 数据返回重载工厂类
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version}
 */
public class ReloadResultFactory {

  private ReloadResultFactory() {}

  /**
   * 获取数据重载返回接口实例 根据传递参数自动获取对应的返回值
   *
   * @param parameterResultType 请求参数携带的返回数据类型
   * @return 重载返回值实例
   */
  public static ReloadResult newInstance(Class<?> parameterResultType) {
    // 是否为基本数据类型
    boolean isBasic = ResultReloadUtils.isBasicResultType(parameterResultType);
    /*
     * 为基本数据类型时重载基本数据类型返回值
     * 为实体数据类型时重载实体数据类型返回值
     */
    return isBasic
        ? new BasicReloadResult(parameterResultType)
        : new EntityReloadResult(parameterResultType);
  }
}
