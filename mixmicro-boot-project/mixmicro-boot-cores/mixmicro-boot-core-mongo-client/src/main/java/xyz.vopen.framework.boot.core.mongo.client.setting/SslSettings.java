package xyz.vopen.framework.boot.core.mongo.client.setting;

import lombok.Data;

/**
 * Settings for connecting to MongoDB via SSL.
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @see com.mongodb.connection.SslSettings
 * @since 1.0
 */
@Data
public class SslSettings {
  private boolean enabled;
  private boolean invalidHostNameAllowed;
}
