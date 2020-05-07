package xyz.vopen.framework.boot.plugin.quartz.wrapper.support;

import lombok.Builder;
import xyz.vopen.framework.boot.plugin.quartz.wrapper.MixmicroBootJobParamWrapper;
import xyz.vopen.framework.boot.plugin.quartz.wrapper.MixmicroBootJobWrapper;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

/**
 * Loop job wrapper class，extend {@link MixmicroBootJobWrapper}
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 */
public class MixmicroBootLoopJobWrapper extends MixmicroBootJobWrapper {
  /** Job execution repeat times */
  private int repeatTimes;
  /**
   * Interval between repeated Job
   */
  private Long loopIntervalTime;

  /**
   * Constructor initialization {@link MixmicroBootLoopJobWrapper}
   *
   * @param jobKey {@link MixmicroBootJobWrapper#getJobKey()}
   * @param jobClass {@link MixmicroBootJobWrapper#getJobClass()}
   * @param repeatTimes {@link MixmicroBootLoopJobWrapper#getRepeatTimes()}
   * @param loopIntervalTime {@link MixmicroBootLoopJobWrapper#getLoopIntervalTime()}
   * @param startAtTime {@link MixmicroBootJobWrapper#getStartAtTime()}
   * @param param {@link MixmicroBootJobWrapper#getParam()}
   */
  @Builder(builderMethodName = "Context", buildMethodName = "wrapper")
  public MixmicroBootLoopJobWrapper(
      String jobKey,
      Class<? extends QuartzJobBean> jobClass,
      int repeatTimes,
      Long loopIntervalTime,
      Date startAtTime,
      MixmicroBootJobParamWrapper param) {
    super(jobKey, jobClass, startAtTime, param);
    this.repeatTimes = repeatTimes;
    this.loopIntervalTime = loopIntervalTime;
  }

  /**
   * Get interval time in milliseconds
   *
   * @return interval time
   */
  public Long getLoopIntervalTime() {
    return loopIntervalTime <= 0 ? 1000L : loopIntervalTime;
  }

  public int getRepeatTimes() {
    return repeatTimes;
  }
}
