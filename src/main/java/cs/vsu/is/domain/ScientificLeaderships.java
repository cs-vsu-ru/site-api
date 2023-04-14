package cs.vsu.is.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A ScientificLeaderships.
 */
@Entity
@Table(name = "scientific_leaderships")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ScientificLeaderships implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "year")
    private Integer year;

    @ManyToOne
    private Students student;

    @ManyToOne
    private ScientificWorkType scientificWorkType;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "user", "articles", "events", "lessons", "pages", "scientificLeaderships", "teachings", "roles" },
        allowSetters = true
    )
    private Employee employee;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ScientificLeaderships id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYear() {
        return this.year;
    }

    public ScientificLeaderships year(Integer year) {
        this.setYear(year);
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Students getStudent() {
        return this.student;
    }

    public void setStudent(Students students) {
        this.student = students;
    }

    public ScientificLeaderships student(Students students) {
        this.setStudent(students);
        return this;
    }

    public ScientificWorkType getScientificWorkType() {
        return this.scientificWorkType;
    }

    public void setScientificWorkType(ScientificWorkType scientificWorkType) {
        this.scientificWorkType = scientificWorkType;
    }

    public ScientificLeaderships scientificWorkType(ScientificWorkType scientificWorkType) {
        this.setScientificWorkType(scientificWorkType);
        return this;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public ScientificLeaderships employee(Employee employee) {
        this.setEmployee(employee);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ScientificLeaderships)) {
            return false;
        }
        return id != null && id.equals(((ScientificLeaderships) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ScientificLeaderships{" +
            "id=" + getId() +
            ", year=" + getYear() +
            "}";
    }
}
