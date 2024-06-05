package com.melodify.service;

import com.melodify.controller.dto.filter.ProfileFilter;
import com.melodify.controller.dto.request.ProfileRequest;
import com.melodify.controller.dto.response.ProfileResponse;
import com.melodify.datasource.entity.ProfileEntity;
import com.melodify.datasource.repository.ProfileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProfileServiceImpl extends GenericServiceImpl<ProfileEntity, ProfileRequest, ProfileResponse, ProfileRepository> implements GenericService<ProfileEntity, ProfileRequest, ProfileResponse> {

    public ProfileServiceImpl(ProfileRepository repository) {
        super(repository);
    }

    @Override
    public ProfileEntity equalProperties(ProfileEntity entity, ProfileRequest request) {
        if (request.firstName() != null) {
            entity.setName(request.firstName());
        }

//        if (request.accountId() != null) {
//            entity.setAccount(request.accountId()); //TODO
//        }

        return entity;
    }

    @Override
    public ProfileResponse respond(ProfileEntity entity) {
        return null; //TODO
    }

    @Override
    public ProfileEntity newEntity() {
        return new ProfileEntity();
    }
}
