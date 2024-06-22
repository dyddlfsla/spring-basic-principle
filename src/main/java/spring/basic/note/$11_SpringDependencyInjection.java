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
  * 1) 코드가 간단해지지만 주입된 의존관계를 변경할 방법이 없어 테스트하기 어려운 코드가 된다.
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
  * ―――――――――――――――――――――――――――――――――――――――――――――――――――――――――
  *
  * Ⅱ. @Autowired 의 옵션 처리.
  *
  * @Autowired 를 사용할 때, 의존관계가 주입되지 않아도 동작해야 될 때가 있다.
  * 그런데 @Autowired 는 `required` 옵션의 기본값이 `true` 이므로 의존관계 주입시 주입할 대상이 존재하지 않으면 예외가 발생한다.
  *
  * 이런 상황에서 다음과 같은 방법으로 @Autowired 에 옵션을 지정할 수 있다.
  *
  * 1) @Autowired(required = false)
  *  의존관계 주입시, 주입할 대상이 존재하지 않으면 메소드 자체도 호출하지 않는다.
  *
  *  @Autowired(required = false)
  *  public void setNoBean1(Member noBean1) { //Member 클래스는 스프링 Bean 이 아니므로, 주입할 Member 객체가 없는 상태다.
  *    System.out.println("noBean1 = " + noBean1); //메소드 자체가 호출되지 않으므로 아무것도 출력되지 않는다.
  *  }
  *
  * 2) org.springframework.lang.Nullable;
  *  @Nullable 을 붙이면 의존관계 주입 시 주입할 대상이 없으면 null 을 참조하도록 한다.
  *
  *  @Autowired
  *  public void setNoBean2(@Nullable Member noBean2) {
  *    System.out.println("noBean2 = " + noBean2); // noBean2 는 null 을 참조한다. noBean2 = null 출력.
  *  }
  *
  * 3) Optional<T>
  * 의존관계 주입 시 주입할 대상이 없으면 Optional.empty 를 주입한다.
  *
  * @Autowired
  * public void setNoBean3(Optional<Member> noBean3) {
  *   System.out.println("noBean3 = " + noBean3); // noBean3 는 Optional.empty 를 참조한다. noBean3 = Optional.empty 출력.
  * }
  *
  * ※ @Nullable 이나 Optional<T> 는 스프링 프레임워크 전반에 지원된다.
  *   예를 들어, 생성자 주입에서 일부분 필드에만 해당 어노테이션을 사용할 수 있다.
  *
  * ―――――――――――――――――――――――――――――――――――――――――――――――――――――――――
  *
  * Ⅲ. !important 생성자 주입을 사용하라.
  *
  * 앞서, 스프링에서 의존관계를 주입할 때 생성자 주입, 수정자(Setter) 주입, 필드 주입, 일반 메소드 주입
  * 을 사용할 수 있다고 했는데 사실 `생성자 주입`을 사용하는 것이 가장 좋은 방법이다.
  *
  * 과거에는 수정자 주입이나 필드 주입을 많이 사용했었지만, 최근에는 스프링을 포함한 여러 DI 프레임워크에서
  * 생성자 주입을 권장하고 있다.
  *
  * ◆ 생성자 주입(Constructor Injection)이 가장 좋은 이유.
  * 1) 스프링에서 Bean 들의 의존관계는 한번 설정되면 애플리케이션 종료까지 의존관계를 변경할 일이 없다.
  *    - 오히려 의존관계가 변경되서는 안된다.(불변해야 한다.) 이러한 의존관계 불변을 잘 구현하는 방식이 생성자 주입이다.
  * 2) 만약 수정자(Setter) 주입을 사용한다면, Setter 메소드를 만들고 public 으로 열어두어야 한다.
  *    - 누군가 Setter 메소드를 잘못 사용할 수 있으며, 의존관계를 변경시킬 수 있는 Setter 메소드를 만드는 것은 좋은 설계가 아니다.
  * 3) 생성자 주입은, Bean 이 생성되면서 호출되는 생성자 코드로 딱 1번만 의존관계를 주입시키고 더 이상 호출되지 않는다.
  *    - 의존관계를 불변하게 설계할 수 있다.
  * 4) 의존관계 필드에 final 키워드를 사용할 수 있다. 따라서 생성자에 의존관계 주입이 누락된 경우 컴파일 오류를 통해 쉽게 파악할 수 있다.
  *    - 프로그램에서 가장 좋은 오류는 컴파일 오류이다.
  *    - 수정자 주입을 제외한 나머지 방식은 모두 생성자 호출 이후에 호출되므로 final 를 사용할 수 없다.
  *
  * 정리하자면 생성자 주입은 프레임워크에 의존하지 않고 순수한 자바 언어의 특징을 잘 살리는 주입 방식이다.
  * 또한, 불가피하게 의존관계가 변경되는 경우에는 생성자 주입을 기본으로 사용하되, 필요한 부분에서만 수정자(Setter) 주입을 사용하면 된다.
  * 생성자 주입과 수정자 주입은 둘 다 같이 사용할 수 있다.
  *
  * solution => 항상 생성자 주입을 기본으로 사용하라! 그리고 가끔 의존관계 변경이 필요한 경우에만 수정자 주입을 사용한다.
  *
  * ―――――――――――――――――――――――――――――――――――――――――――――――――――――――――――
  *
  * Ⅳ. Lombok 라이브러리 추가하기.
  *
  * 실무에서 스프링 개발을 하다보면, 대부분의 Bean 의존관계가 불변이고 final 키워드도 사용하게 된다.
  * 그러면 생성자 코드도 작성해야 하고.. 주입 받은 객체를 필드에 대입시켜야 하고..
  * 조금 더 코드를 간편하게 작성할 수는 없을까?
  *
  * ◆ Lombok 라이브러리
  * Lombok 이란, Java 애플리케이션 개발에서 비즈니스 로직에 포함되진 않지만,
  * 반복적으로 작성해야 하는 코드(일명, 보일러플레이트-boilerplate) 들을 좀 더 효율적으로 작성할 수 있도록 도와주는 라이브러리이다.
  *
  * ◆ Lombok 적용 방법.
  * 1) build.gradle 에 라이브러리 추가.
  *   gradle 프로젝트라면, build.gradle 파일에서 lombok 라이브러리를 추가해야 한다.
  *
  * configurations {
  *   	compileOnly {
  *   		extendsFrom annotationProcessor
  *   	}
  *   }
  *
  * dependencies {
  *  	compileOnly('org.projectlombok:lombok')
  *  	annotationProcessor('org.projectlombok:lombok')
  *
  *  	testCompileOnly('org.projectlombok:lombok')
  *  	testAnnotationProcessor('org.projectlombok:lombok')
  * }
  * 프로젝트를 다시 빌드한 후, 라이브러리가 잘 추가되었는지 확인한다.
  *
  * 2) IntelliJ Preference -> Setting -> plugin -> lombok 플러그인 추가.
  *             Preference -> Annotation Processor -> Enable annotation processing 체크.
  *
  * 3) Lombok 의 어노테이션 활용하기.
  *   - @Getter, @Setter : 클래스 필드에 대해 자동으로 Getter, Setter 메소드를 추가해준다.
  *   - @ToString : 클래스의 toString() 메소드를 자동으로 재정의해준다.
  *   - @EqualsAndHashCode : 클래스의 equals() 와 hashCode() 를 자동으로 재정의해준다.
  *   - @NoArgsConstructor, @AllArgsConstructor, @RequiredArgsConstructor : 클래스의 생성자 코드를 추가해준다.
  *     ▶ @NoArgsConstructor: 매개변수가 없는 기본 생성자 생성.
  *     ▶ @AllArgsConstructor: 클래스에 선언된 모든 필드를 매개변수로 갖는 생성자 생성.
  *     ▶ @RequiredArgsConstructor: final 이 붙은 필드나 @NonNull 이 붙은 필드를 매개변수로 갖는 생성자 생성.
  *   - @Data : @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor 를 모두 포함하는 종합 어노테이션이다.
  *   - @Builder : 클래스에 Builder 패턴 코드를 추가해준다.
  *
  * ――――――――――――――――――――――――――――――――――――――――――――――――――――――――――
  *
  * Ⅴ. @Autowired 사용 시, 같은 타입의 Bean 이 2개 이상인 경우
  *
  * 앞서, @Autowired 를 사용하여 의존 관계를 주입하면
  * @Autowired 는 주입 대상과 같은 타입의 Bean 을 찾아서 주입해준다.
  *
  * @Component
  * public class RateDiscountPolicy implements DiscountPolicy {...}
  *
  * @Autowired
  * public OrderServiceImpl(DiscountPolicy discountPolicy, MemberRepository memberRepository) {
  *   this.discountPolicy = discountPolicy;
  *   this.memberRepository = memberRepository;
  * }
  *
  * 예를 들어, 위의 코드에서 스프링 컨테이너는 this.discountPolicy 변수에 알맞은 스프링 Bean 을 찾아 주입시켜주어야 한다.
  * 이때, @Autowired 는 타입을 통해 Bean 을 찾는데 매개변수가 DiscountPolicy discountPolicy 로 선언되어 있으므로
  * DiscountPolicy 타입의 Bean 을 검색한다. 그리고 DiscountPolicy 타입의 Bean 은 RateDiscountPolicy 객체 하나만 있으므로
  * 안정적으로 this.discountPolicy 에 RateDiscountPolicy 객체를 주입시켜 줄 수 있다.
  *
  * 그렇다면, 만약 DiscountPolicy 타입의 Bean 이 하나가 아니라 2개 이상이면 어떻게 될까?
  *
  * @Component //FixDiscountPolicy 에 @Component 를 붙여 스프링 Bean 으로 등록한다.
  * public class FixDiscountPolicy implements DiscountPolicy { ... }
  *
  * 이제, DiscountPolicy 타입의 Bean 은 RateDiscountPolicy 와 FixDiscountPolicy 로 2개가 된다.
  * @Autowired 는 두 개의 Bean 중 어느 Bean 을 this.discountPolicy 변수에 넣어야 할까?
  * 실제로 코드를 실행해보면, 스프링은 다음과 같은 예외를 발생시킨다.
  *
  * UnsatisfiedDependencyException: Error creating bean with name 'orderServiceImpl'
  * expected single matching bean but found 2: fixDiscountPolicy,rateDiscountPolicy
  *
  * 말 그대로 매칭되는 Bean 이 두 개가 있어, 어느 것을 주입시킬지 몰라 의존관계 주입에 실패했다는 것이다.
  * 이런 상황에서, 매개변수 타입을 하위타입으로 FixDiscountPolicy discountPolicy 와 같이 선언하면
  * FixDiscountPolicy 객체는 하나만 존재하므로 다시 혼동없이 주입시킬 수 있을 것이다.
  *
  * 그러나 이런 해결 방식은 DIP 원칙을 위반할 뿐만 아니라, 이름만 다르고 아예 타입이 동일한 bean 이 2개 이상 존재하는 경우
  * 다시 문제가 된다.
  *
  * 스프링은 Bean 검색 시 Bean 이 중복되는 문제에 대해 여러가지 해결책을 제시한다.
  *
  * ―――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――
  *
  *
  *
  * */

}
