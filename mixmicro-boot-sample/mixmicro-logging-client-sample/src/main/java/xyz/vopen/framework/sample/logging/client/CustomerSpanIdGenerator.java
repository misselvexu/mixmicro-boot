package xyz.vopen.framework.sample.logging.client;

import xyz.vopen.framework.logging.client.MinBoxLoggingException;
import xyz.vopen.framework.logging.client.span.LoggingSpanGenerator;

public class CustomerSpanIdGenerator implements LoggingSpanGenerator {
  @Override
  public String createSpanId() throws MinBoxLoggingException {
    String currentTime = String.valueOf(System.currentTimeMillis());
    return String.format("%s-%s", "span", currentTime);
  }
}
