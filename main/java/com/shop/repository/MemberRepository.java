package com.shop.repository;

import com.shop.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

//유진( B4.회원가입 )

public interface MemberRepository extends JpaRepository<Member, Long>{  /*유진 추가사항
    /**유진)추가  */
    Member findByMemIDOrEmail(String memID, String email); // 회원 가입시 아이디와 email 로 중복회원 검사 //

    Member findByMemID(String memID);

    Member findByEmail(String email);

 //   List<Member> findAll();      /**유진)추가 - 관리자 회원조회  */

    // List<Member> findByMemID(@Param("email") String email, Pageable pageable);
}


// QuerydslPredicateExecutor<Member> {  /*추가사항 */