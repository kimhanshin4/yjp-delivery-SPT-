package com.yjp.delivery.common.config;

import com.yjp.delivery.security.AuthorizationFilter;
import com.yjp.delivery.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final RestTemplate restTemplate;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
        throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public AuthorizationFilter authorizationFilter() {
        return new AuthorizationFilter(restTemplate, userDetailsService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CSRF 설정
        http.csrf((csrf) -> csrf.disable());

        // 기본 설정인 Session 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정
        http.sessionManagement((sessionManagement) ->
            sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.authorizeHttpRequests((authorizeHttpRequests) ->
            authorizeHttpRequests
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .permitAll() // resources 접근 허용 설정
                .requestMatchers("/").permitAll() // 로그인 페이지 요청 허가
                .requestMatchers("/v1/user/kakao/**").permitAll() // 카카오 로그인
                .requestMatchers(HttpMethod.PATCH, "/v1/users").hasRole("USER") // 프로필 수정
                .requestMatchers(HttpMethod.GET, "/v1/users").permitAll() // 프로필 조회

                .requestMatchers(HttpMethod.POST, "/v1/orders").hasRole("USER") // 주문 생성
                .requestMatchers(HttpMethod.GET, "/v1/orders")
                .hasAnyRole("USER", "ADMIN") // 주문 단건 조회
                .requestMatchers("/v1/orders/users").hasRole("USER")
                .requestMatchers("/v1/orders/shops").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/v1/orders")
                .hasAnyRole("USER", "ADMIN") // 주문 삭제

                .requestMatchers("/v1/reviews").hasRole("USER") // 리뷰 생성, 삭제, 수정
                .requestMatchers("/v1/reviews/**").permitAll() //가게,유저 전체 리뷰 조회

                .requestMatchers("/v1/shops").permitAll() //가게 전체 조회
                .requestMatchers("/v1/shops/{shopId}").permitAll() // 가게 단건 조회
                .requestMatchers("/v1/shops/like").hasRole("USER") // 가게 좋아요

                .requestMatchers("/v1/menus/{menuId}").permitAll() //메뉴 단건 조회

                .requestMatchers("/v1/admin/**").hasRole("ADMIN") // 관리자 권한
                .anyRequest().authenticated() // 그 외 모든 요청 인증처리
        );

//        http.formLogin((formLogin) ->
//            formLogin
//                .loginPage("/api/user/login-page").permitAll()
//        );

        // 필터 관리
        http.addFilterBefore(authorizationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
