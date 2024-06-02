package com.melodify.datasource.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "genre")
public class GenreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 40, nullable = false)
    private String name;
}
