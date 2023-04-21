package cs.vsu.is.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Pages.
 */
@Entity
@Getter
@Setter
@Table(name = "pages")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Pages implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "content", columnDefinition = "json")
  private String content;

  @Column(name = "title")
  private String title;

  @Column(name = "url")
  private String url;

  @ManyToOne(optional = false)
  @NotNull
  @JsonIgnoreProperties(value = { "user", "articles", "events", "lessons", "pages", "scientificLeaderships",
      "teachings", "roles" }, allowSetters = true)
  private Employee employee;

  @ManyToOne
  @JsonIgnoreProperties(value = { "articles", "events", "pages" }, allowSetters = true)
  private AccessModes accessModes;

}
