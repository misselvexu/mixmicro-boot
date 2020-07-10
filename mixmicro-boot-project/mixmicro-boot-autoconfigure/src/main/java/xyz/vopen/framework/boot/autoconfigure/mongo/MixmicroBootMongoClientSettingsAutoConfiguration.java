package xyz.vopen.framework.boot.autoconfigure.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.connection.ServerSettings;
import com.mongodb.connection.SslSettings;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoClientSettingsBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import xyz.vopen.framework.boot.core.mongo.client.setting.ClusterSettings;
import xyz.vopen.framework.boot.core.mongo.client.setting.ConnectionPoolSettings;
import xyz.vopen.framework.boot.core.mongo.client.setting.MongoClientSettingsBean;
import xyz.vopen.framework.boot.core.mongo.client.setting.SocketSettings;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Mongo client settings configuration
 *
 * <p>Execute before {@link MongoAutoConfiguration}
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 */
@EnableConfigurationProperties(MixmicroBootMongoClientSettingsProperties.class)
@ConditionalOnClass(MongoClient.class)
@AutoConfigureBefore(MongoAutoConfiguration.class)
public class MixmicroBootMongoClientSettingsAutoConfiguration {

  private final MongoClientSettingsBean clientSettingsBean;

  public MixmicroBootMongoClientSettingsAutoConfiguration(
      MixmicroBootMongoClientSettingsProperties properties) {
    this.clientSettingsBean =
        Optional.ofNullable(properties.getSettings()).orElse(new MongoClientSettingsBean());
  }

  /**
   * The {@link SocketSettings} builder customizer support
   *
   * <p>Extended configuration parameters come from {@link MongoClientSettingsBean#getSocket()}
   *
   * @return Build mongo client settings customizer instance
   */
  @Bean
  @Order(0)
  public MongoClientSettingsBuilderCustomizer apiBootMongoSocketSettings() {
    return clientSettingsBuilder -> {
      Optional.ofNullable(clientSettingsBean.getSocket())
          .ifPresent(
              socketSettings -> {
                clientSettingsBuilder.applyToSocketSettings(
                    (socket) ->
                        socket
                            .readTimeout(
                                Long.valueOf(socketSettings.getReadTimeoutMilliSeconds())
                                    .intValue(),
                                TimeUnit.MILLISECONDS)
                            .connectTimeout(
                                Long.valueOf(socketSettings.getConnectTimeoutMilliSeconds())
                                    .intValue(),
                                TimeUnit.MILLISECONDS)
                            .receiveBufferSize(socketSettings.getReceiveBufferSize())
                            .sendBufferSize(socketSettings.getSendBufferSize()));
              });
    };
  }

  /**
   * The heartbeat {@link SocketSettings} builder customizer support
   *
   * <p>Extended configuration parameters come from {@link
   * MongoClientSettingsBean#getHeartbeatSocket()}
   *
   * @return Build mongo client settings customizer instance
   */
  @Bean
  @Order(1)
  public MongoClientSettingsBuilderCustomizer apiBootMongoHeartbeatSocketSettings() {
    return clientSettingsBuilder -> {
      Optional.ofNullable(clientSettingsBean.getHeartbeatSocket())
          .ifPresent(
              heartbeatSocketSettings -> {
                clientSettingsBuilder.applyToSocketSettings(
                    (heartBeatSocket) ->
                        heartBeatSocket
                            .readTimeout(
                                Long.valueOf(heartbeatSocketSettings.getReadTimeoutMilliSeconds())
                                    .intValue(),
                                TimeUnit.MILLISECONDS)
                            .connectTimeout(
                                Long.valueOf(
                                        heartbeatSocketSettings.getConnectTimeoutMilliSeconds())
                                    .intValue(),
                                TimeUnit.MILLISECONDS)
                            .receiveBufferSize(heartbeatSocketSettings.getReceiveBufferSize())
                            .sendBufferSize(heartbeatSocketSettings.getSendBufferSize()));
              });
    };
  }

  /**
   * The {@link ClusterSettings} builder customizer support
   *
   * <p>Extended configuration parameters come from {@link MongoClientSettingsBean#getCluster()}
   *
   * @return Build mongo client settings customizer instance
   */
  @Bean
  @Order(2)
  public MongoClientSettingsBuilderCustomizer apiBootMongoClusterSettings() {
    return clientSettingsBuilder -> {
      Optional.ofNullable(clientSettingsBean.getCluster())
          .ifPresent(
              clusterSettings -> {
                clientSettingsBuilder.applyToClusterSettings(
                    (cluster) -> {
                      // mode
                      Optional.ofNullable(clusterSettings.getMode()).ifPresent(cluster::mode);
                      // requiredClusterType
                      Optional.ofNullable(clusterSettings.getRequiredClusterType())
                          .ifPresent(cluster::requiredClusterType);
                      // requiredReplicaSetName
                      Optional.ofNullable(clusterSettings.getRequiredReplicaSetName())
                          .ifPresent(cluster::requiredReplicaSetName);

                      cluster
                          .localThreshold(
                              clusterSettings.getLocalThresholdMilliSeconds(),
                              TimeUnit.MILLISECONDS)
                          .serverSelectionTimeout(
                              clusterSettings.getServerSelectionTimeoutMilliSeconds(),
                              TimeUnit.MILLISECONDS);
                    });
              });
    };
  }

  /**
   * The {@link ServerSettings} builder customizer support
   *
   * <p>Extended configuration parameters come from {@link MongoClientSettingsBean#getServer()}
   *
   * @return Build mongo client settings customizer instance
   */
  @Bean
  @Order(3)
  public MongoClientSettingsBuilderCustomizer apiBootMongoServerSettings() {
    return clientSettingsBuilder -> {
      Optional.ofNullable(clientSettingsBean.getServer())
          .ifPresent(
              serverSettings -> {
                clientSettingsBuilder.applyToServerSettings(
                    (server) ->
                        server
                            .heartbeatFrequency(
                                serverSettings.getHeartbeatFrequencyMilliSeconds(),
                                TimeUnit.MILLISECONDS)
                            .minHeartbeatFrequency(
                                serverSettings.getMinHeartbeatFrequencyMilliSeconds(),
                                TimeUnit.MILLISECONDS));
              });
    };
  }

  /**
   * The {@link ConnectionPoolSettings} builder customizer support
   *
   * <p>Extended configuration parameters come from {@link
   * MongoClientSettingsBean#getConnectionPool()}
   *
   * @return Build mongo client settings customizer instance
   */
  @Bean
  @Order(4)
  public MongoClientSettingsBuilderCustomizer apiBootMongoConnectionPoolSettings() {
    return clientSettingsBuilder -> {
      Optional.ofNullable(clientSettingsBean.getConnectionPool())
          .ifPresent(
              connectionPoolSettings -> {
                clientSettingsBuilder.applyToConnectionPoolSettings(
                    (pool) ->
                        pool.maxSize(connectionPoolSettings.getMaxSize())
                            .minSize(connectionPoolSettings.getMinSize())
                            .maxWaitTime(
                                connectionPoolSettings.getMaxWaitTimeMilliSeconds(),
                                TimeUnit.MILLISECONDS)
                            .maxConnectionLifeTime(
                                connectionPoolSettings.getMaxConnectionLifeTimeMilliSeconds(),
                                TimeUnit.MILLISECONDS)
                            .maxConnectionIdleTime(
                                connectionPoolSettings.getMaxConnectionIdleTimeMilliSeconds(),
                                TimeUnit.MILLISECONDS)
                            .maintenanceFrequency(
                                connectionPoolSettings.getMaintenanceFrequencyMilliSeconds(),
                                TimeUnit.MILLISECONDS)
                            .maintenanceInitialDelay(
                                connectionPoolSettings.getMaintenanceInitialDelayMilliSeconds(),
                                TimeUnit.MILLISECONDS));
              });
    };
  }

  /**
   * The {@link SslSettings} builder customizer support
   *
   * <p>Extended configuration parameters come from {@link MongoClientSettingsBean#getSsl()}
   *
   * @return Build mongo client settings customizer instance
   */
  @Bean
  @Order(5)
  public MongoClientSettingsBuilderCustomizer apiBootMongoSslSettings() {
    return clientSettingsBuilder -> {
      Optional.ofNullable(clientSettingsBean.getSsl())
          .ifPresent(
              sslSettings -> {
                clientSettingsBuilder.applyToSslSettings(
                    (ssl) ->
                        ssl.enabled(sslSettings.isEnabled())
                            .invalidHostNameAllowed(sslSettings.isInvalidHostNameAllowed()));
              });
    };
  }
}
