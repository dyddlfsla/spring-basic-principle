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
  * 5) application: 웹의 서블릿 컨텍스트와 같은 범위로 Bean 인스턴스가 유지되는 스코프이다.
  *
  * Bean 의 스코프는 다음과 같이 지정할 수 있다.s
  *
  * 1) @ComponentScan 을 통해 Bean 을 등록하는 경우,
  * @Scope("prototype") //@Scope 어노테이션 사용.
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
  * ――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――
  *
  * Ⅱ. prototype Scope
  *
  * 프로토타입 스코프는, 스프링 컨테이너가 시작과 동시에 자동으로 Bean 인스턴스를 만드는 방식이 아니다.
  * 클라이언트로부터 요청이 오면 그때서야 스프링 컨테이너는 프로토타입 Bean 을 생성하고 의존관계를 주입한뒤 반환한다.
  * 또한 스프링 컨테이너는 생성된 Bean 인스턴스를 끝까지 관리하지 않는다.
  * 때문에 프로토타입 Bean 은 싱글톤 Bean 과는 다른 차이점이 존재하는데,
  *
  * 스프링 컨테이너는 클라이언트 요청이 올 때마다 새로운 프로토타입 Bean 을 생성한다. 따라서 Bean 인스턴스는 싱글톤 객체가 아니다.
  * 그리고 스프링 컨테이너는 Bean 의 초기화 콜백 메소드까지는 호출하지만 소멸 콜백 메소드는 호출하지 않는다.
  * 즉, @PreDestroy 는 사용할 수 없는 것이다.
  *
  * @Test
  *  void prototypeBeanFind() {
  *   AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
  *   System.out.printf("find prototypeBean1%n");
  *   PrototypeBean bean1 = ac.getBean(PrototypeBean.class);
  *
  *   System.out.printf("find prototypeBean2%n");
  *   PrototypeBean bean2 = ac.getBean(PrototypeBean.class);
  *
  *   assertThat(bean1).isNotSameAs(bean2); //PrototypeBean 은 싱글톤 객체가 아니라, 각자가 개별적인 인스턴스다.
  *   ac.close();
  *  }
  *
  * ▶ 출력 결과
  * find prototypeBean1
  * SingletonBean.init() //PrototypeBean 은 처음부터 생성되는 것이 아니라 외부에서 조회해야만 그때서야 만들어진다.
  * find prototypeBean2
  * SingletonBean.init() // 초기화 콜백 메소드는 호출되지만, 소멸 콜백 메소드는 호출되지 않는다.
  *
  * 정리하자면, 프로토타입은 다음과 같은 특징이 있다.
  * 1) 처음부터 Bean 을 만들지 않고 외부에서 찾으면 그때서야 스프링 컨테이너는 Bean 을 생성하고 반환한다.
  * 2) 스프링 컨테이너는 `Bean 생성 → 의존관계 주입 → 초기화 콜백 메소드` 까지만 책임진다. 그 이후는 관리하지 않는다.
  * 3) 프로토타입 Bean 을 요청한 클라이언트가 해당 Bean 을 관리해야 한다. 소멸 콜백 메소드도 클라이언트가 직접 호출해야 한다.
  *
  *
  *
  * */

}
