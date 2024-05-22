package spring.basic.bean.definition;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.basic.AppConfig;

public class BeanDefinitionTest {

  AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

  @Test
  @DisplayName("Bean 설정 메타정보 확인")
  void findApplicationBean() {
    String[] beanDefinitionNames = ac.getBeanDefinitionNames();
    for (String beanDefinitionName : beanDefinitionNames) {
      BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);
      if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
        System.out.printf("beanDefinitionName = %s, beanDefinition = %s%n", beanDefinitionName, beanDefinition);
      }
    }
  }

}
