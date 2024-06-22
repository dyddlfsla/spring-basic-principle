package spring.basic.scan.qualifier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Service {

  private final C c;

  @Autowired
  public Service(C c) {
    this.c = c;
  }

  public C getC() {
    return c;
  }
}
