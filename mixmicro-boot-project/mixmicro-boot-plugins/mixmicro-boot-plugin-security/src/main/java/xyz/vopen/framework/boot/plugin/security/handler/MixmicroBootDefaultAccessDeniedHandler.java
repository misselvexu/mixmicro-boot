package xyz.vopen.framework.boot.plugin.security.handler;

import com.alibaba.fastjson.JSON;
import xyz.vopen.framework.boot.common.model.MixmicroBootResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * {@link AccessDeniedHandler} exception handler
 *
 * <p>Implement authentication exception custom return format content to front end
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 */
public class MixmicroBootDefaultAccessDeniedHandler implements AccessDeniedHandler {
  /** logger instance */
  static Logger logger = LoggerFactory.getLogger(MixmicroBootDefaultAccessDeniedHandler.class);

  @Override
  public void handle(
      HttpServletRequest request, HttpServletResponse response, AccessDeniedException e)
      throws IOException, ServletException {
    logger.error("Mixmicro Boot Default AccessDeniedHandler.", e);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(HttpStatus.OK.value());
    // Mixmicro Boot Result
    MixmicroBootResult result =
        MixmicroBootResult.builder()
            .errorMessage(HttpStatus.FORBIDDEN.getReasonPhrase())
            .errorCode(String.valueOf(HttpStatus.FORBIDDEN.value()))
            .build();
    // return json
    response.getWriter().write(JSON.toJSONString(result));
  }
}
