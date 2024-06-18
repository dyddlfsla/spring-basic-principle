package spring.basic.note;

public class $5_AppConvertedToSpring {

  /*
 self-taught
  *
  * Ⅰ. Spring Framework 로 전환하기.
  *
  * ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
  *
  * ApplicationContext 를 스프링 컨테이너라고 한다.
  * 기존에는 개발자가 직접 AppConfig 클래스를 이용해서 객체를 생성하고 DI 를 했지만, 이제는 스프링 컨테이너를 통해서 한다.
  * 스프링 컨테이너가 객체들을 생성하고 관리하며, 의존관계를 주입시켜주는 것이다.
  * 즉, 스프링 컨테이너는 스프링 프레임워크에서의 DI 컨테이너인 것이다.
  *
  * 스프링 컨테이너는 @Configuration 이 붙은 클래스(AppConfig.class)를 설정 정보로 사용한다.
  * 또, 여기에서 @Bean 이 붙은 메서드를 모두 호출하고 반환된 객체를 스프링 컨테이너에 등록한다.
  * 이렇게 스프링 컨테이너에 등록된 객체를 스프링 Bean 이라고 한다.
  *
  * - 스프링 Bean 은 메서드의 이름을 스프링 Bean 의 이름으로 사용한다.
  * - @Bean(name = "ooo") 과 같이 별도의 이름을 지정할 수도 있다. ※ 권장되는 방식은 아니다.
  *
  * 기존에는, 필요한 객체를 AppConfig 를 통해서 직접 개발자가 찾았지만, 이제 스프링 컨테이너를 통해 필요한 스프링 Bean 을 찾는다.
  * 스프링 컨테이너의 getBean() 메소드를 통해 찾을수 있다.
  *
  * ac.getBean(String name, Class<T> requiredType);
  *
  * 정리하자면, 개발자가 직접 자바 코드로 하던 일들을 이제 스프링 컨테이너에 객체를 스프링 Bean 으로 등록하고
  * 필요할때마다 스프링 컨테이너에서 꺼내쓰는 방식으로 바뀌었다.
  *
  * 그런데, 스프링 컨테이너를 사용하면 어떤 장점이 있을까?
  *
  * ――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――
  *
  *
  *
  * */

}
