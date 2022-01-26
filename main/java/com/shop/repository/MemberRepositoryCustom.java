package com.shop.repository;

import com.shop.entity.Member;

import java.util.List;
/**유진)추가 - 관리자 회원조회  */
public interface MemberRepositoryCustom{

    List<Member> findAll();  /**유진)추가 - 관리자 회원조회  */
}