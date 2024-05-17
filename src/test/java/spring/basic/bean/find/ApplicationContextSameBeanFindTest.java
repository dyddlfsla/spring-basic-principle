package spring.basic.bean.find;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.basic.member.MemberRepository;
import spring.basic.member.MemoryMemberRepository;

public class ApplicationContextSameBeanFindTest {

  AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(
      SameBeanConfig.class);

  @Configuration
  static class SameBeanConfig {

    @Bean
    public MemberRepository memberRepositoryOne() {
      return new MemoryMemberRepository();
    }

    @Bean
    public MemberRepository memberRepositoryTwo() {
      return new MemoryMemberRepository();
    }

  }

  @Test
  @DisplayName("type 으로 Bean 조회 시, 같은 type 의 Bean 2개 이상 있는경우")
  void findBeanByTypeOnDuplicate() {
    assertThrows(NoUniqueBeanDefinitionException.class,
        () -> ac.getBean(MemberRepository.class));
  }

  @Test
  @DisplayName("같은 type 의 Bean 2개 이상이라면, 추가 매개 변수로 이름까지 넣어주면 된다.")
  void findBeanByTypeAndName() {
    MemberRepository memberRepository = ac.getBean("memberRepositoryOne", MemberRepository.class);
    assertThat(memberRepository).isInstanceOf(MemberRepository.class);
  }

  @Test
  @DisplayName("특정 type 을 모두 조회하기")
  void findAllBeanByType() {
    Map<String, MemberRepository> foundBeans = ac.getBeansOfType(MemberRepository.class);
    for (String key : foundBeans.keySet()) {
      System.out.println("key = " + key + "/ value = " + foundBeans.get(key));
    }
    System.out.println("foundBeans = " + foundBeans);
    assertThat(foundBeans.size()).isEqualTo(2);
  }
}
