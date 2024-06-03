package com.melodify.controller;

import com.melodify.controller.dto.filter.ProfileFilter;
import com.melodify.controller.dto.request.ProfileRequest;
import com.melodify.controller.dto.request.CustomProfileRequest;
import com.melodify.controller.dto.response.UserProfileResponse;
import com.melodify.datasource.entity.ProfileEntity;
import com.melodify.datasource.entity.UserEntity;
import com.melodify.service.ProfileServiceImpl;
import com.melodify.service.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("user")
public class UserController {
    private final UserServiceImpl userService;
    private final ProfileServiceImpl profileService;

    public UserController(
            UserServiceImpl userService,
            ProfileServiceImpl profileService
    ) {
        this.userService = userService;
        this.profileService = profileService;

    }

    @PostMapping
    public ResponseEntity<UserProfileResponse> postAll(@RequestBody ProfileRequest request) {
        UserEntity user = userService.create(request);
        ProfileEntity profile = profileService.create(new ProfileFilter(request.name(), user.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserProfileResponse(user, profile));
    }

    @PostMapping
    public ResponseEntity<UserEntity> post(@RequestBody CustomProfileRequest request) {
        return ResponseEntity.ok(userService.create(request));
    }

    @PostMapping
    public ResponseEntity<UserProfileResponse> postAll(@RequestBody CustomProfileRequest request) {
        UserEntity user = userService.create(request);
        ProfileEntity profile = profileService.create(new ProfileFilter(request.name(), user.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserProfileResponse(user, profile));
    }
}
