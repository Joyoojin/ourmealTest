package com.shop.controller;

import com.shop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

/*유진 생성 - 카카오톡 로그인 api (미완성)
**/

@RestController
@RequiredArgsConstructor
public class HomeController {

    private final MemberService memberService;  //추가
    private final PasswordEncoder passwordEncoder; //추가

    KakaoAPI kakaoApi = new KakaoAPI();

    @RequestMapping(value="/members/kakaologin") // 기존 /login
    public ModelAndView login(@RequestParam("code") String code, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        // 1번 인증코드 요청 전달
        String accessToken = kakaoApi.getAccessToken(code);
        // 2번 인증코드로 토큰 전달
        HashMap<String, Object> userInfo = kakaoApi.getUserInfo(accessToken);

        System.out.println("login info : " + userInfo.toString());

        if(userInfo.get("email") != null) {
            session.setAttribute("userId", userInfo.get("email"));
            session.setAttribute("accessToken", accessToken);
        }

        mav.addObject("userId", userInfo.get("email"));
        mav.setViewName("index");
        return mav;

    }

    @RequestMapping(value="/members/logout")  /* 수정 */
    public ModelAndView logout(HttpSession session) {
        ModelAndView mav = new ModelAndView();

        kakaoApi.kakaoLogout((String)session.getAttribute("accessToken"));
        session.removeAttribute("accessToken");
        session.removeAttribute("userId");
        mav.setViewName("index");
        return mav;
    }
}
