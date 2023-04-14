package cs.vsu.is.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link cs.vsu.is.domain.Teaching} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TeachingDTO implements Serializable {

    private Long id;

    private SubjectDTO subject;

    private SpecialitiesDTO specialities;

    private EmployeeDTO employee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SubjectDTO getSubject() {
        return subject;
    }

    public void setSubject(SubjectDTO subject) {
        this.subject = subject;
    }

    public SpecialitiesDTO getSpecialities() {
        return specialities;
    }

    public void setSpecialities(SpecialitiesDTO specialities) {
        this.specialities = specialities;
    }

    public EmployeeDTO getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDTO employee) {
        this.employee = employee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TeachingDTO)) {
            return false;
        }

        TeachingDTO teachingDTO = (TeachingDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, teachingDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TeachingDTO{" +
            "id=" + getId() +
            ", subject=" + getSubject() +
            ", specialities=" + getSpecialities() +
            ", employee=" + getEmployee() +
            "}";
    }
}
