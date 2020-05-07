package xyz.vopen.framework.mybatis.pageable.request;

import xyz.vopen.framework.mybatis.pageable.DefaultPage;
import xyz.vopen.framework.mybatis.pageable.Page;
import xyz.vopen.framework.mybatis.pageable.common.PageableRequestHelper;
import xyz.vopen.framework.mybatis.pageable.common.exception.ErrorMsgEnum;
import xyz.vopen.framework.mybatis.pageable.common.exception.PageableException;

/**
 * 分页请求对象抽象类
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version} - 2020-04-11.
 */
public abstract class AbstractPageRequest implements Pageable {

  /** 分页当前页码 */
  protected int pageIndex;
  /** 分页每页条数 */
  protected int pageSize;

  /**
   * 抽象分页请求对象构造函数
   *
   * @param pageIndex 当前分页页码
   * @param pageSize 当前分页每页条数
   */
  public AbstractPageRequest(int pageIndex, int pageSize) {
    if (pageIndex < 1) {
      throw new PageableException(ErrorMsgEnum.PAGE_INDEX_FAILED);
    } else if (pageSize < 1) {
      throw new PageableException(ErrorMsgEnum.PAGE_SIZE_FAILED);
    }
    this.pageIndex = pageIndex;
    this.pageSize = pageSize;

    // 设置分页响应threadLocal
    setPageThreadLocal();
  }

  /** 设置分页响应对应到threadLocal */
  private void setPageThreadLocal() {
    // 构造分页响应对象
    Page page = new DefaultPage(this);
    // 写入到threadLocal
    PageableRequestHelper.setPageLocal(page);
  }

  /**
   * 获取当前分页页码
   *
   * @return 当前分页页码
   */
  @Override
  public int getPageIndex() {
    return this.pageIndex;
  }

  /**
   * 获取当前分页每页条数
   *
   * @return 每页条数
   */
  @Override
  public int getPageSize() {
    return this.pageSize;
  }

  /**
   * 获取分页开始位置
   *
   * @return 分页开始位置
   */
  @Override
  public long getOffset() {
    return (long) (this.pageIndex - 1) * (long) this.pageSize;
  }

  /**
   * 获取分页结束位置
   *
   * @return 分页结束位置
   */
  @Override
  public long getEndRow() {
    return this.pageIndex * this.pageSize;
  }
}
