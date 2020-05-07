package xyz.vopen.framework.mybatis.pageable.config;

import org.apache.ibatis.plugin.Interceptor;

import java.util.List;

/**
 * 自动分页配置抽象类 由于支持jdk1.8以下的版本，所以这里不能采用default新特性
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version} - 2020-04-11.
 */
public class AbstractPageableConfigurer implements PageableConfigurer {
  @Override
  public List<Interceptor> prePlugins() {
    return null;
  }

  @Override
  public List<Interceptor> postPlugins() {
    return null;
  }
}
