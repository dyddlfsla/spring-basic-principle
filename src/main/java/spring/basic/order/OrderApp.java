package spring.basic.order;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.basic.AppConfig;
import spring.basic.member.Grade;
import spring.basic.member.Member;
import spring.basic.member.MemberService;

public class OrderApp {

  public static void main(String[] args) {

    // AppConfig appConfig = new AppConfig();
    // MemberService memberService = appConfig.memberService();
    // OrderService orderService = appConfig.orderService();

    ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
    MemberService memberService = ac.getBean("memberService", MemberService.class);
    OrderService orderService = ac.getBean("orderService", OrderService.class);

    Long memberId = 1L;
    Member member = new Member(memberId, "memberA", Grade.VIP);
    memberService.join(member);

    Order order = orderService.createOrder(memberId, "itemA", 20000);

    System.out.println("order = " + order);

  }

}
