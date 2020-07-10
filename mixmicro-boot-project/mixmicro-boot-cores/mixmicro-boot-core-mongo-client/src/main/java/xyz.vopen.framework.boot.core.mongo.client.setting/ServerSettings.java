package xyz.vopen.framework.boot.core.mongo.client.setting;

import lombok.Data;

/**
 * Settings relating to monitoring of each server.
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @see com.mongodb.connection.ServerSettings
 * @since 1.0
 */
@Data
public class ServerSettings {
  private long heartbeatFrequencyMilliSeconds = 10000;
  private long minHeartbeatFrequencyMilliSeconds = 500;
}
