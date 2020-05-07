package xyz.vopen.framework.mybatis.pageable.common;

import xyz.vopen.framework.mybatis.pageable.Page;

/**
 * 分页请求工具类
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version} - 2020-04-11.
 */
public class PageableRequestHelper {
  /** 分页请求对象多线程threadLocal */
  private static final ThreadLocal<Page<?>> PAGEABLE_THREAD_LOCAL = new ThreadLocal();

  /**
   * 获取threadLocal内存放的page分页响应对象
   *
   * @return page 分页响应对应
   */
  public static Page<?> getPageLocal() {
    return PAGEABLE_THREAD_LOCAL.get();
  }

  /** 删除threadLocal内存放的page分页响应对象 */
  public static void removePageLocal() {
    PAGEABLE_THREAD_LOCAL.remove();
  }

  /**
   * 设置threadLocal内存放的page分页响应对象
   *
   * @param page 分页响应对象实例
   */
  public static void setPageLocal(Page<?> page) {
    PAGEABLE_THREAD_LOCAL.set(page);
  }
}
