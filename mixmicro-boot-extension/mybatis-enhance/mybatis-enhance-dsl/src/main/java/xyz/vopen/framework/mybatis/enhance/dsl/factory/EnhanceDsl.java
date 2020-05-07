package xyz.vopen.framework.mybatis.enhance.dsl.factory;

import xyz.vopen.framework.mybatis.enhance.dsl.delete.Deleteable;
import xyz.vopen.framework.mybatis.enhance.dsl.serach.Searchable;
import xyz.vopen.framework.mybatis.enhance.dsl.update.Updateable;
import xyz.vopen.framework.mybatis.enhance.exception.EnhanceFrameworkException;

import java.util.List;

/**
 * 该类提供动态查询方法 针对查询列表查询单条提供不同的方法
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version}
 */
public interface EnhanceDsl {
  /**
   * 动态查询列表
   *
   * @param searchable 查询对象
   * @param <T> 返回类型【默认使用从Mapper抽取的ModuleClass】
   * @return 查询结果列表
   * @throws EnhanceFrameworkException 框架异常
   */
  <T> List<T> select(Searchable searchable) throws EnhanceFrameworkException;

  /**
   * 动态查询单条信息
   *
   * @param searchable 查询对象
   * @param <T> 返回类型【默认使用从Mapper抽取的ModuleClass】
   * @return 查询结果对象
   * @throws EnhanceFrameworkException 框架异常
   */
  <T> T selectOne(Searchable searchable) throws EnhanceFrameworkException;

  /**
   * 动态删除
   *
   * @param deleteable 删除对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  void delete(Deleteable deleteable) throws EnhanceFrameworkException;

  /**
   * 动态更新
   *
   * @param updateable 更新对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  void update(Updateable updateable) throws EnhanceFrameworkException;
}
