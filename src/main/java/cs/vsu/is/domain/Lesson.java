package cs.vsu.is.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A Lesson.
 */
@Entity
@Table(name = "lesson")
@Getter
@Setter
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

  @ManyToOne
  private Subject subject;

  @ManyToOne
  private EduSchedulePlace eduSchedulePlace;

  @ManyToOne
  private Schedule schedule;

  @ManyToOne
  @JsonIgnoreProperties(value = { "user", "articles", "events", "lessons", "pages", "scientificLeaderships",
      "teachings", "roles" }, allowSetters = true)
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

  public Subject getSubject() {
    return this.subject;
  }

  public void setSubject(Subject subject) {
    this.subject = subject;
  }

  public Lesson subject(Subject subject) {
    this.setSubject(subject);
    return this;
  }

  public EduSchedulePlace getEduSchedulePlace() {
    return this.eduSchedulePlace;
  }

  public void setEduSchedulePlace(EduSchedulePlace eduSchedulePlace) {
    this.eduSchedulePlace = eduSchedulePlace;
  }

  public Lesson eduSchedulePlace(EduSchedulePlace eduSchedulePlace) {
    this.setEduSchedulePlace(eduSchedulePlace);
    return this;
  }

  public Schedule getSchedule() {
    return this.schedule;
  }

  public void setSchedule(Schedule schedule) {
    this.schedule = schedule;
  }

  public Lesson schedule(Schedule schedule) {
    this.setSchedule(schedule);
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

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
  // setters here

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
    // see
    // https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
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
