package org.server.models;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "projects")
public class Project implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id @Column private int id;

    @Column private String name;
    @Column private String description;
    @Column private Date deadline;
    @Column private String todo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id")
    private Status status;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "projects")
    private List<Employee> employees;

    public Project() { this.employees = new ArrayList<>(); }

    public Project(String name, String description, Date deadline) {
        this();
        this.name = name;
        this.description = description;
        this.deadline = deadline;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Date getDeadline() { return deadline; }
    public Status getStatus() { return status; }
    public List<Employee> getEmployees() { return employees; }
    public String getTodo() { return todo; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setDeadline(Date deadline) { this.deadline = deadline; }
    public void setStatus(Status status) { this.status = status; }
    public void setEmployees(List<Employee> employees) { this.employees = employees; }
    public void setTodo(String todo) { this.todo = todo; }

    @Override
    public String toString() {
        return "Project{id=" + id + ", name='" + name + "', " +
                "description='" + description + "', deadline=" + deadline +
                ", status=" + status + '}';
    }
}