package com.melodify.service;

import com.melodify.datasource.entity.PlaylistEntity;
import com.melodify.datasource.repository.PlaylistRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class PlaylistServiceImpl extends GenericServiceImpl<PlaylistEntity, PlaylistEntity, PlaylistEntity, PlaylistRepository> {

    public PlaylistServiceImpl(PlaylistRepository repository) {
        super(repository);
    }

    @Override
    public PlaylistEntity equalProperties(PlaylistEntity entity, PlaylistEntity request) {
        Optional.ofNullable(request.getName())
                .ifPresent(entity::setName);

        Optional.ofNullable(request.getProfile())
                .ifPresent(entity::setProfile);

        Optional.ofNullable(request.getSongs())
                .ifPresent(entity::setSongs);
        return entity; // TODO After PlaylistRequest
    }

    @Override
    public PlaylistEntity respond(PlaylistEntity entity) {
        return entity; //TODO When PlaylistResponse
    }

    @Override
    public PlaylistEntity newEntity() {
        return new PlaylistEntity();
    }
}
