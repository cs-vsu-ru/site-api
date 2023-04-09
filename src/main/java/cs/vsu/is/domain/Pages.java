package cs.vsu.is.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A Pages.
 */
@Entity
@Table(name = "pages")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Pages implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

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

    public Pages id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return this.content;
    }

    public Pages content(String content) {
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

    public Pages employee(Employee employee) {
        this.setEmployee(employee);
        return this;
    }

    public AccessModes getAccessModes() {
        return this.accessModes;
    }

    public void setAccessModes(AccessModes accessModes) {
        this.accessModes = accessModes;
    }

    public Pages accessModes(AccessModes accessModes) {
        this.setAccessModes(accessModes);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pages)) {
            return false;
        }
        return id != null && id.equals(((Pages) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Pages{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            "}";
    }
}
