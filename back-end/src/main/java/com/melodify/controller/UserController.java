package com.melodify.controller;

import com.melodify.controller.dto.filter.ProfileFilter;
import com.melodify.controller.dto.request.RoleRequest;
import com.melodify.controller.dto.request.UserRequest;
import com.melodify.controller.dto.request.CustomUserRequest;
import com.melodify.controller.dto.response.UserResponse;
import com.melodify.datasource.entity.ProfileEntity;
import com.melodify.datasource.entity.AccountEntity;
import com.melodify.datasource.repository.ProfileRepository;
import com.melodify.infra.exception.CustomException;
import com.melodify.service.ProfileServiceImpl;
import com.melodify.service.AccountServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("account")
public class UserController {
    private final AccountServiceImpl accountService;
    private final ProfileServiceImpl profileService;
    private final ProfileRepository profileRepository;

    public UserController(
            AccountServiceImpl accountService,
            ProfileServiceImpl profileService,
            ProfileRepository profileRepository
    ) {
        this.accountService = accountService;
        this.profileService = profileService;
        this.profileRepository = profileRepository;
    }

    @PostMapping
    public ResponseEntity<UserResponse> post(@RequestBody UserRequest request) {
        AccountEntity account = accountService.create(request);
        ProfileEntity profile = profileService.create(new ProfileFilter(request.name(), account.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserResponse(account, profile));
    }

    @PutMapping("role")
    public ResponseEntity<AccountEntity> putRole(@PathVariable Long profileId, @RequestBody RoleRequest request) {
        CustomUserRequest dto = new CustomUserRequest(null, null, null, request.role());
        return ResponseEntity.ok(accountService.alterRole(findAccountId(profileId), request.role()));
    }

    @GetMapping("{profileId}")
    public ResponseEntity<UserResponse> get(@PathVariable Long profileId) {
        AccountEntity account = accountService.get(findAccountId(profileId));
        ProfileEntity profile = profileService.get(profileId);
        return ResponseEntity.ok(new UserResponse(account, profile));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAll() {
        List<ProfileEntity> profiles = profileService.getAll();
        List<UserResponse> response = new ArrayList<>();

        for (ProfileEntity profile : profiles) {
            response.add(new UserResponse(profile.getAccount(), profile));
        }

        return ResponseEntity.ok(response);
    }

    @PutMapping("{profileId}")
    public ResponseEntity<UserResponse> put(
            @PathVariable Long profileId,
            @RequestBody UserRequest request
    ) {
        AccountEntity account = accountService.alter(findAccountId(profileId), request);
        ProfileEntity profile = profileService.alter(profileId, new ProfileFilter(request.name(), account.getId()));
        return ResponseEntity.ok(new UserResponse(account, profile));
    }

    @DeleteMapping("{profileId}")
    public ResponseEntity<String> delete(@PathVariable Long profileId) {
        String responseProfile = profileService.delete(profileId);
        String responseAccount = accountService.delete(findAccountId(profileId));
        return ResponseEntity.ok(responseProfile + "\n" + responseAccount);
    }

    private Long findAccountId(Long profileId) {
        return profileRepository.findById(profileId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND))
                .getAccount().getId();
    }
}
