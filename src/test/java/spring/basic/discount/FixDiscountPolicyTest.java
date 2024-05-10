package spring.basic.discount;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import spring.basic.member.Grade;
import spring.basic.member.Member;

class FixDiscountPolicyTest {

  DiscountPolicy discountPolicy = new FixDiscountPolicy();

  @Test
  void discountOnVIP() {
    //given
    Member member = new Member(1L, "memberVIP", Grade.VIP);
    //when
    int discountAmount = discountPolicy.discount(member, 10000);
    //then
    assertThat(discountAmount).isEqualTo(1000);
  }

  @Test
  void discountOnBASIC() {
    //given
    Member member = new Member(1L, "memberBASIC", Grade.BASIC);
    //when
    int discountAmount = discountPolicy.discount(member, 10000);
    //then
    assertThat(discountAmount).isEqualTo(0);
  }
}