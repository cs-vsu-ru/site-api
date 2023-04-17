package cs.vsu.is.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A AccessModes.
 */
@Entity
@Getter
@Setter
@Table(name = "access_modes")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AccessModes implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "name")
  private String name;

  @OneToMany(mappedBy = "accessModes")
  @JsonIgnoreProperties(value = { "employee", "accessModes" }, allowSetters = true)
  private Set<Articles> articles = new HashSet<>();

  @OneToMany(mappedBy = "accessModes")
  @JsonIgnoreProperties(value = { "employee", "accessModes" }, allowSetters = true)
  private Set<Events> events = new HashSet<>();

  @OneToMany(mappedBy = "accessModes")
  @JsonIgnoreProperties(value = { "employee", "accessModes" }, allowSetters = true)
  private Set<Pages> pages = new HashSet<>();

}
