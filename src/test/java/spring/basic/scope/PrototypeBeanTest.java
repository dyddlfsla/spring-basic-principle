package spring.basic.scope;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

public class PrototypeBeanTest {

  @Scope("prototype")
  static class PrototypeBean {

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
  void prototypeBeanFind() {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
    System.out.printf("find prototypeBean1%n");
    PrototypeBean bean1 = ac.getBean(PrototypeBean.class);

    System.out.printf("find prototypeBean2%n");
    PrototypeBean bean2 = ac.getBean(PrototypeBean.class);

    assertThat(bean1).isNotSameAs(bean2);
    ac.close();
  }

}
