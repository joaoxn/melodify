package com.melodify.service;

import com.melodify.datasource.entity.GenreEntity;
import com.melodify.datasource.repository.GenreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class GenreServiceImpl extends GenericServiceImpl<GenreEntity, GenreEntity, GenreEntity, GenreRepository> {

    public GenreServiceImpl(GenreRepository repository) {
        super(repository);
    }

    @Override
    public GenreEntity equalProperties(GenreEntity entity, GenreEntity request) {
        Optional.ofNullable(request.getName())
                .ifPresent(entity::setName);
        return entity;
    }

    @Override
    public GenreEntity respond(GenreEntity entity) {
        return entity; // TODO waiting for GenreResponse
    }

    @Override
    public GenreEntity newEntity() {
        return new GenreEntity();
    }
}
