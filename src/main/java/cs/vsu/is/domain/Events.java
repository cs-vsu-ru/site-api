package cs.vsu.is.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import javax.persistence.*;

/**
 * A Events.
 */
@Entity
@Getter
@Setter
@Table(name = "events")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Events implements Serializable {

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

    @Column(name = "last_modified_date")
    private String lastModifiedDate;

    @Column(name = "last_modified_time")
    private String lastModifiedTime;

    @Column(name = "start_time")
    private String startTime;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "end_time")
    private String endTime;

    @Column(name = "end_date")
    private String endDate;

    @Column(name = "title")
    private String title;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @ManyToOne
    @JsonIgnoreProperties(value = {"user", "articles", "events", "lessons", "pages", "scientificLeaderships",
        "teachings", "roles"}, allowSetters = true)
    private Employee employee;

    @ManyToOne
    @JsonIgnoreProperties(value = {"articles", "events", "pages"}, allowSetters = true)
    private AccessModes accessModes;

    @PrePersist
    public void prePersist() {
        this.timestamp = LocalDateTime.now();
    }
}
