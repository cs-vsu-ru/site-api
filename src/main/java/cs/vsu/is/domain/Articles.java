package cs.vsu.is.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;

/**
 * A Articles.
 */
@Entity
@Table(name = "articles")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Articles implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "publication_date")
    private Instant publicationDate;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "roles", "articles", "lessons", "events", "user", "scientificLeaderships", "teachings" },
        allowSetters = true
    )
    private Employee employee;

    @ManyToOne
    @JsonIgnoreProperties(value = { "articles", "pages", "events" }, allowSetters = true)
    private AccessModes accessModes;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Articles id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getPublicationDate() {
        return this.publicationDate;
    }

    public Articles publicationDate(Instant publicationDate) {
        this.setPublicationDate(publicationDate);
        return this;
    }

    public void setPublicationDate(Instant publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getContent() {
        return this.content;
    }

    public Articles content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Articles employee(Employee employee) {
        this.setEmployee(employee);
        return this;
    }

    public AccessModes getAccessModes() {
        return this.accessModes;
    }

    public void setAccessModes(AccessModes accessModes) {
        this.accessModes = accessModes;
    }

    public Articles accessModes(AccessModes accessModes) {
        this.setAccessModes(accessModes);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Articles)) {
            return false;
        }
        return id != null && id.equals(((Articles) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Articles{" +
            "id=" + getId() +
            ", publicationDate='" + getPublicationDate() + "'" +
            ", content='" + getContent() + "'" +
            "}";
    }
}
