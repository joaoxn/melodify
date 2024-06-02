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
        return null; //TODO
    }

    @Override
    public MusicEntity newEntity() {
        return new MusicEntity();
    }
}
