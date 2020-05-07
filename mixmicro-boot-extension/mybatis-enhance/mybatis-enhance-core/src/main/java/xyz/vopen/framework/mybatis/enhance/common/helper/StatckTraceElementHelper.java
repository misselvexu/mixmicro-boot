package xyz.vopen.framework.mybatis.enhance.common.helper;

/**
 * 堆栈跟踪器工具类
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version}
 */
public class StatckTraceElementHelper {
  /**
   * 获取当前线程指定索引的堆栈追踪器信息
   *
   * @param index 索引
   * @return 堆栈跟踪元素
   */
  public static StackTraceElement getCurrentStackElement(int index) {
    // 获取当前线程的堆栈跟踪器信息
    StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
    return stackTraceElements[index];
  }

  /**
   * 获取DSL动态查询的堆栈跟踪元素信息 索引：7，为动态查询时，创建动态查询的类以及类方法的堆栈信息
   *
   * @return 堆栈跟踪元素
   */
  public static StackTraceElement getDslStackElement() {
    return getCurrentStackElement(7);
  }
}
