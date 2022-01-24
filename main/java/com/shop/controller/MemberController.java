package com.shop.controller;

import com.shop.dto.MemberFormDto;
import com.shop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shop.entity.Member;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.validation.BindingResult;
import javax.validation.Valid;

//유진( B7.회원가입 )
//유진( B9.회원가입 )

@RequestMapping("/members")
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

/*회원 가입 */
    @GetMapping(value = "/new")     // 회원가입 페이지로 이동하기
    public String memberForm(Model model){
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "member/memberForm";     // memberForm.html 페이지로 이동
    }

    @PostMapping(value = "/new")   //회원가입 페이지에서 작성완료 버튼 클릭될 경우  -> MemberForm의 @NotEmpty 같은 validation 체크하기
    public String newMember(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model){          //@Valid 선언과 파라미터로 bindingResult 추가

        if(bindingResult.hasErrors()){    // 회원가입 입력 칸에 작성이 유효하지 않을 경우 (예.이름을 입력하지 않거나)// 에러페이지로 튕기지 않고 BindingResult.hasErrors 호출.
            return "member/memberForm";  //어떤 에러가 있는지 메시지를 기존의 회원가입 페이지 실행 됨..! (예. 이름은 필수 입력입니다.)
        }
        try {
            Member member = Member.createMember(memberFormDto, passwordEncoder);
            memberService.saveMember(member);
        } catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());           // 중복회원 가입 예외 발생시 에러메세지를 뷰로 전달
            return "member/memberForm";
        }
        return "redirect:/";
    }

/* 로그인 */
    @GetMapping(value = "/login")
    public String loginMember(){
        return "/member/memberLoginForm";
    }

    @GetMapping(value = "/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요"); //login 실패시 에러메세지
        return "/member/memberLoginForm";
    }

}