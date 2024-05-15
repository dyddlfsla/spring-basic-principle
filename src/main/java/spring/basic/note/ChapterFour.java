package spring.basic.note;

public class ChapterFour {

  /*
 self-taught
  *
  * ◆ IoC, DI, 그리고 컨테이너
  *
  * ① 제어의 역전 IoC, Inversion of Control
  *
  * 제어의 역전이란, 말 그대로 프로그램 흐름에 대한 제어권이 '역전'되어서 개발자가 제어권을 가진 것이 아니라,
  * 프레임워크와 컨테이너가 제어권을 가지게 된 것을 말한다.
  *
  * 예를 들어, 다음과 같은 코드는
  *
  * public class Main {
  *  public static void main(String[] args) {
  *      Service service = new Service();
  *      Client client = new Client(service);
  *      client.doSomething();
  *   }
  * }
  *
  * 개발자가 직접 필요한 객체를 new 연산자로 생성하고, 메소드를 호출한다. 프로그램 흐름의 제어권이 개발자에게 있는 것이다.
  *
  *  public static void main(String[] args) {
  *     // IoC 컨테이너 생성
  *     ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
  *
  *     // 클라이언트는 서비스 객체를 직접 생성하지 않고, 컨테이너로부터 받음
  *     Client client = context.getBean(Client.class);
  *     client.doSomething();
  * }
  *
  *
  * 하지만, 다음과 같이 컨테이너를 통해 의존성을 외부에서 주입하게 되면 그때부터는 개발자가 직접 객체를 생성하지 않는다.
  * 객체의 생성, 의존성 관리 같은 제어 권한이 개발자에서 컨테이너로 '역전'된 것이다.
  *
  * 이를, 제어의 역전이라고 한다.
  *
  * ② 프레임워크 vs. 라이브러리
  *
  * - 라이브러리 (Library)
  * 라이브러리는 개발자가 필요할 때 호출하여 사용할 수 있는 코드의 모음이다.
  * 보통 특정 기능을 수행하기 위한 함수, 클래스, 모듈 등이 포함될 수 있는데, 중요한 것은 라이브러리는 개발자가 직접 호출하여 사용하며,
  * 제어의 흐름은 개발자에게 있다는 것이다.즉, 개발자가 코드의 제어 흐름을 직접 작성하고 라이브러리를 호출하는 방식입니다.
  *
  * - 프레임워크 (Framework)
  * 프레임워크는 소프트웨어 개발을 위한 구조와 규칙을 제공하는 뼈대이다.
  * 프레임워크는 개발자가 특정 기능을 구현할 때 사용할 수 있는 인터페이스, 추상 클래스, 라이프 사이클 이벤트 등을 제공하며,
  * 프레임워크의 규칙에 따라 개발자가 코드를 작성해야 한다. 프레임워크는 개발자가 코드의 일부를 작성하고
  * 나머지 부분은 프레임워크가 제공하는 기능을 사용하여 완성한다. 따라서 제어의 흐름은 프레임워크에게 있게 되는 것이다.
  *
  *
  * */

}
