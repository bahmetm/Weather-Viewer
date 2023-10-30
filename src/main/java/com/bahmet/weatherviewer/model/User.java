package com.bahmet.weatherviewer.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users", indexes = {
        @Index(name = "idx_username", columnList = "username")
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user")
    private Set<Session> sessions = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "users_locations",
            joinColumns = @JoinColumn(name = "user"),
            inverseJoinColumns = @JoinColumn(name = "location"))
    private Set<Location> locations = new HashSet<>();

    public User() {
    }

    public User(String username, String hashedPassword) {
        this.username = username;
        this.password = hashedPassword;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
