package xyz.vopen.framework.boot.autoconfigure.oss;

import lombok.Data;
import xyz.vopen.framework.boot.plugin.oss.OssRegion;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import static xyz.vopen.framework.boot.autoconfigure.oss.ApiBootOssProperties.API_BOOT_OSS_PREFIX;

/**
 * Mixmicro Boot Oss 属性配置
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 *     <p>DateTime：2019-03-21 11:13
 */
@Configuration
@ConfigurationProperties(prefix = API_BOOT_OSS_PREFIX)
@Data
public class ApiBootOssProperties {
  /** oss 配置前缀 */
  public static final String API_BOOT_OSS_PREFIX = "mixmicro.boot.oss";
  /** 存储地域 */
  private OssRegion region;
  /** 授权秘钥Id */
  private String accessKeyId;
  /** 授权秘钥Secret */
  private String accessKeySecret;
  /** 存储空间名称 */
  private String bucketName;
  /** Oss存储空间自定义域名 */
  private String domain;
}
