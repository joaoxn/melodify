package com.melodify.datasource.repository;

import com.melodify.datasource.entity.SongEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SongRepository extends JpaRepository<SongEntity, Long> {
    @Query("SELECT m FROM SongEntity m WHERE m.name like :name ORDER BY m.views")
    Optional<List<SongEntity>> findAllByNameSearch(String nameSearch);

    @Query(value = "SELECT m FROM SongEntity m JOIN m.artistsProfile a WHERE a.id = :id")
    Optional<List<SongEntity>> findAllByArtistId(Long id);

    @Query(value = "SELECT m FROM PlaylistEntity p JOIN p.songs m WHERE p.id = :id")
    Optional<List<SongEntity>> findAllByPlaylistId(Long id);

    Optional<List<SongEntity>> findAllByGenreId(Long genreId);
}
