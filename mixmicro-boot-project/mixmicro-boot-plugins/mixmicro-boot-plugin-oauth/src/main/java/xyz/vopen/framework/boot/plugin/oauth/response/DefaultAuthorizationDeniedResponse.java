package xyz.vopen.framework.boot.plugin.oauth.response;

import com.fasterxml.jackson.core.JsonGenerator;
import xyz.vopen.framework.boot.plugin.oauth.exception.MixmicroBootOAuth2Exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.HtmlUtils;
import xyz.vopen.framework.boot.plugin.oauth.translator.MixmicroBootWebResponseExceptionTranslator;

/**
 * The default {@link AuthorizationDeniedResponse} support Provide default OAuth2Exception exception
 * response content
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @see MixmicroBootWebResponseExceptionTranslator
 * @see MixmicroBootOAuth2Exception
 */
public class DefaultAuthorizationDeniedResponse implements AuthorizationDeniedResponse {
  @Override
  public void serializeResponse(MixmicroBootOAuth2Exception e, JsonGenerator generator) {
    try {
      String message = e.getMessage();
      if (message != null) {
        message = HtmlUtils.htmlEscape(message);
      }
      generator.writeObjectField("errorMessage", message);
      generator.writeObjectField("errorCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
