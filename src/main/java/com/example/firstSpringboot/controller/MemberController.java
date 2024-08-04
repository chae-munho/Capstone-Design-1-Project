package com.example.firstSpringboot.controller;

import com.example.firstSpringboot.dto.MemberDTO;
import com.example.firstSpringboot.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
//MemberController은 웹 사이트 메인 페이지로 접속 후 다른 링크로 들어가기 위한 컨트롤러입니다.
@Controller
@RequiredArgsConstructor
public class MemberController {
    //생성자 주입
    private final MemberService memberService;
    //회원가입 페이지 출력 요청
    @GetMapping("/")  //회원가입, 로그인 이전 메인페이지를 띄어줍니다.
    public String index() {
        return "index";
    }
    @GetMapping("/main")  //회원가입, 로그인 이후 메인페이지를 띄어줍니다
    public String main() {
        return "main";
    }
    @GetMapping("/introduce")  //게임 소개 페이지를 띄어줍니다.
    public String introduce() {
        return "introduce";
    }
    @GetMapping("/member/save")  //회원가입 페이지를 띄어줍니다.
    public String saveForm() {
        return "save";
    }
    @GetMapping("/member/recommendation")  //추천게임 페이지를 띄어줍니다.
    public String Recommendation() {
        return "recommendation";
    }

    @PostMapping("/member/save")  //회원가입 후 로직입니다.
    public String save(@ModelAttribute MemberDTO memberDTO) {
        System.out.println("MemberController.save");
        System.out.println("memberDTO = " + memberDTO);
        memberService.save(memberDTO);
        return "login";
    }
    @GetMapping("/member/login") //로그인 페이지를 띄어줍니다.
    public String loginForm() {
        return "login";
    }
    @PostMapping("/member/login")  //로그인 성공후의 로직입니다.
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session, Model model) {
        MemberDTO loginResult = memberService.login(memberDTO);

        if(loginResult != null) {//login 성공
            session.setAttribute("loginName", loginResult.getMemberName()); //세션에 Name 정보를 저장
            return "main";
        }
        else {//login 실패
            return "login";
        }
    }
    @GetMapping("/member/logout")  //로그아웃 기능을 수행합니다.
    public String logout(HttpSession session) {
        session.invalidate();
        return "index";
    }
    @PostMapping("/member/email-check")  //이메일 중복 방지 기능을 수행합니다.
    public @ResponseBody String emailCheck(@RequestParam("memberEmail") String memberEmail) {
        System.out.println("memberEmail = " + memberEmail);
        String checkResult = memberService.emailCheck(memberEmail);
        return checkResult;

    }
}
