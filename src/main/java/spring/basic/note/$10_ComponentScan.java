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
  * 이것들을 하나씩 @Bean 메소드로 등록하는 것은 그다지 효율적이지 않기 때문이다. 또한,
  *
  * @Bean
  * public OrderService orderService() {
  *   return new OrderServiceImpl(discountPolicy(), memberRepository());
  * }
  * @Bean
  * public MemberRepository memberRepository() {
  *   return new MemoryMemberRepository();
  * }
  *
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
  *
  * ――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――
  *
  * Ⅱ. @ComponentScan 의 기본 탐색 위치와 대상
  *
  * @ComponentScan 을 통해 스프링 Bean 을 만들 때, 모든 자바 클래스를 다 확인하고 @Component 어노테이션이 붙어 있는지 확인하는 것은
  * 시간이 오래 걸릴 뿐더러 비효율적이다. 그래서 @ComponentScan 을 사용할 때 부가적인 옵션을 추가하여 탐색 위치를 지정할 수 있다.
  *
  * ▶ @ComponentScan(basePackages = "spring.basic.member") //spring.basic.member 패키지부터 탐색을 시작한다.
  *
  * - basePackages: 탐색을 시작할 패키지 위치를 지정한다. 이 패키지를 포함하여 하위 패키지들 검색하여 @Component 가 붙은 클래스를 찾아낸다.
  * - basePackages = { "spring.basic.member", "spring.basic.order"} 처럼 시작 위치를 2개 이상 지정할 수도 있다.
  * - basePackageClasses: 해당 클래스가 속한 패키지를 탐색 시작 패키지로 지정한다.
  * - basePackages 를 따로 지정하지 않으면, 어디부터 탐색을 할까?
  *   - 지정하지 않으면 @ComponentScan 이 붙어 있는 설정 정보 클래스가 있는 패키지를 탐색 시작 패키지로 사용한다.
  *
  * ※ 권장하는 방법
  * basePackages 를 사용하여 별도로 탐색 위치를 지정하지 말고, 그냥 @ComponentScan 이 붙은 설정 정보 클래스를
  * 프로젝트의 최상단 root 에 위치시키는 것이다.
  * 스프링부트에서도 이러한 방식을 기본으로 제공한다.
  *
  * 이 프로젝트의 경우, root 패키지가 spring.basic 이다. 그렇다면, spring.basic 패키지에 ComponentScanAppConfig 클래스를 위치시키고
  * @ComponentScan 만 붙여주고 basePackages 조건은 생략한다.
  *
  * 이렇게 하면 spring.basic 패키지와 그 하위 패키지에 존재하는 클래스들이 모두 탐색 대상이 된다.
  * 또한, 원래 설정 정보는 프로젝트를 대표하는 정보이므로 프로젝트의 root 위치에 두는 것이 좋다.
  * 스프링부트를 사용한다면 설정 정보 클래스에 @ComponentScan 대신 @SpringBootApplication 을 붙이고 루트 위치에 놓으면 된다.
  * (@SpringBootApplication 에 @ComponentScan 도 포함되어 있다.)
  *  => 사실 자바의 어노테이션은 상속의 개념이 없다.
  *    그럼에도 어느 어노테이션이 다른 애노테이션을 포함할 수 있는 것은 스프링이 제공하는 기술이다.
  *
  * ◆ @ComponentScan 의 탐색 대상
  * @ComponentScan 은 @Component 가 붙은 클래스 뿐만 아니라 다음과 같은 어노테이션이 붙은 클래스도 탐색하고 그에 따른 부가 기능을 제공한다.
  *
  * 1) @Component: 컴포넌트 스캔에서 사용.
  * 2) @Controller: 대상을 등록하고 Spring mvc 의 컨트롤러로 인식한다.
  * 3) @Service: Service 의 경우, 추가적으로 해주는 기능은 없지만, 개발자에게 해당 클래스가 서비스 로직을 담당하고 있다는 것을 알려준다.
  * 4) @Repository: 대상을 등록하고, 스프링 데이터 접근 계층에서 사용되는 것으로 인식
  * 5) @Configuration: 스프링 설정 정보로 인식하고, 싱글톤이 유지되도록 작업한다.
  *
  * ―――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――
  *
  * Ⅲ. @ComponentScan - Filter
  *
  * @ComponentScan 은 includeFilters 와 excludeFilters 조건을 사용하여 특정 클래스를 컴포넌트 스캔 대상에서 포함/제외시킬 수 있다.
  *
  * @ComponentScan(includeFilters = @Filter(type = FilterType.ANNOTATION, classes = MyIncludeComponent.class),
  *               excludeFilters = @Filter(type = FilterType.ANNOTATION, classes = MyExcludeComponent.class))
  *
  * 위 코드는 @MyIncludeComponent 어노테이션이 붙은 클래스는 탐색 대상에 include(포함)시키고,
  * @MyExcludeComponent 어노테이션이 붙은 클래스는 탐색 대상에서 exclude(제외)시킨다.
  *
  * 이때, FilterType 은 다음과 같이 열거형 상수로 지정된다.
  *
  * public enum FilterType {
  *  ANNOTATION,             //특정 어노테이션이 붙은 대상을 조사하여 포함/제외시킨다.
  *  ASSIGNABLE_TYPE,        //해당 타입과 자식 타입을 조사하여 포함/제외시킨다.
  *  ASPECTJ,                //AspectJ 를 사용해서 포함/제외시킨다.
  *  REGEX,                 //정규표현식을 사용해서 포함/제외시킨다.
  *  CUSTOM;                //TypeFilter 를 직접 구현하여 포함/제외시킨다.
  * }
  *
  * ※ 탐색 대상으로 포함시키는데 있어 @Component 면 충분하기 때문에, includeFilters 는 잘 사용되지 않는다.
  *   excludeFilters 는 사용되기는 하나 역시 자주 사용되지는 않는다.
  *   최근 스프링부트는 컴포넌트 스캔을 기본으로 제공하고 있는데, 여러 옵션을 건드려 사용하는 것 보다는
  *   스프링이 제공하는 기본 설정 방식을 최대한 유지하는 것을 추천한다.
  *
  * ―――――――――――――――――――――――――――――――――――――――――――――――――――――――――――
  *
  * Ⅳ. @ComponentScan 을 통한 스프링 중복 등록과 충돌 문제.
  *
  * @ComponentScan 을 통해 Bean 을 등록할 때 만약 이름을 동일하게 하면 어떻게 될까?
  *
  * @Component("nameA")
  * public class MemberServiceImpl {}
  *
  * @Component("nameA")
  * public class OrderServiceImpl {}
  *
  * 위의 두 클래스는 자동으로 등록되는 Bean 인데 이름이 동일하다.
  * 이렇게 자동 등록 Bean vs. 자동 등록 Bean 이 충돌하면 스프링은 ConflictingBeanDefinitionException 예외를 발생시킨다.
  *
  * 그러면 수동 등록 Bean 과 자동 등록 Bean 이 충돌하게 어떻게 될까?
  * 이런 경우, SpringBoot 2.1 이전에는 수동으로 등록된 Bean 이 자동 등록된 Bean 을 오버라이딩(덮어씌우기)하도록 했다.
  * 그러나 이제는 스프링부트가 BeanDefinitionOverrideException 예외를 발생시킨다.
  *
  * 사실, 이렇게 스프링 Bean 등록 방식을 통일하지 않은 것은 불안정한 작업 방식이다.
  * 결국 프로그램은 여러 개발자들이 모여 코드를 작성하는데 누구는 수동으로 Bean 을 등록하고 있고,
  * 누구는 자동으로 Bean 을 등록하면 이것이 쌓이고 쌓여 어느 시점에는 정말 잡기 힘든 버그가 된다.
  * 스프링부트가 스프링 Bean 중복 시 오버라이딩을 허용하지 않고 예외를 발생시키도록 한것도 이러한 이유들 때문이다.
  *
  *
  *
  * */

}
