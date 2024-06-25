package spring.basic.scope;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

public class SingletonScopeTest {

  @Scope("singleton")
  static class SingletonBean {

    @PostConstruct
    public void init() {
      System.out.printf("SingletonBean.init()%n");
    }

    @PreDestroy
    public void destroy() {
      System.out.printf("SingletonBean.destroy()%n");
    }

  }

  @Test
  void singletonBeanFind() {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(
        SingletonBean.class);
    SingletonBean bean1 = ac.getBean(SingletonBean.class);
    SingletonBean bean2 = ac.getBean(SingletonBean.class);

    assertThat(bean1).isSameAs(bean2);
    ac.close();
  }

}
