package com.melodify.service;

import com.melodify.controller.dto.request.ProfileRequest;
import com.melodify.controller.dto.response.AccountResponse;
import com.melodify.controller.dto.response.ProfileResponse;
import com.melodify.datasource.entity.ProfileEntity;
import com.melodify.datasource.entity.RoleEntity;
import com.melodify.datasource.repository.AccountRepository;
import com.melodify.datasource.repository.ProfileRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class ProfileServiceImpl extends GenericServiceImpl<ProfileEntity, ProfileRequest, ProfileResponse, ProfileRepository> implements GenericService<ProfileEntity, ProfileRequest, ProfileResponse> {
    private final AccountRepository accountRepository;

    public ProfileServiceImpl(ProfileRepository repository, AccountRepository accountRepository) {
        super(repository);
        this.accountRepository = accountRepository;
    }

    @Override
    public ProfileEntity equalProperties(ProfileEntity entity, ProfileRequest request) {
        Optional.ofNullable(request.name())
                .ifPresent(entity::setName);

        Optional.ofNullable(request.email())
                .ifPresent(entity::setEmail);

        accountRepository.findById(request.accountId())
                .ifPresent(entity::setAccount);
        return entity;
    }

    @Override
    @SneakyThrows
    public ProfileResponse respond(ProfileEntity entity) {
        return new ProfileResponse(
                entity.getName(),
                entity.getEmail(),
                new AccountResponse(
                        entity.getAccount().getLogin(),
                        entity.getAccount().getRoles()
                                .stream().map(RoleEntity::getName).toList()
                )
        );
    }

    @Override
    public ProfileEntity newEntity() {
        return new ProfileEntity();
    }
}
