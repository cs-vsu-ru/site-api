package cs.vsu.is.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Employee.
 */
@Entity
@Table(name = "employee")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "date_of_birth")
    private Instant dateOfBirth;

    @ManyToMany
    @JoinTable(
        name = "rel_employee__role",
        joinColumns = @JoinColumn(name = "employee_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @JsonIgnoreProperties(value = { "employees" }, allowSetters = true)
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "employee")
    @JsonIgnoreProperties(value = { "employee" }, allowSetters = true)
    private Set<Articles> articles = new HashSet<>();

    @OneToMany(mappedBy = "employee")
    @JsonIgnoreProperties(value = { "subjects", "eduSchedulePlaces", "schedules", "employee" }, allowSetters = true)
    private Set<Lesson> lessons = new HashSet<>();

    @OneToMany(mappedBy = "employee")
    @JsonIgnoreProperties(value = { "employee" }, allowSetters = true)
    private Set<Events> events = new HashSet<>();

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @ManyToMany(mappedBy = "employees")
    @JsonIgnoreProperties(value = { "lessons", "employees" }, allowSetters = true)
    private Set<Subject> subjects = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Employee id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPatronymic() {
        return this.patronymic;
    }

    public Employee patronymic(String patronymic) {
        this.setPatronymic(patronymic);
        return this;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Instant getDateOfBirth() {
        return this.dateOfBirth;
    }

    public Employee dateOfBirth(Instant dateOfBirth) {
        this.setDateOfBirth(dateOfBirth);
        return this;
    }

    public void setDateOfBirth(Instant dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Set<Role> getRoles() {
        return this.roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Employee roles(Set<Role> roles) {
        this.setRoles(roles);
        return this;
    }

    public Employee addRole(Role role) {
        this.roles.add(role);
        role.getEmployees().add(this);
        return this;
    }

    public Employee removeRole(Role role) {
        this.roles.remove(role);
        role.getEmployees().remove(this);
        return this;
    }

    public Set<Articles> getArticles() {
        return this.articles;
    }

    public void setArticles(Set<Articles> articles) {
        if (this.articles != null) {
            this.articles.forEach(i -> i.setEmployee(null));
        }
        if (articles != null) {
            articles.forEach(i -> i.setEmployee(this));
        }
        this.articles = articles;
    }

    public Employee articles(Set<Articles> articles) {
        this.setArticles(articles);
        return this;
    }

    public Employee addArticles(Articles articles) {
        this.articles.add(articles);
        articles.setEmployee(this);
        return this;
    }

    public Employee removeArticles(Articles articles) {
        this.articles.remove(articles);
        articles.setEmployee(null);
        return this;
    }

    public Set<Lesson> getLessons() {
        return this.lessons;
    }

    public void setLessons(Set<Lesson> lessons) {
        if (this.lessons != null) {
            this.lessons.forEach(i -> i.setEmployee(null));
        }
        if (lessons != null) {
            lessons.forEach(i -> i.setEmployee(this));
        }
        this.lessons = lessons;
    }

    public Employee lessons(Set<Lesson> lessons) {
        this.setLessons(lessons);
        return this;
    }

    public Employee addLessons(Lesson lesson) {
        this.lessons.add(lesson);
        lesson.setEmployee(this);
        return this;
    }

    public Employee removeLessons(Lesson lesson) {
        this.lessons.remove(lesson);
        lesson.setEmployee(null);
        return this;
    }

    public Set<Events> getEvents() {
        return this.events;
    }

    public void setEvents(Set<Events> events) {
        if (this.events != null) {
            this.events.forEach(i -> i.setEmployee(null));
        }
        if (events != null) {
            events.forEach(i -> i.setEmployee(this));
        }
        this.events = events;
    }

    public Employee events(Set<Events> events) {
        this.setEvents(events);
        return this;
    }

    public Employee addEvents(Events events) {
        this.events.add(events);
        events.setEmployee(this);
        return this;
    }

    public Employee removeEvents(Events events) {
        this.events.remove(events);
        events.setEmployee(null);
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Employee user(User user) {
        this.setUser(user);
        return this;
    }

    public Set<Subject> getSubjects() {
        return this.subjects;
    }

    public void setSubjects(Set<Subject> subjects) {
        if (this.subjects != null) {
            this.subjects.forEach(i -> i.removeEmployees(this));
        }
        if (subjects != null) {
            subjects.forEach(i -> i.addEmployees(this));
        }
        this.subjects = subjects;
    }

    public Employee subjects(Set<Subject> subjects) {
        this.setSubjects(subjects);
        return this;
    }

    public Employee addSubjects(Subject subject) {
        this.subjects.add(subject);
        subject.getEmployees().add(this);
        return this;
    }

    public Employee removeSubjects(Subject subject) {
        this.subjects.remove(subject);
        subject.getEmployees().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employee)) {
            return false;
        }
        return id != null && id.equals(((Employee) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Employee{" +
            "id=" + getId() +
            ", patronymic='" + getPatronymic() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            "}";
    }
}
