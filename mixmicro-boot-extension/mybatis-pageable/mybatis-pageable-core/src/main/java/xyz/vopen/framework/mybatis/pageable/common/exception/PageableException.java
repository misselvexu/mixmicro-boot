package xyz.vopen.framework.mybatis.pageable.common.exception;

/**
 * 分页异常定义
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version} - 2020-04-11.
 */
public class PageableException extends RuntimeException {

  /**
   * 格式化错误信息
   *
   * @param errorMsgEnum 错误消息枚举
   * @param params 参数列表
   */
  public PageableException(ErrorMsgEnum errorMsgEnum, String... params) {
    super(String.format(errorMsgEnum.getMessage(), params));
  }
}
