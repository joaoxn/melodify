package com.melodify.datasource.repository;

import com.melodify.datasource.entity.PlaylistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlaylistRepository extends JpaRepository<PlaylistEntity, Long> {
    Optional<PlaylistEntity> findByName(String name);
}
