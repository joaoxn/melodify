package com.melodify.datasource.repository;

import com.melodify.datasource.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {
    Optional<ProfileEntity> findByUserId(Long id);
    Optional<ProfileEntity> findByName(String name);
    Optional<List<ProfileEntity>> findAllByEmail(String email);
    Optional<List<ProfileEntity>> findAllByAccountLoginIn(List<String> loginList);
}
