package com.melodify.service;

import com.melodify.datasource.entity.RoleEntity;
import com.melodify.datasource.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenService {
    private RoleRepository roleRepository;

    public RoleEntity createRole(String name) {
        log.info("TokenService.create(String entity) -> Creating entity based on given Request");
        RoleEntity entitySave = new RoleEntity(name);
        return roleRepository.save(entitySave);
    }

    public List<RoleEntity> getRoles() {
        return roleRepository.findAll();
    }

    //TODO: [Security] Token Manager
}
