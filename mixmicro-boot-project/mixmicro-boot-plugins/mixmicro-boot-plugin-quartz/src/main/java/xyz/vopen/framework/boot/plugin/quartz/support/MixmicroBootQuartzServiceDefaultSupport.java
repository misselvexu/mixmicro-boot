package xyz.vopen.framework.boot.plugin.quartz.support;

import xyz.vopen.framework.boot.plugin.quartz.MixmicroBootQuartzService;
import xyz.vopen.framework.boot.plugin.quartz.wrapper.MixmicroBootJobWrapper;
import xyz.vopen.framework.boot.plugin.quartz.wrapper.support.MixmicroBootCronJobWrapper;
import xyz.vopen.framework.boot.plugin.quartz.wrapper.support.MixmicroBootLoopJobWrapper;
import xyz.vopen.framework.boot.plugin.quartz.wrapper.support.MixmicroBootOnceJobWrapper;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

/** Default implement of {@link MixmicroBootQuartzService} */
public class MixmicroBootQuartzServiceDefaultSupport implements MixmicroBootQuartzService {
  /** logger instance */
  static Logger logger = LoggerFactory.getLogger(MixmicroBootQuartzServiceDefaultSupport.class);

  /** Quartz Scheduler Instance */
  private Scheduler scheduler;

  public MixmicroBootQuartzServiceDefaultSupport(Scheduler scheduler) {
    this.scheduler = scheduler;
  }

  /**
   * Get <code>{@link Scheduler}</code> object instance
   *
   * @return {@link Scheduler}
   * @throws SchedulerException Scheduler Exception
   */
  @Override
  public Scheduler getScheduler() throws SchedulerException {
    return this.scheduler;
  }

  /**
   * Create new job
   *
   * <p>Throws a <code>{@link SchedulerException}</code> if {@link MixmicroBootJobWrapper} is not passed
   * If you don't pass {@link MixmicroBootJobWrapper#getJobKey()} use the default uuid Create
   * corresponding job based on {@link MixmicroBootJobWrapper} object type
   *
   * @param jobWrapper {@link MixmicroBootJobWrapper}
   * @return {@link MixmicroBootJobWrapper#getJobKey()}
   * @see MixmicroBootOnceJobWrapper
   * @see MixmicroBootCronJobWrapper
   * @see MixmicroBootLoopJobWrapper
   */
  @Override
  public String newJob(MixmicroBootJobWrapper jobWrapper) {
    try {
      if (ObjectUtils.isEmpty(jobWrapper)) {
        throw new SchedulerException("When creating a new task, parameters must be passed.");
      }
      if (StringUtils.isEmpty(jobWrapper.getJobKey())) {
        jobWrapper.setJobKey(UUID.randomUUID().toString());
      }
      if (jobWrapper instanceof MixmicroBootCronJobWrapper) {
        newCronJob((MixmicroBootCronJobWrapper) jobWrapper);
      } else if (jobWrapper instanceof MixmicroBootLoopJobWrapper) {
        newLoopJob((MixmicroBootLoopJobWrapper) jobWrapper);
      } else if (jobWrapper instanceof MixmicroBootOnceJobWrapper) {
        newOnceJob((MixmicroBootOnceJobWrapper) jobWrapper);
      }
    } catch (Exception e) {
      logger.error("Create new job error.", e);
    }
    return jobWrapper.getJobKey();
  }

  /**
   * Delete a job
   *
   * <p>Pause the job from the scheduler based on the {@link TriggerKey} Removes the job with the
   * specified key from the <code>{@link Scheduler}</code> Delete job from {@link Scheduler}
   *
   * @param jobKey {@link MixmicroBootJobWrapper#getJobKey()}
   */
  @Override
  public void deleteJob(String jobKey) {
    try {
      TriggerKey triggerKey = TriggerKey.triggerKey(jobKey);
      scheduler.pauseTrigger(triggerKey);
      scheduler.unscheduleJob(triggerKey);
      scheduler.deleteJob(JobKey.jobKey(jobKey));
    } catch (Exception e) {
      logger.error("I encountered some problems while deleting the job", e);
    }
  }

  /**
   * Delete based on the given jobKey array
   *
   * <p>Iterate through each {@link MixmicroBootJobWrapper#getJobKey()} to delete the job
   *
   * @param jobKeys {@link MixmicroBootJobWrapper#getJobKey()} jobKey array
   */
  @Override
  public void deleteJobs(String... jobKeys) {
    if (!ObjectUtils.isEmpty(jobKeys)) {
      Arrays.stream(jobKeys).forEach(jobKey -> deleteJob(jobKey));
    }
  }

  /**
   * Delete based on the given jobKey collection
   *
   * <p>Iterate through each {@link MixmicroBootJobWrapper#getJobKey()} to delete the job
   *
   * @param jobKeys {@link MixmicroBootJobWrapper#getJobKey()} jobKey collection
   */
  @Override
  public void deleteJobs(Collection<String> jobKeys) {
    if (!ObjectUtils.isEmpty(jobKeys)) {
      jobKeys.forEach(jobKey -> deleteJob(jobKey));
    }
  }

  /**
   * Pause job based on jobKey
   *
   * @param jobKey {@link MixmicroBootJobWrapper#getJobKey()}
   */
  @Override
  public void pauseJob(String jobKey) {
    try {
      scheduler.pauseJob(JobKey.jobKey(jobKey));
    } catch (Exception e) {
      logger.error("I encountered some problems while pausing the job", e);
    }
  }

  /**
   * Pause based on the given jobKey array
   *
   * <p>Iterate through each {@link MixmicroBootJobWrapper#getJobKey()} to pause the job
   *
   * @param jobKeys {@link MixmicroBootJobWrapper#getJobKey()} jobKey array
   */
  @Override
  public void pauseJobs(String... jobKeys) {
    if (!ObjectUtils.isEmpty(jobKeys)) {
      Arrays.stream(jobKeys).forEach(jobKey -> pauseJob(jobKey));
    }
  }

  /**
   * Pause based on the given jobKey collection
   *
   * <p>Iterate through each {@link MixmicroBootJobWrapper#getJobKey()} to pause the job
   *
   * @param jobKeys {@link MixmicroBootJobWrapper#getJobKey()} jobKey collection
   */
  @Override
  public void pauseJobs(Collection<String> jobKeys) {
    if (!ObjectUtils.isEmpty(jobKeys)) {
      jobKeys.forEach(jobKey -> pauseJob(jobKey));
    }
  }

  /**
   * Resume a job
   *
   * @param jobKey {@link MixmicroBootJobWrapper#getJobKey()}
   */
  @Override
  public void resumeJob(String jobKey) {
    try {
      scheduler.resumeJob(JobKey.jobKey(jobKey));
    } catch (Exception e) {
      logger.error("I encountered some problems when resuming a suspended job", e);
    }
  }

  /**
   * Resume based on the given jobKey array
   *
   * <p>Iterate through each {@link MixmicroBootJobWrapper#getJobKey()} to resume the job
   *
   * @param jobKeys {@link MixmicroBootJobWrapper#getJobKey()} jobKey array
   */
  @Override
  public void resumeJobs(String... jobKeys) {
    if (!ObjectUtils.isEmpty(jobKeys)) {
      Arrays.stream(jobKeys).forEach(jobKey -> resumeJob(jobKey));
    }
  }

  /**
   * Resume based on the given jobKey collection
   *
   * <p>Iterate through each {@link MixmicroBootJobWrapper#getJobKey()} to resume the job
   *
   * @param jobKeys {@link MixmicroBootJobWrapper#getJobKey()} jobKey collection
   */
  @Override
  public void resumeJobs(Collection<String> jobKeys) {
    if (!ObjectUtils.isEmpty(jobKeys)) {
      jobKeys.forEach(jobKey -> resumeJob(jobKey));
    }
  }

  /**
   * Update job cron expression
   *
   * <p>This method works for <code>{@link MixmicroBootCronJobWrapper}</code> Determine if the cron
   * expression has changed and update
   *
   * @param jobKey {@link MixmicroBootJobWrapper#getJobKey()}
   * @param cron {@link MixmicroBootCronJobWrapper#getCron()}
   */
  @Override
  public void updateJobCron(String jobKey, String cron) {
    try {
      CronTrigger cronTrigger = (CronTrigger) getTrigger(jobKey);
      if (!cronTrigger.getCronExpression().equals(cron)) {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobKey);
        cronTrigger =
            TriggerBuilder.newTrigger()
                .withIdentity(triggerKey)
                .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                .startNow()
                .build();
        scheduler.rescheduleJob(triggerKey, cronTrigger);
      }
    } catch (Exception e) {
      logger.error("Update job cron error.", e);
    }
  }

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
  @Override
  public void updateJobStartTime(String jobKey, Date jobStartTime) {
    try {
      Trigger trigger = getTrigger(jobKey);
      trigger =
          TriggerBuilder.newTrigger()
              .withIdentity(trigger.getKey())
              .withSchedule(trigger.getScheduleBuilder())
              .startAt(jobStartTime)
              .build();
      scheduler.rescheduleJob(trigger.getKey(), trigger);
    } catch (Exception e) {
      logger.error("Update job start time error", e);
    }
  }

  /**
   * Start all jobs in the <code>{@link Scheduler}</code>
   *
   * @throws SchedulerException Scheduler Exception
   */
  @Override
  public void startAllJobs() throws SchedulerException {
    scheduler.start();
  }

  /**
   * Shutdown all jobs in the <code>{@link Scheduler}</code>
   *
   * @throws SchedulerException Scheduler Exception
   */
  @Override
  public void shutdownAllJobs() throws SchedulerException {
    if (!scheduler.isShutdown()) {
      scheduler.shutdown();
    }
  }

  /**
   * Get {@link Trigger} from {@link Scheduler}
   *
   * <p>Throws an {@link SchedulerException} if {@link Trigger} does not exist
   *
   * @param triggerKeyName {@link TriggerKey#getName()}
   * @return {@link Trigger}
   * @throws SchedulerException Scheduler Exception
   */
  protected Trigger getTrigger(String triggerKeyName) throws SchedulerException {
    Trigger trigger = scheduler.getTrigger(TriggerKey.triggerKey(triggerKeyName));
    if (ObjectUtils.isEmpty(trigger)) {
      throw new SchedulerException(
          "Unable to get " + triggerKeyName + " information for task trigger");
    }
    return trigger;
  }

  /**
   * Get {@link JobDetail} from {@link Scheduler}
   *
   * <p>Throws an {@link SchedulerException} if {@link JobDetail} does not exist
   *
   * @param jobKey {@link JobKey#getName()}
   * @return {@link JobDetail}
   * @throws SchedulerException Scheduler Exception
   */
  protected JobDetail getJobDetail(String jobKey) throws SchedulerException {
    JobDetail jobDetail = scheduler.getJobDetail(JobKey.jobKey(jobKey));
    if (ObjectUtils.isEmpty(jobDetail)) {
      throw new SchedulerException("Unable to get " + jobKey + " information for task jobDetail");
    }
    return jobDetail;
  }

  /**
   * Create a {@link JobKey}
   *
   * @param jobKey {@link JobKey#getName()}
   * @return {@link JobKey}
   * @throws SchedulerException Scheduler Exception
   */
  protected JobKey newJobKey(String jobKey) throws SchedulerException {
    return JobKey.jobKey(jobKey);
  }

  /**
   * Create a {@link JobDetail}
   *
   * <p>Use <code>{@link JobBuilder}</code> to create a new job Set {@link
   * MixmicroBootJobWrapper#getJobKey()} as the unique representation of the job If a parameter exists,
   * set the parameter to {@link JobDetail#getJobDataMap()}
   *
   * @param wrapper {@link MixmicroBootJobWrapper}
   * @return {@link JobDetail}
   */
  protected JobDetail newJobDetail(MixmicroBootJobWrapper wrapper) {
    JobDetail jobDetail =
        JobBuilder.newJob(wrapper.getJobClass()).withIdentity(wrapper.getJobKey()).build();
    if (!ObjectUtils.isEmpty(wrapper.getParam())) {
      jobDetail.getJobDataMap().putAll(wrapper.getParam().getAllParam());
    }
    return jobDetail;
  }

  /**
   * Create a {@link TriggerKey}
   *
   * @param triggerKey {@link TriggerKey#getName()}
   * @return {@link TriggerKey}
   * @throws SchedulerException Scheduler Exception
   */
  protected TriggerKey newTriggerKey(String triggerKey) throws SchedulerException {
    return TriggerKey.triggerKey(triggerKey);
  }

  /**
   * Create a cron job
   *
   * <p>Use cron expressions {@link MixmicroBootCronJobWrapper#getCron()} to create {@link Trigger}
   * Adding {@link JobDetail} to the {@link Scheduler}
   *
   * @param wrapper {@link MixmicroBootCronJobWrapper}
   * @return The first execute time
   * @throws SchedulerException Scheduler Exception
   * @see CronScheduleBuilder#cronSchedule(String)
   */
  protected Date newCronJob(MixmicroBootCronJobWrapper wrapper) throws SchedulerException {
    TriggerKey triggerKey = newTriggerKey(wrapper.getJobKey());
    JobDetail jobDetail = newJobDetail(wrapper);
    Trigger trigger =
        TriggerBuilder.newTrigger()
            .withIdentity(triggerKey)
            .withSchedule(CronScheduleBuilder.cronSchedule(wrapper.getCron()))
            .build();
    return scheduler.scheduleJob(jobDetail, trigger);
  }

  /**
   * Create a loop job
   *
   * <p>Total number of times = first time + repeat times for example: 6（total） = 1（first） +
   * 5（repeat）
   *
   * @param wrapper {@link MixmicroBootLoopJobWrapper}
   * @return The first execute time
   * @throws SchedulerException Scheduler Exception
   */
  protected Date newLoopJob(MixmicroBootLoopJobWrapper wrapper) throws SchedulerException {
    TriggerKey triggerKey = newTriggerKey(wrapper.getJobKey());
    JobDetail jobDetail = newJobDetail(wrapper);
    Trigger trigger =
        TriggerBuilder.newTrigger()
            .withIdentity(triggerKey)
            .withSchedule(
                SimpleScheduleBuilder.simpleSchedule()
                    .withRepeatCount(wrapper.getRepeatTimes())
                    .withIntervalInMilliseconds(wrapper.getLoopIntervalTime()))
            .startAt(wrapper.getStartAtTime())
            .build();
    return scheduler.scheduleJob(jobDetail, trigger);
  }

  /**
   * Create a once job
   *
   * <p>The job is executed only once
   *
   * @param wrapper {@link MixmicroBootOnceJobWrapper}
   * @return The first execute time
   * @throws SchedulerException Scheduler Exception
   */
  protected Date newOnceJob(MixmicroBootOnceJobWrapper wrapper) throws SchedulerException {
    TriggerKey triggerKey = newTriggerKey(wrapper.getJobKey());
    JobDetail jobDetail = newJobDetail(wrapper);
    Trigger trigger =
        TriggerBuilder.newTrigger()
            .withIdentity(triggerKey)
            .withSchedule(SimpleScheduleBuilder.simpleSchedule())
            .startAt(wrapper.getStartAtTime())
            .build();
    return scheduler.scheduleJob(jobDetail, trigger);
  }
}
