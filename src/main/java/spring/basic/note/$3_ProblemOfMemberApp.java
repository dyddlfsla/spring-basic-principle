package spring.basic.note;

public class $3_ProblemOfMemberApp {

  /*


  Ⅰ. 회원상품주문 애플리케이션과 OOP.

  자, 회원상품주문 애플리케이션을 만들어 보았다.

  각 역할에 따라, 인터페이스를 분리하고 인터페이스의 구현체를 사용함으로써 추상화에 의존하도록 만들었다.
  그렇다면 이 애플리케이션은 객체지향 설계 원칙을 충분히 따르고 있는걸까?

  public class OrderServiceImpl implements OrderService {

    // 기존에 있던 정액 할인.
    DiscountPolicy discountPolicy = new FixDiscountPolicy();

    // 새로운 할인 정책인 정율 할인 정책을 구현하고 필드에서 부품 갈아끼우듯 새 할인 정책으로 바꾸었다.
    DiscountPolicy discountPolicy = new RateDiscountPolicy();

    ...

  }

  개발한 코드를 보자면,
  우리는 인터페이스와 인터페이스 구현 객체로 나누어 개발했고 이는 역할과 구현을 충실히 분리한 것이다 => OK.
  인터페이스를 통한 다형성을 활용했다. => OK.
  OCP, DIP 같은 객체지향 설계 원칙도 잘 지켰다. => NO. 그렇게 보이지만 실제로는 지켜지지 않았다.
  필드 타입으로 인터페이스 DiscountPolicy 를 선언하고 구현 객체로 RateDiscountPolicy 와 FixDiscountPolicy 를
  부품 끼우듯 잘 했는데 뭐가 문제일까?
  클래스 간의 의존관계를 잘 살펴보자. OrderServiceImpl 클래스는 인터페이스 DiscountPolicy 에 의존하고 있을 뿐만 아니라,
  구현 클래스인 RateDiscountPolicy 클래스와 와 FixDiscountPolicy 클래스에도 의존하고 있는 것이다.

  따라서, 구현화에 의존하지 말고 추상화에만 의존하라는 DIP 를 위반한다.
  또한, 정책을 바꾸기 위해서는 new FixDiscountPolicy -> new RateDiscountPolicy 로 코드를 바꾸어야 하는데
       확장에는 열려 있고 변경에는 닫혀 있어야 한다는 OCP 도 위반하게 된다.
  OCP 이건 대체 말이 되는 원칙일까? 프로그램의 기능이 수정된다는건 코드 관점에서 본다면, 당연히 코드 수정을 의미하는데
  어떻게 코드가 변하지 않는데 프로그램 기능이 확장된다는 소리일까?

  ◆ 어떻게 이 문제를 해결할 것인가?
  => 간단하다. 구현 객체에는 의존하지 않고 인터페이스에만 의존하도록 하면 된다.
  private DiscountPolicy discountPolicy; //끝.

  하지만 new 연산자를 통한 객체 생성 코드가 없다. discountPolicy 변수에 아무런 객체도 대입되지 않았으므로
  저 코드가 실행되면 NullPointerException 예외가 발생하고 말 것이다.

  ――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――

  Ⅱ. 관심사의 분리.

  애플리케이션을 하나의 공연이라고 가정하자. 각각의 역할은 곧 인터페이스가 되고, 역할을 수행하는 배우는 구현 클래스가 된다.
  여기서 가장 중요한 문제.

  => 역할과 배우가 있다는건 알겠어. 근데 그 역할을 어떤 배우가 할지 누가 결정하는가??
  DiscountPolicy discountPolicy = new FixDiscountPolicy();
  DiscountPolicy discountPolicy = new RateDiscountPolicy();

  사실 위 코드는, 배우가 상대 배우를 직접 고르고 있는 것과 같다.
  그러니 배우가 서로 각자의 배우에게 의존하는 꼴이 되어버리는 것이다.
  배우가 상대 배우를 직접 고르는 것은 적절하지 않다. 배우를 고르는 일은 배우가 아닌 '감독(공연기획자)'이 해야할 일인 것이다.

  자, 이제 제 3자로서, 역할에 배우를 지정해주는 감독(공연기획자)이 필요하다.
  감독은 오로지 배우을 지정하는 일만 해주면 된다.

  ―――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――

  Ⅲ. 공연기획자 만들기.

  ◆ AppConfig 클래스의 등장
  코드로 돌아가, 애플리케이션의 전체 동작 방식을 구성(config) 하기 위해, 구현 객체를 생성하고
  연결해주는 별도의 설정 클래스를 만들자.

  public class AppConfig {

    public OrderService orderService() { // 생성자를 통해 의존성을 주입한다고 해서, 생성자 주입이라고도 한다.
      return new OrderServiceImpl(discountPolicy(), memberRepository());
    }

    private MemberRepository memberRepository() {
      return new MemoryMemberRepository();
    }

    private DiscountPolicy discountPolicy() {
      //return new RateDiscountPolicy();
      return new FixDiscountPolicy();
  }

  AppConfig 클래스에서 OrderServiceImpl 와 구현 memberRepository, discountPolicy 를 연결시켜 준다. (의존성 주입).

  public class OrderServiceImpl implements OrderService {

    private final DiscountPolicy discountPolicy;
    private final MemberRepository memberRepository;

    public OrderServiceImpl(DiscountPolicy discountPolicy, MemberRepository memberRepository) { // 생성자 주입.
      this.discountPolicy = discountPolicy;
      this.memberRepository = memberRepository;
    }

  }

  이제, OrderServiceImpl 클래스는 구현 객체인 new MemoryMemberRepository(), new FixDiscountPolicy()에 대해 몰라도 된다.
  그냥 추상화(인터페이스)에만 의존하면 되는 것이다.

  OrderServiceImpl 입장에서 본다면 마치, 의존 관계가 외부에서 주입해주는 것 같다.
  그래서 이것을 DI(Dependency Injection) 의존 관계 주입 또는 의존성 주입이라고 한다.

  AppConfig 클래스를 만들어줌으로서 관심사를 확실히 분리할 수 있게 되었다.
  배역, 배우를 생각해보자.
  AppConfig 는 감독(공연기획자)이다.
  AppConfig 는 필드에 대입될 구현 객체를 선택한다. 즉, 역할을 연기할 배우를 선택하는 것이다.
  각 구현 클래스들은 어떤 클래스와 협력하게 될지 모른다. 그저 AppConfig 로부터 통보 받는다.(DI, 의존성 주입)
  Config 라는 이름처럼 애플리케이션이 어떻게 동작해야 할지 그 구성 정보를 책임지는 것이다.

  배우가 상대 배우 상관없이 자기 연기만 하면 되듯, 이제 각 구현 클래스들은 구현된 자기 일만 수행하면 된다.
  마찬가지로, 감독은 연기는 몰라도 되고 배우를 지정하는 자기 일에만 책임을 지면 된다.

  이제 프로그램이 변경될 때, 클라이언트 코드(MemberService, OrderService 등)는 변경되지 않는다.
  OCP, DIP 원칙이 지켜진다.
  대신, 구성 영역(AppConfig)는 당연히 변경되어야 한다.

  */

}
