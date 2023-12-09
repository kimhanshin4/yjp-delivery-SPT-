package com.yjp.delivery.service;

import com.yjp.delivery.common.validator.UserValidator;
import com.yjp.delivery.controller.user.dto.request.ProfileEditReq;
import com.yjp.delivery.controller.user.dto.request.ProfileGetReq;
import com.yjp.delivery.controller.user.dto.response.ProfileEditRes;
import com.yjp.delivery.controller.user.dto.response.ProfileGetRes;
import com.yjp.delivery.store.entity.UserEntity;
import com.yjp.delivery.store.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public ProfileGetRes getUserProfile(ProfileGetReq req) {
        UserEntity user = userRepository.findByUserId(req.getUserId());
        UserValidator.validate(user);

        return UserServiceMapper.INSTANCE.toProfileGetRes(user);
    }

    public ProfileEditRes editUserProfile(ProfileEditReq req) {
        UserEntity findUser = userRepository.findByUserId(req.getUserId());
        UserValidator.validate(findUser);

        ProfileEditRes res = UserServiceMapper.INSTANCE.toProfileEditRes(
            userRepository.save(UserEntity.builder()
                .userId(findUser.getUserId())
                .email(findUser.getEmail())
                .username(req.getUsername())
                .profileImageUrl(req.getProfileImageUrl())
                .introduction(req.getIntroduction())
                .role(findUser.getRole())
                .social(findUser.getSocial())
                .build())
        );

        return res;
    }

    @Mapper
    public interface UserServiceMapper {

        UserServiceMapper INSTANCE = Mappers.getMapper(UserServiceMapper.class);

        ProfileGetRes toProfileGetRes(UserEntity userEntity);

        ProfileEditRes toProfileEditRes(UserEntity userEntity);
    }
}
