package com.melodify.datasource.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.File;
import java.util.List;

@Data
@Entity
@Table(name = "music")
public class MusicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 100, nullable = false)
    private String artist_name;

    @OneToOne
    @JoinColumn(name = "artist_id")
    private AccountEntity artist;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            joinColumns = {
                    @JoinColumn(name = "music_id")
            }
    )
    private List<GenreEntity> genre;

    @Column(nullable = false)
    private File audio;
}
