package com.shop.repository;

import com.shop.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
/**유진)추가 - 관리자 회원조회  */
/** 유진.추가 카테고리 검색 */
@Repository
@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom{


    private final EntityManager em;



    /**유진)추가 - 관리자 회원조회  */

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

}
//  private JPAQueryFactory queryFactory2;

//    public MemberRepositoryCustomImpl(em){
//        this.queryFactory2 = new JPAQueryFactory(em);
//    }


/* 유진.카테고리별 검색 추가- 작동안되네 */
    /*
    private BooleanExpression searchPCategoryEq(PCategory searchPCategory){
        return searchPCategory == null ? null : QItem.item.pCategory.eq(searchPCategory);
    }
*/
/*  멤버 관리자 조회 테스트 삭제 가능
    private BooleanExpression regDtsAfter(String searchDateType){

        LocalDateTime dateTime = LocalDateTime.now();

        if(StringUtils.equals("all", searchDateType) || searchDateType == null){
            return null;
        } else if(StringUtils.equals("1d", searchDateType)){
            dateTime = dateTime.minusDays(1);
        } else if(StringUtils.equals("1w", searchDateType)){
            dateTime = dateTime.minusWeeks(1);
        } else if(StringUtils.equals("1m", searchDateType)){
            dateTime = dateTime.minusMonths(1);
        } else if(StringUtils.equals("6m", searchDateType)){
            dateTime = dateTime.minusMonths(6);
        }

        return QMember.member.regTime.after(dateTime);
    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery){

        if(StringUtils.equals("memID", searchBy)){
            return QMember.member.memID.like("%" + searchQuery + "%");
        } else if(StringUtils.equals("createdBy", searchBy)){
            return QMember.member.createdBy.like("%" + searchQuery + "%");
        }

        return null;
    }
*/