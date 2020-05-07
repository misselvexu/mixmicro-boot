package xyz.vopen.framework.boot.plugin.quartz.wrapper;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Job execution param wrapper class
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 */
@Data
public class MixmicroBootJobParamWrapper {
  /** job params */
  private static Map<String, Object> param = new HashMap();

  /** Disable constructor instantiation */
  private MixmicroBootJobParamWrapper() {}

  /**
   * Create a instance
   *
   * @return {@link MixmicroBootJobParamWrapper}
   */
  public static MixmicroBootJobParamWrapper wrapper() {
    return new MixmicroBootJobParamWrapper();
  }

  /**
   * Put new param to map
   *
   * @param name param name
   * @param value param value
   * @return {@link MixmicroBootJobParamWrapper} current object instance
   */
  public MixmicroBootJobParamWrapper put(String name, Object value) {
    param.put(name, value);
    return this;
  }

  /**
   * Get all params
   *
   * @return {@link #param}
   */
  public Map<String, Object> getAllParam() {
    return param;
  }
}
