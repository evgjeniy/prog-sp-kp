package org.server.models;

import org.server.services.PasswordEncryptionService;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
public class User implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id @Column(name = "user_id") private int id;

    @Column private String login;
    @Column private String password;
    @Column private String salt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public User() {}

    public User(String login, String password) {
        this();
        this.login = login;
        this.salt = PasswordEncryptionService.generateSalt();
        this.password = PasswordEncryptionService.getEncryptedPassword(password, salt);
    }

    public int getId() { return id; }
    public String getLogin() { return login; }
    public String getPassword() { return password; }
    public String getSalt() {
        if (salt == null) salt = PasswordEncryptionService.generateSalt();
        return salt;
    }
    public Role getRole() { return role; }
    public Employee getEmployee() { return employee; }

    public void setId(int id) { this.id = id; }
    public void setLogin(String login) { this.login = login; }
    public void setPassword(String password) {
        this.salt = PasswordEncryptionService.generateSalt();
        this.password = PasswordEncryptionService.getEncryptedPassword(password, salt);
    }
    public void setSalt(String salt) { this.salt = salt; }
    public void setRole(Role role) { this.role = role; }
    public void setEmployee(Employee employee) { this.employee = employee; }

    public String getFullName() { return employee.getFullName(); }

    @Override
    public String toString() {
        return "User{ id=" + id + ", login='" + login + "', password='" + password +
                "', salt='" + salt + "', role=" + role + ", employee=" + employee + '}';
    }
}