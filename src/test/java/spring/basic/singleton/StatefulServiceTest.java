package spring.basic.singleton;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

class StatefulServiceTest {

  @Configuration
  static class TestConfig{

    @Bean
    public StatefulService statefulService() {
      return new StatefulService();
    }
  }

  @Test
  void statefulServiceSingleton() {
    ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

    StatefulService statefulService1 = ac.getBean(StatefulService.class);
    StatefulService statefulService2 = ac.getBean(StatefulService.class);

    //ThreadA: A 사용자가 10000원 주문
    statefulService1.order("userA", 10000);
    //ThreadB: B 사용자가 20000원 주문
    statefulService2.order("userB", 20000);

    //ThreadA: 사용자 A 주문 금액 조회
    int price = statefulService1.getPrice();

    assertThat(statefulService1.getPrice()).isEqualTo(20000);
  }

}