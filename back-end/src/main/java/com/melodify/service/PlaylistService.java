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
        if (data.getName() != null) {
            entity.setName(data.getName());
        }

        if (data.getAccount() != null) {
            entity.setAccount(data.getAccount());
        }

        if (data.getMusics() != null) {
            entity.setMusics(data.getMusics());
        }

        return entity;
    }

    @Override
    public PlaylistEntity newEntity() {
        return new PlaylistEntity();
    }
}
