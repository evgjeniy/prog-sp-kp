package org.server.models;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birthday=" + birthday +
                ", post='" + post + '\'' +
                ", salary=" + salary +
                '}';
    }
}
