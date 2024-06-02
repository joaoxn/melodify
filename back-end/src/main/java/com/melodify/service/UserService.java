package com.melodify.service;

import com.melodify.datasource.entity.UserEntity;
import com.melodify.datasource.repository.UserRepository;

public class UserService extends GenericService<UserEntity, UserEntity, UserRepository> {
    public UserService(UserRepository repository) {
        super(repository);
    }

    @Override
    public UserEntity equalProperties(UserEntity entity, UserEntity data) {
        return null; //TODO
    }

    @Override
    public UserEntity newEntity() {
        return new UserEntity();
    }
}
