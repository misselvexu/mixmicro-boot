package xyz.vopen.framework.sample.springboot.logging.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import xyz.vopen.framework.logging.spring.context.annotation.client.EnableLoggingClient;

@SpringBootApplication
@EnableLoggingClient
@EnableDiscoveryClient
public class LoggingApplication {
  /** logger instance */
  static Logger logger = LoggerFactory.getLogger(LoggingApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(LoggingApplication.class, args);
    logger.info("{}服务启动成功.", "ApiBoot Logging Sample");
  }

  /**
   * 测试使用RestTemplate来透传链路信息
   *
   * @return
   */
  @Bean
  @ConditionalOnMissingBean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
