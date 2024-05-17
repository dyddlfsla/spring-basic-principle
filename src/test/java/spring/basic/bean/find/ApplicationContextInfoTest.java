package spring.basic.bean.find;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.basic.AppConfig;

class ApplicationContextInfoTest {

  AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

  @Test
  @DisplayName("모든 Bean 출력하기")
  void findAllBeans() {
    String[] beanDefinitionNames = ac.getBeanDefinitionNames();

    for (String beanDefinitionName : beanDefinitionNames) {
      Object bean = ac.getBean(beanDefinitionName);
      System.out.printf("name = %s , object = %s%n", beanDefinitionName, bean);

    }
  }

  @Test
  @DisplayName("Application Bean 출력하기")
  void findApplicationBeans() {
    String[] beanDefinitionNames = ac.getBeanDefinitionNames();

    for (String beanDefinitionName : beanDefinitionNames) {
      BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);

      //Role ROLE_APPLICATION: 직접 등록한 스프링 Bean
      //Role ROLE_INFRASTRUCTURE: 스프링 내부에서 사용하는 Bean
      if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
        Object bean = ac.getBean(beanDefinitionName);
        System.out.printf("name = %s , object = %s%n", beanDefinitionName, bean);
      }
    }
  }
}
