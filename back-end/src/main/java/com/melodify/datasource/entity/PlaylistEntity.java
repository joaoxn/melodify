package com.melodify.datasource.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "playlist")
public class PlaylistEntity {
    @Column(length = 100, nullable = false)
    private String name;

    @JoinColumn(name = "account_id", nullable = false)
    private AccountEntity account;
}
