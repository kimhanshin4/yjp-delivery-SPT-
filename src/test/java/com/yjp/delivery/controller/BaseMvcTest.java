package com.yjp.delivery.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yjp.delivery.common.meta.Role;
import com.yjp.delivery.controller.filter.MockSpringSecurityFilter;
import com.yjp.delivery.security.UserDetailsImpl;
import com.yjp.delivery.store.entity.UserEntity;
import java.security.Principal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
public class BaseMvcTest {

    @Autowired
    protected ObjectMapper objectMapper;
    protected MockMvc mockMvc;
    protected Principal userMockPrincipal;
    protected Principal adminMockPrincipal;
    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setUp() {
        mockUserSetUp();
        mockAdminSetUp();
        var mockMvcRequestBuilders =
            MockMvcRequestBuilders.get("http://example.com")
                .header("Authorization", "Bearer <<전달받은토큰값>>");
        this.mockMvc =
            MockMvcBuilders.webAppContextSetup(context)
                .defaultRequest(mockMvcRequestBuilders)
                .apply(springSecurity(new MockSpringSecurityFilter()))
                .build();
    }

    private void mockUserSetUp() {
        String email = "emoney96@naver.com";
        String username = "ysys";
        Role role = Role.ROLE_USER;
        UserEntity userEntity = UserEntity.builder()
            .email(email)
            .username(username)
            .role(role)
            .build();

        UserDetailsImpl userDetails = new UserDetailsImpl(userEntity);
        userMockPrincipal = new UsernamePasswordAuthenticationToken(
            userDetails, "", userDetails.getAuthorities());
    }

    private void mockAdminSetUp() {
        String email = "emoney96@naver.com";
        String username = "ysys";
        Role role = Role.ROLE_ADMIN;
        UserEntity userEntity = UserEntity.builder()
            .email(email)
            .username(username)
            .role(role)
            .build();

        UserDetailsImpl userDetails = new UserDetailsImpl(userEntity);
        adminMockPrincipal = new UsernamePasswordAuthenticationToken(
            userDetails, "", userDetails.getAuthorities());
    }
}
