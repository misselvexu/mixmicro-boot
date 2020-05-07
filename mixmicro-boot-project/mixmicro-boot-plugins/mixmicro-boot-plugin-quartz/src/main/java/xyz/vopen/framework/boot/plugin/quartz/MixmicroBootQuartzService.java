package xyz.vopen.framework.boot.plugin.quartz;

import xyz.vopen.framework.boot.plugin.quartz.wrapper.MixmicroBootJobWrapper;
import xyz.vopen.framework.boot.plugin.quartz.wrapper.support.MixmicroBootCronJobWrapper;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import xyz.vopen.framework.boot.plugin.quartz.wrapper.support.MixmicroBootLoopJobWrapper;
import xyz.vopen.framework.boot.plugin.quartz.wrapper.support.MixmicroBootOnceJobWrapper;

import java.util.Collection;
import java.util.Date;

/**
 * Mixmicro Boot integrated Quartz operation task interface method definition Add integration tasksAdd,
 * pause, modify, delete, etc.
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @see Scheduler
 */
public interface MixmicroBootQuartzService {
  /**
   * Get Quartz scheduler object instance
   *
   * @return {@link Scheduler}
   * @throws SchedulerException Scheduler Exception
   */
  Scheduler getScheduler() throws SchedulerException;

  /**
   * Create a new job
   *
   * @param jobWrapper {@link MixmicroBootJobWrapper}
   * @return {@link MixmicroBootJobWrapper#getJobKey()}
   * @see MixmicroBootOnceJobWrapper
   * @see MixmicroBootLoopJobWrapper
   * @see MixmicroBootCronJobWrapper
   */
  String newJob(MixmicroBootJobWrapper jobWrapper);

  /**
   * Delete a job pause the job first then remove the job from the scheduler finally delete the job
   *
   * @param jobKey {@link MixmicroBootJobWrapper#getJobKey()}
   */
  void deleteJob(String jobKey);

  /**
   * Delete based on the given jobKey array
   *
   * @param jobKeys {@link MixmicroBootJobWrapper#getJobKey()} job key array
   */
  void deleteJobs(String... jobKeys);

  /**
   * Delete based on the given jobKey collection
   *
   * @param jobKeys {@link MixmicroBootJobWrapper#getJobKey()} job key collection
   */
  void deleteJobs(Collection<String> jobKeys);

  /**
   * Pause job based on jobKey
   *
   * @param jobKey {@link MixmicroBootJobWrapper#getJobKey()}
   */
  void pauseJob(String jobKey);

  /**
   * Pause based on the given jobKey array
   *
   * @param jobKeys {@link MixmicroBootJobWrapper#getJobKey()} jobKey array
   */
  void pauseJobs(String... jobKeys);

  /**
   * Pause based on the given jobKey collection
   *
   * @param jobKeys {@link MixmicroBootJobWrapper#getJobKey()} jobKey collection
   */
  void pauseJobs(Collection<String> jobKeys);

  /**
   * Resume a job
   *
   * @param jobKey {@link MixmicroBootJobWrapper#getJobKey()}
   */
  void resumeJob(String jobKey);

  /**
   * Resume based on the given jobKey array
   *
   * @param jobKeys {@link MixmicroBootJobWrapper#getJobKey()} jobKey array
   */
  void resumeJobs(String... jobKeys);

  /**
   * Resume based on the given jobKey collection
   *
   * @param jobKeys {@link MixmicroBootJobWrapper#getJobKey()} jobKey collection
   */
  void resumeJobs(Collection<String> jobKeys);

  /**
   * Update job cron expression This method works for <code>{@link MixmicroBootCronJobWrapper}</code>
   *
   * @param jobKey {@link MixmicroBootJobWrapper#getJobKey()}
   * @param cron {@link MixmicroBootCronJobWrapper#getCron()}
   */
  void updateJobCron(String jobKey, String cron);

  /**
   * Update job start time
   *
   * <p>This method works for {@link
   * MixmicroBootOnceJobWrapper} or {@link
   * MixmicroBootLoopJobWrapper}
   *
   * @param jobKey {@link MixmicroBootJobWrapper#getJobKey()}
   * @param jobStartTime {@link MixmicroBootJobWrapper#getStartAtTime()} job startAtTime
   */
  void updateJobStartTime(String jobKey, Date jobStartTime);

  /**
   * Start all jobs in the <code>{@link Scheduler}</code>
   *
   * @throws SchedulerException Scheduler Exception
   */
  void startAllJobs() throws SchedulerException;

  /**
   * Shutdown all jobs in the <code>{@link Scheduler}</code>
   *
   * @throws SchedulerException Scheduler Exception
   */
  void shutdownAllJobs() throws SchedulerException;
}
