package com.melodify.controller;

import com.melodify.controller.dto.filter.ProfileFilter;
import com.melodify.controller.dto.request.ProfileRequest;
import com.melodify.controller.dto.request.CustomProfileRequest;
import com.melodify.controller.dto.response.AccountProfileResponse;
import com.melodify.datasource.entity.ProfileEntity;
import com.melodify.datasource.entity.AccountEntity;
import com.melodify.service.ProfileServiceImpl;
import com.melodify.service.AccountServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("user")
public class AccountController {
    private final AccountServiceImpl userService;
    private final ProfileServiceImpl profileService;

    public AccountController(
            AccountServiceImpl userService,
            ProfileServiceImpl profileService
    ) {
        this.userService = userService;
        this.profileService = profileService;

    }

    @PostMapping
    public ResponseEntity<AccountProfileResponse> postAll(@RequestBody ProfileRequest request) {
        AccountEntity user = userService.create(request);
        ProfileEntity profile = profileService.create(new ProfileFilter(request.name(), user.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(new AccountProfileResponse(user, profile));
    }

    @PostMapping
    public ResponseEntity<AccountEntity> post(@RequestBody CustomProfileRequest request) {
        return ResponseEntity.ok(userService.create(request));
    }

    @PostMapping
    public ResponseEntity<AccountProfileResponse> postAll(@RequestBody CustomProfileRequest request) {
        AccountEntity user = userService.create(request);
        ProfileEntity profile = profileService.create(new ProfileFilter(request.name(), user.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(new AccountProfileResponse(user, profile));
    }
}
