package spring.basic.scan.qualifier;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import spring.basic.discount.DiscountPolicy;
import spring.basic.member.Member;

@Component
@Primary
public class FixDiscountPolicy implements DiscountPolicy {

  @Override
  public int discount(Member member, int price) {
    return 0;
  }
}
