package xyz.vopen.framework.boot.plugin.oauth.exception;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * The ApiBootOAuth2Exception Serializer The specific implementation is handed over to
 * "AuthorizationDeniedResponse#serializeResponse"
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 */
public class MixmicroBootOAuth2ExceptionSerializer extends StdSerializer<MixmicroBootOAuth2Exception> {
  protected MixmicroBootOAuth2ExceptionSerializer() {
    super(MixmicroBootOAuth2Exception.class);
  }

  @Override
  public void serialize(
      MixmicroBootOAuth2Exception e, JsonGenerator generator, SerializerProvider serializerProvider)
      throws IOException {
    generator.writeStartObject();
    e.getResponse().serializeResponse(e, generator);
    generator.writeEndObject();
  }
}
