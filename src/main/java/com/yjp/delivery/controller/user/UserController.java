package com.yjp.delivery.controller.user;

import com.yjp.delivery.controller.user.dto.request.ProfileEditReq;
import com.yjp.delivery.controller.user.dto.request.ProfileGetReq;
import com.yjp.delivery.controller.user.dto.response.ProfileEditRes;
import com.yjp.delivery.controller.user.dto.response.ProfileGetRes;
import com.yjp.delivery.security.UserDetailsImpl;
import com.yjp.delivery.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/v1/user/profile")
    public ProfileGetRes getProfile(@RequestBody ProfileGetReq req) {
        return userService.getUserProfile(req);
    }

    @PatchMapping("/v1/user/profile")
    public ProfileEditRes editProfile(@RequestBody ProfileEditReq req,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        req.setUserId(userDetails.getUser().getUserId());
        return userService.editUserProfile(req);
    }
}
