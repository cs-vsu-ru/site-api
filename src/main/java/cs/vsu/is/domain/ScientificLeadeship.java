package cs.vsu.is.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
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

    @ManyToMany(mappedBy = "scientificLeadeships")
    @JsonIgnoreProperties(value = { "scientificLeadeships" }, allowSetters = true)
    private Set<ScientificWorkType> scientificWorkTypes = new HashSet<>();

    @ManyToMany(mappedBy = "scientificLeadeships")
    @JsonIgnoreProperties(value = { "scientificLeadeships" }, allowSetters = true)
    private Set<Students> students = new HashSet<>();

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

    public Set<ScientificWorkType> getScientificWorkTypes() {
        return this.scientificWorkTypes;
    }

    public void setScientificWorkTypes(Set<ScientificWorkType> scientificWorkTypes) {
        if (this.scientificWorkTypes != null) {
            this.scientificWorkTypes.forEach(i -> i.removeScientificLeadeship(this));
        }
        if (scientificWorkTypes != null) {
            scientificWorkTypes.forEach(i -> i.addScientificLeadeship(this));
        }
        this.scientificWorkTypes = scientificWorkTypes;
    }

    public ScientificLeadeship scientificWorkTypes(Set<ScientificWorkType> scientificWorkTypes) {
        this.setScientificWorkTypes(scientificWorkTypes);
        return this;
    }

    public ScientificLeadeship addScientificWorkType(ScientificWorkType scientificWorkType) {
        this.scientificWorkTypes.add(scientificWorkType);
        scientificWorkType.getScientificLeadeships().add(this);
        return this;
    }

    public ScientificLeadeship removeScientificWorkType(ScientificWorkType scientificWorkType) {
        this.scientificWorkTypes.remove(scientificWorkType);
        scientificWorkType.getScientificLeadeships().remove(this);
        return this;
    }

    public Set<Students> getStudents() {
        return this.students;
    }

    public void setStudents(Set<Students> students) {
        if (this.students != null) {
            this.students.forEach(i -> i.removeScientificLeadeship(this));
        }
        if (students != null) {
            students.forEach(i -> i.addScientificLeadeship(this));
        }
        this.students = students;
    }

    public ScientificLeadeship students(Set<Students> students) {
        this.setStudents(students);
        return this;
    }

    public ScientificLeadeship addStudents(Students students) {
        this.students.add(students);
        students.getScientificLeadeships().add(this);
        return this;
    }

    public ScientificLeadeship removeStudents(Students students) {
        this.students.remove(students);
        students.getScientificLeadeships().remove(this);
        return this;
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
