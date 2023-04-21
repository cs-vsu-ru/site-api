package cs.vsu.is.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A ScientificLeadeship.
 */
@Entity
@Table(name = "scientific_leadeship")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ScientificLeadeship implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ScientificLeadeship id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ScientificLeadeship)) {
            return false;
        }
        return id != null && id.equals(((ScientificLeadeship) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ScientificLeadeship{" +
            "id=" + getId() +
            "}";
    }
}
