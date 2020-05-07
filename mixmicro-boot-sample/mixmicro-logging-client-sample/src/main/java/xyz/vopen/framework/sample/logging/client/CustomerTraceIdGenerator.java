package xyz.vopen.framework.sample.logging.client;

import xyz.vopen.framework.logging.client.MinBoxLoggingException;
import xyz.vopen.framework.logging.client.tracer.LoggingTraceGenerator;

import java.util.UUID;

public class CustomerTraceIdGenerator implements LoggingTraceGenerator {
  @Override
  public String createTraceId() throws MinBoxLoggingException {
    return UUID.randomUUID().toString().replace("-", "");
  }
}
