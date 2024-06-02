package com.melodify.datasource.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "playlist_music")
public class PlaylistMusicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "playlist_id", nullable = false)
    private PlaylistEntity playlist;

    @JoinColumn(name = "music_id", nullable = false)
    private MusicEntity music;

    @Column(nullable = false)
    private Integer position;
}
