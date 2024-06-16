package spring.basic.singleton;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.basic.AppConfig;
import spring.basic.member.MemberRepository;
import spring.basic.member.MemberServiceImpl;
import spring.basic.order.OrderServiceImpl;

public class ConfigurationSingletonTest {

  @Test
  void configurationSingletonTest() {
    ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
    OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);

    MemberRepository memberRepository3 = ac.getBean("memberRepository", MemberRepository.class);
    MemberRepository memberRepository1 = memberService.getMemberRepository();
    MemberRepository memberRepository2 = orderService.getMemberRepository();

    assertThat(memberRepository1).isSameAs(memberRepository2);
    assertThat(memberRepository1).isSameAs(memberRepository3);
    assertThat(memberRepository2).isSameAs(memberRepository3);
  }

  @Test
  void configurationDeep() {
    ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
    AppConfig bean = ac.getBean(AppConfig.class);
    System.out.println("bean = " + bean);
  }

}
