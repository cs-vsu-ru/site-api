package cs.vsu.is.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A Students.
 */
@Entity
@Table(name = "students")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Students implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "student_pers_num")
    private String studentPersonalNumber;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Students id(Long id) {
        this.setId(id);
        return this;
    }

    public Students(Long id, String name, String surname, String studentPersonalNumber) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.studentPersonalNumber = studentPersonalNumber;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Students name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }

    public Students surname(String surname) {
        this.setSurname(surname);
        return this;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Students)) {
            return false;
        }
        return id != null && id.equals(((Students) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Students{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", surname='" + getSurname() + "'" +
            ", personalId=" + getStudentPersonalNumber() +
            "}";
    }

    public String getStudentPersonalNumber() {
        return studentPersonalNumber;
    }

    public void setStudentPersonalNumber(String studentPersonalNumber) {
        this.studentPersonalNumber = studentPersonalNumber;
    }
}
