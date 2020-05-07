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

package xyz.vopen.framework.boot.plugin.mail;

import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import lombok.AllArgsConstructor;
import xyz.vopen.framework.boot.common.exception.MixmicroBootException;
import xyz.vopen.framework.boot.plugin.mail.request.MixmicroBootMailRequest;
import xyz.vopen.framework.boot.plugin.mail.response.MixmicroBootMailResponse;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * Mixmicro Boot Mail Service AliYun Support
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 *     <p>DateTime：2019-06-19 18:52
 */
@AllArgsConstructor
public class MixmicroBootAliYunMailService implements MixmicroBootMailService {
  /** Mail Address Join Split */
  static final String JOIN_SPLIT = ",";
  /** Acs Client Support Instance */
  private IAcsClient client;
  /** The mail address configured in the management console. */
  private String accountName;
  /** Use the reply address configured in the administrative console (status must be validated). */
  private boolean replyToAddress;
  /** Random accounts range from 0 to 1:0 and addresses from 1. */
  private int addressType;
  /** From alias */
  private String defaultFromAlias;
  /** tag name */
  private String defaultTagName;

  /**
   * Send Mail
   *
   * @param mixmicroBootMailRequest Send Mail Request Entity
   * @return ApiBootMailResponse
   * @throws MixmicroBootException Mixmicro Boot Exception
   */
  @Override
  public MixmicroBootMailResponse sendMail(MixmicroBootMailRequest mixmicroBootMailRequest)
      throws MixmicroBootException {
    try {
      // check param
      checkParam(mixmicroBootMailRequest);

      // send mail with single
      SingleSendMailRequest request = new SingleSendMailRequest();
      request.setAccountName(accountName);
      request.setFromAlias(
          StringUtils.isEmpty(mixmicroBootMailRequest.getFormAlias())
              ? defaultFromAlias
              : mixmicroBootMailRequest.getFormAlias());
      request.setAddressType(addressType);
      // tag name
      String tagName =
          StringUtils.isEmpty(mixmicroBootMailRequest.getTagName())
              ? defaultTagName
              : mixmicroBootMailRequest.getTagName();
      if (!StringUtils.isEmpty(tagName)) {
        request.setTagName(tagName);
      }
      request.setReplyToAddress(replyToAddress);
      request.setToAddress(String.join(JOIN_SPLIT, mixmicroBootMailRequest.getToAddress()));
      request.setSubject(mixmicroBootMailRequest.getSubject());
      switchBody(request, mixmicroBootMailRequest.getContent(), mixmicroBootMailRequest.getContentType());

      // execute send mail
      SingleSendMailResponse response = client.getAcsResponse(request);

      return MixmicroBootMailResponse.builder()
          .success(!StringUtils.isEmpty(response.getRequestId()))
          .build();
    } catch (Exception e) {
      throw new MixmicroBootException(e.getMessage(), e);
    }
  }

  /**
   * check param
   *
   * @param request Mixmicro Boot Mail Request
   * @throws MixmicroBootException Mixmicro Boot Exception
   */
  private void checkParam(MixmicroBootMailRequest request) throws MixmicroBootException {
    // mail content
    if (StringUtils.isEmpty(request.getContent())) {
      throw new MixmicroBootException("Mixmicro Boot Mail Request Param：[contentType] is required.");
    }
    // to address
    else if (StringUtils.isEmpty(request.getToAddress())) {
      throw new MixmicroBootException("Mixmicro Boot Mail Request Param：[toAddress] is required.");
    }
    // subject
    else if (StringUtils.isEmpty(request.getSubject())) {
      throw new MixmicroBootException("Mixmicro Boot Mail Request Param：[subject] is required.");
    }
  }

  /**
   * switch mail body if contentType is null, default use text
   *
   * @param request single send mail request
   * @param contentType content type
   */
  private void switchBody(SingleSendMailRequest request, String content, ContentType contentType) {
    if (ObjectUtils.isEmpty(contentType)) {
      request.setTextBody(content);
    } else {
      switch (contentType) {
        case HTML:
          request.setHtmlBody(content);
          break;
        case TEXT:
          request.setTextBody(content);
          break;
      }
    }
  }
}
