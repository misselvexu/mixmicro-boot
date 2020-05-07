package xyz.vopen.framework.boot.plugin.oauth.response;

import com.fasterxml.jackson.core.JsonGenerator;
import xyz.vopen.framework.boot.plugin.oauth.exception.MixmicroBootOAuth2Exception;
import org.springframework.http.HttpStatus;

/**
 * Interface definition to respond to authorization exception
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 */
public interface AuthorizationDeniedResponse {
  /**
   * Provide the response HttpStatus, default is 401
   *
   * @return {@link HttpStatus}
   */
  default HttpStatus getHttpStatus() {
    return HttpStatus.UNAUTHORIZED;
  }

  /**
   * Serialize the response content
   *
   * @param e {@link MixmicroBootOAuth2Exception}
   * @param generator {@link JsonGenerator}
   */
  default void serializeResponse(MixmicroBootOAuth2Exception e, JsonGenerator generator) {
    // default nothing to do
  }
}
