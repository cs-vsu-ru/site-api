package cs.vsu.is.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Subject.
 */
@Entity
@Table(name = "subject")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Subject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany
    @JoinTable(
        name = "rel_subject__lesson",
        joinColumns = @JoinColumn(name = "subject_id"),
        inverseJoinColumns = @JoinColumn(name = "lesson_id")
    )
    @JsonIgnoreProperties(value = { "subjects", "eduSchedulePlaces", "schedules", "employee" }, allowSetters = true)
    private Set<Lesson> lessons = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_subject__employees",
        joinColumns = @JoinColumn(name = "subject_id"),
        inverseJoinColumns = @JoinColumn(name = "employees_id")
    )
    @JsonIgnoreProperties(value = { "roles", "articles", "lessons", "events", "user", "subjects" }, allowSetters = true)
    private Set<Employee> employees = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Subject id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Subject name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public Subject description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Lesson> getLessons() {
        return this.lessons;
    }

    public void setLessons(Set<Lesson> lessons) {
        this.lessons = lessons;
    }

    public Subject lessons(Set<Lesson> lessons) {
        this.setLessons(lessons);
        return this;
    }

    public Subject addLesson(Lesson lesson) {
        this.lessons.add(lesson);
        lesson.getSubjects().add(this);
        return this;
    }

    public Subject removeLesson(Lesson lesson) {
        this.lessons.remove(lesson);
        lesson.getSubjects().remove(this);
        return this;
    }

    public Set<Employee> getEmployees() {
        return this.employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public Subject employees(Set<Employee> employees) {
        this.setEmployees(employees);
        return this;
    }

    public Subject addEmployees(Employee employee) {
        this.employees.add(employee);
        employee.getSubjects().add(this);
        return this;
    }

    public Subject removeEmployees(Employee employee) {
        this.employees.remove(employee);
        employee.getSubjects().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Subject)) {
            return false;
        }
        return id != null && id.equals(((Subject) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Subject{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
