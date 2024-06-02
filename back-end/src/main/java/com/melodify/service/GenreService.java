package com.melodify.service;

import com.melodify.datasource.entity.GenreEntity;
import com.melodify.datasource.repository.GenreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GenreService extends GenericService<GenreEntity, GenreEntity, GenreRepository> {
    public GenreService(GenreRepository repository) {
        super(repository);
    }

    @Override
    public GenreEntity equalProperties(GenreEntity entity, GenreEntity data) {
        return null; //TODO
    }

    @Override
    public GenreEntity newEntity() {
        return new GenreEntity();
    }
}
