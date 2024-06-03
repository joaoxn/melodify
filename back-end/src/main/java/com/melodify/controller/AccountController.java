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
@RequestMapping("account")
public class AccountController {
    private final AccountServiceImpl accountService;
    private final ProfileServiceImpl profileService;

    public AccountController(
            AccountServiceImpl accountService,
            ProfileServiceImpl profileService
    ) {
        this.accountService = accountService;
        this.profileService = profileService;

    }

    @PostMapping
    public ResponseEntity<AccountProfileResponse> postAll(@RequestBody ProfileRequest request) {
        AccountEntity account = accountService.create(request);
        ProfileEntity profile = profileService.create(new ProfileFilter(request.name(), account.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(new AccountProfileResponse(account, profile));
    }

    @PostMapping
    public ResponseEntity<AccountEntity> post(@RequestBody CustomProfileRequest request) {
        return ResponseEntity.ok(accountService.create(request));
    }

    @PostMapping
    public ResponseEntity<AccountProfileResponse> postAll(@RequestBody CustomProfileRequest request) {
        AccountEntity account = accountService.create(request);
        ProfileEntity profile = profileService.create(new ProfileFilter(request.name(), account.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(new AccountProfileResponse(account, profile));
    }
}
