package com.yjp.delivery.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjp.delivery.sevice.KakaoService;
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
  public String homePage(){
    return "login";
  }

  @GetMapping("/v1/user/kakao/callback")
  public String kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException{
    String token = kakaoService.kakaoLogin(code); // (JWT)
//
//    // cookie에는 공백 문자가 들어갈 수 없어서 substring 해줌
//    Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, token.substring(7));
//    cookie.setPath("/");
//    response.addCookie(cookie);
//    // 클라이언트가 따로 작업하지 않아도 쿠키에 바로 넣어줄 수 있음.
    return "redirect:/";
  }
}
