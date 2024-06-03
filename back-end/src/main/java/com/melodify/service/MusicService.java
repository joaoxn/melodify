package com.melodify.service;

import com.melodify.datasource.entity.MusicEntity;
import com.melodify.datasource.repository.MusicRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MusicService extends GenericService<MusicEntity, MusicEntity, MusicRepository> {
    public MusicService(MusicRepository repository) {
        super(repository);
    }

    @Override
    public MusicEntity equalProperties(MusicEntity entity, MusicEntity data) {
        if (data.getName() != null) {
            entity.setName(data.getName());
        }

        if (data.getArtistName() != null) {
            entity.setArtistName(data.getArtistName());
        }

        if (data.getArtistAccount() != null) {
            entity.setArtistAccount(data.getArtistAccount());
        }

        if (data.getGenre() != null) {
            entity.setGenre(data.getGenre());
        }

        if (data.getAudio() != null) {
            entity.setAudio(data.getAudio());
        }
        
        return entity;
    }

    @Override
    public MusicEntity newEntity() {
        return new MusicEntity();
    }
}
