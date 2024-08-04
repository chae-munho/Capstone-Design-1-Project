package com.example.firstSpringboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

//요청은 컨트롤러가 받는다
@Controller
public class FirstController {
    @GetMapping("/hi")
    public String niceToMeetYou(Model model) { //greetings.mustache의 username을 받을려면 Model을 사용해야함 안그러면 오류남
        model.addAttribute("username", "문호");
        return "greetings"; //templated/greetings.mustache을 알아서 찾아서 브라우저로 전송한다.
    }

    @GetMapping("/bye")
    public String seeYouNext(Model model) {
        model.addAttribute("nickname", "munho");
        return "goodbye";
    }
    @GetMapping("/test")
    public String test() {
        return "articles/test";
    }



}
