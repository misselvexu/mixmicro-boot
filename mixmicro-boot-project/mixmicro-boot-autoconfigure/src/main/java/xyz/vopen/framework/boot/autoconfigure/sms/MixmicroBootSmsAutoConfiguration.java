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
    prefix = MixmicroBootSmsProperties.MIXMICRO_BOOT_SMS_PREFIX,
    name = {"access-key-id", "access-key-secret", "sign-name"})
@EnableConfigurationProperties(MixmicroBootSmsProperties.class)
public class MixmicroBootSmsAutoConfiguration {
  /** Mixmicro Boot Sms 属性配置类 */
  private MixmicroBootSmsProperties mixmicroBootSmsProperties;

  public MixmicroBootSmsAutoConfiguration(MixmicroBootSmsProperties mixmicroBootSmsProperties) {
    this.mixmicroBootSmsProperties = mixmicroBootSmsProperties;
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
        mixmicroBootSmsProperties.getAccessKeyId(),
        mixmicroBootSmsProperties.getAccessKeySecret(),
        mixmicroBootSmsProperties.getSignName(),
        mixmicroBootSmsProperties.getProfile(),
        mixmicroBootSmsProperties.getConnectionTimeout(),
        mixmicroBootSmsProperties.getReadTimeout());
  }
}
