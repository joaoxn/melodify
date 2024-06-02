package com.melodify.datasource.repository;

import com.melodify.datasource.entity.PlaylistMusicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistMusicRepository extends JpaRepository<PlaylistMusicEntity, Long> {
}
