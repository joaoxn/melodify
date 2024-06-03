package com.melodify.service;

import com.melodify.datasource.entity.UserEntity;
import com.melodify.datasource.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService extends GenericService<UserEntity, UserEntity, UserRepository> {
    public UserService(UserRepository repository) {
        super(repository);
    }

    @Override
    public UserEntity equalProperties(UserEntity entity, UserEntity data) {
        if (data.getLogin() != null) {
            entity.setLogin(data.getLogin());
        }

        if (data.getPassword() != null) {
            entity.setPassword(data.getPassword());
        }

        if (data.getRole() != null) {
            entity.setRole(data.getRole());
        }

        return entity;
    }

    @Override
    public UserEntity newEntity() {
        return new UserEntity();
    }
}
