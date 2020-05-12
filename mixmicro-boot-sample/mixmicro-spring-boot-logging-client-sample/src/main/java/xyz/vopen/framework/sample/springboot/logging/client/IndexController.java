package xyz.vopen.framework.sample.springboot.logging.client;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import xyz.vopen.framework.logging.client.global.MixmicroLogging;

@RestController
public class IndexController {

  @Autowired private RestTemplate restTemplate;

  @Autowired private MixmicroLogging logging;

  @GetMapping(value = "/index")
  public String user() {
    logging.info("starting index process");
    try {
      throw new RuntimeException("模拟执行一次");
    } catch (Exception e) {
      logging.error("异常", e);
    }
    logging.debug("ending index process");

    return "result";
  }

  @PostMapping(value = "/index")
  public User index(@RequestBody User user) throws Exception {
    System.out.println(restTemplate.getInterceptors());
    return user;
  }

  @Data
  public static class User {
    private String name;
    private String email;
    private int age;
  }
}
