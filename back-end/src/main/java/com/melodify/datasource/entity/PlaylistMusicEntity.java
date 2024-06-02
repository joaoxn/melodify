package com.melodify.datasource.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "playlist_music")
public class PlaylistMusicEntity {
    @JoinColumn(name = "playlist_id", nullable = false)
    private PlaylistEntity playlist;

    @JoinColumn(name = "music_id", nullable = false)
    private MusicEntity music;

    @Column(nullable = false)
    private Integer position;
}
