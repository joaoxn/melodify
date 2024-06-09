package com.melodify.service;

import com.melodify.controller.dto.request.AccountRequest;
import com.melodify.controller.dto.response.AccountResponse;
import com.melodify.datasource.entity.AccountEntity;
import com.melodify.datasource.entity.RoleEntity;
import com.melodify.datasource.repository.RoleRepository;
import com.melodify.datasource.repository.AccountRepository;
import com.melodify.infra.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        AccountEntity account = repository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Not found Account with id " + id));

        RoleEntity role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Not found Role with name " + roleName));
        account.addRole(role);
        repository.save(account);
        return respond(account);
    }

    public AccountResponse deleteRole(Long id, String roleName) {
        AccountEntity account = repository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Not found Account with id "+ id));

        RoleEntity role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Not found Role with name " + roleName));

        account.removeRole(role);
        return respond(repository.save(account));
    }

    @Override
    public AccountEntity equalProperties(AccountEntity entity, AccountRequest request) {
        Optional.ofNullable(request.login())
                .ifPresent(entity::setLogin);

        Optional.ofNullable(request.password()) //TODO Encrypting when Spring Security is implemented
                .ifPresent(entity::setPassword);
        return entity;
    }

    @Override
    public AccountResponse respond(AccountEntity entity) {
        return new AccountResponse(
                entity.getLogin(),
                entity.getRoles().stream().map(RoleEntity::getName).toList()
        );
    }

    @Override
    public AccountEntity newEntity() {
        return new AccountEntity();
    }
}
