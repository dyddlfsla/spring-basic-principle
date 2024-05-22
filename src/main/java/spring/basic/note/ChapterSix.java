package spring.basic.note;

public class ChapterSix {

  /*
 self-taught
  *
  * Ⅰ. Spring Container
  *
  * ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class)
  *
  * -ApplicationContext 를 스프링 컨테이너라고 한다.
  * -ApplicationContext 는 인터페이스이다.
  * -스프링 컨테이너는 XML 기반으로 만들수도 있고, 어노테이션 기반의 자바 설정 클래스로도 만들 수 있다.
  *  XML 을 사용한 방식은 현재 잘 사용되지 않는다.
  *
  * 우리가 사용한 방식은, 어노테이션 기반의 자바 설정 클래스로 스프링 컨테이너를 만든 것이다.
  *
  * 우선, 구성 정보로 사용할 클래스에 @Configuration 어노테이션을 붙여준다.
  *
  * @Configuration
  * public class AppConfig {
  *   ...
  * }
  *
  * 이제 이 설정 정보 클래스를, AnnotationConfigApplicationContext 클래스의 생성자 매개변수로 전달해준다.
  *
  * ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class)
  * -AnnotationConfigApplicationContext 클래스 는 ApplicationContext 의 구현 클래스 중 하나이다.
  *
  * ※ 스프링 컨테이너를 말할 때, BeanFactory 와 ApplicationContext 를 구분해서 이야기한다.
  * 그러나 BeanFactory 를 직접 사용하는 경우는 거의 없으므로, 일반적으로 ApplicationContext 를 스프링 컨테이너라고 한다.
  *
  * ――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――
  *
  * Ⅱ. Spring Container 생성 과정.
  *
  * 스프링 컨테이너를 생성할 때에는 구성 정보(여기서는 AppConfig.class)를 전달해주어야 한다.
  *
  * 스프링 컨테이너가 생성되면 스프링 컨테이너는 내부에 스프링 Bean 객체를 저장하는 스프링 Bean 저장소를 구성한다.
  *
  * Spring Bean 저장소
  * +----------+----------+
  * | Bean 이름 | Bean 객체 |
  * +----------+----------+
  * |   Data1  |   Data2  |
  * +----------+----------+
  * |   Data4  |   Data5  |
  * +----------+----------+
  * |   Data7  |   Data8  |
  * +----------+----------+
  *
  *
  * ① 스프링 컨테이너는 저장소를 구성한 후, 구성 정보 클래스에서 @Bean 어노테이션이 붙은 메소드들을 모두 찾아낸다.
  * 그리고, 해당 메소드가 반환하는 객체들을 모두 스프링 Bean 객체로 등록시킨다.
  *
  * ② 스프링 Bean 등록 시, 기본적으로 해당 객체를 반환하는 메소드의 이름을 Bean 의 이름으로 사용한다.
  *   - @Bean(name = "value") 와 같이, 별도의 Bean 이름을 지정할수도 있다.
  *   - 스프링 Bean 의 이름은 항상 다른 이름을 부여해야 한다. 만약 같은 이름을 부여하면 다른 Bean 이 무시되거나, 기존 Bean 덮어씌워버리는 오류가 발생한다.
  *
  * ③ 스프링 Bean 들간의 의존 관계 설정
  *   스프링 컨테이너는 스프링 Bean 간의 의존 관계를 파악하고 연결시켜 준다.
  *   @Bean
  *   public OrderService orderService() {
  *     return new OrderServiceImpl(discountPolicy(), memberRepository());
  *   }
  *   // OrderService 객체와 discountPolicy, memberRepository 객체를 연결시켜 주고 있다.
  *
  * ※ 원래 스프링은 Bean 을 생성하고, Bean 간의 의존 관계를 주입하는 단계가 나누어져 있다.
  *   그런데 이렇게 자바 코드로 스프링 Bean 을 등록하면, 생성자를 호출하면서 동시에 의존관계 주입까지 한번에 처리된다.
  *
  * ――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――
  *
  * Ⅲ. 스프링 컨테이너에 등록된 모든 Bean 조회.
  *
  * AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
  *
  * void findAllBeans() {
  *   String[] beanDefinitionNames = ac.getBeanDefinitionNames();
  *
  *   for (String beanDefinitionName : beanDefinitionNames) {
  *   Object bean = ac.getBean(beanDefinitionName);
  *   System.out.printf("name = %s , object = %s%n", beanDefinitionName, bean);
  *   }
  * }
  *
  * getBeanDefinitionNames(): 스프링 컨테이너에 등록된 모든 Bean 의 이름을 String[] 형태로 반환한다.
  * getBean(String name): name 으로 Bean 객체를 찾아 반환한다.
  *
  * -스프링 Bean 은 Role(역할)을 가지고 있는데,
  * ROLE_APPLICATION: 일반적으로 사용자가 정의한 Bean
  * ROLE_INFRASTRUCTURE: 스프링 프레임워크가 내부적으로 사용하기 위해 만든 Bean
  *
  * ――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――
  *
  * Ⅳ. 스프링 Bean 조회 - 기본
  *
  * AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
  *
  * ◆ Bean 이름으로 찾기
  * -getBean(String name) 를 호출하면, 이름이 일치하는 Bean 객체를 반환한다.
  *
  * ◆ Bean 타입으로 찾기
  * -getBean(Class<T> requiredType) 을 호출하면, 타입과 일치하는 Bean 객체를 반환한다.
  *
  * ◆ Bean 이름으로 찾기 실패한 경우
  * Bean 을 찾지 못했다는 예외가 발생한다.
  * -NoSuchBeanDefinitionException: No bean named 'XXX' available
  *
  * ――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――
  *
  * Ⅴ. 스프링 Bean 조회 - 동일한 타입의 bean 이 2개 이상인 경우
  *
  * void findBeanByTypeOnDuplicate() {
  *   assertThrows(NoUniqueBeanDefinitionException.class,
  *     () -> ac.getBean(MemberRepository.class));
  * }
  *
  * getBean(Class<T> requiredType) 을 호출하여 타입으로 스프링 bean 을 조회 시,
  * 같은 타입의 스프링 bean 이 2개 이상 있다면 다음과 같은 예외가 발생한다.
  * NoUniqueBeanDefinitionException: No qualifying bean of type 'spring.basic.member.MemberRepository' available:
  * 메소드가 어떤 스프링 bean 을 반환해야 할지 특정할 수 없으므로 발생하는 예외이다.
  *
  * 이렇게 같은 타입의 bean 이 2개 이상 있는 경우에는, 한 개의 bean 만 특정할 수 있도록 bean 의 이름까지 넣어주는게 좋다.
  * getBean(String name, Class<T> requiredType)
  *
  * ◆ 특정 타입의 Bean 을 모두 검색하기.
  *
  * Map<String, MemberRepository> foundBeans = ac.getBeansOfType(MemberRepository.class);
  *
  * getBeansOfType(@Nullable Class<T> type) 메소드를 사용하면,
  * 해당 타입에 맞는 모든 Bean 들이 담겨 있는 Map 컬렉션을 얻을 수 있다.
  *
  * ――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――
  *
  * Ⅵ. 스프링 bean 조회 - 상속 관계
  *
  * - 스프링 bean 을 조회할 때, 부모 타입을 사용하면 자식 타입에 해당하는 bean 까지 모두 조회된다.
  *
  * 예를 들어, RateDiscountPolicy 클래스와 FixDiscountPolicy 클래스가 DiscountPolicy 인터페이스를 구현하고 있는 경우,
  * DiscountPolicy 타입의 스프링 bean 을 조회하면 RateDiscountPolicy 객체와 FixDiscountPolicy 객체 둘 다 같이 조회될 것이고
  * 어떤 bean 을 반환해야 할지, 특정할 수 없으므로 예외가 발생한다.
  *
  * DiscountPolicy discountPolicy = ac.getBean(DiscountPolicy.class); // 2개 이상의 bean 이 검색되고, 예외가 발생.
  *
  * 위에서 배운 것처럼, bean 을 특정할 수 있도록 이름도 매개 변수로 추가해주면 된다.
  *
  * DiscountPolicy discountPolicy = ac.getBean("rateDiscountPolicy", DiscountPolicy.class);
  *
  * ◆ Object 타입으로 스프링 bean 조회하기.
  * 자바의 모든 클래스는 Object 클래스의 하위 클래스이다.
  * Object 타입으로 스프링 bean 을 조회하면 어떻게 될까?
  * => 사용자가 정의한 스프링 bean 을 포함한 스프링 컨테이너에 담긴 모든 bean 객체가 조회된다.
  *
  *
  * */

}
