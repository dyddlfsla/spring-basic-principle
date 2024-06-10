package spring.basic.note;

public class $10_ApplicationAndSingleton {


  /*
 self-taught
  *
  * Ⅰ. 웹 어플리케이션과 싱글톤
  *
  * Spring framework 는 태생이 기업용 온라인 서비스 기술을 지원하기 위해 탄생했다.
  * 스프링 프레임워크로 만들어지는 대부분의 애플리케이션들은 웹 어플리케이션이다.
  *
  * ◆ 웹 애플리케이션의 특징과 중복된 객체 생성
  *
  * 웹 애플리케이션은 다른 애플리케이션과는 다른 특징이 있는데,
  * 짧은 시간 동안 다수의 클라이언트로부터 요청을 받는다는 것이다.
  *
  * 그런데 여기서 문제가 발생한다.
  * 우리가 과거에 만들었던 상품 주문과 할인 정책 코드는 클라이언트 요청이 들어올 때마다
  * MemberServiceImpl, OrderServiceImpl, MemoryMemberRepository, FixDiscountPolicy 객체를 만든다는 것이다.
  *
  * ▶ AppConfig appConfig = new AppConfig();
  * ▶ MemberService memberService = appConfig.memberService();
  * ▶ OrderService orderService = appConfig.orderService();
  *
  * 만약 100 대의 클라이언트가 주문 생성을 요청하면 한 객체당 x100 무려 400개의 객체가 만들어지는 셈이다.
  * 동일한 작업을 하는 객체를 중복해서 계속 생성하는 것은 비효율적인 메모리 낭비가 된다.
  * 어떻게 하면 이 문제를 해결할 수 있을까?
  *
  * 해결방안은 MemberServiceImpl, OrderServiceImpl, MemoryMemberRepository, FixDiscountPolicy 객체를
  * 딱 1개씩만 생성하고 모두가 객체를 공유하도록 하면 된다.
  *
  * ◆ 싱글톤 패턴 - Singleton pattern
  *
  * 싱글톤이란 디자인 패턴 중 하나로써, 클래스의 인스턴스가 딱 1개만 생성되는 것을 보장한다.
  *
  *
  * */

}
