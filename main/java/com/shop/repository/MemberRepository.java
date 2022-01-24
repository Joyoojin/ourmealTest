package com.shop.repository;

import com.shop.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

//유진( B4.회원가입 )

public interface MemberRepository extends JpaRepository<Member, Long> {
    /**유진)추가  */
    Member findByMemIDOrEmail(String memID, String email); // 회원 가입시 아이디와 email 로 중복회원 검사 //

    Member findByMemID(String memID);

    Member findByEmail(String email);


}  
