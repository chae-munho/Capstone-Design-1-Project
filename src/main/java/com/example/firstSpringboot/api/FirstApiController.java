package com.example.firstSpringboot.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //일반 컨트롤러는 뷰 템플릿 페이지를 반환하지만 RestController는 Json을 반환하는 컨트롤러 물론 JSON말고 다른것도 반환
public class FirstApiController {
    @GetMapping("/api/hello")
    public String hello() {
        return "hello world";
    }
}
