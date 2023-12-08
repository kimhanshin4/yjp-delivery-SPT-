package com.yjp.delivery.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjp.delivery.service.KakaoService;
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
        response.addHeader("Authorization", token);

        return "redirect:/";
    }

    @GetMapping("/v1/user/kakao/logout-callback")
    public String kakaoLogout(HttpServletResponse response) {
        return "redirect:/";
    }
}
