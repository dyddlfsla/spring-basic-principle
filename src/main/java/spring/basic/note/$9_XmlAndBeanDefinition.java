package spring.basic.note;

public class $9_XmlAndBeanDefinition {


  /*
self-taught
  *
  * Ⅰ. 다양한 형식 지원 - 자바 코드, XML
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
  * - src/main/resources 에 appConfig.xml 파일을 생성한다.
  * - appConfig.xml 파일에 스프링 Bean 설정을 한다.
  *
  *     <bean id="orderService" class="spring.basic.order.OrderServiceImpl">
  *       <constructor-arg name="discountPolicy" ref="discountPolicy"/>
  *       <constructor-arg name="memberRepository" ref="memberRepository"/>
  *     </bean>
  *
  * - GenericXmlApplicationContext 를 사용하면서 appConfig.xml 을 넘겨주면 된다.
  * ▶ ApplicationContext ac = new GenericXmlApplicationContext("appConfig.xml");
  *
  * - XML 설정에 대해 더 많이 알고 싶다면, 스프링 공식 문서를 참고하자.
  *
  * ――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――
  *
  * Ⅱ. 스프링 Bean 설정 메타 정보 - BeanDefinition
  *
  * 스프링은 어떻게 해서 이런 다양한 형식 지원을 가능하게 할까?
  * 그 중심에는 BeanDefinition 이라는 추상화가 있다.
  *
  * 쉽게 말하자면, Bean 관련 설정을 읽을 때에도 역할과 구현을 구분한 것이다.
  * - XML 을 읽어서 BeanDefinition 을 만들고,
  * - 자바 코드를 읽어서 BeanDefinition 을 만든다.
  * 스프링 컨테이너는 설정이 자바 코드인지, XML 인지 몰라도 된다. 그저 BeanDefinition 만 알면 된다.
  *
  * ◆ BeanDefinition
  * BeanDefinition 은 Bean 의 설정 정보를 담고 있는 인터페이스이다.
  * @Bean, <bean> 당 각각 하나의 Bean 메타 정보가 생성된다.
  * 스프링 컨테이너는 이 메타 정보를 바탕으로 스프링 Bean 을 생성한다.
  *
  * AnnotationConfigApplicationContext 는 AnnotatedBeanDefinitionReader 를 사용해서 AppConfig.class 를 읽고, BeanDefinition 을 생성한다.
  * GenericXmlApplicationContext 는 XmlBeanDefinitionReader 를 사용해서 appConfig.xml 을 읽고, BeanDefinition 을 생성한다.
  * 앞으로 새로운 형식의 설정 정보가 추가되면 그에 따른 ○○○BeanDefinitionReader 를 만들고 읽어서 BeanDefinition 을 생성하면 된다.
  *
  * ◆ BeanDefinition 이 가지고 있는 정보
  * 1) BeanClassName: 생성할 Bean 의 클래스 명
  * 2) factoryBeanName: 팩토리 역할의 Bean 을 사용할 경우 이름, 예) appConfig
  * 3) factoryMethodName: Bean 을 생성할 경우 팩토리 메서드 명, 예) memberService
  * 4) Scope: 싱글톤, 프로토타입 등의 스코프 정보
  * 5) lazyInit: 스프링 컨테이너를 생성할 때 Bean 도 바로 생성하는 것이 아니라, 실제 Bean 사용되기 전까지 최대로 생성을 지연하는지 여부
  * 6) InitMethodName: Bean 을 생성하고 초기화 하는 메서드 명
  * 7) DestroyMethodName: Bean 을 소멸하기 전에 호출되는 메서드 명
  * 8) Constructor arguments, Properties: 의존관계 주입에서 사용한다.
  *
  * BeanDefinition 을 직접 생성해서 스프링 컨테이너에 등록할 수도 있다. 하지만 실무에서는 거의 사용하지 않는다.
  * BeanDefinition 의 모든 것을 이해하기보다는 스프링의 다양한 형식 정보 지원이 BeanDefinition 을 통한 추상화라는 것만 알면 된다.
  * 스프링 공식 문서나 관련 오픈 소스들을 보면 BeanDefinition 관련된 정보를 종종 볼 수 있다.
  *
  *
  *
  * */

}
