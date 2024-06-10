package spring.basic.note;

public class $4_InversionOfControl {

  /*
 self-taught
  *
  * ◆ IoC, DI, 그리고 컨테이너
  *
  * Ⅰ. 제어의 역전 IoC, Inversion of Control
  *
  * 제어의 역전이란, 말 그대로 프로그램의 흐름에 대한 제어권이 '역전'되어서 개발자가 제어권을 가진 것이 아니라,
  * 프레임워크와 컨테이너가 제어권을 가지게 된 것을 말한다.
  *
  * 예를 들어, 다음과 같은 코드는
  *
  * public class Main {
  *  public static void main(String[] args) {
  *      Service service = new Service();
  *      Client client = new Client(service);
  *      client.doSomething();
  *   }
  * }
  *
  * 개발자가 직접 필요한 객체를 new 연산자로 생성하고, 메소드를 호출한다. 프로그램 흐름의 제어권이 개발자에게 있는 것이다.
  *
  *  public static void main(String[] args) {
  *     // IoC 컨테이너 생성
  *     ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
  *
  *     // 클라이언트는 서비스 객체를 직접 생성하지 않고, 컨테이너로부터 받음
  *     Client client = context.getBean(Client.class);
  *     client.doSomething();
  * }
  *
  *
  * 하지만, 위와 같은 코드에서는, 더 이상 개발자가 직접 객체를 생성하고 관리 하지 않는다. 개발자 대신 ApplicationContext 객체가
  * 객체를 생성, 관리하고 객체 간의 의존관계까지 연결시켜 준다. 즉, 객체의 관리, 의존성 연결 같은 제어 권한이 개발자에서 컨테이너로 '역전'된 것이다.
  *
  * 이처럼 프로그램에서 제어 흐름이 개발자나 코드에 의해 직접 조작되는 것이 아닌 외부에서
  * 조작되는 것을, 제어의 역전이라고 한다.
  *
  * ――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――
  *
  * Ⅱ. 프레임워크 vs. 라이브러리
  *
  * - 라이브러리 (Library)
  * 라이브러리는 개발자가 필요할 때 호출하여 사용할 수 있는 코드의 모음이다.
  * 보통 특정 기능을 수행하기 위한 함수, 클래스, 모듈 등이 포함될 수 있는데, 중요한 것은 라이브러리는 개발자가 직접 호출하여 사용하며,
  * 제어의 흐름은 개발자에게 있다는 것이다.즉, 개발자가 코드의 제어 흐름을 직접 작성하고 라이브러리를 호출하는 방식입니다.
  *
  * - 프레임워크 (Framework)
  * 프레임워크는 소프트웨어 개발을 위한 구조와 규칙을 제공하는 뼈대이다.
  * 프레임워크는 개발자가 특정 기능을 구현할 때 사용할 수 있는 인터페이스, 추상 클래스, 라이프 사이클 이벤트 등을 제공하며,
  * 프레임워크의 규칙에 따라 개발자가 코드를 작성해야 한다. 프레임워크는 개발자가 코드의 일부를 작성하고
  * 나머지 부분은 프레임워크가 제공하는 기능을 사용하여 완성한다. 따라서 제어의 흐름은 프레임워크에게 있게 되는 것이다.
  *
  * ―――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――
  *
  * Ⅲ. 의존관계 주입 Dependency Injection
  *
  * 우선 먼저 의존관계가 무엇인지 알아보자. 의존관계란 쉽게 말해서, 하나의 객체가 다른 객체를 사용하거나 참조하느냐를 말한다.
  *
  * public class OrderServiceImpl implements OrderService {
  *
  *  private final DiscountPolicy discountPolicy;
  *  private final MemberRepository memberRepository;
  *  ...
  * }
  *
  * 위 코드에서, 클래스 OrderServiceImpl 은 필드로 DiscountPolicy, MemberRepository 를 사용하고 있으므로,
  * OrderServiceImpl 은 DiscountPolicy 와 MemberRepository 에 의존한다고 말할 수 있다.(정적 의존관계)
  * 그럼 의존 관계 '주입'이라는 것은 무엇일까? 위 코드를 살펴보면 discountPolicy 변수에 객체 생성 코드가 없으므로 불완전해 보인다.
  * 그러나 실제로는 아무 문제 없다. 왜냐하면 아래와 같이, 컨테이너가 대신 객체를 생성하고 변수에 대입시켜주기 때문이다.
  *
  * public class AppConfig {
  *
  *   public OrderService orderService() {
  *     return new OrderServiceImpl(discountPolicy(), memberRepository());
  *
  *   private MemberRepository memberRepository() {
  *     return new MemoryMemberRepository();
  *  }
  *
  *   private DiscountPolicy discountPolicy() {
  *     return new FixDiscountPolicy();
  *  }
  *  ...
  * }
  *
  * 이처럼, 객체 간의 의존성 연결이 직접적으로 이루어지는 것이 아니라, 외부를 통해 이루어지기 때문에 '주입' 된다고 표현하는 것이다.
  *
  * 한 가지 더, 의존 관계는 2가지 관점에서 살펴볼 수 있는데
  * 첫 번째는 정적 의존 관계 (Static Dependency) 로서, 정적 의존 관계는 컴파일 시간에 결정되고
  * 소스 코드나 클래스의 구조에 의해 결정된다. 즉, 코드의 직접적인 관계로 나타나게 된다.
  * 두 번째는, 동적 의존 관계 (Dynamic Dependency) 로서, 동적 의존 관계는 컴파일 시간이 아닌 런타임 시간에 결정되며,
  * 객체의 실제 생성 및 메서드 호출과 같은 동적인 행위에 의해 결정되는 것을 말한다.
  *
  * 정적 의존 관계는 애플리케이션을 실행하지 않고도 그 의존 관계를 파악할 수 있지만,
  * 실제로 어떤 객체가 사용되는지는 알 수없다. 즉, DiscountPolicy 에 FixDiscount 가 올지, RateDiscount 가 올지는 모른다는 것이다.
  * 때문에, 동적 의존 관계를 알기 위해서는 실제로 프로그램을 실행해야 한다.
  *
  * 프로그램을 실행하게 되면 AppConfig 에 의해 생성된 객체가 discountPolicy, memberRepository 변수에 대입되고
  * 실제로 어떤 객체가 대입되었는지 따라 동적 의존 관계가 정의된다.
  *
  * => 의존관계 주입을 사용하면, 정적 의존 관계를 변경하지 않고도, 동적 의존 관계를 변경할 수 있게 된다.
  *
  * ―――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――
  *
  * Ⅳ. IoC 컨테이너, DI 컨테이너
  *
  * AppConfig 처럼 외부에서 객체를 생성하고 관리하면서 의존 관계를 연결해 주는 존재를
  * IoC 컨테이너 또는 DI 컨테이너라고 한다.
  * 최근에는 의존 관계에 초점을 맞춰서 주로 DI 컨테이너라는 용어를 더 많이 쓴다.
  * - 애플리케이션의 멤버들을 하나씩 조립한다는 뜻에서 어셈블러, 오브젝트 팩토리라는 용어도 쓰이긴 한다.
  *
  *
  *
  * */

}
