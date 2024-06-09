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
    private List<String> artistsNames;

    @ManyToMany
    @JoinTable(
            name = "music_artists",
            joinColumns = @JoinColumn(name = "music_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "artist_id", nullable = false)
    )
    private List<ProfileEntity> artistsProfiles;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            joinColumns = @JoinColumn(name = "music_id")
    )
    private List<GenreEntity> genres;

    private Integer views;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "download_permission",
            joinColumns = @JoinColumn(name = "music_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "music_id", nullable = false)
    )
    private List<RoleEntity> downloadPermission;

    @Column(columnDefinition = "boolean default false")
    private Boolean downloadOnlySelected;

    @Column(nullable = false)
    private File audio;
}
