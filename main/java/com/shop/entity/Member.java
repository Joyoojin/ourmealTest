package com.shop.entity;

import com.shop.constant.Role;
import com.shop.dto.MemberFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

//유진( B3.회원가입 )
//Member 엔티티에 회원정보 저장.  관리할 회원 정보(이름,이메일,비밀번호,주소,역할)


@Entity
@Table(name="member")
@Getter @Setter
@ToString
public class Member extends BaseEntity {

    @Id
    @Column(name="member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;  //자동 생성 숫자 id

    private String memID; //회원이 작성한 id  
                                                         /**유진)추가 -아이디 */
    private String name;

    @Column(unique = true) // 회원가입 시 이메일 중복 불가.unique 속성
    private String email;

    private String password;

    private String address;

    private String memPhone;  /**유진)추가 : 전화번호 */

    @Enumerated(EnumType.STRING) // Enum 타입을 엔티티의 속성으로 지정. 추후 enum 순서가 수정변경되어도  문제 발생하지 않도록 EnumType을 String으로 저장.
    private Role role;

    /* Member 엔티티 생성메서드 */
    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder){
        Member member = new Member();
        member.setMemID(memberFormDto.getMemID()); /** 유진. 추가 작성*/
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setAddress(memberFormDto.getAddress());
        String password = passwordEncoder.encode(memberFormDto.getPassword()); // SecurityConfig 클래스에서 등록한 BCryptPasswordEncoder Bean 을 파라미터로 넘겨서 비밀번호 암호화
        member.setPassword(password);
        member.setMemPhone(memberFormDto.getMemPhone()); /** 유진. 추가 작성*/
        member.setRole(Role.ADMIN);   // 차후 운영단계에선 , USER 으로 수정 필요!
        return member;
    }

} 
