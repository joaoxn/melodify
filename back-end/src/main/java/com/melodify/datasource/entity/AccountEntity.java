package com.melodify.datasource.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "account")
public class AccountEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String login;

    @Column(length = 60, nullable = false)
    private String password;

    @ManyToOne(targetEntity = RoleEntity.class)
    @JoinTable(
            joinColumns = @JoinColumn(name = "account_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "role_id", nullable = false, unique = true)
    )
    private List<RoleEntity> roles;
}
