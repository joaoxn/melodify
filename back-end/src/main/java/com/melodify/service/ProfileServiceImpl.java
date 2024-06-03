package com.melodify.service;

import com.melodify.controller.dto.filter.ProfileFilter;
import com.melodify.datasource.entity.ProfileEntity;
import com.melodify.datasource.repository.ProfileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProfileServiceImpl extends GenericServiceImpl<ProfileEntity, ProfileFilter, ProfileRepository> {

    public ProfileServiceImpl(ProfileRepository repository) {
        super(repository);
    }

    @Override
    public ProfileEntity equalProperties(ProfileEntity entity, ProfileFilter request) {
        if (request.name() != null) {
            entity.setName(request.name());
        }

//        if (request.userId() != null) {
//            entity.setUser(request.userId()); //TODO
//        }

        return entity;
    }

    @Override
    public ProfileEntity newEntity() {
        return new ProfileEntity();
    }
}
