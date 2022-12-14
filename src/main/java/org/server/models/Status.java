package org.server.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "statuses")
public class Status implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id @Column private int id;

    @Column private String name;

    @OneToMany(mappedBy = "status", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Project> projects;

    public Status() { this.projects = new ArrayList<>(); }

    public Status(String name) {
        this();
        this.name = name;
    }

    public Status(int id, String name) {
        this(name);
        this.id = id;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public List<Project> getProjects() { return projects; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setProjects(List<Project> projects) { this.projects = projects; }

    @Override
    public String toString() {  return "Status{id=" + id + ", name='" + name + "'}"; }
}