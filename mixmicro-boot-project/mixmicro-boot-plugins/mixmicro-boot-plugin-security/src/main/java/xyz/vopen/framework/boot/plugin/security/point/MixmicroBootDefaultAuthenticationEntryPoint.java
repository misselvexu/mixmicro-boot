package xyz.vopen.framework.boot.plugin.security.point;

import com.alibaba.fastjson.JSON;
import xyz.vopen.framework.boot.common.model.MixmicroBootResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The {@link AuthenticationEntryPoint} implement class
 *
 * <p>The default implementation class of {@link AuthenticationEntryPoint} provided by ApiBoot
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 */
public class MixmicroBootDefaultAuthenticationEntryPoint implements AuthenticationEntryPoint {
  /** logger instance */
  static Logger logger = LoggerFactory.getLogger(MixmicroBootDefaultAuthenticationEntryPoint.class);

  @Override
  public void commence(
      HttpServletRequest httpServletRequest,
      HttpServletResponse response,
      AuthenticationException e)
      throws IOException, ServletException {
    logger.error("Unauthorized", e);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(HttpStatus.FORBIDDEN.value());
    // Mixmicro Boot Result
    MixmicroBootResult result =
        MixmicroBootResult.builder()
            .errorMessage(HttpStatus.UNAUTHORIZED.getReasonPhrase())
            .errorCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()))
            .build();
    // return json
    response.getWriter().write(JSON.toJSONString(result));
  }
}
