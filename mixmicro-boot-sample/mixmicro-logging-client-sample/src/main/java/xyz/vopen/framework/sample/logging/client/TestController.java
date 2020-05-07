package xyz.vopen.framework.sample.logging.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.vopen.framework.logging.client.global.GlobalLogging;

@RestController
public class TestController {

  @Autowired private GlobalLogging globalLogging;

  @GetMapping(value = "/test")
  public String test() {
    globalLogging.info("这是第一条日志内容");
    callMethod2();
    callMethod3();
    return "测试日志接口";
  }

  private void callMethod2() {
    globalLogging.debug("这是第二条");
  }

  private void callMethod3() {
    try {
      int a = 3 / 0;
    } catch (Exception e) {
      globalLogging.error(e.getMessage(), e);
    }
  }
}
