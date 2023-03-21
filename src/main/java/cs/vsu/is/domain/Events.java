package cs.vsu.is.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;

/**
 * A Events.
 */
@Entity
@Table(name = "events")
@SuppressWarnings("common-java:DuplicatedBlocks")
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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Events id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getPublicationDate() {
        return this.publicationDate;
    }

    public Events publicationDate(Instant publicationDate) {
        this.setPublicationDate(publicationDate);
        return this;
    }

    public void setPublicationDate(Instant publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getContent() {
        return this.content;
    }

    public Events content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public Events lastModifiedDate(Instant lastModifiedDate) {
        this.setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Instant getStartTime() {
        return this.startTime;
    }

    public Events startTime(Instant startTime) {
        this.setStartTime(startTime);
        return this;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return this.endTime;
    }

    public Events endTime(Instant endTime) {
        this.setEndTime(endTime);
        return this;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Events)) {
            return false;
        }
        return id != null && id.equals(((Events) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Events{" +
            "id=" + getId() +
            ", publicationDate='" + getPublicationDate() + "'" +
            ", content='" + getContent() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            "}";
    }
}
