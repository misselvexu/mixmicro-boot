/*
 * Copyright (c) 2018 VOPEN.XYZ
 *
 *      Licensed under the Apache License, Version 2.0 (the "License");
 *      you may not use this file except in compliance with the License.
 *      You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *      Unless required by applicable law or agreed to in writing, software
 *      distributed under the License is distributed on an "AS IS" BASIS,
 *      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *      See the License for the specific language governing permissions and
 *      limitations under the License.
 *
 */

package xyz.vopen.framework.boot.autoconfigure.mail;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import xyz.vopen.framework.boot.plugin.mail.MixmicroBootAliYunMailService;
import xyz.vopen.framework.boot.plugin.mail.MixmicroBootMailService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Mixmicro Boot Mail Auto Configuration
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 *     <p>DateTime：2019-06-19 18:41
 */
@Configuration
@ConditionalOnClass(MixmicroBootAliYunMailService.class)
@ConditionalOnProperty(
    prefix = MixmicroBootMailProperties.MIXMICRO_BOOT_MAIL_PREFIX,
    name = {"access-key", "access-secret", "account-name", "region"})
@EnableConfigurationProperties(MixmicroBootMailProperties.class)
public class MixmicroBootMailAutoConfiguration {
  /** Mixmicro Boot Mail Properties */
  private MixmicroBootMailProperties mixmicroBootMailProperties;

  public MixmicroBootMailAutoConfiguration(MixmicroBootMailProperties mixmicroBootMailProperties) {
    this.mixmicroBootMailProperties = mixmicroBootMailProperties;
  }

  /**
   * Mixmicro Boot Mail Service AliYun Support
   *
   * @param acsClient AcsClient
   * @return ApiBootMailService Support Instance
   */
  @Bean
  @ConditionalOnMissingBean
  public MixmicroBootMailService apiBootMailService(IAcsClient acsClient) {
    return new MixmicroBootAliYunMailService(
        acsClient,
        mixmicroBootMailProperties.getAccountName(),
        mixmicroBootMailProperties.isReplyToAddress(),
        mixmicroBootMailProperties.getAddressType(),
        mixmicroBootMailProperties.getFromAlias(),
        mixmicroBootMailProperties.getTagName());
  }

  /**
   * Instance AliYun Acs Client
   *
   * @return IAcsClient Support Instance
   * @see DefaultProfile
   * @see DefaultAcsClient
   */
  @Bean
  @ConditionalOnMissingBean
  public IAcsClient acsClient() {
    IClientProfile profile =
        DefaultProfile.getProfile(
            mixmicroBootMailProperties.getRegion().getValue(),
            mixmicroBootMailProperties.getAccessKey(),
            mixmicroBootMailProperties.getAccessSecret());
    return new DefaultAcsClient(profile);
  }
}
