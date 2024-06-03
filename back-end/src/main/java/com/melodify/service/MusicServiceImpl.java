package com.melodify.service;

import com.melodify.datasource.entity.MusicEntity;
import com.melodify.datasource.repository.MusicRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MusicServiceImpl extends GenericServiceImpl<MusicEntity, MusicEntity, MusicRepository> {

    public MusicServiceImpl(MusicRepository repository) {
        super(repository);
    }

    @Override
    public MusicEntity equalProperties(MusicEntity entity, MusicEntity request) {
        if (request.getName() != null) {
            entity.setName(request.getName());
        }

        if (request.getArtistName() != null) {
            entity.setArtistName(request.getArtistName());
        }

        if (request.getArtistAccount() != null) {
            entity.setArtistAccount(request.getArtistAccount());
        }

        if (request.getGenre() != null) {
            entity.setGenre(request.getGenre());
        }

        if (request.getAudio() != null) {
            entity.setAudio(request.getAudio());
        }
        
        return entity;
    }

    @Override
    public MusicEntity newEntity() {
        return new MusicEntity();
    }
}
