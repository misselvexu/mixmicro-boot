package xyz.vopen.framework.boot.plugin.quartz.wrapper.support;

import lombok.Builder;
import xyz.vopen.framework.boot.plugin.quartz.wrapper.MixmicroBootJobParamWrapper;
import xyz.vopen.framework.boot.plugin.quartz.wrapper.MixmicroBootJobWrapper;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

/**
 * Once job wrapper class，extend {@link MixmicroBootJobWrapper}
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 */
public class MixmicroBootOnceJobWrapper extends MixmicroBootJobWrapper {
  /**
   * Constructor initialization {@link MixmicroBootOnceJobWrapper}
   *
   * @param jobKey {@link MixmicroBootJobWrapper#getJobKey()}
   * @param jobClass {@link MixmicroBootJobWrapper#getJobClass()}
   * @param startAtTime {@link MixmicroBootJobWrapper#getStartAtTime()}
   * @param param {@link MixmicroBootJobWrapper#getParam()}
   */
  @Builder(builderMethodName = "Context", buildMethodName = "wrapper")
  public MixmicroBootOnceJobWrapper(
      String jobKey,
      Class<? extends QuartzJobBean> jobClass,
      Date startAtTime,
      MixmicroBootJobParamWrapper param) {
    super(jobKey, jobClass, startAtTime, param);
  }
}
