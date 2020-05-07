package xyz.vopen.framework.sample.springboot.logging.client;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice(annotations = RestController.class)
@ResponseBody
public class ExceptionAdvice {
  @ExceptionHandler(Exception.class)
  public void exception(Exception exception) {
    exception.printStackTrace();
  }
}
