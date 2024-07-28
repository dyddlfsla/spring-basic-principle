package spring.basic.note;

public class $7_BeanFactory {

  /*

  Ⅰ. BeanFactory 와 ApplicationContext

            ┌───────────────┐
            │ <<interface>> │
            │  BeanFactory  │
            └───────────────┘
                    ↑
         ┌──────────────────────┐
         │   <<interface>>      │
         │ ApplicationContext   │
         └──────────────────────┘
                    ↑
  ┌─────────────────────────────────────┐
  │             <<class>>               │
  │ AnnotationConfigApplicationContext  │
  └─────────────────────────────────────┘

  ▶ BeanFactory
  모든 스프링 컨테이너는 BeanFactory 인터페이스를 상속하고 있다. 즉, BeanFactory 는 스프링 컨테이너의 최상위 인터페이스이다.
  BeanFactory 는 스프링 빈을 관리하고 조회하는 역할을 담당한다.
  앞서 Bean 조회 시 사용했던 getBean() 메소드는 BeanFactory 인터페이스에 정의되어 있다.

  ▶ ApplicationContext
  BeanFactory 의 기능을 모두 상속받아서 제공한다. 뿐만 아니라, Bean 을 관리하고 추가적인 부가기능을 제공한다.

  - MessageSource: 메시지소스를 활용한 국제화 기능
  - EnvironmentCapable: 환경변수
  - ApplicationEventPublisher: 이벤트 발행 기능
  - ResourceLoader: 편리한 리소스 조회 기능

  정리하자면,
  BeanFactory 는 스프링 컨테이너의 최상위 인터페이스이고, 스프링 빈을 관리하고 조회하는 기본적인 기능을 제공한다.
  ApplicationContext 는 BeanFactory 의 기능을 모두 상속받아서 제공하며, 더 많은 부가기능을 제공한다.
  BeanFactory 를 직접 사용할 일은 거의 없고, 대부분 ApplicationContext 를 사용한다.
  BeanFactory 나 ApplicationContext 를 모두 스프링 컨테이너라고 한다.

  ――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――


  */

}
