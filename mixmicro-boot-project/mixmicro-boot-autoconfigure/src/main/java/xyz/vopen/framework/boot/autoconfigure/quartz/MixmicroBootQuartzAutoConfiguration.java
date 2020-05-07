package xyz.vopen.framework.boot.autoconfigure.quartz;

import xyz.vopen.framework.boot.plugin.quartz.MixmicroBootQuartzService;
import xyz.vopen.framework.boot.plugin.quartz.support.MixmicroBootQuartzServiceDefaultSupport;
import org.quartz.Calendar;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AbstractDependsOnBeanFactoryPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.quartz.JobStoreType;
import org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.boot.autoconfigure.transaction.PlatformTransactionManagerCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

/**
 * Mixmicro Boot Quartz 自动化配置类
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 *     <p>DateTime：2019-03-27 15:24
 */
@Configuration
@EnableConfigurationProperties(MixmicroBootQuartzProperties.class)
@ConditionalOnClass({
  Scheduler.class,
  SchedulerFactoryBean.class,
  PlatformTransactionManagerCustomizer.class,
  MixmicroBootQuartzService.class
})
@AutoConfigureAfter({DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@AutoConfigureBefore(QuartzAutoConfiguration.class)
public class MixmicroBootQuartzAutoConfiguration {

  private MixmicroBootQuartzProperties properties;
  private ObjectProvider<SchedulerFactoryBeanCustomizer> customizers;
  private JobDetail[] jobDetails;
  private Map<String, Calendar> calendars;
  private Trigger[] triggers;
  private ApplicationContext applicationContext;

  public MixmicroBootQuartzAutoConfiguration(
      MixmicroBootQuartzProperties properties,
      ObjectProvider<SchedulerFactoryBeanCustomizer> customizers,
      JobDetail[] jobDetails,
      Map<String, Calendar> calendars,
      Trigger[] triggers,
      ApplicationContext applicationContext) {
    this.properties = properties;
    this.customizers = customizers;
    this.jobDetails = jobDetails;
    this.calendars = calendars;
    this.triggers = triggers;
    this.applicationContext = applicationContext;
  }

  /**
   * 实例化Mixmicro Boot Quartz Service
   *
   * @param scheduler Quartz Scheduler Instance
   * @return ApiBootQuartzService
   */
  @Bean
  @ConditionalOnMissingBean(MixmicroBootQuartzService.class)
  MixmicroBootQuartzService apiBootQuartzService(Scheduler scheduler) {
    return new MixmicroBootQuartzServiceDefaultSupport(scheduler);
  }

  @Bean
  @ConditionalOnMissingBean
  public SchedulerFactoryBean quartzScheduler() {
    SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
    SpringBeanJobFactory jobFactory = new SpringBeanJobFactory();
    jobFactory.setApplicationContext(this.applicationContext);
    schedulerFactoryBean.setJobFactory(jobFactory);
    if (this.properties.getSchedulerName() != null) {
      schedulerFactoryBean.setSchedulerName(this.properties.getSchedulerName());
    }

    schedulerFactoryBean.setAutoStartup(this.properties.isAutoStartup());
    schedulerFactoryBean.setStartupDelay((int) this.properties.getStartupDelay().getSeconds());
    schedulerFactoryBean.setWaitForJobsToCompleteOnShutdown(
        this.properties.isWaitForJobsToCompleteOnShutdown());
    schedulerFactoryBean.setOverwriteExistingJobs(this.properties.isOverwriteExistingJobs());
    if (!this.properties.getProperties().isEmpty()) {
      schedulerFactoryBean.setQuartzProperties(this.asProperties(this.properties.getProperties()));
    }

    if (this.jobDetails != null && this.jobDetails.length > 0) {
      schedulerFactoryBean.setJobDetails(this.jobDetails);
    }

    if (this.calendars != null && !this.calendars.isEmpty()) {
      schedulerFactoryBean.setCalendars(this.calendars);
    }

    if (this.triggers != null && this.triggers.length > 0) {
      schedulerFactoryBean.setTriggers(this.triggers);
    }

    this.customize(schedulerFactoryBean);
    return schedulerFactoryBean;
  }

  private Properties asProperties(Map<String, String> source) {
    Properties properties = new Properties();
    properties.putAll(source);
    return properties;
  }

  private void customize(SchedulerFactoryBean schedulerFactoryBean) {
    this.customizers
        .orderedStream()
        .forEach((customizer) -> customizer.customize(schedulerFactoryBean));
  }

  @Configuration
  @ConditionalOnSingleCandidate(DataSource.class)
  protected static class JdbcStoreTypeConfiguration {
    protected JdbcStoreTypeConfiguration() {}

    /**
     * properties needed to initialize Jdbc mode
     *
     * @param properties Mixmicro Boot Quartz Properties
     * @return SchedulerFactoryBeanCustomizer
     */
    @Bean
    @Order(0)
    public SchedulerFactoryBeanCustomizer jobPropertiesCustomizer(
        MixmicroBootQuartzProperties properties) {
      return schedulerFactoryBean -> {
        // jdbc away
        if (properties.getJobStoreType() == JobStoreType.JDBC) {

          MixmicroBootQuartzProperties.Prop prop = properties.getProp();
          // get prop class declared fields
          Field[] fields = prop.getClass().getDeclaredFields();
          Arrays.stream(fields)
              .forEach(
                  field -> {
                    try {
                      field.setAccessible(true);
                      String value = String.valueOf(field.get(prop));
                      PropKey propKey = field.getDeclaredAnnotation(PropKey.class);

                      // put prop to quartz properties
                      properties.getProperties().put(propKey.value(), value);
                    } catch (IllegalAccessException e) {
                      e.printStackTrace();
                    }
                  });
        }
      };
    }

    @Bean
    @Order(1)
    public SchedulerFactoryBeanCustomizer jobDataSourceCustomizer(
        MixmicroBootQuartzProperties properties,
        DataSource dataSource,
        @QuartzDataSource ObjectProvider<DataSource> quartzDataSource,
        ObjectProvider<PlatformTransactionManager> transactionManager) {
      return (schedulerFactoryBean) -> {
        if (properties.getJobStoreType() == JobStoreType.JDBC) {
          DataSource dataSourceToUse = this.getDataSource(dataSource, quartzDataSource);
          schedulerFactoryBean.setDataSource(dataSourceToUse);
          PlatformTransactionManager txManager = transactionManager.getIfUnique();
          if (txManager != null) {
            schedulerFactoryBean.setTransactionManager(txManager);
          }
        }
      };
    }

    private DataSource getDataSource(
        DataSource dataSource, ObjectProvider<DataSource> quartzDataSource) {
      DataSource dataSourceIfAvailable = quartzDataSource.getIfAvailable();
      return dataSourceIfAvailable != null ? dataSourceIfAvailable : dataSource;
    }

    @Bean
    @ConditionalOnMissingBean
    public MixmicroBootQuartzDataSourceInitializer apiBootQuartzDataSourceInitializer(
        DataSource dataSource,
        @QuartzDataSource ObjectProvider<DataSource> quartzDataSource,
        ResourceLoader resourceLoader,
        MixmicroBootQuartzProperties properties) {
      DataSource dataSourceToUse = this.getDataSource(dataSource, quartzDataSource);
      return new MixmicroBootQuartzDataSourceInitializer(dataSourceToUse, resourceLoader, properties);
    }

    @Bean
    public static MixmicroBootQuartzAutoConfiguration.JdbcStoreTypeConfiguration
            .DataSourceInitializerSchedulerDependencyPostProcessor
        jobDataSourceInitializerSchedulerDependencyPostProcessor() {
      return new MixmicroBootQuartzAutoConfiguration.JdbcStoreTypeConfiguration
          .DataSourceInitializerSchedulerDependencyPostProcessor();
    }

    private static class DataSourceInitializerSchedulerDependencyPostProcessor
        extends AbstractDependsOnBeanFactoryPostProcessor {
      DataSourceInitializerSchedulerDependencyPostProcessor() {
        super(
            Scheduler.class,
            SchedulerFactoryBean.class,
            new String[] {"apiBootQuartzDataSourceInitializer"});
      }
    }
  }
}
