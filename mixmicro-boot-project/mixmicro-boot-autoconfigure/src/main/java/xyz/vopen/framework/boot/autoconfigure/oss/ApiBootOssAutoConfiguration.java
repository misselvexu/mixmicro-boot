package xyz.vopen.framework.boot.autoconfigure.oss;

import com.aliyun.oss.OSSClient;
import xyz.vopen.framework.boot.plugin.oss.MixmicroBootOssService;
import xyz.vopen.framework.boot.plugin.oss.progress.MixmicroBootObjectStorageProgress;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static xyz.vopen.framework.boot.autoconfigure.oss.ApiBootOssProperties.API_BOOT_OSS_PREFIX;

/**
 * Mixmicro Boot 整合阿里云Oss对象存储自动化配置
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 *     <p>DateTime：2019-03-21 11:12
 */
@Configuration
@EnableConfigurationProperties(ApiBootOssProperties.class)
@ConditionalOnClass(OSSClient.class)
@ConditionalOnProperty(
    prefix = API_BOOT_OSS_PREFIX,
    name = {"region", "access-key-id", "access-key-secret", "bucket-name"})
public class ApiBootOssAutoConfiguration {
  /** Mixmicro Boot Oss 属性配置 */
  private ApiBootOssProperties apiBootOssProperties;
  /** Mixmicro Boot Progress Provider */
  private MixmicroBootObjectStorageProgress mixmicroBootObjectStorageProgress;

  public ApiBootOssAutoConfiguration(
      ApiBootOssProperties apiBootOssProperties,
      ObjectProvider<MixmicroBootObjectStorageProgress> apiBootProgressProvider) {
    this.apiBootOssProperties = apiBootOssProperties;
    this.mixmicroBootObjectStorageProgress = apiBootProgressProvider.getIfAvailable();
  }

  /**
   * 实例化ApiBootOssService
   *
   * @return ApiBootOssService 实例
   */
  @Bean
  @ConditionalOnMissingBean
  MixmicroBootOssService apiBootOssService() {
    MixmicroBootOssService apiBootOssService =
        new MixmicroBootOssService(
            apiBootOssProperties.getRegion().getEndpoint(),
            apiBootOssProperties.getBucketName(),
            apiBootOssProperties.getAccessKeyId(),
            apiBootOssProperties.getAccessKeySecret(),
            apiBootOssProperties.getDomain());
    apiBootOssService.setMixmicroBootObjectStorageProgress(mixmicroBootObjectStorageProgress);
    return apiBootOssService;
  }
}
