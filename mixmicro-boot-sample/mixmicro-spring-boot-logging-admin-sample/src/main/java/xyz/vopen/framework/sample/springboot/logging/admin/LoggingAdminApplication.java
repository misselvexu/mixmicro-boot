package xyz.vopen.framework.sample.springboot.logging.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import xyz.vopen.framework.logging.spring.context.annotation.admin.EnableLoggingAdmin;

@SpringBootApplication
@EnableLoggingAdmin
@EnableDiscoveryClient
public class LoggingAdminApplication {

  static Logger logger = LoggerFactory.getLogger(LoggingAdminApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(LoggingAdminApplication.class, args);
    logger.info("{}服务启动成功.", "Logging Admin");
  }
}
