package com.melodify.service;

import com.melodify.datasource.entity.PlaylistEntity;
import com.melodify.datasource.repository.PlaylistRepository;

public class PlaylistService extends GenericService<PlaylistEntity, PlaylistEntity, PlaylistRepository> {

    public PlaylistService(PlaylistRepository repository) {
        super(repository);
    }

    @Override
    public PlaylistEntity equalProperties(PlaylistEntity entity, PlaylistEntity data) {
        return null;
    }

    @Override
    public PlaylistEntity newEntity() {
        return null;
    }
}
