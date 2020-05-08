package xyz.vopen.framework.sample.logging.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.vopen.framework.logging.client.global.MixmicroLogging;

@RestController
public class TestController {

  @Autowired private MixmicroLogging mixmicroLogging;

  @GetMapping(value = "/test")
  public String test() {
    mixmicroLogging.info("这是第一条日志内容");
    callMethod2();
    callMethod3();
    return "测试日志接口";
  }

  private void callMethod2() {
    mixmicroLogging.debug("这是第二条");
  }

  private void callMethod3() {
    try {
      int a = 3 / 0;
    } catch (Exception e) {
      mixmicroLogging.error(e.getMessage(), e);
    }
  }
}
