package spring.basic.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

//private final MemberRepository memberRepository = new MemoryMemberRepository();
  private final MemberRepository memberRepository;
  /*
 self-taught
  * 이제 AppConfig 클래스에서 MemberServiceImpl 와 구현 memberRepository 를 연결시켜 주자 (의존성 주입).
  *
  * private final MemberRepository memberRepository;
  * 그럼 이제, MemberServiceImpl 클래스는 구현 객체인 new MemoryMemberRepository(); 에 대해 몰라도 된다.
  * 그냥 추상화에만 의존하면 되는 것이다.
  * MemberServiceImpl 입장에서 본다면 마치, 의존 관계가 외부에서 주입해주는 것 같다.
  * 그래서 이것을 DI(Dependency Injection) 의존 관계 주입 또는 의존성 주입이라고 한다.
  *
  * */
//  @Autowired
//  public MemberServiceImpl(MemberRepository memberRepository) {
//    this.memberRepository = memberRepository;
//  }

//  @Autowired
//  public void setMemberRepository(MemberRepository memberRepository) {
//    this.memberRepository = memberRepository;
//  }

  @Override
  public void join(Member member) {
    memberRepository.save(member);
  }

  @Override
  public Member findMember(Long memberId) {
    return memberRepository.findById(memberId);
  }

  public MemberRepository getMemberRepository() {
    return memberRepository;
  }
}
