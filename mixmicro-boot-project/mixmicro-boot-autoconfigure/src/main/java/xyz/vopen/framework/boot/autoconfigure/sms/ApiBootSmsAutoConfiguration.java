package xyz.vopen.framework.boot.autoconfigure.sms;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import xyz.vopen.framework.boot.plugin.sms.MixmicroBootAliYunSmsService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Mixmicro Boot 短信服务自动化配置
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 *     <p>DateTime：2019-03-22 10:53
 */
@Configuration
@ConditionalOnClass(SendSmsRequest.class)
@ConditionalOnProperty(
    prefix = ApiBootSmsProperties.API_BOOT_SMS_PREFIX,
    name = {"access-key-id", "access-key-secret", "sign-name"})
@EnableConfigurationProperties(ApiBootSmsProperties.class)
public class ApiBootSmsAutoConfiguration {
  /** Mixmicro Boot Sms 属性配置类 */
  private ApiBootSmsProperties apiBootSmsProperties;

  public ApiBootSmsAutoConfiguration(ApiBootSmsProperties apiBootSmsProperties) {
    this.apiBootSmsProperties = apiBootSmsProperties;
  }

  /**
   * 实例化Mixmicro Boot Sms Service
   *
   * @return Mixmicro Boot Sms Service
   */
  @Bean
  @ConditionalOnMissingBean
  MixmicroBootAliYunSmsService apiBootSmsService() {
    return new MixmicroBootAliYunSmsService(
        apiBootSmsProperties.getAccessKeyId(),
        apiBootSmsProperties.getAccessKeySecret(),
        apiBootSmsProperties.getSignName(),
        apiBootSmsProperties.getProfile(),
        apiBootSmsProperties.getConnectionTimeout(),
        apiBootSmsProperties.getReadTimeout());
  }
}
