package spring.basic.scan;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.basic.ComponentScanAppConfig;
import spring.basic.member.MemberService;

@SpringBootTest
public class ComponentScanAppConfigTest {

  @Test
  void basicScan() {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(
        ComponentScanAppConfig.class);

    MemberService memberService = ac.getBean(MemberService.class);
    Assertions.assertThat(memberService).isInstanceOf(MemberService.class);
  }

}
