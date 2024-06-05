package com.melodify.service;

import com.melodify.datasource.entity.PlaylistEntity;
import com.melodify.datasource.repository.PlaylistRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PlaylistServiceImpl extends GenericServiceImpl<PlaylistEntity, PlaylistEntity, PlaylistRepository> {

    public PlaylistServiceImpl(PlaylistRepository repository) {
        super(repository);
    }

    @Override
    public PlaylistEntity equalProperties(PlaylistEntity entity, PlaylistEntity request) {
        if (request.getName() != null) {
            entity.setName(request.getName());
        }

        if (request.getProfile() != null) {
            entity.setProfile(request.getProfile());
        }

        if (request.getMusics() != null) {
            entity.setMusics(request.getMusics());
        }

        return entity;
    }

    @Override
    public PlaylistEntity newEntity() {
        return new PlaylistEntity();
    }
}
