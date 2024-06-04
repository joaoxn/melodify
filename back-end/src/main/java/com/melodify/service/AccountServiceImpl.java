package com.melodify.service;

import com.melodify.controller.dto.request.UserRequest;
import com.melodify.controller.dto.request.CustomUserRequest;
import com.melodify.datasource.entity.AccountEntity;
import com.melodify.datasource.repository.RoleRepository;
import com.melodify.datasource.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AccountServiceImpl extends GenericServiceImpl<AccountEntity, UserRequest, AccountRepository> implements GenericService<AccountEntity, UserRequest> {
    private AccountRepository repository;
    private RoleRepository roleRepository;

    public AccountServiceImpl(AccountRepository repository, RoleRepository roleRepository) {
        super(repository);
        this.repository = repository;
        this.roleRepository = roleRepository;
    }

    public AccountEntity create(CustomUserRequest request) {
//        TODO
//        log.info("GenericService.create(DTO entity) -> Criando entidade baseado na Requisição dada");
//        log.info("SERVICE equalProperties() -> ( Chamada: create(...) ) Transferindo dados da Requisição para uma Entidade própria");
//        E entitySave = equalProperties(newEntity(), request);
//        return repository.save(entitySave);
        return null;
    }

    public AccountEntity addRole(Long id, String roleName) {
        //TODO
        return null;
    }

    public AccountEntity alterRole(Long id, String roleName) {
        //TODO
        return null;
    }

    public AccountEntity deleteRole(Long id, String roleName) {
        //TODO
        return null;
    }

    @Override
    public AccountEntity equalProperties(AccountEntity entity, UserRequest request) {
        if (request.login() != null) {
            entity.setLogin(request.login());
        }

        if (request.password() != null) {
            entity.setPassword(request.password());
        }

        return entity;
    }

    @Override
    public AccountEntity newEntity() {
        return new AccountEntity();
    }
}
