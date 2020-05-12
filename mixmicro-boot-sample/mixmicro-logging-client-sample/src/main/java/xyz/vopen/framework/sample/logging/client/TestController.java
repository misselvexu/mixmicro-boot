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
    mixmicroLogging.info("test log");
    test01();
    test02();
    return "test result";
  }

  private void test01() {
    mixmicroLogging.debug("这是第二条");
  }

  private void test02() {
    try {
      throw new RuntimeException("模拟异常");
    } catch (Exception e) {
      mixmicroLogging.error(e.getMessage(), e);
    }
  }
}
