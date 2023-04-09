package cs.vsu.is.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A Teaching.
 */
@Entity
@Table(name = "teaching")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Teaching implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "roles", "articles", "lessons", "events", "user", "scientificLeaderships", "teachings" },
        allowSetters = true
    )
    private Employee employee;

    @ManyToOne
    @JsonIgnoreProperties(value = { "lessons" }, allowSetters = true)
    private Subject subject;

    @ManyToOne
    private Specialities specialities;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Teaching id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Teaching employee(Employee employee) {
        this.setEmployee(employee);
        return this;
    }

    public Subject getSubject() {
        return this.subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Teaching subject(Subject subject) {
        this.setSubject(subject);
        return this;
    }

    public Specialities getSpecialities() {
        return this.specialities;
    }

    public void setSpecialities(Specialities specialities) {
        this.specialities = specialities;
    }

    public Teaching specialities(Specialities specialities) {
        this.setSpecialities(specialities);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Teaching)) {
            return false;
        }
        return id != null && id.equals(((Teaching) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Teaching{" +
            "id=" + getId() +
            "}";
    }
}
