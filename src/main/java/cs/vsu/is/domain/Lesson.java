package cs.vsu.is.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Lesson.
 */
@Entity
@Table(name = "lesson")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Lesson implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "course")
    private Integer course;

    @Column(name = "jhi_group")
    private Integer group;

    @Column(name = "subgroup")
    private Integer subgroup;

    @ManyToMany(mappedBy = "lessons")
    @JsonIgnoreProperties(value = { "lessons" }, allowSetters = true)
    private Set<Subject> subjects = new HashSet<>();

    @ManyToMany(mappedBy = "lessons")
    @JsonIgnoreProperties(value = { "lessons" }, allowSetters = true)
    private Set<EduSchedulePlace> eduSchedulePlaces = new HashSet<>();

    @ManyToMany(mappedBy = "lessons")
    @JsonIgnoreProperties(value = { "lessons" }, allowSetters = true)
    private Set<Schedule> schedules = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "roles", "articles", "lessons", "events", "scientificLeaderships", "teachings", "user", "pages" },
        allowSetters = true
    )
    private Employee employee;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Lesson id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCourse() {
        return this.course;
    }

    public Lesson course(Integer course) {
        this.setCourse(course);
        return this;
    }

    public void setCourse(Integer course) {
        this.course = course;
    }

    public Integer getGroup() {
        return this.group;
    }

    public Lesson group(Integer group) {
        this.setGroup(group);
        return this;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }

    public Integer getSubgroup() {
        return this.subgroup;
    }

    public Lesson subgroup(Integer subgroup) {
        this.setSubgroup(subgroup);
        return this;
    }

    public void setSubgroup(Integer subgroup) {
        this.subgroup = subgroup;
    }

    public Set<Subject> getSubjects() {
        return this.subjects;
    }

    public void setSubjects(Set<Subject> subjects) {
        if (this.subjects != null) {
            this.subjects.forEach(i -> i.removeLesson(this));
        }
        if (subjects != null) {
            subjects.forEach(i -> i.addLesson(this));
        }
        this.subjects = subjects;
    }

    public Lesson subjects(Set<Subject> subjects) {
        this.setSubjects(subjects);
        return this;
    }

    public Lesson addSubject(Subject subject) {
        this.subjects.add(subject);
        subject.getLessons().add(this);
        return this;
    }

    public Lesson removeSubject(Subject subject) {
        this.subjects.remove(subject);
        subject.getLessons().remove(this);
        return this;
    }

    public Set<EduSchedulePlace> getEduSchedulePlaces() {
        return this.eduSchedulePlaces;
    }

    public void setEduSchedulePlaces(Set<EduSchedulePlace> eduSchedulePlaces) {
        if (this.eduSchedulePlaces != null) {
            this.eduSchedulePlaces.forEach(i -> i.removeLesson(this));
        }
        if (eduSchedulePlaces != null) {
            eduSchedulePlaces.forEach(i -> i.addLesson(this));
        }
        this.eduSchedulePlaces = eduSchedulePlaces;
    }

    public Lesson eduSchedulePlaces(Set<EduSchedulePlace> eduSchedulePlaces) {
        this.setEduSchedulePlaces(eduSchedulePlaces);
        return this;
    }

    public Lesson addEduSchedulePlace(EduSchedulePlace eduSchedulePlace) {
        this.eduSchedulePlaces.add(eduSchedulePlace);
        eduSchedulePlace.getLessons().add(this);
        return this;
    }

    public Lesson removeEduSchedulePlace(EduSchedulePlace eduSchedulePlace) {
        this.eduSchedulePlaces.remove(eduSchedulePlace);
        eduSchedulePlace.getLessons().remove(this);
        return this;
    }

    public Set<Schedule> getSchedules() {
        return this.schedules;
    }

    public void setSchedules(Set<Schedule> schedules) {
        if (this.schedules != null) {
            this.schedules.forEach(i -> i.removeLesson(this));
        }
        if (schedules != null) {
            schedules.forEach(i -> i.addLesson(this));
        }
        this.schedules = schedules;
    }

    public Lesson schedules(Set<Schedule> schedules) {
        this.setSchedules(schedules);
        return this;
    }

    public Lesson addSchedule(Schedule schedule) {
        this.schedules.add(schedule);
        schedule.getLessons().add(this);
        return this;
    }

    public Lesson removeSchedule(Schedule schedule) {
        this.schedules.remove(schedule);
        schedule.getLessons().remove(this);
        return this;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Lesson employee(Employee employee) {
        this.setEmployee(employee);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Lesson)) {
            return false;
        }
        return id != null && id.equals(((Lesson) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Lesson{" +
            "id=" + getId() +
            ", course=" + getCourse() +
            ", group=" + getGroup() +
            ", subgroup=" + getSubgroup() +
            "}";
    }
}
