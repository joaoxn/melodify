package com.melodify.service;

import com.melodify.datasource.entity.AccountEntity;
import com.melodify.datasource.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AccountServiceImpl extends GenericServiceImpl<AccountEntity, AccountEntity, AccountRepository> {

    public AccountServiceImpl(AccountRepository repository) {
        super(repository);
    }

    @Override
    public AccountEntity equalProperties(AccountEntity entity, AccountEntity request) {
        if (request.getName() != null) {
            entity.setName(request.getName());
        }

        if (request.getUser() != null) {
            entity.setUser(request.getUser());
        }

        return entity;
    }

    @Override
    public AccountEntity newEntity() {
        return new AccountEntity();
    }
}
