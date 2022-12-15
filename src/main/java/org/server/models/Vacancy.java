package org.server.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "vacancies")
public class Vacancy implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id @Column private int id;

    @Column private String post;
    @Column private String description;
    @Column private float salary;

    @Column(name = "test_task_description")
    private String testDescription;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "vacancies_candidates",
            joinColumns = @JoinColumn(name = "vacancy_id"),
            inverseJoinColumns = @JoinColumn(name = "candidate_id")
    )
    private Set<Candidate> candidates;

    public Vacancy() {}

    public Vacancy(String post, String description, float salary, String testDescription) {
        this.post = post;
        this.description = description;
        this.salary = salary;
        this.testDescription = testDescription;
    }

    public int getId() { return id; }
    public String getPost() { return post; }
    public String getDescription() { return description; }
    public float getSalary() { return salary; }
    public Set<Candidate> getCandidates() { return candidates; }
    public String getTestDescription() { return testDescription; }

    public void setId(int id) { this.id = id; }
    public void setPost(String post) { this.post = post; }
    public void setDescription(String description) { this.description = description; }
    public void setSalary(float salary) { this.salary = salary; }
    public void setCandidates(Set<Candidate> candidates) { this.candidates = candidates; }
    public void setTestDescription(String testDescription) { this.testDescription = testDescription; }

    @Override
    public String toString() {
        return "Vacancy{id=" + id + ", post='" + post + "', description='" + description +
                "', salary=" + salary + ", candidates=" + candidates + '}';
    }
}