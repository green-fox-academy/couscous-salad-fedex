package com.greenfoxacademy.fedex.logging;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;

@Configuration
public class RequestLoggingFilterConfig extends AbstractRequestLoggingFilter {
  private long timeInMillis;

  @Override
  protected void beforeRequest(HttpServletRequest request, String message) {
    timeInMillis = System.currentTimeMillis();
  }

  @Override
  protected void afterRequest(HttpServletRequest request, String message) {
    long executionTime = System.currentTimeMillis() - timeInMillis;
    logger.info(message + " Execution time: " + executionTime + "ms");
  }
}
