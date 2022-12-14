package org.server.models;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "employees")
public class Employee implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id @Column(name = "employee_id") private int id;

    @Column private String name;
    @Column private String surname;
    @Column private Date birthday;
    @Column private String post;
    @Column private float salary;
    @Column private String mail;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private User user;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "employees_projects",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private Set<Project> projects;

    public Employee() {}

    public Employee(String name, String surname, Date birthday, String post, float salary, String mail) {
        this();
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.post = post;
        this.salary = salary;
        this.mail = mail;
        user = new User();
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getSurname() { return surname; }
    public Date getBirthday() { return birthday; }
    public String getPost() { return post; }
    public float getSalary() { return salary; }
    public String getMail() { return mail; }
    public User getUser() { return user; }
    public Set<Project> getProjects() { return projects; }
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setSurname(String surname) { this.surname = surname; }
    public void setBirthday(Date birthday) { this.birthday = birthday; }
    public void setPost(String post) { this.post = post;}
    public void setSalary(float salary) { this.salary = salary; }
    public void setMail(String mail) { this.mail = mail; }
    public void setUser(User user) { this.user = user; }
    public void setProjects(Set<Project> projects) { this.projects = projects; }

    public String getFullName() { return surname + " " + name; }

    @Override
    public String toString() {
        return "Employee{id=" + id + ", name='" + name + "', surname='" + surname +
                "', birthday=" + birthday + ", post='" + post + "', salary=" + salary + '}';
    }
}