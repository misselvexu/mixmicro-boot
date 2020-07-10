package xyz.vopen.framework.boot.autoconfigure.mongo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import xyz.vopen.framework.boot.core.mongo.client.setting.MongoClientSettingsBean;

import static xyz.vopen.framework.boot.autoconfigure.mongo.MixmicroBootMongoClientSettingsProperties.MIXMICRO_BOOT_MONGO_CLIENT_SETTINGS;

/**
 * {@link com.mongodb.MongoClientSettings} config properties
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 */
@Data
@ConfigurationProperties(prefix = MIXMICRO_BOOT_MONGO_CLIENT_SETTINGS)
public class MixmicroBootMongoClientSettingsProperties {
    /**
     * The mongo client settings configure properties prefix
     */
    public static final String MIXMICRO_BOOT_MONGO_CLIENT_SETTINGS = "mixmicro.boot.mongo";
    /**
     * The mongo client settings bean
     * <p>
     * Provides {@link com.mongodb.MongoClientSettings} related expansion configuration
     */
    @NestedConfigurationProperty
    private MongoClientSettingsBean settings;
}
