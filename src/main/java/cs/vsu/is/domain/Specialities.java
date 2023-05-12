package cs.vsu.is.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

/**
 * A Specialities.
 */
@Entity
@Table(name = "specialities")
@Getter
@Setter
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Specialities implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "code")
  private String code;

  @Column(name = "name")
  private String name;

  @ManyToMany
  @JoinTable(name = "specialities_subject", joinColumns = @JoinColumn(name = "specialities_id"), inverseJoinColumns = @JoinColumn(name = "subject_id"))
  private Set<Subject> subjects = new HashSet<>();
}
