package spring.basic.web;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;
import spring.basic.common.MyLogger;

@Service
@RequiredArgsConstructor
public class LogDemoService {

  private final ObjectProvider<MyLogger> myLoggerProvider;

  public void logic(String id) {
    MyLogger myLogger = myLoggerProvider.getObject();
    myLogger.log(String.format("service id = %s%n", id));
  }
}
