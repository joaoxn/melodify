package com.melodify.service;

import com.melodify.controller.dto.request.AccountRequest;
import com.melodify.controller.dto.request.CustomAccountRequest;
import com.melodify.datasource.entity.UserEntity;
import com.melodify.datasource.repository.RoleRepository;
import com.melodify.datasource.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl extends GenericServiceImpl<UserEntity, AccountRequest, UserRepository> implements GenericService<UserEntity, AccountRequest> {
    private UserRepository repository;
    private RoleRepository roleRepository;

    public UserServiceImpl(UserRepository repository, RoleRepository roleRepository) {
        super(repository);
        this.repository = repository;
        this.roleRepository = roleRepository;
    }

    public UserEntity create(CustomAccountRequest request) {
//        TODO
//        log.info("GenericService.create(DTO entity) -> Criando entidade baseado na Requisição dada");
//        log.info("SERVICE equalProperties() -> ( Chamada: create(...) ) Transferindo dados da Requisição para uma Entidade própria");
//        E entitySave = equalProperties(newEntity(), request);
//        return repository.save(entitySave);
        return null;
    }

    @Override
    public UserEntity equalProperties(UserEntity entity, AccountRequest request) {
        if (request.login() != null) {
            entity.setLogin(request.login());
        }

        if (request.password() != null) {
            entity.setPassword(request.password());
        }

        return entity;
    }

    @Override
    public UserEntity newEntity() {
        return new UserEntity();
    }
}
