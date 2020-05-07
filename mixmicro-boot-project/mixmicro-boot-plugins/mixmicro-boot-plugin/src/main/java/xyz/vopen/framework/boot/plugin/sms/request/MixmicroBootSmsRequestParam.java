package xyz.vopen.framework.boot.plugin.sms.request;

import com.alibaba.fastjson.JSON;
import lombok.Getter;

import java.util.HashMap;

/**
 * Mixmicro Boot Send SMS Request Parameter Encapsulation Object
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 */
@Getter
public class MixmicroBootSmsRequestParam {
  /** parameters */
  private static final HashMap<String, Object> params = new HashMap();

  /**
   * put one parameter to {@link #params}
   *
   * @param name the parameter name
   * @param value the parameter value
   * @return {@link MixmicroBootSmsRequestParam} this object instance
   */
  public MixmicroBootSmsRequestParam put(String name, Object value) {
    params.put(name, value);
    return this;
  }

  /**
   * get the {@link #params} after formatting the json string
   *
   * @return parameters json string
   */
  public String getParamJson() {
    return JSON.toJSONString(params);
  }
}
