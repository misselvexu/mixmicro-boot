package xyz.vopen.framework.boot.autoconfigure.quartz;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.quartz.JobStoreType;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceInitializationMode;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static xyz.vopen.framework.boot.autoconfigure.quartz.MixmicroBootQuartzProperties.MIXMICRO_BOOT_QUARTZ_PREFIX;

/**
 * Mixmicro Boot Quartz 配置类
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 *     <p>DateTime：2019-03-27 15:32
 */
@Data
@Configuration
@ConfigurationProperties(prefix = MIXMICRO_BOOT_QUARTZ_PREFIX)
public class MixmicroBootQuartzProperties {
  /** quartz 配置前缀 */
  public static final String MIXMICRO_BOOT_QUARTZ_PREFIX = "mixmicro.boot.quartz";
  /** Quartz job store type. */
  private JobStoreType jobStoreType = JobStoreType.MEMORY;

  /** Name of the scheduler. */
  private String schedulerName;

  /** Whether to automatically start the scheduler after initialization. */
  private boolean autoStartup = true;

  /**
   * Delay after which the scheduler is started once initialization completes. Setting this property
   * makes sense if no jobs should be run before the entire application has started up.
   */
  private Duration startupDelay = Duration.ofSeconds(0);

  /** Whether to wait for running jobs to complete on shutdown. */
  private boolean waitForJobsToCompleteOnShutdown = false;

  /** Whether configured jobs should overwrite existing job definitions. */
  private boolean overwriteExistingJobs = false;

  /** Additional Quartz Scheduler properties. */
  private final Map<String, String> properties = new HashMap<>();

  /** prop config all param have default value */
  private final MixmicroBootQuartzProperties.Prop prop = new MixmicroBootQuartzProperties.Prop();

  private final MixmicroBootQuartzProperties.Jdbc jdbc = new MixmicroBootQuartzProperties.Jdbc();

  /**
   * ApiBootQuartzProperties#properties
   *
   * <p>quartz properties config
   */
  @Getter
  @Setter
  public static class Prop {
    @PropKey("org.quartz.scheduler.instanceName")
    private String schedulerInstanceName = "jobScheduler";

    @PropKey("org.quartz.scheduler.instanceId")
    private String schedulerInstanceId = "AUTO";

    @PropKey("org.quartz.jobStore.class")
    private String jobStoreClass = "org.quartz.impl.jdbcjobstore.JobStoreTX";

    @PropKey("org.quartz.jobStore.driverDelegateClass")
    private String jobStoreDriverDelegateClass = "org.quartz.impl.jdbcjobstore.StdJDBCDelegate";

    @PropKey("org.quartz.jobStore.tablePrefix")
    private String jobStoreTablePrefix = "QRTZ_";

    @PropKey("org.quartz.jobStore.isClustered")
    private boolean jobStoreClustered = true;

    @PropKey("org.quartz.jobStore.clusterCheckinInterval")
    private long jobStoreClusterCheckinInterval = 20000;

    @PropKey("org.quartz.threadPool.threadsInheritContextClassLoaderOfInitializingThread")
    private boolean threadPoolThreadsInheritContextClassLoaderOfInitializingThread = true;
  }

  public static class Jdbc {

    private static final String DEFAULT_SCHEMA_LOCATION =
        "classpath:org/quartz/impl/" + "jdbcjobstore/tables_@@platform@@.sql";

    /** Path to the SQL file to use to initialize the database schema. */
    private String schema = DEFAULT_SCHEMA_LOCATION;

    /** Database schema initialization mode. */
    private DataSourceInitializationMode initializeSchema = DataSourceInitializationMode.EMBEDDED;

    /** Prefix for single-line comments in SQL initialization scripts. */
    private String commentPrefix = "--";

    public String getSchema() {
      return this.schema;
    }

    public void setSchema(String schema) {
      this.schema = schema;
    }

    public DataSourceInitializationMode getInitializeSchema() {
      return this.initializeSchema;
    }

    public void setInitializeSchema(DataSourceInitializationMode initializeSchema) {
      this.initializeSchema = initializeSchema;
    }

    public String getCommentPrefix() {
      return this.commentPrefix;
    }

    public void setCommentPrefix(String commentPrefix) {
      this.commentPrefix = commentPrefix;
    }
  }
}
