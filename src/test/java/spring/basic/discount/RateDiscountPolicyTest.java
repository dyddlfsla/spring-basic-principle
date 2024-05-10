package spring.basic.discount;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import spring.basic.member.Grade;
import spring.basic.member.Member;

class RateDiscountPolicyTest {

  RateDiscountPolicy rateDiscountPolicy = new RateDiscountPolicy();

  @Test
  @DisplayName("VIP 는 10% 할인이 적용되어야 한다.")
  void discountOnVIP() {
    //given
    Member member = new Member(1L, "memberVIP", Grade.VIP);
    //when
    int discountAmount = rateDiscountPolicy.discount(member, 20000);
    //then
    assertThat(discountAmount).isEqualTo(2000);
  }

  @Test
  @DisplayName("VIP 가 아니면 할인 적용이 되지 않아야 한다.")
  void discountOnBASIC() {
    //given
    Member member = new Member(1L, "memberBASIC", Grade.BASIC);
    //when
    int discountAmount = rateDiscountPolicy.discount(member, 20000);
    //then
    assertThat(discountAmount).isEqualTo(0);
  }

}