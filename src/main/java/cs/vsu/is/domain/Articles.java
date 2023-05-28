package cs.vsu.is.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

import javax.persistence.*;

/**
 * A Articles.
 */
@Entity
@Table(name = "articles")
@Getter
@Setter
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Articles implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "publication_date")
  private String publicationDate;

  @Column(name = "publication_time")
  private String publicationTime;

  @Column(name = "content", columnDefinition = "json")
  private String content;

  @Column(name = "title")
  private String title;

  @Column(name = "image_url")
  private String imageURL;

  @ManyToOne(fetch = FetchType.LAZY)
  @JsonIgnoreProperties(value = { "user", "articles", "events", "lessons", "pages", "scientificLeaderships",
      "teachings", "roles" }, allowSetters = true)
  private Employee employee;
  @ManyToOne
  @JsonIgnoreProperties(value = { "articles", "events", "pages" }, allowSetters = true)
  private AccessModes accessModes;

  // jhipster-needle-entity-add-field - JHipster will add fields here

}
