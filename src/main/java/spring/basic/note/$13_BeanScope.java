package spring.basic.note;

public class $13_BeanScope {

  /*

  Ⅰ. Bean Scope 란?

  Bean 의 스코프(Scope)는 `Bean 의 범위`를 의미하는데, 어떤 범위를 말하냐면
  Bean 인스턴스가 메모리 위에 생성되어 `존재할 수 있는 범위(기간)`를 말한다.

  지금까지는 스프링 컨테이너에 의해 Bean 이 한번 생성되면, 해당 Bean 은 싱글톤 방식으로 관리되어
  계속 메모리 위에 존재하다가 프로그램이 종료되면 그때서야 스프링 컨테이너와 함께 소멸되는 생명주기를 갖고 있다고 배웠다.

  그렇다면 이때, Bean 의 스코프는 싱글톤 스코프에 해당하는 것이고,
  싱글톤 스코프란 스프링 컨테이너의 시작 시점 ~ 스프링 컨테이너 종료 시점까지 Bean 인스턴스가 존재하는 것을 말한다.

  스프링의 기본 스코프는 싱글톤 스코프이지만, 싱글톤 스코프외에도 스프링은 다양한 스코프를 지원한다.

  1) singleton Scope: 기본 스코프. 스프링 컨테이너의 시작과 종료까지 Bean 인스턴스가 존재한다.
  2) prototype Scope: 스프링 컨테이너가 Bean 의 생성과 의존관계 주입까지만 관여하고 더 이상은 관여하지 않는 방식이다.
                      매우 짧은 범위의 스코프이다.

  ◆ 웹 관련 스코프
  3) request: HTTP 요청이 서버로 들어오고 나갈 때까지 Bean 인스턴스가 유지되는 스코프이다.
  4) session: 웹 세션이 생성되고 종료될 때까지 Bean 인스턴스가 유지되는 스코프이다.
  5) application: 웹의 서블릿 컨텍스트와 같은 범위로 Bean 인스턴스가 유지되는 스코프이다.

  Bean 의 스코프는 다음과 같이 지정할 수 있다.

  1) @ComponentScan 을 통해 Bean 을 등록하는 경우,
  @Component
  @Scope("prototype") //@Scope 어노테이션 사용.
  public class OrderServiceImpl implements OrderService {...}

  2) @Bean 메소드를 통해 Bean 을 등록하는 경우,
  @Bean
  @Scope("prototype")
  public OrderService orderService() {
    return new OrderServiceImpl();
  }

  ――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――

  Ⅱ. prototype Scope

  프로토타입 스코프는, 스프링 컨테이너가 시작과 동시에 자동으로 Bean 인스턴스를 만드는 방식이 아니다.
  클라이언트로부터 요청이 오면 그때서야 스프링 컨테이너는 프로토타입 Bean 을 생성하고 의존관계를 주입한뒤 반환한다.
  또한 스프링 컨테이너는 생성된 Bean 인스턴스를 끝까지 관리하지 않는다.
  때문에 프로토타입 Bean 은 싱글톤 Bean 과는 다른 차이점이 존재하는데,

  스프링 컨테이너는 클라이언트 요청이 올 때마다 새로운 프로토타입 Bean 을 생성한다. 따라서 Bean 인스턴스는 싱글톤 객체가 아니다.
  그리고 스프링 컨테이너는 Bean 의 초기화 콜백 메소드까지는 호출하지만 소멸 콜백 메소드는 호출하지 않는다.
  즉, @PreDestroy 는 사용할 수 없는 것이다.

  @Scope("prototype")
  static class PrototypeBean {

    @PostConstruct
    public void init() {
      System.out.printf("SingletonBean.init()%n");
    }

    @PreDestroy
    public void destroy() {
      System.out.printf("SingletonBean.destroy()%n");
    }
  }

  @Test
  void prototypeBeanFind() {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
    System.out.printf("find prototypeBean1%n");
    PrototypeBean bean1 = ac.getBean(PrototypeBean.class);

    System.out.printf("find prototypeBean2%n");
    PrototypeBean bean2 = ac.getBean(PrototypeBean.class);

    assertThat(bean1).isNotSameAs(bean2); // PrototypeBean 은 싱글톤 객체가 아니라, 각자가 개별적인 인스턴스다.
    ac.close(); // 스프링 컨테이너 종료.
  }

  ▶ 출력 결과
  find prototypeBean1
  SingletonBean.init() // PrototypeBean 은 처음부터 생성되는 것이 아니라 외부에서 조회해야만 그때서야 만들어진다.
  find prototypeBean2
  SingletonBean.init() // 초기화 콜백 메소드는 호출되지만, 소멸 콜백 메소드는 호출되지 않는다.

  정리하자면, 프로토타입은 다음과 같은 특징이 있다.
  1) 처음부터 Bean 을 만들지 않고 외부에서 찾으면 그때서야 스프링 컨테이너는 Bean 을 생성하고 반환한다.
  2) 스프링 컨테이너는 `Bean 생성 → 의존관계 주입 → 초기화 콜백 메소드` 까지만 책임진다. 그 이후는 관리하지 않는다.
  3) 프로토타입 Bean 을 요청한 클라이언트가 해당 Bean 을 관리해야 한다. 소멸 콜백 메소드도 클라이언트가 직접 호출해야 한다.

  ◆ Prototype Scope 와 Singleton Scope 을 함께 사용 시 발생하는 문제.

  싱글톤 스코프를 가진 Bean 과 프로토타입 스코프를 가진 Bean 을 함께 사용하는 상황을 만들어보자.

  예를 들어, ClientBean(싱글톤 스코프) 이 부품 객체로 PrototypeBean(프로토타입 스코프) 을 가지고 있고,
  외부 클라이언트로부터 요청이 오면 ClientBean 의 logic() 을 호출하고 그 결과값으로 count 를 얻는다고 하자.

  class ClientBean {
    private final PrototypeBean prototypeBean;

    public ClientBean(PrototypeBean prototypeBean) { //생성자 주입
      this.prototypeBean = prototypeBean;
     }

    public int logic() {
      prototypeBean.addCount(); // 초기값 0인 count 를 count++;
      return prototypeBean.getCount();
    }
  }

  ClientA 가 ClientBean 을 조회한 뒤 logic() 을 호출하였다.
  그 다음 ClientB 가 다시 ClientBean 을 조회한 뒤 logic() 을 호출하였다.

  그렇다면 ClientA 와 ClientB 가 얻는 count 값은 어떻게 될까?
  PrototypeBean 은 싱글톤 객체가 아니라고 했으므로
  ClientBean 가 가진 PrototypeBean 은 서로 다른 개별 인스턴스가 되고
  따라서 ClientA, ClientB 가 얻게 되는 count 값은 1, 1 이 될 것 같지만,

  틀렸다. ClientA 는 1을, ClientB 는 2 라는 값을 얻는다.
  왜냐하면 ClientBean 이 가진 PrototypeBean 은 서로 다른 개별 인스턴스가 아닌 하나의 PrototypeBean 을 사용하고 있기 때문이다.
  스프링 컨테이너는 외부에서 PrototypeBean 을 조회할 때 새로운 Bean 을 만들어 주는 것이지,
  단순히 PrototypeBean 을 `사용한다고` 해서 새로운 Bean 을 만들어 주는 것이 아니기 때문이다.

  스프링 컨테이너는 시작과 동시에 싱글톤 스코프인 ClientBean 객체를 생성하는데 이때 부품 객체로 PrototypeBean 이 필요하므로
  스프링 컨테이너는 PrototypeBean 도 같이 생성하여 ClientBean 과 연결시켜놓는다.

  그 다음, 처음 ClientA 가 ClientBean 을 조회하면, 스프링 컨테이너는 생성해놓은 ClientBean 을 반환할 것이고
  ClientB 가 ClientBean 을 조회하면, 컨테이너는 또 다시 만들어 놓았던 ClientBean 을 반환하게 되는데
  당연히 부품 객체로 사용되는 PrototypeBean 객체는 계속 동일한 객체로 유지되고 있는 것이다.
  ClientA, ClientB 둘 다 ClientBean 을 조회한 것이지 PrototypeBean 을 조회한 것이 아니므로
  PrototypeBean 은 처음 ClientBean 의 생성자 호출 시 생성되고, 그 이상 새로 생성되지 않는다.

  결국 싱글톤 Bean 과 프로토타입 Bean 을 함께 사용하는 경우,
  어떤 객체를 Prototype 으로 설계함으로써  클라이언트 요청 시마다 새로운 객체를 사용하려던 원래 의도와는 달리
  처음 생성된 PrototypeBean 을 변함없이 그대로 사용하게 되는 문제가 발생한다.

  ――――――――――――――――――――――――――――――――――――――――――――――――――――――――――

  Ⅱ. ObjectProvider 와 jakarta.inject.Provider

  어떻게해야 프로토타입 Bean 을 매번 새로 생성하여 사용할 수 있을까?
  스프링 컨테이너는 프로토타입 Bean 을 조회할 때마다 새로운 객체로 생성한다고 했다.
  그렇다면, 클라이언트가 요청할 때 스프링 컨테이너에게 Bean 을 `대신 조회해주는 누군가`가 있으면 된다.

  ◆ ObjectProvider, ObjectFactory
  우리는 지금껏 어떤 Bean 을 찾고자 할때, 스프링 컨테이너에게 직접 getBean(); 메소드를 호출하는 방법을 사용했다.
  그런데 스프링에서는 어떤 Bean 을 찾을 때, 컨테이너 대신 Bean 을 찾아주는 DL 기능을 지원한다.
  이것이 ObjectProvider/ObjectFactory 클래스이다.

  class ClientBean {
    private final ObjectProvider<PrototypeBean> prototypeProvider; // ObjectProvider<T> 사용.

    public ClientBean(ObjectProvider<PrototypeBean>  prototypeProvider) {
      this.prototypeProvider = prototypeProvider;
    }

    public int logic() {
      PrototypeBean prototypeBean = prototypeProvider.getObject(); // Provider 의 getObjet() 를 호출해 PrototypeBean 객체 조회.
      prototypeBean.addCount();
      return prototypeBean.getCount();
    }
  }

  위의 코드를 보면, PrototypeBean prototypeBean = prototypeProvider.getObject(); 와 같이
  스프링 컨테이너 대신 ObjectProvider 를 통해 PrototypeBean 을 조회하고 있다.
  프로토타입 Bean 을 조회하면 컨테이너는 항상 새로운 Bean 을 생성해 반환하므로 ObjectProvider 를 통해 얻는 PrototypeBean 은 항상 새로운 인스턴스이다.
  그래서 이제 logic() 는 항상 새로운 PrototypeBean 을 사용할 수 있다.

  기존에는 스프링 컨테이너가 ClientBean 과 PrototypeBean 을 각각 생성한 뒤 ClientBean 에게 PrototypeBean 넣어줌으로써
  DI 가 발생했다. 하지만 이제는 ClientBean 이 직접 사용할 PrototypeBean 객체를 ObjectProvider 를 통해 `찾고` 있는 것이다.
  이렇게 의존성이 외부에서 주입되는 것이 아니라, 객체가 직접 의존할 객체를 찾는 것을 DL(Dependency Lookup)이라고 한다.
  DI 와 DL 은 객체 간의 의존성을 연결시키는 대표적인 방법이므로 잘 알아두자.

  ObjectProvider/ObjectFactory 는 다음과 같은 특징을 가진다.
  1) 기능이 단순하여, 단위테스트에 사용하기 좋다.
  2) 스프링에 의존적이지만 별도의 라이브러리가 필요 없다.
  3) ObjectFactory 는 Provider 에 부가적인 기능을 더 추가시킨 것이다.

  ◆ jakarta.inject.Provider

  ObjectProvider 대신에 자바 표준 기술이 제공하는 Provider 도 사용할 수 있다.
  이것을 사용하기 위해선 우선 라이브러리를 추가해야 한다.

  ▶ implementation 'jakarta.inject:jakarta.inject-api:2.0.1'

  class ClientBean {

    private final Provider<PrototypeBean> prototypeProvider; //jakarta.Provider 사용.

    public ClientBean(Provider<PrototypeBean> prototypeProvider) {
      this.prototypeProvider = prototypeProvider;
    }

    public int logic() {
      PrototypeBean prototypeBean = prototypeProvider.get(); //Provider 의 get() 을 호출해 PrototypeBean 객체 조회
      prototypeBean.addCount();
      return prototypeBean.getCount();
    }
  }

  ObjectProvider 와 달리 jakarta.Provider 는 다음과 같은 특징이 있다.
  1) 메소드가 get() 하나만 존재하여 사용법이 매우 단순하다.
  2) 스프링이 아닌 자바 표준 기술이므로 프레임워크에 의존하지 않는다.
  3) 대신 별도의 라이브러리가 필요하다.
  4) 자바 표준이므로 스프링 컨테이너가 아닌 다른 DI 컨테이너에서도 사용할 수 있다.

  ◆ 그러면 프로토타입 스코프는 언제 사용할까? 어렵게 생각할 필요 없이 어떤 로직을 수행할 때마다 새로운 스프링 Bean 객체를
  사용해야 하는 경우에 사용하면 된다. 그런데 사실, 실무에서는 싱글톤 스코프만으로도 대부분의 문제가 해결되므로
  프로토타입 스코프를 사용하는 경우가 잘 없다.

  그리고 또한, ObjectProvider, jakarta.Provider 는 꼭 프로토타입 스코프를 처리하기 위해 사용하는 것이 아니라
  DL 을 사용해야 하는 상황에서도 언제든지 사용할 수 있다.

  또 개발을 하다 보면 위와 같이, 비슷한 기능을 자바 표준과 스프링에서 모두 제공하는 경우가 있다.
  이럴 때, 스프링을 써야 할까? 자바 표준을 써야 할까? 종종 오해하는 것이 스프링은 결국 자바 언어로 만들어진 하위 존재이므로
  자바 표준이 스프링보다 무조건 낫다고 생각하는 것이다. 그러나 그것은 잘못된 생각이다.
  JPA 의 경우, 최종적으로 자바 표준이 시장에서 하이버네이트를 이기고 올라섰으므로, JPA 를 사용해야 하는 것이 맞지만
  스프링의 경우, 스프링이 이미 시장의 표준과 같은 지위를 갖고 있다. 그러므로 무조건 자바 표준을 사용하는 것이 아니라
  상황에 따라 각각의 기술을 비교하고 더 나은 것을 사용하는 것이 맞다.

  보통, 같은 기능을 제공하더라도 자바 표준 기술보다는 스프링이 좀 더 다양하고 편리한 부가 기능을 제공하는 경우가 많다.

  ――――――――――――――――――――――――――――――――――――――――――――――――――――――――――

  Ⅲ. 웹 스코프 - request

  이제는 웹과 관련된 스코프에 대해 알아보자.

  웹 스코프는 공통적으로 다음과 같은 특징을 가지고 있다.
  1) 웹 스코프는 웹 환경에서만 동작한다.
  2) 웹 스코프는 컨테이너가 종료시점까지 관리해준다. 즉 소멸 콜백 메소드가 호출된다.

  ◆ 웹 스코프의 종류
  1) request: HTTP request 가 들어올때 Bean 이 생성되고 response 가 나가면 Bean 이 소멸되는 스코프.
  2) session: Bean 의 생명주기가 HTTP Session 과 동일한 스코프.
  3) application: Bean 의 생명주기가 ServletContext 와 동일한 스코프.
  4) websocket: Bean 의 생명주기가 web socket 과 동일한 스코프.

  여기서는 request 만 설명하지만 session 이나 application 의 동작 방식도 다 유사하다.

  ▶ @Scope("request")
  request 스코프는 HTTP 요청 시 Bean 인스턴스를 생성하고 해당 Bean 인스턴스는 서버에서 해당 요청이 처리되는 동안
  다른 요청에 공유되지 않고 `해당 요청 내에서만 사용된다`.

  ※ 스프링을 웹 환경으로 만들기 위해 다음 라이브러리를 추가한다.
    - implementation 'org.springframework.boot:spring-boot-starter-web'
    - 스프링부트는 내장된 Tomcat 서버를 사용하여 웹 서버 위에 스프링 애플리케이션을 실행시킨다.
    - 웹 환경이되면, 관련된 추가 설정과 Bean 들이 필요하므로 AnnotationConfigServletWebServerApplicationContext 라는
      스프링 컨테이너를 사용하게 된다.

  HTTP 요청이 오면 로그를 출력해주는 MyLogger 클래스를 만들고 HTTP 요청 내에서만 사용되므로 @Scope("request") 를 붙여준다.

  @Component
  @Scope(value = "request")
  public class MyLogger {

    private String uuid;

    @PostConstruct // Bean 생성 시 초기화할 작업.
    public void init() {
    uuid = UUID.randomUUID().toString(); // UUID(Universally Unique Identifier)는 고유한 식별자를 생성하기 위한 자바 API 중 하나다.
    }                                    // 분산 시스템이나 데이터베이스 관련 작업시 사용하기 좋다.

  }

  MyLogger 를 사용하는 Controller 는 다음과 같다.

  @Controller
  @RequiredArgsConstructor
  public class LogDemoController {

    private final LogDemoService logDemoService;
    private final MyLogger myLogger;
    ...
  }

  이제 애플리케이션을 실행해보면..
  스프링부트에서 Bean 생성에 실패했다는 예외가 발생한다. 무엇이 문제일까?
  코드를 잘 살펴보면, Controller 와 MyLogger 를 생성자 주입을 통해 연결시키는 코드이다.
  그런데 Controller 객체는 싱글톤 스코프이므로 컨테이너 시작과 동시에 객체가 생성되지만,
  MyLogger 객체는 request 스코프이므로 HTTP 요청이 와야지만 생성되는 것이다.
  우리는 지금 애플리케이션을 실행만 시켰지, HTTP 요청까지 보낸 것이 아니므로 Controller 인스턴스만 존재하고
  MyLogger 인스턴스는 존재하지 않는 상황인데, 여기서 의존관계를 연결하려고 하니 예외가 발생하게 된다.

  그렇다면 이 상황을 어떻게 해결할 수 있을까?

  지금 문제는 Controller 와 MyLogger 가 서로 다른 스코프를 가지기 때문에 발생하는 문제이다.
  따라서, 생성자 주입을 사용하지 말고 ObjectProvider 를 통해 HTTP 요청이 왔을 때, MyLogger 객체를 꺼내서 사용하면 된다.

  @Controller
  @RequiredArgsConstructor
  public class LogDemoController {

    private final ObjectProvider<MyLogger> myLoggerProvider;

    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) {
      MyLogger myLogger = myLoggerProvider.getObject(); // HTTP 요청이 왔을 때, ObjectProvider 에서 MyLogger 를 꺼내서 사용한다.
      String requestURL = request.getRequestURL().toString();
      myLogger.setRequestURL(requestURL);
      myLogger.log("controller test");
      logDemoService.logic("testId");
      return "OK";
    }

  }

  ※ 현재, Controller 에서 requestURL 이나 MyLogger 를 연결시키고 있는데 이런 코드는 좋은 설계가 아니다.
  Controller 보다는 공통 처리가 가능한 인터셉터나 서블릿 필터에서 처리하는 것이 맞다.

  자, 이제 HTTP 요청을 보내면,

  [54712a51-85dd-452f-91c2-5cb09e2df8bc] request scope been create: spring.basic.common.MyLogger@4ef4769a // 첫 번째 HTTP 요청
  [54712a51-85dd-452f-91c2-5cb09e2df8bc] [http://localhost:8080/log-demo] controller test
  [54712a51-85dd-452f-91c2-5cb09e2df8bc] [http://localhost:8080/log-demo] service id = testId
  [54712a51-85dd-452f-91c2-5cb09e2df8bc] request scope been close: spring.basic.common.MyLogger@4ef4769a

  [3ab02516-50e3-4df6-a410-fe3a7ca86e0c] request scope been create: spring.basic.common.MyLogger@7f197bd0 // 두 번째 HTTP 요청
  [3ab02516-50e3-4df6-a410-fe3a7ca86e0c] [http://localhost:8080/log-demo] controller test
  [3ab02516-50e3-4df6-a410-fe3a7ca86e0c] [http://localhost:8080/log-demo] service id = testId
  [3ab02516-50e3-4df6-a410-fe3a7ca86e0c] request scope been close: spring.basic.common.MyLogger@7f197bd0

  MyLogger 인스턴스가 요청 시마다 생성될 뿐만 아니라, 생성된 MyLogger 인스턴스는 해당 요청 내에서만 독립적으로 사용되는 것을 알 수 있다.
  이처럼, request 스코프는 HTTP 요청부터 응답까지 Bean 인스턴스를 유지한다.

  이 정도로도 충분한 것 같지만, MyLogger myLogger = myLoggerProvider.getObject(); 와 같이
  요청이 올 때마다 Bean 을 매번 검색해 사용하는 코드가 지저분해보인다. 다른 해결 방법은 없을까?

  ――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――

  Ⅳ. Scope 와 Proxy

  그러면 이번에는, 프록시 방식을 사용해보자.

  MyLogger 클래스의 @Scope 에 proxyMode 옵션을 추가한다.

  @Component
  @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
  public class MyLogger {
   ...
  }

  그리고 Controller 클래스에서, 원래 사용했던 생성자 주입 방식으로 돌려놓는다.

  @Controller
  @RequiredArgsConstructor
  public class LogDemoController {

    private final LogDemoService logDemoService;
    private final MyLogger myLogger;
    ...
  }

  애플리케이션을 실행해보면..

  [d0469481-e91b-42ff-bfd3-90832ef0b4dc] request scope been create: spring.basic.common.MyLogger@5fdabe7
  myLogger = class spring.basic.common.MyLogger$$SpringCGLIB$$0
  [d0469481-e91b-42ff-bfd3-90832ef0b4dc] [http://localhost:8080/log-demo] controller test
  [d0469481-e91b-42ff-bfd3-90832ef0b4dc] [http://localhost:8080/log-demo] service id = testId
  [d0469481-e91b-42ff-bfd3-90832ef0b4dc] request scope been close: spring.basic.common.MyLogger@5fdabe7

  정상적으로 실행되고 HTTP 요청도 문제없다!
  예전 코드에서는 HTTP 요청이 들어와야만 MyLogger 인스턴스가 생성되므로
  애플리케이션 실행 시점에서 MyLogger 가 없기 때문에 Controller 의 생성자 주입이 실패했었다.

  그런데, 어떻게 해서 이게 가능해진것일까?

  @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
  proxyMode 옵션을 사용하게 되면, HTTP 요청이 오지 않은 시점에서도
  스프링 컨테이너가 미리 MyLogger 를 대체하는 가짜 프록시 객체를 만들어두고 그것을 Controller 에 주입시켜 주는 것이다.

  myLogger = class spring.basic.common.MyLogger$$SpringCGLIB$$0
  위의 출력 내용을 보면 알수 있듯이, 현재 사용되고 있는 MyLogger 객체는 내가 작성한 MyLogger 클래스가 아니다.
  뒤에 $$SpringCGLIB$$ 라는 것이 붙어있는데, 앞서 설정 정보의 클래스를 조작하여 스프링 Bean 의 싱글톤을 구현하는 CGLIB 가 여기서도 사용되고 있다.

  즉, CGLIB 가 내가 작성한 MyLogger 클래스를 상속하는 또 다른 클래스를 만든 뒤, 프록시 객체를 생성하여 스프링 Bean 으로 등록시켜버린다.
  이 프록시 객체는 실제로 스프링 컨테이너에 "myLogger" 라는 이름을 가지고 저장된다.

  그리고 이후 실제 HTTP 요청이 들어오면 프록시 객체는 그때 내부에 존재하는 진짜 MyLogger 객체를 찾고 MyLogger 에게 대신 명령을 내린다.

  예를 들어, myLogger.logic() 을 호출하게 되면, 일단 프록시 객체의 logic() 가 호출된다.
  그와 동시에 프록시 객체는 현재 요청 컨텍스트에 존재하는 진짜 MyLogger 객체를 찾은 뒤
  MyLogger 객체에게 logic() 메소드 호출을 위임하는 것이다.
  그래서 결과적으로 로그를 출력하는 실제 객체는 MyLogger 인스턴스이다.

  정리하자면,
  @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS) 라는 옵션이 붙으면
  1) CGLIB 는 해당 클래스를 상속받은 프록시 객체를 만들고 그것을 스프링 컨테이너에 `대신` 등록한다.
  2) 이 가짜 프록시 객체는 Bean 으로 주입되어 있다가 실제 HTTP 요청이 와서 MyLogger 의 메소드를 호출하면
     그때 내부에서 진짜 MyLogger 객체를 찾은 뒤 호출된 메소드를 위임한다.
  3) 이 프록시 객체는 실제 request scope 와 관계가 없다. 내부에 단순한 위임 로직만 가지고 텅빈 객체이고 싱글톤처럼 동작한다.

  프록시 객체를 활용하면 다음과 같은 장점이 있다.
  1) 프록시 객체 덕분에 request 스코프를 가진 Bean 을 마치 싱글톤 스코프를 가진 Bean 처럼 손쉽게 사용할 수 있다.
  2) Provider 방식, 프록시 방식의 핵심 아이디어는 Bean 인스턴스의 생성을 해당 인스턴스가 진짜 필요한 시점까지 지연(lazy) 처리한다는 것이다.
  3) 애노테이션 설정만으로도 원본 객체를 프록시 객체로 대체할 수 있다. 이것이 바로 다형성과 DI 컨테이너가 가진 강력한 강점이다.
  4) 프록시는 웹 스코프가 아니어도 여러 상황에서 유용하게 사용할 수 있다.

  반면에, 프록시 객체를 사용할 때 주의점도 존재한다.
  1) 마치 싱글톤 스코프처럼 동작하는 것처럼 보일 뿐, 실제 동작 방식은 싱글톤이 아니므로 복잡한 로직과 사용시 주의해야 한다.
  2) 이런 특별한 Scope 는 꼭 필요한 곳에서 최소화하여 사용해야 한다.

  */

}
