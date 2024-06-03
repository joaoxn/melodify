package com.melodify.service;

import com.melodify.datasource.entity.GenreEntity;
import com.melodify.datasource.repository.GenreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GenreServiceImpl extends GenericServiceImpl<GenreEntity, GenreEntity, GenreRepository> {

    public GenreServiceImpl(GenreRepository repository) {
        super(repository);
    }

    @Override
    public GenreEntity equalProperties(GenreEntity entity, GenreEntity request) {
        if (request.getName() != null) {
            entity.setName(request.getName());
        }

        return entity;
    }

    @Override
    public GenreEntity newEntity() {
        return new GenreEntity();
    }
}
