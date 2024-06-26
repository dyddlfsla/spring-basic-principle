package spring.basic.common;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.util.UUID;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class MyLogger {

  private String uuid;
  private String requestURL;

  public void setRequestURL(String requestURL) {
    this.requestURL = requestURL;
  }

  public void log(String message) {
    System.out.printf("[%s] [%s] %s%n", uuid, requestURL, message);
  }

  @PostConstruct
  public void init() {
    uuid = UUID.randomUUID().toString();
    System.out.printf("[%s] request scope been create: %s%n", uuid, this);
  }

  @PreDestroy
  public void close() {
    System.out.printf("[%s] request scope been close: %s%n", uuid, this);
  }
}
