package spring.basic.order;

import spring.basic.discount.DiscountPolicy;
import spring.basic.discount.FixDiscountPolicy;
import spring.basic.discount.RateDiscountPolicy;
import spring.basic.member.Member;
import spring.basic.member.MemberRepository;
import spring.basic.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {

  //  private final MemberRepository memberRepository = new MemoryMemberRepository();
  //  private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
  /*
 self-taught
  *  //기존에 있던 정액 할인.
  *  DiscountPolicy discountPolicy = new FixDiscountPolicy();
  *  //새로운 할인 정책인 정율 할인 정책을 구현하고 필드에서 부품 갈아끼우듯 새 할인 정책으로 바꾸었다.
  *  DiscountPolicy discountPolicy = new RateDiscountPolicy();
  *
  * 개발한 코드를 보자면,
  * 우리는 인터페이스와 인터페이스 구현 객체로 나누어 개발했고 이는 역할과 구현을 충실히 분리한 것이다 => OK.
  * 인터페이스를 통한 다형성을 활용했다. => OK.
  * OCP, DIP 같은 객체지향 설계 원칙도 잘 지켰다. => NO. 그렇게 보이지만 실제로는 지켜지지 않았다.
  * 필드 타입으로 인터페이스 DiscountPolicy 를 선언하고 구현 객체로 RateDiscountPolicy 와 FixDiscountPolicy 를 부품 끼우듯 잘 했는데
  * 뭐가 문제일까?
  * 클래스 간의 의존관계를 잘 살펴보자. OrderServiceImpl 클래스는 인터페이스 DiscountPolicy 에 의존하고 있을 뿐만 아니라,
  * 구현 클래스인 RateDiscountPolicy 클래스와 와 FixDiscountPolicy 클래스에도 의존하고 있는 것이다.
  * 따라서, 구현화에 의존하지 말고 추상화에만 의존하라는 DIP 를 위반한다.
  * 또한, 정책을 바꾸기 위해서는 new FixDiscountPolicy -> new RateDiscountPolicy 로 코드를 바꾸어야 하는데
  *      확장에는 열려 있고 변경에는 닫혀 있어야 한다는 OCP 도 위반하게 된다.
  * OCP 이건 대체 말이 되는 원칙일까? 프로그램의 기능이 수정된다는건 코드 관점에서 본다면, 당연히 코드 수정을 의미하는데
  * 어떻게 코드가 변하지 않는데 프로그램 기능이 확장된다는 소리일까?
  *
  * ◆ 어떻게 이 문제를 해결할 것인가?
  * => 간단하다. 구현 객체에는 의존하지 않고 인터페이스에만 의존하도록 하면 된다.
  * private DiscountPolicy discountPolicy; //끝.
  *
  * 이게 무슨..
  * 객체 생성 코드가 없다. discountPolicy 변수에 아무런 객체도 대입되지 않았으므로
  * 저 코드가 실행되면 NullPointerException 예외가 발생할거라는건 옆집 강아지 바둑이도 알 것이다.
  *
  * */
  private final DiscountPolicy discountPolicy;
  private final MemberRepository memberRepository;
 /*
 self-taught
  * 이제 AppConfig 클래스에서 OrderServiceImpl 와 구현 memberRepository, discountPolicy 를 연결시켜 주자 (의존성 주입).
  *
  * private final DiscountPolicy discountPolicy;
  * private final MemberRepository memberRepository;
  * 그럼 이제, OrderServiceImpl 클래스는 구현 객체인 new MemoryMemberRepository(), new FixDiscountPolicy()에 대해 몰라도 된다.
  * 그냥 추상화에만 의존하면 되는 것이다.
  *
  * OrderServiceImpl 입장에서 본다면 마치, 의존 관계가 외부에서 주입해주는 것 같다.
  * 그래서 이것을 DI(Dependency Injection) 의존 관계 주입 또는 의존성 주입이라고 한다.
  *
  * */

  public OrderServiceImpl(DiscountPolicy discountPolicy, MemberRepository memberRepository) {
    this.discountPolicy = discountPolicy;
    this.memberRepository = memberRepository;
  }

  @Override
  public Order createOrder(Long memberId, String itemName, int itemPrice) {
    Member member = memberRepository.findById(memberId);
    int discountPrice = discountPolicy.discount(member, itemPrice);

    return new Order(memberId, itemName, itemPrice, discountPrice);
  }
}
