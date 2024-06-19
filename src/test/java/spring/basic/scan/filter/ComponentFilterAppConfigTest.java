package spring.basic.scan.filter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

public class ComponentFilterAppConfigTest {

  @Configuration
  @ComponentScan(includeFilters = @Filter(type = FilterType.ANNOTATION, classes = MyIncludeComponent.class),
                 excludeFilters = @Filter(type = FilterType.ANNOTATION, classes = MyExcludeComponent.class))
  static class ComponentFilterAppConfig {}

  @Test
  void filterScan() {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ComponentFilterAppConfig.class);

    BeanA beanA = ac.getBean("beanA", BeanA.class);
    assertThat(beanA).isNotNull();

    assertThatExceptionOfType(NoSuchBeanDefinitionException.class)
        .isThrownBy(() -> ac.getBean("beanB", BeanB.class));
    //excludeFilters = @Filter(type = FilterType.ANNOTATION, classes = MyExcludeComponent.class) 로 인해 BeanB 는 스프링 Bean 으로 등록되지 않음.

  }

}
