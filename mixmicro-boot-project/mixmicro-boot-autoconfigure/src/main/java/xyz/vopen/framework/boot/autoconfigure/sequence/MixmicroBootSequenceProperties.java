package xyz.vopen.framework.boot.autoconfigure.sequence;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static xyz.vopen.framework.boot.autoconfigure.sequence.MixmicroBootSequenceProperties.MIXMICRO_BOOT_SEQUENCE_PREFIX;

/**
 * 整合Sequence分布式高效ID的属性相关配置
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 */
@Data
@ConfigurationProperties(prefix = MIXMICRO_BOOT_SEQUENCE_PREFIX)
public class MixmicroBootSequenceProperties {

  /** 分布式ID配置前缀 */
  public static final String MIXMICRO_BOOT_SEQUENCE_PREFIX = "mixmicro.boot.sequence";

  /** 数据中心编号，取值范围为0 ~ 3 */
  private int dataCenterId = 1;

  /** 工作机器编号，取值范围为0 ~ 255 */
  private int workerId = 1;

  /** 是否解决高并发下获取时间戳的性能问题 */
  private boolean clock;

  /** 允许时间回拨的毫秒量 */
  private long timeOffsetMilliseconds = 5L;

  /** 毫秒内的随机序列 */
  private boolean randomSequence;
}
