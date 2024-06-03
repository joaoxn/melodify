package com.melodify.service;

import com.melodify.datasource.entity.AccountEntity;
import com.melodify.datasource.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AccountService extends GenericService<AccountEntity, AccountEntity, AccountRepository> {

    public AccountService(AccountRepository repository) {
        super(repository);
    }

    @Override
    public AccountEntity equalProperties(AccountEntity entity, AccountEntity data) {
        if (data.getName() != null) {
            entity.setName(data.getName());
        }

        if (data.getUser() != null) {
            entity.setUser(data.getUser());
        }

        return entity;
    }

    @Override
    public AccountEntity newEntity() {
        return new AccountEntity();
    }
}
