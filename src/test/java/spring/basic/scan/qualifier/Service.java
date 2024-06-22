package spring.basic.scan.qualifier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spring.basic.discount.DiscountPolicy;

@Component
public class Service {

  private final DiscountPolicy discountPolicy;

  @Autowired
  public Service(DiscountPolicy discountPolicy) {
    this.discountPolicy = discountPolicy;
  }

  public DiscountPolicy getDiscountPolicy() {
    return discountPolicy;
  }
}
