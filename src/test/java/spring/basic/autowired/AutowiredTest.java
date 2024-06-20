package spring.basic.autowired;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;
import spring.basic.member.Member;

public class AutowiredTest {

  static class TestBean {

    @Autowired(required = false)
    public void setNoBean1(Member noBean1) {
      System.out.println("noBean1 = " + noBean1);
    }

    @Autowired
    public void setNoBean2(@Nullable Member noBean2) {
      System.out.println("noBean2 = " + noBean2);
    }

    @Autowired
    public void setNoBean3(Optional<Member> noBean3) {
      System.out.println("noBean3 = " + noBean3);
    }
  }

  @Test
  void autowiredOption() {
    ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);
  }

}
