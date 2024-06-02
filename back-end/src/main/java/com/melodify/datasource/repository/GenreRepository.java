package com.melodify.datasource.repository;

import com.melodify.datasource.entity.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<GenreEntity, Long> {
    Optional<GenreEntity> findByName(String name);
}
