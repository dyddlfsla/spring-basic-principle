package spring.basic.note;

public class $9_ApplicationAndSingleton {

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
  * ▶ MemberService memberService = appConfig.memberService(); //클라이언트가 회원가입을 요청하면 실행되는 코드.
  * ▶ memberService.join();
  * ▶ OrderService orderService = appConfig.orderService(); //클라이언트로가 상품 주문을 요청하면 실행되는 코드.
  * ▶ orderService.createOrder();
  *
  * public MemberService memberService() {
  *   return new MemberServiceImpl(memberRepository());
  * }
  * public OrderService orderService() {
  *   return new OrderServiceImpl(discountPolicy(), memberRepository());
  * }
  * public MemberRepository memberRepository() {
  *   return new MemoryMemberRepository();
  * }
  * public DiscountPolicy discountPolicy() {
  *   return new FixDiscountPolicy();
  * }
  *
  * 만약 100 대의 클라이언트가 주문 생성을 요청하면 한 객체당 x100 무려 400개의 객체가 만들어지는 셈이다.
  * 동일한 작업을 하는 객체를 중복해서 계속 생성하는 것은 비효율적인 메모리 낭비가 된다.
  * 어떻게 하면 이 문제를 해결할 수 있을까?
  *
  * 해결방안은 MemberServiceImpl, OrderServiceImpl, MemoryMemberRepository, FixDiscountPolicy 객체를
  * 딱 1개씩만 생성하고 모두가 객체를 공유하도록 하면 된다.
  *
  * ―――――――――――――――――――――――――――――――――――――――――――――――――――――――――――
  *
  * Ⅱ. 싱글톤 패턴 - Singleton pattern
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
  * Ⅲ. Singleton 과 Spring Container.
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
  * Ⅳ. 싱글톤 방식의 주의점. !important
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
  * 즉, OrderService 는 무상태(stateless)인 것이다.
  *
  * ――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――
  *
  * Ⅴ. @Configuration 과 싱글톤
  *
  * 스프링 컨테이너는 스프링 Bean 을 싱글톤으로 만들어 관리한다..
  * 그런데 이상한 점이 있다. 스프링 컨테이너의 설정 정보로 사용되는 AppConfig.class 의 코드를 보면,
  *
  * @Configuration
  * public class AppConfig {
  *
  *  @Bean(name = "memberService")
  *  public MemberService memberService() {
  *    return new MemberServiceImpl(memberRepository()); //memberService() 에서 memberRepository() 를 호출함.
  *  }
  *
  *  @Bean
  *  public OrderService orderService() {
  *    return new OrderServiceImpl(discountPolicy(), memberRepository()); //orderService() 에서 memberRepository() 를 호출함.
  *  }
  *
  *  @Bean
  *  public MemberRepository memberRepository() {
  *    return new MemoryMemberRepository();
  *  }
  *
  * 스프링 컨테이너는 설정 정보 클래스의 내용을 읽은 뒤
  * @Bean 이 붙은 메소드를 호출하여 반환된 객체들을 스프링 Bean 으로 등록한다.
  * 그런데 memberRepository() 메소드는 memberService() 와 orderService() 메소드가 호출될 때에도 같이 호출되는 상태이다.
  *
  * 결론적으로 스프링 컨테이너가 스프링 저장소를 구성할 때, new MemoryMemberRepository 는 총 3번 호출되는 것이다.
  * 그렇다면 MemoryMemberRepository 객체는 3개가 되어야 할텐데, 앞서 말한 싱글톤 방식과 맞지 않는다.
  *
  * 일단 orderService() 와 memberService() 호출 시 만들어진 MemoryMemberRepository 객체를 비교해보자,
  * 두 MemoryMemberRepository 객체는 동일하다고 나온다. 그럼 new MemoryMemberRepository 가 사실은 한번만 호출되는게 아닐까?
  *
  * @Bean(name = "memberService")
  *  public MemberService memberService() {
  *    System.out.println("call AppConfig.memberService"); // 메소드 사이에 출력 메소드를 넣어보자.
  *    return new MemberServiceImpl(memberRepository());
  *  }
  *
  * 결과는,
  * call AppConfig.memberService
  * call AppConfig.memberRepository
  * call AppConfig.orderService
  *
  * 이상하다. 스프링 저장소가 구성되면서 new MemoryMemberRepository(); 가 3번 호출되어야 할텐데
  * 실제로는 new MemoryMemberRepository(); 가 한번만 호출되고 있는 것이다.
  * 어떻게 이런 일이 일어나는 걸까?
  *
  * ―――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――
  *
  * Ⅵ. @Configuration 과 바이트코드 조작의 마법
  *
  * 스프링 컨테이너는 싱글톤 레지스트리다. 즉, 스프링 Bean 이 싱글톤이 되도록 무조건 보장해야 한다.
  * 그런데 스프링이라고 하더라도 이미 작성된 자바 코드를 마음대로 바꿀 순 없으므로, 작성된 자바 코드대로
  * new MemoryMemberRepository(); 는 3번 호출되어야 하는 것이 맞다.
  * 그러나 출력 메소드를 삽입하여 확인한 결과, new MemoryMemberRepository(); 는 한번만 호출되었다.
  *
  * 스프링은 이 문제를 해결하기 위해서 클래스의 바이트코드를 변환하는 라이브러리(Code Generation Library, CGLIB) 를 사용한다.
  *
  * 우선, 설정 정보를 담고 있는 AppConfig 클래스도 하나의 Bean 으로 등록되는데, 이를 출력해보자.
  *
  * AppConfig bean = ac.getBean(AppConfig.class);
  * System.out.println("bean = " + bean);
  *
  * ▶ bean = spring.basic.AppConfig$$SpringCGLIB$$0@63fbfaeb
  *
  * 원래 클래스의 toString() 을 호출하면, 클래스명@16진수해시코드가 출력되는데
  * 클래스명이 AppConfig 로 끝나지 않고 뒤에 $$SpringCGLIB$$ 이 붙어 있다. 이게 무엇일까?
  *
  * 사실, 지금 스프링 컨테이너에는 우리가 작성한 AppConfig 가 등록되어 있는 것이 아니라,
  * AppConfig 를 상속하고 있는 AppConfig@CGLIB 클래스가 등록되어 있고,
  * 스프링 컨테이너는 이 AppConfig@CGLIB 의 내용을 가지고 스프링 Bean 을 만들고 있는 것이다.
  *
  * 추측하자면, AppConfig@CGLIB 은 AppConfig 를 상속한 뒤, (AppConfig@CGLIB 가 자식 클래스였기에  Bean 타입 조회 시 검색될 수 있었다.)
  * memberRepository() 메소드를 다음과 같이 오버라이딩하고 있을 것이다.
  *
  * ※ AppConfig@CGLIB 의 예상 코드 (CGLIB 의 실제 코드는 훨씬 더 복잡하다.)
  * @Override
  * @Bean
  * public MemberRepository memberRepository() {
  *  if (memoryMemberRepository 가 이미 있으면) {
  *    return 스프링 컨테이너에서 찾아서 반환;
  *  } else {
  *    return 스프링 컨테이너에 없는 Bean 이라면 MemoryMemberRepository 를 새로 등록하고 반환;
  *  }
  *  ...
  * }
  *
  * @Bean 이 붙은 메소드는 재정의되어서 이미 스프링 Bean 이 존재하는 경우, 존재하는 Bean 을 찾아서 반환하고,
  * 해당 Bean 이 없다면 새로 만들어 등록 후, 반환하게 되는 것이다.
  * 그리고 이 덕분에 싱글톤이 가능한 것이다.
  *
  * ◆ 만약 @Configuration 을 적용하지 않고, @Bean 만 적용하면 어떻게 될까?
  *
  * AppConfig.class 로 돌아가 @Configuration 을 삭제하고, 다시 스프링 컨테이너에서 조회해보자.
  *
  * bean = spring.basic.AppConfig@5d43661b
  * 이제서야 우리가 작성한 AppConfig 가 Bean 으로 등록된 것을 알 수 있다.
  *
  * call AppConfig.memberService
  * call AppConfig.orderService
  * call AppConfig.memberRepository
  * call AppConfig.memberRepository
  * call AppConfig.memberRepository
  *
  * 게다가, 처음 우리가 예상했던대로 new MemoryMemberRepository() 가 3번 호출되는 것을 볼 수 있다.
  * 또한, 동일한 인스턴스인지 확인하는 테스트 코드도 모두 실패한다. 서로 다른 3개의 MemoryMemberRepository 객체가 생성된 것이다.
  *
  * 당연히, @Configuration 을 지운 채 스프링 컨테이너의 설정 정보로 사용하는 것은 잘못된 것이다.
  * @Configuration 이 없어도 스프링 Bean 이 등록되기는 하지만, 싱글톤을 보장하지도 않을뿐더러,
  * 의존관계 주입 역시 컨테이너의 개입 없이 자바 코드에 설정된대로 작동할 뿐이다.
  *
  * 한가지만 기억하면 된다. 스프링 관련 설정 정보가 있는 클래스에는 무조건 @Configuration 을 붙이자.
  *
  *
  *
  *
  * */

}
