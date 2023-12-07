package com.yjp.delivery.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjp.delivery.service.KakaoService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final KakaoService kakaoService;

    @GetMapping("/")
    public String homePage() {
        return "login";
    }

    @GetMapping("/v1/user/kakao/callback")
    public String kakaoLogin(@RequestParam String code, HttpServletResponse response)
        throws JsonProcessingException {
        String token = kakaoService.kakaoLogin(code);
        // cookie에는 공백 문자가 들어갈 수 없어서 substring 해줌
        Cookie cookie = new Cookie("Authorization", token);
        cookie.setPath("/");
        response.addCookie(cookie);
        return "redirect:/";
    }

    @GetMapping("/v1/user/kakao/logout-callback")
    public String kakaoLogout(HttpServletResponse response) {
        Cookie cookie = new Cookie("Authorization", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return "redirect:/";
    }
}
