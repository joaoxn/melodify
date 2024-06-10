package com.melodify.service;

import co.elastic.clients.elasticsearch.nodes.Http;
import com.melodify.controller.dto.request.SongRequest;
import com.melodify.datasource.entity.AccountEntity;
import com.melodify.datasource.entity.SongEntity;
import com.melodify.datasource.entity.RoleEntity;
import com.melodify.datasource.repository.GenreRepository;
import com.melodify.datasource.repository.SongRepository;
import com.melodify.datasource.repository.ProfileRepository;
import com.melodify.datasource.repository.RoleRepository;
import com.melodify.infra.exception.CustomException;
import com.melodify.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.el.util.Validation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class SongServiceImpl extends GenericServiceImpl<SongEntity, SongRequest, SongEntity, SongRepository> {
    private final SongRepository repository;
    private final ProfileRepository profileRepository;
    private final GenreRepository genreRepository;
    private final RoleRepository roleRepository;

    public SongServiceImpl(
            SongRepository repository,
            ProfileRepository profileRepository,
            GenreRepository genreRepository,
            RoleRepository roleRepository
    ) {
        super(repository);
        this.repository = repository;
        this.profileRepository = profileRepository;
        this.genreRepository = genreRepository;
        this.roleRepository = roleRepository;
    }

    public Integer addViews(Long id, Integer views) {
        SongEntity song = repository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Nou found SongEntity with id "+ id));
        return song.addViews(views);
    }

    public Boolean isSongDownloadable(AccountEntity account, SongEntity song) {
        Boolean downloadOnlySelected = song.getDownloadOnlySelected();
        List<RoleEntity> downloadPermission = song.getDownloadPermission();
        for (RoleEntity role : account.getRoles()) {
            if (downloadPermission.contains(role)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public SongEntity equalProperties(SongEntity entity, SongRequest request) {
        ValidationUtil.validadeOrThrow(
                ValidationUtil.length(request.name(), 3, 100, true),
                new CustomException(HttpStatus.BAD_REQUEST,
                        "Out of bounds: Song name must have length between 3 and 100 (Name: " + request.name() + ")")
        );

        Optional.ofNullable(request.name())
                .ifPresent(entity::setName);

        profileRepository.findAllByAccountLoginIn(request.artistsLogins())
                .ifPresent(entity::setArtistsProfiles);

        entity.getArtistsProfiles()
                .forEach(profile -> request.artistsNames().remove(profile.getName()));

        Optional.ofNullable(request.artistsNames())
                .ifPresent((names) -> {
                    for (String name : names) {
                        ValidationUtil.validadeOrThrow(
                                ValidationUtil.length(name, 3, 100, false),
                                new CustomException(HttpStatus.BAD_REQUEST,
                                        "Out of bounds: Artist name must have length between 3 and 100 and not be null (Name: " + name + ")")
                        );
                    }
                    entity.setArtistsNames(names);
                });

        genreRepository.findAllByNameIn(request.genres())
                .ifPresent(entity::setGenres);

        roleRepository.findAllByNameIn(request.downloadPermissionRoles())
                .ifPresent(entity::setDownloadPermission);

        Optional.ofNullable(request.downloadOnlySelected())
                .ifPresent(entity::setDownloadOnlySelected);

        Optional.ofNullable(request.audio())
                .ifPresent(entity::setAudio);
        return entity;
    }

    @Override
    public SongEntity respond(SongEntity entity) {
        return entity; //TODO waiting for SongResponse
    }

    @Override
    public SongEntity newEntity() {
        return new SongEntity();
    }
}
