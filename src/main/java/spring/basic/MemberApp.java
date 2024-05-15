package spring.basic;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.basic.member.Grade;
import spring.basic.member.Member;
import spring.basic.member.MemberService;

public class MemberApp {

  public static void main(String[] args) {

//    AppConfig appConfig = new AppConfig();
//    MemberService memberService = appConfig.memberService();

    ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
    MemberService memberService = ac.getBean("memberService", MemberService.class);

    Member member = new Member(1L, "memberA", Grade.VIP);
    memberService.join(member);

    Member foundMember = memberService.findMember(1L);

    System.out.printf("new member's name: %s, foundMember's name: %s\n", member.getName(), foundMember.getName());
    /*
   self-taught
    * System.out.print 를 통해 직접 '눈'으로 확인하는 테스트는 결코 좋은 테스트가 아니다. 좋은 테스트를 작성하는 것이 개발의 기본이다.
     */

    /*
   self-taught
    * 현재까지 만든 코드는 잘 실행되었다. 그러나 생각해볼 것이 있다.
    * 이 코드들은 설계상의 문제점이 있을까?
    * MemberService memberService = new MemberServiceImpl();
    * 지금은 MemoryMemberRepository 를 구현체로 사용했지만 만약 추후 다른 저장소로 변경한다면 OCP 원칙을 지킬 수 있을까?
    * DIP 원칙은 어떨까?
    * 사실 지금까지의 코드는 의존 관계가 인터페이스 뿐만 아니라 구현체에까지 모두 의존하고 있다.
    *
    *
    * */
  }

}
