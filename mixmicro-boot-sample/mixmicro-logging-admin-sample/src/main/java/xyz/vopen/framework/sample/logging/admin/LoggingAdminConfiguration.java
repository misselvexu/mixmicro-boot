package xyz.vopen.framework.sample.logging.admin;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.vopen.framework.logging.admin.LoggingAdminFactoryBean;
import xyz.vopen.framework.logging.admin.repository.LoggingDataSourceRepository;

import javax.sql.DataSource;

@Configuration
public class LoggingAdminConfiguration {

  @Bean
  public LoggingAdminFactoryBean dataSourceLoggingAdminFactoryBean(DataSource dataSource) {
    LoggingAdminFactoryBean adminFactoryBean = new LoggingAdminFactoryBean();
    adminFactoryBean.setShowConsoleReportLog(true);
    adminFactoryBean.setFormatConsoleLogJson(true);
    adminFactoryBean.setLoggingRepository(new LoggingDataSourceRepository(dataSource));
    return adminFactoryBean;
  }
}
