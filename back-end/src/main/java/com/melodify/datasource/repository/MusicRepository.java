package com.melodify.datasource.repository;

import com.melodify.datasource.entity.MusicEntity;
import com.melodify.datasource.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MusicRepository extends JpaRepository<MusicEntity, Long> {
    @Query("SELECT m FROM MusicEntity m WHERE m.name like :name")
    Optional<List<MusicEntity>> findAllByName(String name);

    @Query(value = "SELECT m FROM MusicEntity m JOIN m.artistsProfile a WHERE a.id = :id")
    Optional<List<MusicEntity>> findAllByArtistId(Long id);

    @Query(value = "SELECT m FROM PlaylistEntity p JOIN p.musics m WHERE p.id = :id")
    Optional<List<MusicEntity>> findAllByPlaylistId(Long id);
}
