package xyz.vopen.framework.boot.autoconfigure.swagger;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import static xyz.vopen.framework.boot.autoconfigure.swagger.SwaggerProperties.MIXMICRO_BOOT_SWAGGER_PREFIX;

/**
 * swagger属性配置
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 *     <p>DateTime：2019-03-16 23:39
 */
@Data
@Configuration
@ConfigurationProperties(prefix = MIXMICRO_BOOT_SWAGGER_PREFIX)
public class SwaggerProperties {
  /** swagger配置前缀 */
  public static final String MIXMICRO_BOOT_SWAGGER_PREFIX = "mixmicro.boot.swagger";
  /** 是否开启文档，默认开启 */
  private boolean enable = true;
  /** swagger扫描本项目的base-package 扫描该package下的所有controller来生成文档 */
  private String basePackage;
  /** 文档标题 */
  private String title = "Integrate Swagger Plugin";
  /** 文档描述 */
  private String description = "Integrate Swagger Plugin";
  /** 文档版本号 */
  private String version = "2020.10.RELEASE";
  /** 文档版权 */
  private String license = "Mixmicro+ Boot";
  /** 文档版权路径 */
  private String licenseUrl = "http://vopen.xyz";
  /** 文档编写联系人信息 */
  private Contact contact = new Contact();
  /** 文档接口访问时认证信息 */
  private Authorization authorization = new Authorization();

  /** 文档编写联系人信息 */
  @Data
  public static class Contact {
    /** 联系人名称 */
    private String name = "VOPEN.XYZ";
    /** 联系人主页 */
    private String website = "http://vopen.xyz";
    /** 联系人邮箱地址 */
    private String email = "iskp.me@gmail.com";
  }

  /** 文档接口访问所需认证配置信息 */
  @Data
  public static class Authorization {
    /** 整合Oauth2后授权名称 */
    private String name = "Mixmicro Boot Security Oauth 认证头信息";
    /** 整合Oauth2后授权请求头Header内的key-name */
    private String keyName = "Authorization";
    /** 整合Oauth2后授权表达式 */
    private String authRegex = "^.*$";
  }
}
