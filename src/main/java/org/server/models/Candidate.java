package org.server.models;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "candidates")
public class Candidate implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id @Column private int id;

    @Column private String name;
    @Column private String surname;
    @Column private Date birthday;
    @Column private String mail;
    @Column private String summary;

    @Column(name = "test_task_url")
    private String testTaskUrl;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "candidates")
    private List<Vacancy> vacancies;

    public Candidate() { this.vacancies = new ArrayList<>(); }

    public Candidate(String name, String surname, Date birthday, String mail, String summary, String testTaskUrl) {
        this();
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.mail = mail;
        this.summary = summary;
        this.testTaskUrl = testTaskUrl;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getSurname() { return surname; }
    public Date getBirthday() { return birthday; }
    public String getMail() { return mail; }
    public String getSummary() { return summary; }
    public String getTestTaskUrl() { return testTaskUrl; }
    public List<Vacancy> getVacancies() { return vacancies; }
    public String getFullName() { return surname + " " + name; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setSurname(String surname) { this.surname = surname; }
    public void setBirthday(Date birthday) { this.birthday = birthday; }
    public void setMail(String mail) { this.mail = mail; }
    public void setSummary(String summary) { this.summary = summary; }
    public void setTestTaskUrl(String testTaskUrl) { this.testTaskUrl = testTaskUrl; }
    public void setVacancies(List<Vacancy> vacancies) { this.vacancies = vacancies; }



    @Override
    public String toString() {
        return "Candidate{id=" + id + ", name='" + name + "', surname='" + surname +
                "', birthday=" + birthday + ", mail='" + mail + ",test='" + testTaskUrl + "'}";
    }
}