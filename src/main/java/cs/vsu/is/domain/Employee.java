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

  @Column(name = "user_id")
  private Long userId;

  @OneToMany(mappedBy = "employee")
  private Set<Articles> articles;

  @OneToMany(mappedBy = "employee")
  private Set<Lesson> lessons;

  @OneToMany(mappedBy = "employee")
  private Set<Events> events;

  @OneToMany(mappedBy = "employee")
  private Set<Pages> pages;

  @ManyToMany
  @JoinTable(name = "rel_employee__role", joinColumns = @JoinColumn(name = "employee_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
  @JsonIgnoreProperties(value = { "employees" }, allowSetters = true)
  private Set<Role> roles = new HashSet<>();

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

  public Long getUserId() {
    return this.userId;
  }

  public Employee userId(Long userId) {
    this.setUserId(userId);
    return this;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
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

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
  // setters here

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
    // see
    // https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    return getClass().hashCode();
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "Employee{" +
        "id=" + getId() +
        ", patronymic='" + getPatronymic() + "'" +
        ", dateOfBirth='" + getDateOfBirth() + "'" +
        ", userId=" + getUserId() +
        "}";
  }
}
