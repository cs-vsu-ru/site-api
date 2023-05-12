package cs.vsu.is.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Employee.
 */
@Entity
@Table(name = "employee")
@Getter
@Setter
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Employee implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "post")
  private String post;
  @Column(name = "academic_title")
  private String academicTitle;
  @Column(name = "academic_degree")
  private String academicDegree;
  @Column(name = "experience")
  private String experience;
  @Column(name = "professional_experience")
  private String professionalExperience;

  @Column(name = "patronymic")
  private String patronymic;

  @Column(name = "date_of_birth")
  private LocalDate dateOfBirth;

  @OneToOne(optional = false)
  @NotNull
  @JoinColumn(unique = true)
  private User user;

  @OneToMany(mappedBy = "employee")
  @JsonIgnoreProperties(value = { "employee", "accessModes" }, allowSetters = true)
  private Set<Articles> articles = new HashSet<>();

  @OneToMany(mappedBy = "employee")
  @JsonIgnoreProperties(value = { "employee", "accessModes" }, allowSetters = true)
  private Set<Events> events = new HashSet<>();

  @OneToMany(mappedBy = "employee")
  @JsonIgnoreProperties(value = { "subject", "eduSchedulePlace", "schedule", "employee" }, allowSetters = true)
  private Set<Lesson> lessons = new HashSet<>();

  @OneToMany(mappedBy = "employee")
  @JsonIgnoreProperties(value = { "employee", "accessModes" }, allowSetters = true)
  private Set<Pages> pages = new HashSet<>();

  @OneToMany(mappedBy = "employee")
  @JsonIgnoreProperties(value = { "student", "scientificWorkType", "employee" }, allowSetters = true)
  private Set<ScientificLeaderships> scientificLeaderships = new HashSet<>();

  @OneToMany(mappedBy = "employee")
  @JsonIgnoreProperties(value = { "subject", "specialities", "employee" }, allowSetters = true)
  private Set<Teaching> teachings = new HashSet<>();
}
