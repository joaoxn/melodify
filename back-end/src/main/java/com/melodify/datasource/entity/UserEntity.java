package com.melodify.datasource.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "user")
public class UserEntity {
    @Column(length = 30, nullable = false)
    private String login;

    @Column(length = 60, nullable = false)
    private String password;

    @JoinColumn(name = "role_id", nullable = false)
    private RoleEntity role;
}
