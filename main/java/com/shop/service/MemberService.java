package com.shop.service;

import com.shop.entity.Member;
import com.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

//유진( B5.회원가입 )
//유진 (C1. 로그인, 로그아웃) : 스프링 시큐리티에서 UserDetailsService 를 구현하고 있는 클래스를 통해 로그인 기능 구현

@Service
@Transactional  // 로직 처리중 에러 발생시, 변경된 데이터를 로직 수행 이전상태로 콜백.
@RequiredArgsConstructor  // final 이나, @Nonnull 붙은 필드에 생성자를 자동으로 생성. 생성자가 1개이고, 생성자 파라미터 타임이 빈등록 가능하면  @Autowired 어노테이션 없이 의존성 주입 가능
public class MemberService implements UserDetailsService {  //UserDetailService 인터페이스( db에서 회원정보 가져옴)를 구현.

    private final MemberRepository memberRepository; // @RequiredArgsConstructor  에 의해 생성자 자동 생성.

    public Member saveMember(Member member){
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    /** 유진 수정  : validateDuplicateMember  이메일 중복체크 -> 이메일, 아이디 둘다 중복체크  로 변경*/
    /*회원가입 검증*/
    private void validateDuplicateMember(Member member) {  //  중복 회원은 IllegalStateException 예외 발생  
        Member findMember = memberRepository.findByMemIDOrEmail(member.getMemID(), member.getEmail()); //id와 email 중에 중복된것이 있는지 검사. 둘 중 하나라도 중복 회원 있으면 예외발생
        if (findMember != null) {
            throw new IllegalStateException("이미 가입된 회원입니다.");  
        }
    }

    /* 로그인 */
    /*스프링 시큐리티에서 회원의 정보를 담기 위해서 사용하는 인터페이스인 UserDetails 이 인터페이스를 직접
    구현하거나 스프링 시큐리티에서 제공하는 User 클래스(UserDetails 인터페이스 구현하는) 사용.
    */
    //loadUserByUsername 메소드 : UserDetailsService 에서 상속된 메소드. 회원 정보 조회하여 사용자 정보와 권한 갖는 userDetails 인터페이스 반환

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {  //loadUserByUsername 메소드 오버라이딩. 로그인할 유저의 email 을 파라미터로 전달
        Member member = memberRepository.findByEmail(email);

        if(member == null){
            throw new UsernameNotFoundException(email);
        }

        return User.builder()                    //UserDetails 을 구현하고 있는 User 객체 반환.
                .username(member.getEmail())    //User 객체 생성하기 위해서, 생성자로 회원의 이메일,비밀번호,role을 파라미터로 넘겨줌
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }

}