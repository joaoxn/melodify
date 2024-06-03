package com.melodify.datasource.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "users")
public class AccountEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String login;

    @Column(length = 60, nullable = false)
    private String password;

    @ManyToOne(targetEntity = RoleEntity.class)
    @JoinColumn(name = "role_id", nullable = false)
    private RoleEntity role;
}
