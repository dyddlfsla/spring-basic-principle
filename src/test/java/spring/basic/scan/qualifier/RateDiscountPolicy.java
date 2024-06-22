package spring.basic.scan.qualifier;

import org.springframework.stereotype.Component;
import spring.basic.discount.DiscountPolicy;
import spring.basic.member.Member;

@Component
public class RateDiscountPolicy implements DiscountPolicy {

  @Override
  public int discount(Member member, int price) {
    return 0;
  }
}
