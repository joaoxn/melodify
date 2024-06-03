package com.melodify.datasource.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "profile")
public class ProfileEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;

    @OneToOne
    @JoinColumn(name = "account_id", nullable = false)
    private AccountEntity account;
}
