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

package xyz.vopen.framework.logging.client.admin.report.support;

import com.alibaba.fastjson.JSON;
import xyz.vopen.framework.logging.client.LoggingFactoryBean;
import xyz.vopen.framework.logging.client.MinBoxLoggingException;
import xyz.vopen.framework.logging.client.admin.report.LoggingAdminReport;
import xyz.vopen.framework.logging.client.cache.LoggingCache;
import xyz.vopen.framework.logging.core.LoggingClientNotice;
import xyz.vopen.framework.logging.core.MixmicroLog;
import xyz.vopen.framework.logging.core.response.ReportResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Mixmicro Boot Logging Admin Report Support
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 */
public class LoggingAdminReportSupport implements LoggingAdminReport, DisposableBean {
  /** logger instance */
  static Logger logger = LoggerFactory.getLogger(LoggingAdminReportSupport.class);

  /** Report Request Logging Uri */
  private static final String REPORT_LOG_URI = "/logging/report";
  /** Content-Type HEADER NAME */
  private static final String HEADER_CONTENT_TYPE = "Content-Type";
  /** Authorization HEADER NAME */
  private static final String HEADER_AUTHORIZATION = "Authorization";

  /** logging factory bean */
  private LoggingFactoryBean factoryBean;

  public LoggingAdminReportSupport(LoggingFactoryBean factoryBean) {
    this.factoryBean = factoryBean;
  }

  /**
   * Report Logs Interval Recycle the request log when an exception is encountered
   *
   * @throws MinBoxLoggingException Logging Exception
   */
  @Override
  public void report() throws MinBoxLoggingException {
    if (ObjectUtils.isEmpty(factoryBean.getLoggingAdminDiscovery())) {
      logger.warn(
          "Not set 【LoggingAdminDiscovery】in LoggingFactoryBean，don't invoke report request logs.");
      return;
    }
    List<MixmicroLog> logs = new ArrayList<>();
    LoggingCache loggingCache = factoryBean.getLoggingCache();
    Integer numberOfRequestLog = factoryBean.getNumberOfRequestLog();
    try {
      logs = loggingCache.getLogs(numberOfRequestLog);
      report(logs);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      if (!ObjectUtils.isEmpty(logs)) {
        logs.stream().forEach(log -> loggingCache.cache(log));
      }
    }
  }

  /**
   * Report Logs To Admin get number logs from cache lookup a available api-boot-logging-admin
   * service url report request logs ro admin service if admin use spring security, set restTemplate
   * header basic auth info
   *
   * @param logs Request Logs
   * @throws MinBoxLoggingException Logging Exception
   */
  @Override
  public void report(List<MixmicroLog> logs) throws MinBoxLoggingException {
    if (ObjectUtils.isEmpty(logs)) {
      logger.warn("Don't have report request logs.");
      return;
    }
    if (ObjectUtils.isEmpty(factoryBean.getLoggingAdminDiscovery())) {
      logger.warn(
          "Not set【LoggingAdminDiscovery】in LoggingFactoryBean，don't invoke report request logs.");
      return;
    }
    String adminServiceUrl = getAfterFormatAdminUrl();
    logger.debug("Report logging admin url：{}", adminServiceUrl);
    LoggingClientNotice notice =
        LoggingClientNotice.instance(
            factoryBean.getServiceId(),
            factoryBean.getServiceAddress(),
            factoryBean.getServicePort(),
            logs);

    HttpHeaders headers = new HttpHeaders();
    headers.add(HEADER_CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
    String basicAuth = factoryBean.getLoggingAdminDiscovery().getBasicAuth();
    if (!ObjectUtils.isEmpty(basicAuth)) {
      headers.add(HEADER_AUTHORIZATION, basicAuth);
    }
    String noticeJson = JSON.toJSONString(notice);
    HttpEntity<String> httpEntity = new HttpEntity(noticeJson, headers);
    ResponseEntity<ReportResponse> response =
        factoryBean
            .getRestTemplate()
            .postForEntity(adminServiceUrl, httpEntity, ReportResponse.class);
    if (response.getStatusCode().is2xxSuccessful()
        && response.getBody().getStatus().equals(ReportResponse.SUCCESS)) {
      logger.debug("Report request logging successfully to admin.");
    } else {
      logger.error("Report request logging error to admin.");
    }
  }

  /**
   * Get After Format Admin URL
   *
   * @return Mixmicro Boot Logging Admin URL
   */
  private String getAfterFormatAdminUrl() {
    // api boot admin service uri
    String adminServiceUri = factoryBean.getLoggingAdminDiscovery().lookup();
    // api boot admin service url
    return String.format("%s%s", adminServiceUri, REPORT_LOG_URI);
  }

  /**
   * Bean Destroy When destroyed, report all request logs in the cache to admin
   *
   * @throws Exception exception
   */
  @Override
  public void destroy() throws Exception {
    // get all cache logs
    List<MixmicroLog> logs = factoryBean.getLoggingCache().getAll();
    // report to admin
    report(logs);
    logger.debug(
        "The program is destroyed to execute the request log in the report cache to admin successfully.");
  }
}
