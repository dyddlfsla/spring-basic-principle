package spring.basic.bean.find;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.basic.discount.DiscountPolicy;
import spring.basic.discount.FixDiscountPolicy;
import spring.basic.discount.RateDiscountPolicy;

public class ApplicationContextExtenderFindTest {

  AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

  @Configuration
  static class TestConfig {

    @Bean
    public DiscountPolicy rateDiscountPolicy() {
      return new RateDiscountPolicy();
    }

    @Bean
    public DiscountPolicy fixDiscountPolicy() {
      return new FixDiscountPolicy();
    }
  }

  @Test
  @DisplayName("부모 타입으로 스프링 bean 조회 시, 자식 타입의 bean 이 2개 이상이면 중복 예외가 발생한다.")
  void findBeanByParentTypeOnDuplicate() {
    assertThrows(NoUniqueBeanDefinitionException.class,
        () -> ac.getBean(DiscountPolicy.class));
  }

  @Test
  @DisplayName("부모 타입으로 스프링 bean 조회 시, 자식 타입의 bean 이 2개 이상이면, 추가 매개 변수로 이름까지 넣어주면 된다.")
  void findBeanByParentTypeAndName() {
    DiscountPolicy discountPolicy = ac.getBean("rateDiscountPolicy", DiscountPolicy.class);
    assertThat(discountPolicy).isInstanceOf(DiscountPolicy.class);
  }

  @Test
  @DisplayName("구체적인 하위 타입을 지정해서 bean 을 조회하면 된다.")
  void findBeanBySubType() {
    RateDiscountPolicy discountPolicy = ac.getBean(RateDiscountPolicy.class);
    assertThat(discountPolicy).isInstanceOf(RateDiscountPolicy.class);
  }

  @Test
  @DisplayName("부모 타입으로 모든 스프링 bean 조회하기")
  void findAllBeansByParentType() {
    Map<String, DiscountPolicy> foundBeans = ac.getBeansOfType(DiscountPolicy.class);
    assertThat(foundBeans.size()).isEqualTo(2);
    for (String key : foundBeans.keySet()) {
      System.out.printf("key: %s, bean: %s%n", key, foundBeans.get(key));
    }
  }

  @Test
  @DisplayName("최상위 부모 타입 Object 로 bean 조회")
  void findAllBeansByObjectType() {
    Map<String, Object> foundBeans = ac.getBeansOfType(Object.class);
    for (String key : foundBeans.keySet()) {
      System.out.printf("key: %s, bean: %s%n", key, foundBeans.get(key));
    }
  }
}
