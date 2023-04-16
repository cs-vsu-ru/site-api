package cs.vsu.is.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

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

  @ManyToMany
  @JoinTable(name = "rel_employee__role", joinColumns = @JoinColumn(name = "employee_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
  @JsonIgnoreProperties(value = { "employees" }, allowSetters = true)
  private Set<Role> roles = new HashSet<>();

}
