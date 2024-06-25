package spring.basic.scope;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Provider;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

public class JakartaProviderTest {

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

    private final Provider<PrototypeBean> prototypeProvider;

    public ClientBean(Provider<PrototypeBean> prototypeProvider) {
      this.prototypeProvider = prototypeProvider;
    }

    public int logic() {
      PrototypeBean prototypeBean = prototypeProvider.get();
      prototypeBean.addCount();
      return prototypeBean.getCount();
    }

    public Provider<PrototypeBean> getPrototypeProvider() {
      return prototypeProvider;
    }
  }

  @Test
  void useJakartaProvider() {

    ApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class,
        PrototypeBean.class);

    ClientBean clientBean1 = ac.getBean(ClientBean.class);
    int count1 = clientBean1.logic();
    assertThat(count1).isEqualTo(1);

    ClientBean clientBean2 = ac.getBean(ClientBean.class);
    int count2 = clientBean2.logic();
    assertThat(count2).isEqualTo(1);

    PrototypeBean prototypeBean1 = clientBean1.getPrototypeProvider().get();
    PrototypeBean prototypeBean2 = clientBean2.getPrototypeProvider().get();
    assertThat(prototypeBean1).isNotSameAs(prototypeBean2);
  }

}
