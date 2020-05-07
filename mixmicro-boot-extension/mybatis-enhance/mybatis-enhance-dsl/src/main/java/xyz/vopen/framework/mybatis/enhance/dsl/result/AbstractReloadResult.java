package xyz.vopen.framework.mybatis.enhance.dsl.result;

/**
 * 重载结果集抽象类
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version}
 */
public abstract class AbstractReloadResult implements ReloadResult {
  /** 请求参数携带的返回类型 */
  protected Class<?> parameterResultType;

  public AbstractReloadResult(Class<?> parameterResultType) {
    this.parameterResultType = parameterResultType;
  }
}
