package com.melodify.service;

import com.melodify.datasource.entity.PlaylistEntity;
import com.melodify.datasource.repository.PlaylistRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PlaylistService extends GenericService<PlaylistEntity, PlaylistEntity, PlaylistRepository> {

    public PlaylistService(PlaylistRepository repository) {
        super(repository);
    }

    @Override
    public PlaylistEntity equalProperties(PlaylistEntity entity, PlaylistEntity data) {
        return null; //TODO
    }

    @Override
    public PlaylistEntity newEntity() {
        return new PlaylistEntity();
    }
}
