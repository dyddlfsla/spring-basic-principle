package spring.basic.note;

public class $10_ComponentScan {

  /*
 self-taught
  *
  *
  * Ⅰ. ComponentScan 을 통한 스프링 Bean 등록
  *
  * 지금까지는 @Configuration 클래스의 메소드에 @Bean 을 붙이거나, XML 파일의 <bean> 태그를 통해서
  * 스프링 Bean 을 등록했는데, 사실 이러한 방식은 잘 쓰이지 않는 방법이다.
  * 예제에서는 등록할 스프링 Bean 이 많지 않으므로 별 문제가 없었지만, 실무에서는 수 백개의 스프링 Bean 이 생성되는데,
  * 이것들을 하나씩 @Bean 메소드로 등록하는 것은 그렇게 효율적이지 않기 때문이다.
  * 또한,
  *
  * @Bean
  * public OrderService orderService() {
  *   return new OrderServiceImpl(discountPolicy(), memberRepository());
  * }
  * @Bean
  * public MemberRepository memberRepository() {
  *   return new MemoryMemberRepository();
  * }
  * 위와 같은 코드는, 스프링 Bean 을 등록함과 동시에, 의존관계 주입도 이루어지기 때문에 좋은 코드라고 할 수 없다.
  *
  * 그래서 스프링 Bean 등록할 때 또 다른 방법인, @ComponentScan 방식을 사용할 수 있다.
  * 우선, 기존의 AppConfig.class 대신 새로운 ComponentScanAppConfig 클래스를 만들자.
  *
  * @Configuration
  * @ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class))
  * public class ComponentScanAppConfig {}
  *
  * 컴포넌트스캔 기능을 사용하려면, 클래스를 생성하고 클래스에 @ComponentScan 을 붙여준다. 클래스 멤버는 아무것도 없어도 된다.
  * 원래는 @ComponentScan 만 붙여도 되지만, 여기에서는 기존에 작성한 예제 코드를 유지하기 위해
  * Filter 를 추가하였다.
  *
  * 그 다음에는, 이제 스프링 Bean 으로 등록할 클래스들(MemberServiceImpl, OrderServiceImpl, MemoryMemberRepository..)에
  * @Component 을 붙여주면 된다.
  *
  * @Component
  * public class MemberServiceImpl implements MemberService {
  *
  * @Component
  * public class OrderServiceImpl implements OrderService {
  *
  * 근데 여기서 문제가 하나 생기는데, 앞서 AppConfig 에서는 @Bean 메소드로 스프링 Bean 을 등록함과 동시에 의존관계를 연결했었다.
  * 그러나 @ComponentScan 을 사용하게 되면 스프링 Bean 은 등록할 수 있지만, 의존관계 주입이 불가능하다.
  * 이때, @Autowired 를 사용하여 의존관계 주입을 설정할 수 있다.
  * @Autowired 는 기본적으로 해당 타입으로 스프링 Bean 을 조회한 뒤 의존관계를 주입시켜 준다.
  *
  * @Autowired
  * public OrderServiceImpl(DiscountPolicy discountPolicy, MemberRepository memberRepository) {
  *   this.discountPolicy = discountPolicy;
  *   this.memberRepository = memberRepository;
  * } //@Autowired 가 없다면 스프링 컨테이너는 discountPolicy, memberRepository 변수에 무슨 객체를 주입해야 할지 몰라 에러가 발생한다.
  *
  * @ComponentScan 은 기본적으로 다음과 같이 작동한다.
  *
  * 1) @ComponentScan 은 @Component 가 붙은 모든 클래스를 스프링 Bean 으로 등록한다.
  * 2) 클래스 이름에서 첫 글자를 소문자로 바꾼 뒤 그것을 Bean 의 이름으로 사용한다. (AppConfig 에서는 @Bean 메소드의 이름이 Bean 이름이 되었다.)
  *    - 만약 Bean 이름을 따로 설정해야 한다면, @Component("memberServiceTwo") 와 같이 할수도 있다.
  * 3) @Autowired 를 사용하면 기본적으로 해당 타입과 맞는 Bean 이 있는지 검색한 후 의존관계를 주입시켜 준다.
  *
  * */

}
