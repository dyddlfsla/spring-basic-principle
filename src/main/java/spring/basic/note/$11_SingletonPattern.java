package spring.basic.note;

public class $11_SingletonPattern {

  /*
 self-taught
  *
  * Ⅰ. 싱글톤 패턴 - Singleton pattern
  *
  * 싱글톤이란 디자인 패턴 중 하나로써, 클래스의 인스턴스가 딱 1개만 생성되는 것을 보장한다.
  * 코드 관점에서 보자면, 객체의 인스턴스를 2개 이상 생성하지 못하도록 막아야 한다.
  *
  * public class SingletonService {
  *
  *    // 자기 자신을 참조하는 static 변수를 선언하고 동시에 자신의 인스턴스를 미리 생성시켜 놓는다.
  *    private static final SingletonService instance = new SingletonService();
  *
  *    //인스턴스가 필요하면 getter 를 통해서만 얻을 수 있다.
  *    public static SingletonService getInstance() {
  *      return instance;
  *    }
  *
  *    //생성자에 private 접근 제어자를 적용해서, 외부에서 new 연산자를 호출하는 것을 막는다.
  *    private SingletonService() {}
  *
  * }
  *
  * ※ 참고로 위의 코드는, Singleton Pattern 을 구현하는 여러 방법 중의 하나일뿐이다.
  *   Singleton Pattern 을 구현하는 코드는 많다.
  *
  * 앞서 MemberServiceImpl, OrderServiceImpl, MemoryMemberRepository, FixDiscountPolicy 객체에 이러한 싱글톤 패턴을 적용시키면,
  * 클라이언트가 회원가입, 상품 주문을 할 때마다 객체를 중복 생성시키는 것을 막을 수 있다.
  *
  *
  *
  * ◆ Singleton Pattern 이 가진 문제점.
  * 싱글톤 패턴을 통해 인스턴스를 효율적으로 사용할 수 있다는 장점이 있지만, 장점만 있는 것은 아니다.
  *
  * 1) 싱글톤 코드를 구현하는데 있어 비용이 소모된다.
  *    위의 싱글톤 코드는 간단한 예시일뿐, 실무에서 싱글톤 패턴을 적용하는 것은 많은 비용을 초래한다.
  * 2) 의존 관계상, 클라이언트가 구체화 클래스에 의존하게 된다.
  *    private static final SingletonService instance = new SingletonService();
  *    객체 지향 설계의 기본 원칙 중 DIP 원칙을 위반하게 된다.
  * 3) 또한, OCP 원칙도 위반할 가능성이 높다.
  * 4) 테스트 코드를 작성하는 것이 까다롭다.
  * 5) 내부 필드값을 변경하거나 초기화 하기 어렵다.
  * 6) 코드의 유연성이 떨어지고 확장 가능성이 축소된다.
  * 7) 사실, 싱글톤 패턴은 Anti Pattern 으로 취급된다.
  *
  * ――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――
  *
  * Ⅱ. Singleton 과 Spring Container.
  *
  * 스프링 컨테이너는, 앞서 말한 싱글톤 패턴의 단점을 모두 제거하면서도 스프링 Bean 을 싱글톤 방식으로 관리한다.
  *
  * ▶ MemberService memberService1 = ac.getBean("memberService", MemberService.class);
  * ▶ MemberService memberService2 = ac.getBean("memberService", MemberService.class);
  *
  * ▶ assertThat(memberService1).isSameAs(memberService2); // true
  *
  * 스프링 컨테이너는 개발자가 직접 싱글톤 패턴을 구현하지 않아도 자동으로 스프링 Bean 을 싱글톤 방식으로 생성한다.
  * 스프링 컨테이너는 싱글톤 컨테이너이기도 한 것이다. 이렇게 싱글톤 객체를 생성하고 관리하는 기능을 싱글톤 레지스트리라고 한다.
  *
  * 스프링 컨테이너를 사용함으로써, 개발자는 각 클래스에 싱글톤 패턴을 작성하지 않아도 되며,
  * DIP, OCP, 테스트, private 로 인한 불이익 등으로부터 완전히 자유롭게 싱글톤을 사용할 수 있다.
  *
  * ※ 스프링의 기본 Bean 관리 방식은 싱글톤이지만, 싱글톤만 지원하는 것은 아니다. 요청할 때마다 새로운 객체를 만들게도 할 수 있다.
  * 이것을 Bean 의 스코프(scope) 라고 한다.
  *
  * ――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――
  *
  * Ⅲ. 싱글톤 방식의 주의점. !important
  *
  * 싱글톤 패턴 코드을 사용하든, 스프링 컨테이너를 사용하든, 객체 인스턴스를 하나만 생성해서 공유할 때에는
  * 꼭 명심해야 하는 것이 있는데, 싱글톤 객체는 상태를 유지(stateful) 해서는 안된다!
  * 항상 무상태(stateless)여야 한다는 것이다.
  *
  * ▶ 특정 클라이언트에게 의존적인 필드가 있으면 안된다.
  * ▶ 특정 클라이언트가 값을 변경할 수 있는 필드가 있으면 안된다.
  * ▶ 가급적 `읽기`만 가능해야 한다.
  * ▶ 필드를 사용하기 보다 자바에서 공유되지 않는 지역변수, 파라미터, ThreadLocal 을 사용해야만 한다.
  * ▶ 스프링 Bean 의 필드에 공유 값을 설정하면 큰 장애가 발생할 수 있다.
  *
  * 마치 멀티 스레드 환경에서 스레드가 공유하는 객체에 대해서 항상 조심해야 하는 것과 같다.
  *
  * @Configuration
  * static class TestConfig{
  *   @Bean
  *   public StatefulService statefulService() {
  *     return new StatefulService();
  *   }
  * }
  *
  * public class StatefulService {
  *
  *  private int price; //상태를 유지하는 필드.
  *  //싱글톤 객체는 항상 무상태(Stateless)로 설계해야 한다.
  *
  *  public void order(String name, int price) {
  *    System.out.printf("name = %s, price = %d%n", name, price);
  *    this.price = price; //여기가 문제
  *  }
  *
  *  public int getPrice() {
  *    return price;
  * }
  *
  * StatefulService statefulService1 = ac.getBean(StatefulService.class);
  * StatefulService statefulService2 = ac.getBean(StatefulService.class);
  *
  *  //ThreadA: A 사용자가 10000원 주문
  *  statefulService1.order("userA", 10000);
  *  //ThreadB: B 사용자가 20000원 주문
  *  statefulService2.order("userB", 20000);
  *
  *  int price = statefulService1.getPrice(); // price = 20000;
  *
  * 사용자 A 의 실제 금액은 10000원인데 조회 시, 20000원으로 잘못 나오고 있다.
  * 왜냐하면, StatefulService 객체는 스프링 Bean 으로서 1개만 생성되어 모두에게 공유되고 있는데,
  * 접근하는 스레드마다 StatefulService 객체의 데이터(price)를 변경할 수 있기 때문이다.
  *
  * ※ 상태를 갖지 않는 무상태에 대해 오해하는 경우가 있는데,
  * 무상태라는 것은 필드를 가지면 안된다라는 뜻이 결코 아니다.
  * 필드는 가지되, 해당 필드의 상태가 다른 객체들에 의해서 공유되서는 안된다는 것이다.
  *
  * 예를 들어,
  * public class OrderServiceImpl implements OrderService {
  *
  *   private final DiscountPolicy discountPolicy;
  *   private final MemberRepository memberRepository;
  * }
  * 이 클래스는 상태를 가지고 있는데, 무상태의 관점에서 잘못 설계된 클래스인가?
  * 아니다. 이 역시 올바르게 설계된 클래스이다. discountPolicy 와 memberRepository 를 갖고 있기는
  * 하지만 이 discountPolicy 와 memberRepository 는 생성자 주입을 통해 주입되어
  * 싱글톤으로 유지되고 final 까지 붙여주어 변경이 불가능 하도록 막아버렸다.
  * 즉, discountPolicy, memberRepository 는 무상태(stateless)인 것이다.
  *
  * */

}
