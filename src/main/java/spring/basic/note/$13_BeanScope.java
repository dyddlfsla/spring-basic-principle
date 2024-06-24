package spring.basic.note;

public class $13_BeanScope {

  /*
 self-taught
  *
  * Ⅰ. Bean Scope 란?
  *
  * Bean 의 스코프(Scope)는 `Bean 의 범위`를 의미하는데, 어떤 범위를 말하냐면
  * Bean 인스턴스가 메모리 위에 생성되어 `존재할 수 있는 범위(기간)`를 말한다.
  *
  * 지금까지는 스프링 컨테이너에 의해 Bean 이 한번 생성되면, 해당 Bean 은 싱글톤 방식으로 관리되어
  * 계속 메모리 위에 존재하다가 프로그램이 종료되면 그때서야 스프링 컨테이너와 함께 소멸되는 생명주기를 갖고 있다고 배웠다.
  *
  * 그렇다면 이때, Bean 의 스코프는 싱글톤 스코프에 해당하는 것이고,
  * 싱글톤 스코프란 스프링 컨테이너의 시작 시점 ~ 스프링 컨테이너 종료 시점까지 Bean 인스턴스가 존재하는 것을 말한다.
  *
  * 스프링의 기본 스코프는 싱글톤 스코프이지만, 싱글톤 스코프외에도 스프링은 다양한 스코프를 지원한다.
  *
  * 1) singleton Scope: 기본 스코프. 스프링 컨테이너의 시작과 종료까지 Bean 인스턴스가 존재한다.
  * 2) prototype Scope: 스프링 컨테이너가  Bean 의 생성과 의존관계 주입까지만 관여하고 더 이상은 관여하지 않는 방식이다.
  *                     매우 짧은 범위의 스코프이다.
  *
  * ◆ 웹 관련 스코프
  * 3) request: HTTP 요청이 서버로 들어오고 나갈 때까지 Bean 인스턴스가 유지되는 스코프이다.
  * 4) session: 웹 세션이 생성되고 종료될 때까지 Bean 인스턴스가 유지되는 스코프이다.
  * 5) application: 웹 의 서블릿 컨텍스트와 같은 범위로 Bean 인스턴스가 유지되는 스코프이다.
  *
  * Bean 의 스코프는 다음과 같이 지정할 수 있다.
  *
  * 1) @ComponentScan 을 통해 Bean 을 등록하는 경우,
  * @Scope("prototype")
  * @Component
  * public class OrderServiceImpl implements OrderService {...}
  *
  * 2) @Bean 메소드를 통해 Bean 을 등록하는 경우,
  * @Scope("prototype")
  * @Bean
  * public OrderService orderService() {
  *   return new OrderServiceImpl();
  * }
  *
  * */

}
