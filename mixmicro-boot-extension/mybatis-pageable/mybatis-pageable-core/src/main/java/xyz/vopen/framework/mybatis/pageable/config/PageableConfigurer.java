package xyz.vopen.framework.mybatis.pageable.config;

import org.apache.ibatis.plugin.Interceptor;

import java.util.List;

/**
 * mybatis-pageable配置接口 实现该接口可以完成部分的内置配置
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version} - 2020-04-11.
 */
public interface PageableConfigurer {
  /**
   * 获取mybatis-pageable执行前执行的Plugin插件列表
   *
   * @return 插件列表
   */
  List<Interceptor> prePlugins();

  /**
   * 获取mybatis-pageable执行后执行的Plugin插件列表
   *
   * @return 插件列表
   */
  List<Interceptor> postPlugins();
}
