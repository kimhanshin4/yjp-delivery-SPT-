package com.yjp.delivery.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yjp.delivery.common.meta.Role;
import com.yjp.delivery.common.meta.Social;
import com.yjp.delivery.controller.sample.dto.response.kakao.KakaoUserInfoGetRes;
import com.yjp.delivery.store.entity.UserEntity;
import com.yjp.delivery.store.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j(topic = "Kakao Login")
@Service
@RequiredArgsConstructor
public class KakaoService {

    private final RestTemplate restTemplate;
    private final UserRepository userRepository;

    public String kakaoLogin(String code, HttpServletResponse res) throws JsonProcessingException {
        // Step 1 : 인가 코드로 토큰 요청해서 받기
        String accessToken = getToken(code, res);

        // Step 2. : 토큰으로 카카오 사용자 정보 가져오기
        KakaoUserInfoGetRes kakaoUserInfoGetRes = getKaKaoUserInfo(accessToken);

        // Step 3. : 필요시에 회원가입 (DB에 저장)
        registerKakaoUserIfNeeded(kakaoUserInfoGetRes);

        return "Bearer " + accessToken;
    }

    private String getToken(String code, HttpServletResponse res) throws JsonProcessingException {
        // 요청 URL 만들기
        URI uri = UriComponentsBuilder
            .fromUriString("https://kauth.kakao.com")
            .path("/oauth/token")
            .encode()
            .build()
            .toUri();

        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성, 카카오에서 필수적으로 요구하는 헤더와 바디
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "4ccfd58a89c4020d6020300338a9cf15");
        body.add("redirect_uri", "http://localhost:8080/v1/user/kakao/callback");
        body.add("code", code);

        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
            .post(uri)
            .headers(headers)
            .body(body);

        // HTTP 요청 보내기
        ResponseEntity<String> response = restTemplate.exchange(
            requestEntity,
            String.class
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
        res.addHeader("RefreshToken", jsonNode.get("refresh_token").asText());

        return jsonNode.get("access_token").asText(); // asText로 String값 반환 -> accessToken
    }

    private KakaoUserInfoGetRes getKaKaoUserInfo(String accessToken)
        throws JsonProcessingException {
        // 사용자 정보 보기 요청 URL
        URI uri = UriComponentsBuilder
            .fromUriString("https://kapi.kakao.com")
            .path("/v2/user/me")
            .encode()
            .build()
            .toUri();

        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
            .post(uri)
            .headers(headers)
            .body(new LinkedMultiValueMap<>());

        // HTTP 요청 보내기
        ResponseEntity<String> response = restTemplate.exchange(
            requestEntity,
            String.class
        );

        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
        Long id = jsonNode.get("id").asLong();
        String nickname = jsonNode.get("properties")
            .get("nickname").asText();
        String email = jsonNode.get("kakao_account")
            .get("email").asText();

        log.info("카카오 사용자 정보: " + id + ", " + nickname + ", " + email);
        return new KakaoUserInfoGetRes(id, nickname, email);
    }

    private void registerKakaoUserIfNeeded(KakaoUserInfoGetRes kakaoUserInfoGetRes) {
        // DB에 중복된 KakaoEmail 있는지 확인
        String kakaoEmail = kakaoUserInfoGetRes.getEmail();
        UserEntity kakaoUser = userRepository.findByEmail(kakaoEmail);

        String[] usernameArr = kakaoEmail.split("@");
        // 등록된 사용자가 없을 경우
        if (kakaoUser == null) {
            kakaoUser = UserEntity.builder()
                .username(usernameArr[0])
                .email(kakaoEmail)
                .role(Role.USER)
                .profileImageUrl(null)
                .introduction(null)
                .social(Social.KAKAO)
                .build();

            userRepository.save(kakaoUser);
        }
    }
}