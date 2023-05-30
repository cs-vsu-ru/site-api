package cs.vsu.is.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

/**
 * A ScientificLeaderships.
 */
@Entity
@Getter
@Setter
@Table(name = "scientific_leaderships")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ScientificLeaderships implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "year")
  private Integer year;

  @ManyToMany
	@JoinTable(name = "rel_students__scientific_leadership", joinColumns = @JoinColumn(name = "scientific_leadership_id"), inverseJoinColumns = @JoinColumn(name = "students_id"))
  private Set<Students> students = new HashSet<>();

  @ManyToOne
  private ScientificWorkType scientificWorkType;

  @ManyToOne
	@JsonIgnoreProperties(value = { "articles", "events", "lessons", "pages",
	"scientificLeaderships", "teachings", "roles", "specialities" }, allowSetters = true)
  private Employee employee;

  @Column(name = "sci_work_name")
  private String sciWorkName;

}
