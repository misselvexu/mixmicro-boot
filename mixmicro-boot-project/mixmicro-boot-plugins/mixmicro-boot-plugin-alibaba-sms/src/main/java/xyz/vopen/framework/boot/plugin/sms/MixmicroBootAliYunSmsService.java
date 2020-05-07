package xyz.vopen.framework.boot.plugin.sms;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import lombok.AllArgsConstructor;
import xyz.vopen.framework.boot.common.exception.MixmicroBootException;
import xyz.vopen.framework.boot.plugin.sms.request.MixmicroBootSmsRequest;
import xyz.vopen.framework.boot.plugin.sms.response.MixmicroBootSmsResponse;

import javax.annotation.PostConstruct;

/**
 * Mixmicro Boot provides a unified service interface after integrating Alibaba Cloud's SMS service When
 * used, directly inject this class
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 */
@AllArgsConstructor
public class MixmicroBootAliYunSmsService implements MixmicroBootSmsService {

  /** Alibaba Cloud SMS Product Name */
  private static final String ALIYUN_PRODUCT = "Dysmsapi";
  /** Alibaba Cloud International SMS Product Domain Name */
  private static final String ALIYUN_PRODUCT_DOMAIN = "dysmsapi.aliyuncs.com";
  /** Return code after sending successfully */
  private static final String SUCCESS_RESULT = "OK";
  /** RAM account Access Key ID */
  private String accessKeyId;
  /** RAM Account Access Key Secret */
  private String accessKeySecret;
  /** SMS signature */
  private String signName;
  /** SMS profile */
  private String profile;
  /** SMS send connection timeout */
  private long connectionTimeout;
  /** SMS message connection timeout */
  private long readTimeout;

  /**
   * setting system properties default value set send sms connection timeout {@link
   * #connectionTimeout} set send sms read timeout {@link #readTimeout}
   */
  @PostConstruct
  public void _init() {
    System.setProperty(
        "sun.net.client.defaultConnectTimeout", String.valueOf(this.connectionTimeout));
    System.setProperty("sun.net.client.defaultReadTimeout", String.valueOf(this.readTimeout));
  }

  /**
   * invoke send SMS
   *
   * @param request {@link MixmicroBootSmsRequest}
   * @return {@link MixmicroBootSmsResponse}
   * @throws MixmicroBootException Mixmicro Boot Exception
   */
  @Override
  public MixmicroBootSmsResponse send(MixmicroBootSmsRequest request) throws MixmicroBootException {
    try {
      IClientProfile profile =
          DefaultProfile.getProfile(this.profile, this.accessKeyId, this.accessKeySecret);
      DefaultProfile.addEndpoint(this.profile, ALIYUN_PRODUCT, ALIYUN_PRODUCT_DOMAIN);
      IAcsClient acsClient = new DefaultAcsClient(profile);

      SendSmsRequest sendSmsRequest = new SendSmsRequest();
      sendSmsRequest.setPhoneNumbers(request.getPhone());
      sendSmsRequest.setSignName(this.signName);
      sendSmsRequest.setTemplateCode(request.getTemplateCode());
      sendSmsRequest.setTemplateParam(request.getParam().getParamJson());

      SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(sendSmsRequest);

      return MixmicroBootSmsResponse.builder()
          .success(SUCCESS_RESULT.equals(sendSmsResponse.getCode()))
          .build();

    } catch (Exception e) {
      throw new MixmicroBootException("invoke send SMS have Exception：" + e.getMessage());
    }
  }
}
