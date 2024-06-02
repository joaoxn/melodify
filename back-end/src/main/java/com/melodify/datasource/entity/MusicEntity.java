package com.melodify.datasource.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.File;

@Data
@Entity
@Table(name = "music")
public class MusicEntity {
    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 100, nullable = false)
    private String artist_name;

    @JoinColumn(name = "artist_id")
    private AccountEntity artist;

    @JoinColumn(name = "genre_id")
    private GenreEntity genre;

    @Column(columnDefinition = "bytea", nullable = false)
    private File audio;
}
