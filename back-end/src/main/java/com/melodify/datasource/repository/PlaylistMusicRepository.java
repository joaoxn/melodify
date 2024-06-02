package com.melodify.datasource.repository;

import com.melodify.datasource.entity.MusicEntity;
import com.melodify.datasource.entity.PlaylistEntity;
import com.melodify.datasource.entity.PlaylistMusicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaylistMusicRepository extends JpaRepository<PlaylistMusicEntity, Long> {
    Optional<List<MusicEntity>> findByPlaylistId(Long id);
    Optional<List<PlaylistEntity>> findByMusicId(Long id);
}
