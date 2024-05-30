package spring.basic.note;

public class $7_HowToGetBean {

  /*
 self-taught
  *
  * Ⅰ. 스프링 컨테이너에 등록된 모든 Bean 조회.
  *
  * AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
  *
  * void findAllBeans() {
  *   String[] beanDefinitionNames = ac.getBeanDefinitionNames();
  *
  *   for (String beanDefinitionName : beanDefinitionNames) {
  *   Object bean = ac.getBean(beanDefinitionName);
  *   System.out.printf("name = %s , object = %s%n", beanDefinitionName, bean);
  *   }
  * }
  *
  * ▶ getBeanDefinitionNames(): 스프링 컨테이너에 등록된 모든 Bean 의 이름을 String[] 형태로 반환한다.
  * ▶ getBean(String name): name 으로 Bean 객체를 찾아 반환한다.
  *
  * -스프링 Bean 은 Role(역할)을 가지고 있는데,
  * ROLE_APPLICATION: 일반적으로 사용자가 정의한 Bean
  * ROLE_INFRASTRUCTURE: 스프링 프레임워크가 내부적으로 사용하기 위해 만든 Bean
  *
  * ――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――
  *
  * Ⅱ. 스프링 Bean 조회 - 기본
  *
  * AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
  *
  * ◆ Bean 이름으로 찾기
  * -getBean(String name) 를 호출하면, 이름이 일치하는 Bean 객체를 반환한다.
  *
  * ◆ Bean 타입으로 찾기
  * -getBean(Class<T> requiredType) 을 호출하면, 타입과 일치하는 Bean 객체를 반환한다.
  *
  * ◆ Bean 이름으로 찾기 실패한 경우
  * Bean 을 찾지 못했다는 예외가 발생한다.
  * -NoSuchBeanDefinitionException: No bean named 'XXX' available
  *
  * ――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――
  *
  * Ⅲ. 스프링 Bean 조회 - 동일한 타입의 bean 이 2개 이상인 경우
  *
  * void findBeanByTypeOnDuplicate() {
  *   assertThrows(NoUniqueBeanDefinitionException.class,
  *     () -> ac.getBean(MemberRepository.class));
  * }
  *
  * getBean(Class<T> requiredType) 을 호출하여 타입으로 스프링 bean 을 조회 시,
  * 같은 타입의 스프링 bean 이 2개 이상 있다면 다음과 같은 예외가 발생한다.
  * NoUniqueBeanDefinitionException: No qualifying bean of type 'spring.basic.member.MemberRepository' available:
  * 메소드가 어떤 스프링 bean 을 반환해야 할지 특정할 수 없으므로 발생하는 예외이다.
  *
  * 이렇게 같은 타입의 bean 이 2개 이상 있는 경우에는, 한 개의 bean 만 특정할 수 있도록 bean 의 이름까지 넣어주는게 좋다.
  * getBean(String name, Class<T> requiredType)
  *
  * ◆ 특정 타입의 Bean 을 모두 검색하기.
  * ▶ Map<String, MemberRepository> foundBeans = ac.getBeansOfType(MemberRepository.class);
  *
  * getBeansOfType(@Nullable Class<T> type) 메소드를 사용하면,
  * 해당 타입에 맞는 모든 Bean 들이 담겨 있는 Map 컬렉션을 얻을 수 있다.
  *
  * ――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――
  *
  * Ⅳ. 스프링 bean 조회 - 상속 관계
  *
  * - 스프링 bean 을 조회할 때, 부모 타입을 사용하면 자식 타입에 해당하는 bean 까지 모두 조회된다.
  *
  * 예를 들어, RateDiscountPolicy 클래스와 FixDiscountPolicy 클래스가 DiscountPolicy 인터페이스를 구현하고 있는 경우,
  * DiscountPolicy 타입의 스프링 bean 을 조회하면 RateDiscountPolicy 객체와 FixDiscountPolicy 객체 둘 다 같이 조회될 것이고
  * 어떤 bean 을 반환해야 할지, 특정할 수 없으므로 예외가 발생한다.
  *
  * DiscountPolicy discountPolicy = ac.getBean(DiscountPolicy.class); // 2개 이상의 bean 이 검색되고, 예외가 발생.
  *
  * 위에서 배운 것처럼, bean 을 특정할 수 있도록 이름도 매개 변수로 추가해주면 된다.
  *
  * DiscountPolicy discountPolicy = ac.getBean("rateDiscountPolicy", DiscountPolicy.class);
  *
  * ◆ Object 타입으로 스프링 bean 조회하기.
  * 자바의 모든 클래스는 Object 클래스의 하위 클래스이다.
  * Object 타입으로 스프링 bean 을 조회하면 어떻게 될까?
  * => 사용자가 정의한 스프링 bean 을 포함한 스프링 컨테이너에 담긴 모든 bean 객체가 조회된다.
  *
  *
  * */



}
