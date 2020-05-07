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

package xyz.vopen.framework.logging.admin.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * Home Page Forwarding Filter
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 *     <p>DateTime：2019-07-31 15:54
 */
public class HomepageForwardingFilter implements Filter {
  private static final Logger log = LoggerFactory.getLogger(HomepageForwardingFilter.class);
  private final String homepage;
  private final HomepageForwardingMatcher<HttpServletRequest> matcher;

  public HomepageForwardingFilter(String homepage, List<String> routes) {
    this.homepage = homepage;
    UrlPathHelper urlPathHelper = new UrlPathHelper();
    this.matcher =
        new HomepageForwardingMatcher<>(
            routes,
            HttpServletRequest::getMethod,
            urlPathHelper::getPathWithinApplication,
            r -> MediaType.parseMediaTypes(r.getHeader(HttpHeaders.ACCEPT)));
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    if (request instanceof HttpServletRequest) {
      HttpServletRequest httpRequest = (HttpServletRequest) request;
      if (this.matcher.test(httpRequest)) {
        log.trace("Forwarding request with URL {} to index", httpRequest.getRequestURI());
        request.getRequestDispatcher(this.homepage).forward(request, response);
        return;
      }
    }

    chain.doFilter(request, response);
  }
}
