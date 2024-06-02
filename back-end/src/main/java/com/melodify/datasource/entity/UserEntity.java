package com.melodify.datasource.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String login;

    @Column(length = 60, nullable = false)
    private String password;

    @JoinColumn(name = "role_id", nullable = false)
    private RoleEntity role;
}
