package xyz.vopen.framework.sample.logging.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import xyz.vopen.framework.logging.spring.context.annotation.admin.EnableLoggingAdmin;

@SpringBootApplication
@EnableLoggingAdmin
public class LoggingAdminSampleApplication {

  static Logger logger = LoggerFactory.getLogger(LoggingAdminSampleApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(LoggingAdminSampleApplication.class, args);
    logger.info("{}服务启动成功.", "Logging Admin Sample");
  }
}
