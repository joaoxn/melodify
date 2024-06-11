package com.melodify.service;

import com.melodify.controller.dto.request.AccountRequest;
import com.melodify.controller.dto.response.AccountResponse;
import com.melodify.datasource.entity.AccountEntity;
import com.melodify.datasource.entity.RoleEntity;
import com.melodify.datasource.repository.RoleRepository;
import com.melodify.datasource.repository.AccountRepository;
import com.melodify.infra.exception.CustomException;
import com.melodify.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
//TODO Validation for min. and max. length for login and password
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
        ValidationUtil.validadeOrThrow(
                ValidationUtil.length(request.login(), 3, 30, true),
                new CustomException(HttpStatus.BAD_REQUEST,
                        "Out of bounds: Account login must have length between 3 and 30 (Login: " + request.login() + ")")
        );

        Optional.ofNullable(request.login())
                .ifPresent(entity::setLogin);

        ValidationUtil.validadeOrThrow(
                ValidationUtil.password(request.password(), true),
                new CustomException(HttpStatus.BAD_REQUEST,
                        "Out of bounds: Account login must have length between 3 and 30 (Login: " + request.login() + ")")
        );

        Optional.ofNullable(request.password()) //TODO Encrypting when Spring Security is implemented
                .ifPresent(entity::setPassword);
        Object[] properties = new Object[] {request.password()};
        int nullIndex = ValidationUtil.areNull(properties);
        if (nullIndex != -1) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Null property: Property cannot be null (Property: "+ properties[nullIndex] +")"); //TODO: shows property as null (should be the property name)
        }
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
