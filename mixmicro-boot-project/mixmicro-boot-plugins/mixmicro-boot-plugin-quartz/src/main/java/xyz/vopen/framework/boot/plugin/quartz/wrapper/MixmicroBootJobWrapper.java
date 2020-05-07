package xyz.vopen.framework.boot.plugin.quartz.wrapper;

import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.io.Serializable;
import java.util.Date;

/**
 * Create a parameter wrapper class for a job
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 */
@AllArgsConstructor
public class MixmicroBootJobWrapper implements Serializable {
  /** Job unique key Can operate jobs based on jobKey */
  @Setter private String jobKey;
  /** Job execution class */
  private Class<? extends QuartzJobBean> jobClass;
  /** Start execution time */
  private Date startAtTime;
  /** Job execution params */
  private MixmicroBootJobParamWrapper param;

  /**
   * Get job start time If {@link #startAtTime} is empty, the current time is used
   *
   * @return Job start time
   */
  public Date getStartAtTime() {
    return startAtTime == null ? new Date() : startAtTime;
  }

  public String getJobKey() {
    return jobKey;
  }

  public Class<? extends QuartzJobBean> getJobClass() {
    return jobClass;
  }

  public MixmicroBootJobParamWrapper getParam() {
    return param;
  }
}
