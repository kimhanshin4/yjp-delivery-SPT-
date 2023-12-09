package com.yjp.delivery.store.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.yjp.delivery.store.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    private UserEntity saveUser;

    @BeforeEach
    void setUp() {
        Long userId = 1L;
        String email = "emoney96@naver.com";
        String username = "ysys";
        saveUser = userRepository.save(UserEntity.builder()
            .userId(userId)
            .email(email)
            .username(username)
            .build());
    }

    @Test
    @DisplayName("email로 유저 조회 테스트")
    void email_유저_조회() {
        // given
        String email = "emoney96@naver.com";

        // when
        UserEntity user = userRepository.findByEmail(email);

        // then
        assertThat(user).isEqualTo(saveUser);
    }

    @Test
    @DisplayName("username으로 유저 조회 테스트")
    void username_유저_조회() {
        // given
        String username = "ysys";

        // when
        UserEntity user = userRepository.findByUsername(username);

        // then
        assertThat(user).isEqualTo(saveUser);
    }

    @Test
    @DisplayName("userId로 유저 조회 테스트")
    void userId_유저_조회() {
        // given
        Long userId = saveUser.getUserId();

        // when
        UserEntity user = userRepository.findByUserId(userId);

        // then
        assertThat(user).isEqualTo(saveUser);
    }
}
