package spring.basic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.basic.discount.DiscountPolicy;
import spring.basic.discount.FixDiscountPolicy;
import spring.basic.member.MemberRepository;
import spring.basic.member.MemberService;
import spring.basic.member.MemberServiceImpl;
import spring.basic.member.MemoryMemberRepository;
import spring.basic.order.OrderService;
import spring.basic.order.OrderServiceImpl;

  /*

  ◆ AppConfig 클래스의 등장.
  코드로 돌아가, 애플리케이션의 전체 동작 방식을 구성(config) 하기 위해, 구현 객체를 생성하고
  연결해주는 별도의 설정 클래스를 만들자.

  */

@Configuration
public class AppConfig {

  @Bean(name = "memberService")
  public MemberService memberService() { //생성자를 통해 의존성을 주입한다고 해서, 생성자 주입이라고도 한다.
    System.out.println("call AppConfig.memberService");
    return new MemberServiceImpl(memberRepository());
  }

  @Bean
  public OrderService orderService() {
    System.out.println("call AppConfig.orderService");
    return new OrderServiceImpl(discountPolicy(), memberRepository());
  }

  @Bean
  public MemberRepository memberRepository() {
    System.out.println("call AppConfig.memberRepository");
    return new MemoryMemberRepository();
  }

  @Bean
  public DiscountPolicy discountPolicy() {
//    return new RateDiscountPolicy();
    return new FixDiscountPolicy();
  }

}
