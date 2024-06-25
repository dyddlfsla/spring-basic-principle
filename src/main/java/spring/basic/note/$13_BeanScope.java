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
  * ――――――――――――――――――――――――――――――――――――――――――――――――――――――――――
  *
  * Ⅱ. ObjectProvider 와 jakarta.inject.Provider
  *
  *
  * 어떻게해야 프로토타입 Bean 을 매번 새로 생성하여 사용할 수 있을까?
  * 스프링 컨테이너는 프로토타입 Bean 을 조회할 때마다 새로운 객체로 생성하여 반환한다고 했다.
  * 그렇다면, 클라이언트가 요청할 때 스프링 컨테이너에게 Bean 을 `대신 조회해주는 누군가`가 있으면 된다.
  *
  * ◆ ObjectProvider, ObjectFactory
  * 우리는 지금껏 어떤 Bean 을 찾고자 할때, 스프링 컨테이너에게 직접 getBean(); 메소드를 호출하는 방법을 사용했다.
  * 그런데 스프링에서는 어떤 Bean 을 컨테이너 대신 찾아주는 DL 서비스를 제공하는데,
  * 이것이 ObjectProvider/ObjectFactory 클래스이다.
  *
  * class ClientBean {
  *   private final ObjectProvider<PrototypeBean> prototypeProvider; // ObjectProvider<T> 사용.
  *
  *   public ClientBean(ObjectProvider<PrototypeBean>  prototypeProvider) {
  *     this.prototypeProvider = prototypeProvider;
  *   }
  *
  *   public int logic() {
  *     PrototypeBean prototypeBean = prototypeProvider.getObject(); // Provider 의 getObjet() 를 호출해 PrototypeBean 객체 조회.
  *     prototypeBean.addCount();
  *     return prototypeBean.getCount();
  *   }
  * }
  *
  * 위의 코드를 보면, PrototypeBean prototypeBean = prototypeProvider.getObject(); 와 같이
  * 스프링 컨테이너 대신 ObjectProvider 를 통해 PrototypeBean 을 조회하고 있다.
  * 프로토타입 Bean 을 조회하면 컨테이너는 항상 새로운 Bean 을 생성해 반환하므로 ObjectProvider 를 통해 얻는 PrototypeBean 은 항상 새로운 인스턴스이다.
  * 그리고 이제 logic() 는 항상 새로운 PrototypeBean 을 사용하게 된다.
  *
  * 기존에는 스프링 컨테이너가 ClientBean 과 PrototypeBean 을 각각 생성한 뒤 ClientBean 에게 PrototypeBean 넣어줌으로써
  * DI 가 발생했다. 하지만 이제는 ClientBean 이 직접 사용할 PrototypeBean 객체를 ObjectProvider 를 통해 `찾고` 있는 것이다.
  * 이렇게 의존성이 외부에서 주입되는 것이 아니라, 객체가 직접 의존할 객체를 찾는 것을 DL(Dependency Lookup)이라고 한다.
  * DI 와 DL 은 객체 간의 의존성을 연결시키는 대표적인 방법이므로 잘 알아두자.
  *
  * ObjectProvider/ObjectFactory 는 다음과 같은 특징을 가진다.
  * 1) 기능이 단순하여, 단위테스트에 사용하기 좋다.
  * 2) 스프링에 의존적이지만 별도의 라이브러리가 필요 없다.
  * 3) ObjectFactory 는 Provider 에 부가적인 기능을 더 추가시킨 것이다.
  *
  * ◆ jakarta.inject.Provider
  *
  * ObjectProvider 대신에 자바 표준 기술이 제공하는 Provider 도 사용할 수 있다.
  * 이것을 사용하기 위해선 우선 라이브러리를 추가해야 한다.
  *
  * ▶ implementation 'jakarta.inject:jakarta.inject-api:2.0.1'
  *
  * class ClientBean {
  *
  *  private final Provider<PrototypeBean> prototypeProvider; //jakarta.Provider 사용.
  *
  *  public ClientBean(Provider<PrototypeBean> prototypeProvider) {
  *    this.prototypeProvider = prototypeProvider;
  *   }
  *
  *  public int logic() {
  *    PrototypeBean prototypeBean = prototypeProvider.get(); //Provider 의 get() 을 호출해 PrototypeBean 객체 조회
  *    prototypeBean.addCount();
  *    return prototypeBean.getCount();
  *  }
  * }
  *
  * ObjectProvider 와 달리 jakarta.Provider 는 다음과 같은 특징이 있다.
  * 1) 메소드가 get() 하나만 존재하여 사용법이 매우 단순하다.
  * 2) 스프링이 아닌 자바 표준 기술이므로 프레임워크에 의존하지 않는다.
  * 3) 대신 별도의 라이브러리가 필요하다.
  * 4) 자바 표준이므로 스프링 컨테이너가 아닌 다른 DI 컨테이너에서도 사용할 수 있다.
  *
  * ◆ 그러면 프로토타입 스코프는 언제 사용할까? 어렵게 생각할 필요 없이 어떤 로직을 수행할 때마다 새로운 스프링 Bean 객체를
  * 사용해야 하는 경우에 사용하면 된다. 그런데 사실, 실무에서는 싱글톤 스코프만으로도 대부분의 문제가 해결되므로
  * 프로토타입 스코프를 사용하는 경우가 잘 없다.
  *
  * 그리고 또한, ObjectProvider, jakarta.Provider 는 꼭 프로토타입 스코프를 처리하기 위해 사용하는 것이 아니라
  * DL 을 사용해야 하는 상황에서도 언제든지 사용할 수 있다.
  *
  * 또, 개발을 하다 보면 이런 경우처럼 비슷한 기능을 자바 표준과 스프링에서 모두 제공하는 경우가 있다.
  * 이럴 때, 스프링을 써야 할까? 자바 표준을 써야 할까? 종종 오해하는 것이 스프링은 결국 자바 언어로 만들어진 하위 존재이니
  * 자바 표준이 스프링보다 무조건 낫다고 생각하는 것이다. 그러나 그것은 잘못된 생각이다.
  * JPA 의 경우, 최종적으로 자바 표준이 개발 시장에서 하이버네이트를 이기고 올라섰으므로, JPA 를 사용해야 하는 것이 맞지만
  * 스프링의 경우, 스프링이 이미 시장의 표준이 된 것이나 다름없는 지위를 갖고 있다. 그러므로 무조건 자바 표준을 사용하는 것이 아니라
  * 상황에 따라 각각의 기술을 비교하고 더 나은 것을 사용하는 것이 맞다.
  *
  * 보통, 같은 기능을 제공하더라도 자바 표준 기술보다는 스프링이 좀 더 다양하고 편리한 부가 기능을 제공하는 경우가 많다.
  *
  *
  *
  * */

}
