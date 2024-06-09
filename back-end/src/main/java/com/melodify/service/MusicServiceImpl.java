package com.melodify.service;

import com.melodify.controller.dto.request.MusicRequest;
import com.melodify.datasource.entity.AccountEntity;
import com.melodify.datasource.entity.MusicEntity;
import com.melodify.datasource.entity.RoleEntity;
import com.melodify.datasource.repository.GenreRepository;
import com.melodify.datasource.repository.MusicRepository;
import com.melodify.datasource.repository.ProfileRepository;
import com.melodify.datasource.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class MusicServiceImpl extends GenericServiceImpl<MusicEntity, MusicRequest, MusicEntity, MusicRepository> {
    private final ProfileRepository profileRepository;
    private final GenreRepository genreRepository;
    private final RoleRepository roleRepository;

    public MusicServiceImpl(
            MusicRepository repository,
            ProfileRepository profileRepository,
            GenreRepository genreRepository,
            RoleRepository roleRepository
    ) {
        super(repository);
        this.profileRepository = profileRepository;
        this.genreRepository = genreRepository;
        this.roleRepository = roleRepository;
    }

    public Boolean isMusicDownloadable(AccountEntity account, MusicEntity music) {
        Boolean downloadOnlySelected = music.getDownloadOnlySelected();
        List<RoleEntity> downloadPermission  = music.getDownloadPermission();
        if (downloadOnlySelected) {
            for (RoleEntity role : account.getRoles()) {
                if (downloadPermission.contains(role)) {
                    return true;
                }
            }
        } else {
            
        }
        return false;
    }

    @Override
    public MusicEntity equalProperties(MusicEntity entity, MusicRequest request) {
        Optional.ofNullable(request.name())
                .ifPresent(entity::setName);

        profileRepository.findAllByAccountLoginIn(request.artistsLogins())
                .ifPresent(entity::setArtistsProfiles);

        entity.getArtistsProfiles()
                .forEach(profile -> request.artistsNames().remove(profile.getName()));

        Optional.ofNullable(request.artistsNames())
                .ifPresent(entity::setArtistsNames);

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
    public MusicEntity respond(MusicEntity entity) {
        return entity; //TODO waiting for MusicResponse
    }

    @Override
    public MusicEntity newEntity() {
        return new MusicEntity();
    }
}
