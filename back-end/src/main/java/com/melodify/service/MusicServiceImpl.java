package com.melodify.service;

import com.melodify.datasource.entity.AccountEntity;
import com.melodify.datasource.entity.MusicEntity;
import com.melodify.datasource.entity.RoleEntity;
import com.melodify.datasource.repository.MusicRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MusicServiceImpl extends GenericServiceImpl<MusicEntity, MusicEntity, MusicRepository> {

    public MusicServiceImpl(MusicRepository repository) {
        super(repository);
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
    public MusicEntity equalProperties(MusicEntity entity, MusicEntity request) {
        if (request.getName() != null) {
            entity.setName(request.getName());
        }

        if (request.getArtistName() != null) {
            entity.setArtistName(request.getArtistName());
        }

        if (request.getArtistProfile() != null) {
            entity.setArtistProfile(request.getArtistProfile());
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
