package spring.basic.scan.qualifier;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import spring.basic.discount.DiscountPolicy;

public class QualifierTest {

  @Configuration
  @ComponentScan
  static class TestComponentScan {}

  @Test
  void qualifier() {
    ApplicationContext ac = new AnnotationConfigApplicationContext(TestComponentScan.class);
    Service bean = ac.getBean(Service.class);
    DiscountPolicy discountPolicy = bean.getDiscountPolicy();

    assertThat(discountPolicy).isExactlyInstanceOf(FixDiscountPolicy.class);
  }

}
