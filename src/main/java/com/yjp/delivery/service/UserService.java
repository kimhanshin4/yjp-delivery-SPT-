package com.yjp.delivery.service;

import com.yjp.delivery.common.validator.UserValidator;
import com.yjp.delivery.controller.user.dto.request.ProfileEditReq;
import com.yjp.delivery.controller.user.dto.request.ProfileGetReq;
import com.yjp.delivery.controller.user.dto.response.ProfileEditRes;
import com.yjp.delivery.controller.user.dto.response.ProfileGetRes;
import com.yjp.delivery.service.provider.S3Provider;
import com.yjp.delivery.store.entity.UserEntity;
import com.yjp.delivery.store.repository.UserRepository;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final S3Provider s3Provider;

    @Value("${cloud.aws.s3.bucket.url}")
    private String url;

    public ProfileGetRes getUserProfile(ProfileGetReq req) {
        UserEntity user = userRepository.findByUserId(req.getUserId());
        UserValidator.validate(user);

        return UserServiceMapper.INSTANCE.toProfileGetRes(user);
    }

    public ProfileEditRes editUserProfile(MultipartFile multipartFile, ProfileEditReq req)
        throws IOException {
        UserEntity findUser = userRepository.findByUserId(req.getUserId());
        UserValidator.validate(findUser);
        String filename = findUser.getProfileImageUrl();
        String imageUrl;

        if (multipartFile != null) {
            if (filename != null) {
                imageUrl = s3Provider.updateImage(filename, multipartFile);
            } else {
                imageUrl = s3Provider.saveFile(multipartFile, "profile");
            }
        } else {
            imageUrl = findUser.getProfileImageUrl();
        }
        return UserServiceMapper.INSTANCE.toProfileEditRes(
            userRepository.save(UserEntity.builder()
                .userId(findUser.getUserId())
                .email(findUser.getEmail())
                .username(req.getUsername())
                .profileImageUrl(imageUrl)
                .introduction(req.getIntroduction())
                .role(findUser.getRole())
                .social(findUser.getSocial())
                .build())
        );
    }

    @Mapper
    public interface UserServiceMapper {

        UserServiceMapper INSTANCE = Mappers.getMapper(UserServiceMapper.class);

        ProfileGetRes toProfileGetRes(UserEntity userEntity);

        ProfileEditRes toProfileEditRes(UserEntity userEntity);
    }
}
