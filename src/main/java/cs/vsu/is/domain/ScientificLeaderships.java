package cs.vsu.is.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
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

  @ManyToOne
  private Students student;

  @ManyToOne
  private ScientificWorkType scientificWorkType;

  @ManyToOne
  @JsonIgnoreProperties(value = { "user", "articles", "events", "lessons", "pages", "scientificLeaderships",
      "teachings", "roles" }, allowSetters = true)
  private Employee employee;

  @Column(name = "sci_work_name")
  private String sciWorkName;

}
