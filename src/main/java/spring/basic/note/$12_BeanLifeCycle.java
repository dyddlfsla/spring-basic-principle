package spring.basic.note;

public class $12_BeanLifeCycle {

  /*
 self-taught
  *
  * Ⅰ. Spring Bean LifeCycle
  *
  *
  * 스프링 컨테이너는 스프링에서 사용되는 객체들을 스프링 Bean 으로 등록하여 관리한다.
  * 이때, 스프링 Bean 역시 결국 하나의 자바 객체이기 때문에 프로그램 실행 중 필요에 의해 생성되어 사용된 후
  * 쓸모가 없어지면 소멸하는 과정을 거치는데, 이때 Bean 처음 생성되었다가 나중에 소멸하기까지의 과정을
  * Bean 의 생명주기(LifeCycle) 이라고 한다.
  *
  * 이 Bean 의 생명주기는 크게 다음과 같은 과정으로 볼 수 있다.
  *
  * 1) 애플리케이션 실행
  *          ↓
  * 2) 스프링 컨테이너 생성
  *          ↓
  * 3) 스프링 Bean 생성
  *          ↓
  * 4) Bean 간의 의존관계 주입
  *          ↓
  * 5) Bean 초기화 작업(초기화 콜백)
  *          ↓
  * 6) 프로그램 내 Bean 객체 사용
  *          ↓
  * 7) Bean 소멸전 작업(소멸전 콜백)
  *          ↓
  * 8) 스프링 Bean 소멸
  *
  * 지금껏 생성자 주입을 계속 사용했기 때문에 Bean 생성과 의존관계가 동시에 일어난다고 착각할 수 있지만,
  * 일반적으로 Bean 은 생성 단계와 의존관계가 주입되는 단계가 나누어져 있다.
  *
  * 그리고 Bean 의 생성과 의존관계 주입 단계가 다 끝나면, 사용자는 Bean 을 사용하기 전 필요한 초기화 작업을
  * 수행하고 Bean 을 본격적으로 사용할 수 있게 된다.
  *
  * 그런데 Bean 의 생성 및 의존관계 주입이 다 끝났는지 어떻게 알 수 있을까?
  * 스프링은 의존관계 주입이 완료되면 Bean 의 초기화 콜백 메소드를 통해 사용자에게 초기화 시점을 알려 줄 수 있다.
  * 또 반대로, Bean 소멸하기전에 꼭 해야되는(네트워크 소켓의 자원 반환 등) 작업이 있다면 Bean 의 소멸전 콜백 메소드를
  * 통해 작업을 처리할 수 있다.
  *
  * 이렇게 스프링은 Bean 의 생명주기와 관련하여 다양한 콜백 기능을 지원한다.
  *
  * ※ !important 귀찮은데 그냥, Bean 이 처음 생성될 때 한번에 초기화 작업까지 하면 안되는걸까?
  * 물론 생성자가 원래 객체 초기화를 담당하는 용도이므로 아주 틀린 말은 아니다.
  * 그럼에도 생성자에서 객체 초기화까지 처리하는 것은 SRP 원칙을 위반하는 것일 수 있다.
  * 생성자는 객체 생성에 필수적인 값만 전달받아, 메모리에 객체를 생성하는 책임을 가진다.
  * 반면에 초기화 작업은 본격적으로 객체를 사용하기 위한 사전준비(커넥션 연결, 초기 데이터 저장)를 담당하는 책임을 가지며,
  * 초기화 작업 자체가 많은 연산을 필요로하는 무거운 작업이 될 때가 많다.
  * 따라서, 생성과 동시에 무거운 초기화 작업을 하는 것보다는 객체 생성과 객체 사전준비를 명확하게 나누어 책임을 분리하는 것이 좋은 설계이다.
  * 물론 간단한 데이터를 저장하는 가벼운 작업이라면 생성자에서 바로 처리하는 것도 더 효율적일 수 있다.
  *
  * ◆ 지연 초기화(Lazy initialization): 객체를 생성한 후, 바로 초기화를 하는 것이 아니라 객체가 실제로 사용되는 순간이 오면
  * 그때가서야 필요한 초기화를 수행하고 작업에 사용하는 방식이다. 초기화 작업이 많은 리소스를 소모하는 작업이라면
  * 객체를 사용하는 시점이 아닌데도 미리 초기화를 수행하고 기다리는 것은 비효율적이다.
  * 이때 지연 초기화를 통해 이러한 문제를 해결할 수 있다.
  *
  * ◆ 스프링 Bean 의 다양한 생명주기
  * 스프링 컨테이너의 싱글톤 Bean 들은 보통 스프링 컨테이너가 종료되면 그때 같이 소멸되는 생명주기를 갖고 있는데,
  * 모든 Bean 이 다 그런 것은 아니다. 컨테이너의 시작과 종료까지 함께 하는 Bean 이 있는가하면 반대로 생명주기가 짧은 Bean 들도 존재한다.
  *
  * ――――――――――――――――――――――――――――――――――――――――――――――――――――――――――
  *
  * Ⅱ. 인터페이스 InitializingBean, DisposableBean
  *
  * InitializingBean 은 Bean 생성 후 초기화 작업을 담당하는 콜백 메소드를 제공하고
  * DisposableBean 은 Bean 소멸 전 작업을 담당하는 콜백 메소드를 제공한다.
  *
  * 우선 스프링 Bean 객체가 InitializingBean, DisposableBean 인터페이스를 구현하도록 한다.
  *
  * public class NetworkClient implements InitializingBean, DisposableBean {
  *   ...
  *
  * //그리고, 각 인터페이스에 정의된 추상메소드를 재정의하면 된다.
  *
  *   @Override
  *   public void afterPropertiesSet() throws Exception { //InitializingBean 의 추상메소드.
  *     System.out.println("초기화 작업 코드");
  *   }
  *   @Override
  *   public void destroy() throws Exception { //DisposableBean 의 추상메소드.
  *     System.out.println("객체 소멸 전 해야할 작업 코드);
  *   }
  * }
  *
  * ◆ InitializingBean, DisposableBean 의 특징.
  * 1) 이 인터페이스들은 스프링 전용 인터페이스이다. 즉 해당 코드가 스프링 전용 인터페이스에 전적으로 의존하게 된다.
  * 2) 추상메소드를 재정의하는 방식이므로 초기화, 소멸 메소드의 이름을 변경할 수 없다.
  * 3) 내가 수정할 수 없는 외부라이브러리에 적용할 수 없다.
  *
  * ―――――――――――――――――――――――――――――――――――――――――――――――――――――――
  *
  * Ⅲ. @Bean(initMethod = "", destroyMethod = "")
  *
  * 스프링의 설정 정보 클래스를 통해서 Bean 의 초기화 콜백과 소멸전 콜백을 사용할 수 있다.
  *
  * 다음과 같이, 설정 정보에서 Bean 을 생성하는 @Bean 메소드에서 initMethod, destroyMethod 옵션을 설정하면 된다.
  *
  * @Configuration
  * class LifeCycleConfig {
  *
  *  @Bean(initMethod = "init", destroyMethod = "close") // 초기화 콜백으로 init() 을, 소멸전 콜백으로 close()을 지정
  *  public NetworkClient networkClient() {
  *    ...
  *  }
  *
  * 그리고 NetworkClient 클래스에서 옵션에 전달한 식별자와 같은 이름의 메소드를 정의해주어야 한다.
  *
  * public void init() { // 메소드 이름: init
  *   System.out.println("초기화 작업 코드");
  * }
  *
  * public void close() { // 메소드 이름: close
  *   System.out.println("객체 소멸 전 해야할 작업 코드);
  * }
  *
  * ◆ @Bean(initMethod = "", destroyMethod = "") 방식의 특징.
  *
  * 1) 초기화, 소멸 메소드의 이름을 자유롭게 정할 수 있다.
  * 2) 스프링 코드에 의존하지 않는다.
  * 3) 설정 정보를 사용하기 때문에, 외부 라이브러리에도 콜백 메소드를 적용할 수 있다.
  *
  *  - @Bean 의 destroyMethod 옵션에는 특별한 기능이 있다.
  *   - destroyMethod 의 구현 코드를 보면 String destroyMethod() default "(inferred)"; 인데, 즉 기본값이 추론으로 되어 있다.
  *   - 외부 라이브러리들의 콜백 메소드 이름은 보통 shutdown, close 인데,
  *   - 이때, destroyMethod 의 추론 기능은 메소드 이름이 close, shutdown 인 메소드를 소멸 콜백 메소드르 인식하여 자동 호출한다.
  *   - 그래서, 소멸 콜백 메소드의 이름을 close 로 작성했다면, destroyMethod = "close" 와 같이 명시하지 않아도 자동으로 호출된다.
  *   - 만약, 추론 기능을 사용하기 싫다면 destroyMethod = "" 와 같이 공백으로 지정하면 된다.
  *
  * ―――――――――――――――――――――――――――――――――――――――――――――――――――――――――――
  *
  * Ⅳ. @PostConstruct, @PreDestroy
  *
  * 이 방식은 Bean 이 가지고 있는 일반 메소드에 어노테이션을 붙여줌으로써 초기화, 소멸 콜백 메소드로 만드는 방식이다.
  *
  * 가장 쉬운 방식으로, 초기화 콜백 메소드로 사용할 메소드에 @PostConstruct 를
  * 소멸 콜백 메소드로 사용할 메소드에 @PreDestroy 를 붙여주면 된다.
  *
  * @PostConstruct
  * public void init() {
  *   System.out.println("초기화 작업 코드");
  * }
  *
  * @PreDestroy
  * public void close() {
  *   System.out.println("객체 소멸 전 해야할 작업 코드);
  * }
  *
  * @PostConstruct, @PreDestroy 의 특징.
  * 1) 최신 스프링에서 가장 권장하는 방식이다.
  * 2) 애노테이션 하나만 작성하면 되므로 코드가 간결하며, @ComponentScan 과 잘 어울린다.
  * 3) 패키지를 잘 보면, javax 또는 jakarta 로 시작하는데 이것은 자바 표준 기술이라는 뜻이다.
  *    - 즉 Spring 에 종속된 기술이 아니므로 스프링 컨테이너뿐만 아니라 다른 DI 컨테이너에서도 작동한다는 것이다.
  * 4) 유일한 단점은 외부 라이브러리의 메소드를 사용할 수 없다는 것이다.
  *   - 외부 라이브러리의 메소드로 콜백을 해야할 때는 앞서 배운 @Bean(initMethod = "", destroyMethod = "")를 사용하면 된다.
  *
  * Conclusion => 1. 스프링 Bean 의 초기화, 소멸 콜백 메소드를 정의할 때는 @PostConstruct, @PreDestroy 를 기본으로 사용한다.
  *               2. 외부 라이브러리를 사용해야 할땐 @Bean(initMethod = "", destroyMethod = "") 를 사용한다.
  *
  *
  *
  * */

}
