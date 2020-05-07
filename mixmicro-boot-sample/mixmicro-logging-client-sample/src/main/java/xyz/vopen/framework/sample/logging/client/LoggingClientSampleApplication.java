package xyz.vopen.framework.sample.logging.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import xyz.vopen.framework.logging.spring.context.annotation.client.EnableLoggingClient;

@SpringBootApplication
@EnableLoggingClient
public class LoggingClientSampleApplication {

  static Logger logger = LoggerFactory.getLogger(LoggingClientSampleApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(LoggingClientSampleApplication.class, args);
    logger.info("{}服务启动成功.", "Logging Client Sample");
  }
}
