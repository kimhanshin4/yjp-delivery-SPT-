package com.yjp.delivery.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.yjp.delivery.controller.user.dto.request.ProfileEditReq;
import com.yjp.delivery.controller.user.dto.request.ProfileGetReq;
import com.yjp.delivery.controller.user.dto.response.ProfileEditRes;
import com.yjp.delivery.controller.user.dto.response.ProfileGetRes;
import com.yjp.delivery.service.provider.S3Provider;
import com.yjp.delivery.store.entity.UserEntity;
import com.yjp.delivery.store.repository.UserRepository;
import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private S3Provider s3Provider;

    @Test
    @DisplayName("프로필 조회 테스트")
    void 프로필_조회() {
        // given
        Long userId = 1L;
        String username = "ysys";
        ProfileGetReq profileGetReq = ProfileGetReq.builder().userId(userId).build();
        UserEntity user = UserEntity.builder().userId(userId).username(username).build();
        when(userRepository.findByUserId(any())).thenReturn(user);

        // when
        ProfileGetRes profileGetRes = userService.getUserProfile(profileGetReq);

        // then
        assertThat(profileGetRes.getUsername()).isEqualTo(username);
        verify(userRepository).findByUserId(any());
    }

    @Test
    @DisplayName("프로필 수정 테스트")
    void 프로필_수정() throws IOException {
        // given
        Long userId = 1L;
        String username = "ysys";
        String updatedUsername = "ysysys";
        MultipartFile multipartFile = null;
        ProfileEditReq profileEditReq = ProfileEditReq.builder()
            .userId(userId)
            .username(updatedUsername)
            .build();
        UserEntity user = UserEntity.builder()
            .userId(userId)
            .username(username)
            .build();
        UserEntity updatedUser = UserEntity.builder()
            .userId(userId)
            .username(updatedUsername)
            .build();
        when(userRepository.findByUserId(any())).thenReturn(user);
        when(userRepository.save(any())).thenReturn(updatedUser);

        // when
        ProfileEditRes profileEditRes = userService.editUserProfile(multipartFile, profileEditReq);

        // then
        assertThat(profileEditRes.getUsername()).isEqualTo(updatedUsername);
        verify(userRepository).findByUserId(any());
        verify(userRepository).save(any());
    }
}
