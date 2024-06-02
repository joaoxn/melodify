package com.melodify.datasource.repository;

import com.melodify.datasource.entity.MusicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MusicRepository extends JpaRepository<MusicEntity, Long> {
    Optional<MusicEntity> findByName(String name);
}
