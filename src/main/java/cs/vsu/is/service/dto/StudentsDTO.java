package cs.vsu.is.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link cs.vsu.is.domain.Students} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StudentsDTO implements Serializable {

    private Long id;

    private String name;

    private String surname;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StudentsDTO)) {
            return false;
        }

        StudentsDTO studentsDTO = (StudentsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, studentsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StudentsDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", surname='" + getSurname() + "'" +
            "}";
    }
}
