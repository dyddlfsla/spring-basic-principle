package spring.basic.note;

public class $11_SpringDependencyInjection {


  /*
 self-taught
  *
  * Ⅰ. 다양한 의존관계 주입 방법
  *
  * 스프링 컨테이너를 통해 스프링 Bean 을 생성했다면, 이 Bean 들 사이의 의존관계를 주입시켜주어야 한다.
  * 스프링에서는 Bean 의존관계를 주입시킬 때, 다음과 같이 보통 4가지 방법을 사용할 수 있다.
  *
  * ◆ 생성자 주입
  * 생성자 주입은 말 그대로 스프링 Bean 객체의 생성자를 사용해서 의존관계를 주입시키는 것이다.
  * 스프링이라고 해서 자바 객체를 마법처럼 생성하는 것은 아니다.
  * 스프링 컨테이너가 Bean 을 생성시키기 위해서는 Bean 이 가지고 있는 생성자 코드를 사용해 new 생성자(); 를 호출하게 되는데,
  * 이때, Bean 의 의존관계를 주입시켜주는 것이다.
  *
  * 생성자 주입은 다음과 같은 특징이 있다.
  * 1) 의존관계 주입이 생성자 호출 시점에 딱 1번만 이루어진다.
  * 2) Bean 의 관계가 불변이면서, 필수 관계일때 사용할 수 있다.
  * 3) !important 만약 클래스의 생성자가 1개만 있다면 @Autowired 어노테이션을 생략할 수 있다.
  *
  * @Autowired
  *   public MemberServiceImpl(MemberRepository memberRepository) { //MemberServiceImpl 의 생성자 코드.
  *     this.memberRepository = memberRepository;
  *   }
  *
  * @Autowired
  *  public OrderServiceImpl(DiscountPolicy discountPolicy, MemberRepository memberRepository) { //OrderServiceImpl 의 생성자 코드.
  *    this.discountPolicy = discountPolicy;
  *    this.memberRepository = memberRepository;
  *  }
  *
  * ◆ 수정자(Setter method) 주입.
  *
  * 수정자 주입은 클래스의 수정자 메소드(Setter method) 를 사용해서 의존관계를 주입하는 것을 말한다.
  * 수정자 주입은 다음과 같은 특징을 가진다.
  * 1) 의존관계가 선택적이거나, 변경 가능성이 있는 경우에 사용한다.
  * 2) 클래스의 Setter 메소드를 이용한다.
  *
  * @Autowired
  * public void setDiscountPolicy(DiscountPolicy discountPolicy) { //Setter 메소드를 통한 의존관계 주입.
  *   this.discountPolicy = discountPolicy;
  * }
  * @Autowired
  * public void setMemberRepository(MemberRepository memberRepository) { //Setter 메소드를 통한 의존관계 주입.
  *   this.memberRepository = memberRepository;
  * }
  *
  * ◆ 필드 주입
  * 클래스의 필드에 직접 의존관계를 주입하는 방식이다.
  * 필드 주입은 다음과 같은 특징을 가지고 있다.
  * 1) 코드가 간단해지지만 주입된 의존관계를 변경할 방법이 없어지므로 테스트하기 어려운 코드가 된다.
  * 2) DI 컨테이너에 완전히 종속되므로 DI 컨테이너가 없다면 아무것도 할 수 없다.
  * 3) 사용하지 말자.
  *
  * @Component
  * public class OrderServiceImpl implements OrderService {
  *
  *  @Autowired
  *  private final DiscountPolicy discountPolicy; //클래스의 필드에 바로 의존관계를 주입한다.
  *  @Autowired
  *  private final MemberRepository memberRepository;
  * }
  *
  * ◆ 일반 메소드 주입
  * 클래스에 의존관계 주입을 위한 별도의 메소드를 만들고 해당 메소드로 의존관계를 주입하는 것이다.
  * 한번에 여러 필드를 주입할 수 있다는 특징이 있지만, 이 방식은 잘 사용되지 않는 방식이다.
  *
  * @Autowired
  * public void init(MemberRepository memberRepository, DiscountPolicy discountPolicy) { //init() 이라는 별도의 메소드로 주입.
  *   this.memberRepository = memberRepository;
  *   this.discountPolicy = discountPolicy;
  * }
  *
  *
  * */

}
