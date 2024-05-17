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
  *   public OrderService orderService() { // OrderService 객체와 discountPolicy, memberRepository 객체를 연결시켜 주고 있다.
  *     return new OrderServiceImpl(discountPolicy(), memberRepository());
  *   }
  *
  * ※ 원래 스프링은 Bean 을 생성하고, Bean 간의 의존 관계를 주입하는 단계가 나누어져 있다.
  *   그런데 이렇게 자바 코드로 스프링 Bean 을 등록하면, 생성자를 호출하면서 동시에 의존관계 주입까지 한번에 처리된다.
  *
  *
  *
  * */

}
