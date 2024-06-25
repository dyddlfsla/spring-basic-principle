package spring.basic.scope;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

public class SingletonWithPrototypeScopeTest {

  @Scope("prototype")
  static class PrototypeBean {

    private int count = 0;

    public void addCount() {
      count++;
    }

    public int getCount() {
      return count;
    }

    @PostConstruct
    public void init() {
      System.out.printf("PrototypeBean.init()%n");
    }

    @PreDestroy
    public void destroy() {
      System.out.printf("PrototypeBean.destroy()%n");
    }
  }

  @Scope("singleton")
  static class ClientBean {
    private final PrototypeBean prototypeBean;

    public ClientBean(PrototypeBean prototypeBean) {
      this.prototypeBean = prototypeBean;
    }

    public int logic() {
      prototypeBean.addCount();
      return prototypeBean.getCount();
    }
  }

  @Test
  void prototypeFind() {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(
        PrototypeBean.class);

    PrototypeBean bean = ac.getBean(PrototypeBean.class);
    bean.addCount();
    assertThat(bean.getCount()).isEqualTo(1);

    PrototypeBean bean2 = ac.getBean(PrototypeBean.class);
    bean2.addCount();
    assertThat(bean2.getCount()).isNotEqualTo(2);
  }

  @Test
  void singletonClientUsePrototype() {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(
        PrototypeBean.class, ClientBean.class);
    ClientBean clientBean1 = ac.getBean(ClientBean.class);
    int count1 = clientBean1.logic();
    assertThat(count1).isEqualTo(1);

    ClientBean clientBean2 = ac.getBean(ClientBean.class);
    int count2 = clientBean2.logic();
    assertThat(count2).isEqualTo(2);

  }

}
