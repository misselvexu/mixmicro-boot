package xyz.vopen.framework.boot.plugin.oauth.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import xyz.vopen.framework.boot.plugin.oauth.response.AuthorizationDeniedResponse;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * Custom implementation of OAuth2Exception
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @see OAuth2Exception
 */
@JsonSerialize(using = MixmicroBootOAuth2ExceptionSerializer.class)
@Getter
public class MixmicroBootOAuth2Exception extends OAuth2Exception {

  private AuthorizationDeniedResponse response;

  public MixmicroBootOAuth2Exception(String message, Throwable t, AuthorizationDeniedResponse response) {
    super(message, t);
    this.response = response;
  }
}
