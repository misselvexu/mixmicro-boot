package xyz.vopen.framework.boot.core.mongo.client.setting;

import lombok.Data;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * The {@link com.mongodb.MongoClientSettings} encapsulated bean
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @since 1.0
 */
@Data
public class MongoClientSettingsBean {
  /** The socket settings */
  @NestedConfigurationProperty private SocketSettings socket;

  /** The heartbeat socket settings */
  @NestedConfigurationProperty private SocketSettings heartbeatSocket;

  /** The cluster settings */
  @NestedConfigurationProperty private ClusterSettings cluster;

  /** Settings relating to monitoring of each server. */
  @NestedConfigurationProperty private ServerSettings server;

  /** All settings that relate to the pool of connections to a MongoDB server. */
  @NestedConfigurationProperty private ConnectionPoolSettings connectionPool;

  /** Settings for connecting to MongoDB via SSL. */
  @NestedConfigurationProperty private SslSettings ssl;
}
