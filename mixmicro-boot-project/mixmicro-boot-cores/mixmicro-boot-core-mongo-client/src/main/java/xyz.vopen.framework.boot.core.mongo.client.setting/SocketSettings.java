package xyz.vopen.framework.boot.core.mongo.client.setting;

import lombok.Data;

/**
 * The mongo socket settings
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @see com.mongodb.connection.SocketSettings
 * @since 1.0
 */
@Data
public class SocketSettings {
  /** The socket connect timeout MilliSeconds */
  private long connectTimeoutMilliSeconds = 10000;
  /** The socket read timeout MilliSeconds */
  private long readTimeoutMilliSeconds = 10000;
  /** The receive buffer size */
  private int receiveBufferSize;
  /** The send buffer size */
  private int sendBufferSize;
}
