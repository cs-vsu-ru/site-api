package cs.vsu.is.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "events")
public class Events implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "publication_date")
  private Instant publicationDate;

  @Column(name = "content")
  private String content;

  @Column(name = "last_modified_date")
  private Instant lastModifiedDate;

  @Column(name = "start_time")
  private Instant startTime;

  @Column(name = "end_time")
  private Instant endTime;

  @ManyToOne
  @JsonIgnoreProperties(value = { "user", "articles", "events", "lessons", "pages", "scientificLeaderships",
      "teachings", "roles" }, allowSetters = true)
  private Employee employee;

  @ManyToOne
  @JsonIgnoreProperties(value = { "articles", "events", "pages" }, allowSetters = true)
  private AccessModes accessModes;

}
