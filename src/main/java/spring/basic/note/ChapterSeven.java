package spring.basic.note;

public class ChapterSeven {

  /*
 self-taught
  *
  * Ⅰ. BeanFactory 와 ApplicationContext
  *
  * +----------------------+
  * |   <<interface>>      |
  * |    BeanFactory      |
  * +----------------------+
  *          ↑
  * +----------------------+
  * |   <<interface>>      |
  * | ApplicationContext   |
  * +----------------------+
  *          ↑
  * +-------------------------------------+
  * |     <<class>>                       |
  * | AnnotationConfigApplicationContext  |
  * +-------------------------------------+
  *
  * ◆ BeanFactory
  * 모든 스프링 컨테이너는 BeanFactory 인터페이스를 상속하고 있다. 즉, BeanFactory 는 스프링 컨테이너의 최상위 인터페이스이다.
  * BeanFactory 는 스프링 빈을 관리하고 조회하는 역할을 담당한다.
  * 앞서 Bean 조회 시 사용했던 getBean() 메소드는 BeanFactory 인터페이스에 정의되어 있다.
  *
  * ◆ ApplicationContext
  * BeanFactory 의 기능을 모두 상속받아서 제공한다. 뿐만 아니라, Bean 을 관리하고 추가적인 부가기능을 제공한다.
  *
  * - MessageSource: 메시지소스를 활용한 국제화 기능
  * - EnvironmentCapable: 환경변수
  * - ApplicationEventPublisher: 이벤트 발행 기능
  * - ResourceLoader: 편리한 리소스 조회 기능
  *
  * 정리하자면,
  * BeanFactory 는 스프링 컨테이너의 최상위 인터페이스이고, 스프링 빈을 관리하고 조회하는 기본적인 기능을 제공한다.
  * ApplicationContext 는 BeanFactory 의 기능을 모두 상속받아서 제공하며, 더 많은 부가기능을 제공한다.
  * BeanFactory 를 직접 사용할 일은 거의 없고, 대부분 ApplicationContext 를 사용한다.
  * BeanFactory 나 ApplicationContext 모두 스프링 컨테이너라고 한다.
  *
  * ――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――
  *
  * Ⅱ. 다양한 형식 지원 - 자바 코드, XML
  *
  *                  ApplicationContext
  *            ↗               ↑             ↖
  *  AnnotationConfig     GenericXml         ○○○
  *  [AppConfig.class]  [appConfig.xml] [appConfig.○○○]
  *
  * 스프링은 구성 정보를 설정할 때, 자바 코드 나 XML 을 사용할 수 있고, 물론 이외에 다른 형식도 사용할 수 있다.
  *
  * ◆ 자바 코드 설정
  * - 자바 코드로 스프링 Bean 을 설정하는 것이다.
  * - AnnotationConfigApplicationContext 를 사용하면서 AppConfig.class 를 넘겨주면 된다.
  * - 자바 코드로 스프링 Bean 을 설정하면 컴파일 시점에 오류를 확인할 수 있고, IDE 의 지원을 받을 수 있다.
  *
  * ◆ XML 설정
  * - 최근에는 SpringBoot 를 많이 사용하게 되면서, XML 설정을 잘 사용하지 않는다.
  * - XML 설정을 사용하면, 컴파일 시점에 오류를 확인할 수 없고, IDE 도 지원이 별로 없다.
  * - 그래도 여전히 많은 레거시 프로젝트들이 XML 설정을 사용하고 있기 때문에 알아두는 것이 좋다.
  * - GenericXmlApplicationContext 를 사용하면서 appConfig.xml 을 넘겨주면 된다.
  * - src/main/resources 에 appConfig.xml 파일을 생성한다.
  * - appConfig.xml 파일에 스프링 Bean 설정을 한다.
  *     <bean id="orderService" class="spring.basic.order.OrderServiceImpl">
  *       <constructor-arg name="discountPolicy" ref="discountPolicy"/>
  *       <constructor-arg name="memberRepository" ref="memberRepository"/>
  *     </bean>
  * - XML 설정에 대해 더 많이 알고 싶다면, 스프링 공식 문서를 참고하자.
  *
  * ――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――
  *
  *
  * */

}
