package com.melodify.datasource.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "playlist")
public class PlaylistEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;

    @JoinColumn(name = "profile_id", nullable = false)
    private ProfileEntity profile;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            joinColumns = @JoinColumn(name = "playlist_id")
    )
    @OrderColumn(name = "song_order")
    private List<SongEntity> songs;
}
