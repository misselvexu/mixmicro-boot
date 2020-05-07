package xyz.vopen.framework.boot.plugin.quartz.wrapper.support;

import lombok.Builder;
import xyz.vopen.framework.boot.plugin.quartz.wrapper.MixmicroBootJobParamWrapper;
import xyz.vopen.framework.boot.plugin.quartz.wrapper.MixmicroBootJobWrapper;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Cron job wrapper class，extend {@link MixmicroBootJobWrapper}
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 */
public class MixmicroBootCronJobWrapper extends MixmicroBootJobWrapper {
  /** Job execution cron expression */
  private String cron;

  /**
   * Constructor initialization {@link MixmicroBootCronJobWrapper}
   *
   * @param jobKey {@link MixmicroBootJobWrapper#getJobKey()}
   * @param jobClass {@link MixmicroBootJobWrapper#getJobClass()}
   * @param cron {@link MixmicroBootCronJobWrapper#getCron()}
   * @param param {@link MixmicroBootJobWrapper#getParam()}
   */
  @Builder(builderMethodName = "Context", buildMethodName = "wrapper")
  public MixmicroBootCronJobWrapper(
      String jobKey,
      Class<? extends QuartzJobBean> jobClass,
      String cron,
      MixmicroBootJobParamWrapper param) {
    super(jobKey, jobClass, null, param);
    this.cron = cron;
  }

  public String getCron() {
    return cron;
  }
}
