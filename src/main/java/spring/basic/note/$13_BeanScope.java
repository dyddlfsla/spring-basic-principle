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
  * ◆ Prototype Scope 와 Singleton Scope 을 함께 사용 시 발생하는 문제.
  *
  * 싱글톤 스코프를 가진 Bean 과 프로토타입 스코프를 가진 Bean 과 함께 사용하는 상황을 만들어보자.
  *
  * 예를 들어, ClientBean(싱글톤 스코프) 이 부품 객체로 PrototypeBean(프로토타입 스코프) 을 가지고 있고,
  * 외부 클라이언트로부터 요청이 오면 ClientBean 의 logic() 을 호출한다고 하자.
  *
  * class ClientBean {
  *   private final PrototypeBean prototypeBean;
  *
  *   public ClientBean(PrototypeBean prototypeBean) { //생성자 주입
  *     this.prototypeBean = prototypeBean;
  *    }
  *
  *   public int logic() {
  *     prototypeBean.addCount();
  *     return prototypeBean.getCount();
  *   }
  * }
  *
  * ClientA 가 ClientBean 을 조회한 뒤 logic() 을 호출하였다.
  * 그 다음 ClientB 가 다시 ClientBean 을 조회한 뒤 logic() 을 호출하였다.
  *
  * 그렇다면 ClientA 와 ClientB 가 얻는 count 값은 어떻게 될까?
   * PrototypeBean 은 싱글톤 객체가 아니라고 했으므로
  * ClientBean 가 가진 PrototypeBean 은 서로 다른 개별 인스턴스가 되고
  * 따라서 ClientA, ClientB 가 얻게 되는 count 값은 1, 1 이 될 것 같지만,
  *
  * 틀렸다. ClientA 는 1을 ClientB 는 2 라는 값을 얻게 될 것이다.
  * 왜냐하면 ClientBean 이 가진 PrototypeBean 은 서로 다른 개별 인스턴스가 아닌 하나의 PrototypeBean 을 사용하고 있기 때문이다.
  * 스프링 컨테이너는 외부에서 PrototypeBean 을 조회할 때 새로운 Bean 을 만들어 주는 것이지,
  * 단순히 PrototypeBean 을 `사용한다고` 해서 새로운 Bean 을 만들어 주는 것이 아니기 때문이다.
  *
  * 스프링 컨테이너는 시작과 동시에 싱글톤 스코프인 ClientBean 객체를 생성하는데 이때 부품 객체로 PrototypeBean 이 필요하므로
  * 스프링 컨테이너는 PrototypeBean 도 같이 생성하여 ClientBean 과 연결시켜놓는다.
  *
  * 그 다음, 처음 ClientA 가 ClientBean 을 조회하면, 스프링 컨테이너는 생성해놓은 ClientBean 을 반환할 것이고
  * ClientB 가 ClientBean 을 조회하면, 컨테이너는 또 다시 만들어 놓았던 ClientBean 을 반환하게 되는데
  * 당연히 부품 객체로 사용되는 PrototypeBean 객체는 계속 동일한 객체로 유지되고 있는 것이다.
  * ClientA, ClientB 둘 다 ClientBean 을 조회한 것이지 PrototypeBean 을 조회한 것이 아니므로
  * PrototypeBean 은 처음 ClientBean 의 생성자 호출 시 생성되고, 그 이상 새로 생성되지 않는다.
  *
  * 결국 싱글톤 Bean 과 프로토타입 Bean 을 함께 사용하는 경우,
  * 어떤 객체를 Prototype 으로 설계함으로써  클라이언트 요청 시마다 새로운 객체를 사용하려던 원래 의도와는 달리
  * 처음 생성된 PrototypeBean 을 변함없이 그대로 사용하게 되는 문제가 발생한다.
  *
  *
  *
  *
  *
  * */

}
