package com.yjp.delivery.controller.user;

import com.yjp.delivery.common.response.RestResponse;
import com.yjp.delivery.controller.user.dto.request.ProfileEditReq;
import com.yjp.delivery.controller.user.dto.request.ProfileGetReq;
import com.yjp.delivery.controller.user.dto.response.ProfileEditRes;
import com.yjp.delivery.controller.user.dto.response.ProfileGetRes;
import com.yjp.delivery.security.UserDetailsImpl;
import com.yjp.delivery.service.UserService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public RestResponse<ProfileGetRes> getProfile(@RequestBody ProfileGetReq req) {
        return RestResponse.success(userService.getUserProfile(req));
    }

    @PatchMapping
    public RestResponse<ProfileEditRes> editProfile(@RequestPart MultipartFile multipartFile,
        @RequestPart ProfileEditReq req,
        @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        req.setUserId(userDetails.getUser().getUserId());
        return RestResponse.success(userService.editUserProfile(multipartFile, req));
    }
}
