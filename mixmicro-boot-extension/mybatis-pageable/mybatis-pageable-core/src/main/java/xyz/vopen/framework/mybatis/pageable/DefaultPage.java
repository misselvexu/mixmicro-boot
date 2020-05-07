package xyz.vopen.framework.mybatis.pageable;

import xyz.vopen.framework.mybatis.pageable.request.Pageable;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 默认的分页数据对象
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version} - 2020-04-11.
 */
@NoArgsConstructor
public class DefaultPage<T> implements Page<T> {
  /** 分页后的数据列表 */
  private List<T> data;
  /** 分页请求对象 */
  private Pageable pageable;
  /** 总条数 */
  private long totalElements;

  public DefaultPage(Pageable pageable) {
    this.pageable = pageable;
  }

  /**
   * 设置分页数据
   *
   * @param data 分页数据内容
   */
  public void setData(List<T> data) {
    this.data = data;
  }

  /**
   * 设置总条数
   *
   * @param totalElements 数据总数
   */
  public void setTotalElements(long totalElements) {
    this.totalElements = totalElements;
  }

  /**
   * 获取分页后的数据列表
   *
   * @return 分页数据列表
   */
  @Override
  public List<T> getData() {
    return this.data;
  }

  /**
   * 获取总页数
   *
   * @return 查询总页数
   */
  @Override
  public long getTotalPages() {
    // 总页数
    long totalPage = getTotalElements() / getPageSize();
    // 如果取余不为0时，+1页
    return getTotalElements() % getPageSize() == 0 ? totalPage : totalPage + 1;
  }

  /**
   * 获取总条数
   *
   * @return 查询总条数
   */
  @Override
  public long getTotalElements() {
    return this.totalElements;
  }

  /**
   * 获取当前分页页码
   *
   * @return 当前页码
   */
  @Override
  public int getPageIndex() {
    return this.pageable.getPageIndex();
  }

  /**
   * 获取每页条数
   *
   * @return 每页条数
   */
  @Override
  public int getPageSize() {
    return this.pageable.getPageSize();
  }

  /**
   * 获取开始位置
   *
   * @return 分页开始位置
   */
  @Override
  public long getOffset() {
    return this.pageable.getOffset();
  }

  /**
   * 获取当前页结束位置
   *
   * @return 分页结束位置
   */
  @Override
  public long getEndRow() {
    return this.pageable.getEndRow();
  }

  /**
   * 是否存在上一页
   *
   * @return true：存在上一页，false：不存在
   */
  @Override
  public boolean hasPrevious() {
    return this.pageable.getPageIndex() > 1;
  }

  /**
   * 是否为首页
   *
   * @return true：首页，false：非首页
   */
  @Override
  public boolean isFirst() {
    return this.pageable.getPageIndex() == 1;
  }

  /**
   * 是否存在下一页
   *
   * @return true：存在，false：不存在
   */
  @Override
  public boolean hasNext() {
    return getTotalPages() > getPageIndex();
  }

  /**
   * 是否为末页
   *
   * @return true：为末页，false：非末页
   */
  @Override
  public boolean isLast() {
    return getTotalPages() == getPageIndex();
  }
}
