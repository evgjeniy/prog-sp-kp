package org.server.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roles")
public class Role implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id @Column(name = "role_id") private int id;

    @Column(name = "role_name") private String name;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<User> users;

    public Role() {
        this.users = new ArrayList<>();
    }

    public Role(String name) {
        this();
        this.name = name;
    }

    public Role(int id, String name) {
        this(name);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void addUser(User user) {
        user.setRole(this);
        this.users.add(user);
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + (name == null ? "null" : name) + '\'' +
                '}';
    }
}