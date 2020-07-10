package xyz.vopen.framework.boot.core.mongo.client.setting;

import lombok.Data;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;

/**
 * All settings that relate to the pool of connections to a MongoDB server.
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @see com.mongodb.connection.ConnectionPoolSettings
 * @since 1.0
 */
@Data
public class ConnectionPoolSettings {

  private int maxSize = 100;
  private int minSize;
  private long maxWaitTimeMilliSeconds = 1000 * 60 * 2;
  private long maxConnectionLifeTimeMilliSeconds;
  private long maxConnectionIdleTimeMilliSeconds;
  private long maintenanceInitialDelayMilliSeconds;
  private long maintenanceFrequencyMilliSeconds = MILLISECONDS.convert(1, MINUTES);
}
