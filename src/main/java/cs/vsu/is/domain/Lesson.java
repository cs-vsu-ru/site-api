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

  @Column(name = "classroom")
  private String classroom;

  @ManyToOne
  @JsonIgnoreProperties(value = { "user", "articles", "events", "lessons", "pages", "scientificLeaderships",
      "teachings", "roles" }, allowSetters = true)
  private Employee employee;

  @Column(name = "subject_name")
  private String subjectName;

}
