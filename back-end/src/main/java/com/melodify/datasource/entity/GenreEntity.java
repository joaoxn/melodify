package com.melodify.datasource.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "genre")
public class GenreEntity {
    @Column(length = 40, nullable = false)
    private String name;
}
