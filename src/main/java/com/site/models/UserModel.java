package com.site.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import java.util.UUID;

@Entity
@Table(name = "TB_USER")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "email", nullable = false)
    @Email
    private String email;
    @Column(name = "password", nullable = false, columnDefinition = "TEXT")
    private String password;
    @Column(name = "telephone", nullable = false, length = 15)
    private String telephone;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
