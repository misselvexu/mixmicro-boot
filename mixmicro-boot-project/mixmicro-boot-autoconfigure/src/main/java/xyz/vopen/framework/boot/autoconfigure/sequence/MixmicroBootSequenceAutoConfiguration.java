package xyz.vopen.framework.boot.autoconfigure.sequence;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.vopen.framework.boot.plugin.sequence.Sequence;

/**
 * 分布式高效ID "Sequence" 自动化配置类
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 */
@Configuration
@ConditionalOnClass(Sequence.class)
@EnableConfigurationProperties(MixmicroBootSequenceProperties.class)
public class MixmicroBootSequenceAutoConfiguration {

  /** 注入 "Sequence" 所需要的属性配置类 */
  private final MixmicroBootSequenceProperties mixmicroBootSequenceProperties;

  public MixmicroBootSequenceAutoConfiguration(
      MixmicroBootSequenceProperties mixmicroBootSequenceProperties) {
    this.mixmicroBootSequenceProperties = mixmicroBootSequenceProperties;
  }

  /**
   * 实例化 {@link MixmicroBootSequenceContext}
   *
   * <p>在{@link MixmicroBootSequenceContext}内进行实例化{@link Sequence}， 部分配置的默认值请参考{@link
   * MixmicroBootSequenceContext}
   *
   * @return Sequence instance object
   */
  @Bean
  @ConditionalOnMissingBean
  public MixmicroBootSequenceContext mixmicroBootSequenceContext() {
    return new MixmicroBootSequenceContext(mixmicroBootSequenceProperties);
  }
}
