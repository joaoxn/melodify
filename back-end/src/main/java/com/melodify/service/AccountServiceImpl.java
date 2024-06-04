package com.melodify.service;

import com.melodify.controller.dto.request.AccountRequest;
import com.melodify.controller.dto.response.AccountResponse;
import com.melodify.datasource.entity.AccountEntity;
import com.melodify.datasource.repository.RoleRepository;
import com.melodify.datasource.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AccountServiceImpl extends GenericServiceImpl<AccountEntity, AccountRequest, AccountResponse, AccountRepository> implements GenericService<AccountEntity, AccountRequest, AccountResponse> {
    private final AccountRepository repository;
    private final RoleRepository roleRepository;

    public AccountServiceImpl(AccountRepository repository, RoleRepository roleRepository) {
        super(repository);
        this.repository = repository;
        this.roleRepository = roleRepository;
    }

    public AccountResponse addRole(Long id, String roleName) {
        //TODO
        return null;
    }

    public AccountResponse alterRole(Long id, String roleName) {
        //TODO
        return null;
    }

    public AccountResponse deleteRole(Long id, String roleName) {
        //TODO
        return null;
    }

    @Override
    public AccountEntity equalProperties(AccountEntity entity, AccountRequest request) {
        if (request.login() != null) {
            entity.setLogin(request.login());
        }

        if (request.password() != null) {
            entity.setPassword(request.password());
        }

        return entity;
    }

    @Override
    public AccountResponse respond(AccountEntity entity) {
        return null; //TODO
    }

    @Override
    public AccountEntity newEntity() {
        return new AccountEntity();
    }
}
