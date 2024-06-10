package spring.basic.singleton;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import spring.basic.AppConfig;
import spring.basic.member.MemberService;

public class SingletonTest {

  @Test
  @DisplayName("spring 이 없는 순수한 DI 컨테이너")
  void pureContainer() {

    AppConfig appConfig = new AppConfig();

    //1. 조회: memberService() 를 호출할 때마다 객체를 생성.
    MemberService memberService1 = appConfig.memberService();

    MemberService memberService2 = appConfig.memberService();

    //memberService1 과 memberService2 는 서로 다르다.
    assertThat(memberService1).isNotSameAs(memberService2);
  }

}
